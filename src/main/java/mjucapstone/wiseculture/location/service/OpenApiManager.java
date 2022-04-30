package mjucapstone.wiseculture.location.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjucapstone.wiseculture.location.config.ApiList;
import mjucapstone.wiseculture.location.config.AreaCode;
import mjucapstone.wiseculture.location.domain.Location;
import mjucapstone.wiseculture.location.dto.OpenApiDto;
import mjucapstone.wiseculture.location.repository.LocationRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OpenApiManager {

    private final LocationRepository locationRepository;

    String ENDPOINT = "http://api.visitkorea.or.kr/openapi/service/rest/KorService";
    String SERVICE_KEY = "?ServiceKey=p/YqHmd4Vr0146ztMaJuM9vCY1WHPTaLCoOXuiKgvBEJkbOYwk/CECUtDuxq7qU55eGYhIEhLj6gVlDAg2yjoQ==";
    String DEFAULT_QUERY_PARAMS = "&MobileOS=ETC&MobileApp=AppTest&_type=json";

    @Transactional
    public void fetchAllByArea(int areaCode) {
        log.info("수행됨1");
        List<OpenApiDto> dtoList = fetch(makeUrl(ApiList.AREA_BASED, 1000, areaCode));
        log.info("수행됨2");
        List<Location> target = new ArrayList<>();
        dtoList.forEach(i -> target.add(i.toEntity()));
        log.info("수행됨3");
        locationRepository.saveAll(target);
    }

    public String makeUrl(String baseApi, int numOfRows, int areaCode, int contentTypeId) {
        return makeBaseUrl(baseApi) +
                numOfRows(numOfRows) +
                areaCode(areaCode) +
                contentTypeId(contentTypeId);
    }

    public String makeUrl(String baseApi, int numOfRows, int areaCode) {
        return makeBaseUrl(baseApi) +
                numOfRows(numOfRows) +
                areaCode(areaCode);
    }

    // 기본 URL 만들기
    public String makeBaseUrl(String apiUriType) {
        return ENDPOINT + apiUriType + SERVICE_KEY + DEFAULT_QUERY_PARAMS;
    }

    // 지역 코드 입력
    public String areaCode(int code) {
        return "&areaCode=" + code;
    }

    // 가져올 데이터 수 정하기
    public String numOfRows(int n) {
        if (n < 0 || n > 10000) {
            // 범위를 넘어가면 디폴트값 내리기
            return "&numOfRows=10";
        }
        return "&numOfRows=" + n;
    }


    // 컨텐츠 타입 지정하기
    public String contentTypeId(int code) {
        return "&contentTypeId=" + code;
    }

    // 오픈 API 서버로부터 데이터 받아오기
    public List<OpenApiDto> fetch(String url) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String jsonString = restTemplate.getForObject(url, String.class);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
            // 가장 큰 JSON 객체 response 가져오기
            JSONObject jsonResponse = (JSONObject) jsonObject.get("response");

            // 그 다음 body 부분 파싱
            JSONObject jsonBody = (JSONObject) jsonResponse.get("body");

            // 그 다음 위치 정보를 배열로 담은 items 파싱
            JSONObject jsonItems = (JSONObject) jsonBody.get("items");

            // items는 JSON임, 이제 그걸 또 배열로 가져온다
            JSONArray jsonItemList = (JSONArray) jsonItems.get("item");

            List<OpenApiDto> result = new ArrayList<>();

            for (Object o : jsonItemList) {
                JSONObject item = (JSONObject) o;
                OpenApiDto dto = makeLocationDto(item);
                if (dto == null) {
                    continue;
                }
                result.add(dto);
                log.info("{}", makeLocationDto(item));
            }
            log.info("fetch완료");
            return result;
        } catch (ParseException e) {
            return null;
        }
    }

    // 콘텐츠 정보 JSON을 DTO로 변환
    private OpenApiDto makeLocationDto(JSONObject item) {
        // 가끔 좌표 데이터가 타입이 다른경우가 있음... 미친넘들 진짜 개팰까
        if (item.get("mapx") instanceof String || item.get("mapy") instanceof String
                || item.get("addr1") == null || item.get("firstimage") == null) {
            return null;
        }
        return OpenApiDto.builder().
                title((String) item.get("title")).
                address((String) item.get("addr1")).
                areaCode((Long) item.get("areacode")).
                contentTypeId((Long) item.get("contenttypeid")).
                firstImage((String) item.get("firstimage")).
                mapX((double) item.get("mapx")).
                mapY((double) item.get("mapy")).
                build();
    }

    // 테스트용
    public Object fetchForTest() throws ParseException {
        RestTemplate restTemplate = new RestTemplate();
        String jsonString = restTemplate.getForObject(makeUrl(ApiList.AREA_BASED, 30, AreaCode.서울), String.class);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
        return (JSONObject) jsonObject.get("response");
    }


}
