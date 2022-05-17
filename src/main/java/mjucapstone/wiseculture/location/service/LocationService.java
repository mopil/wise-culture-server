package mjucapstone.wiseculture.location.service;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.location.domain.Location;
import mjucapstone.wiseculture.location.dto.LocationListResponse;
import mjucapstone.wiseculture.location.dto.LocationResponse;
import mjucapstone.wiseculture.location.repository.LocationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationService {

    /**
     * 오픈 API 서버에서 데이터를 가져온 데이터를 스프링 서버 DB에 로케이션 정보를 저장하는 역할까지만 수행
     */

    private final int DEFAULT_NUM_OF_ROWS = 100;
    private final int DEFAULT_RADIUS = 1000;

    private final OpenApiManager openApiManager;
    private final LocationRepository locationRepository;

    private LocationListResponse makeResponseList(List<Location> locations) {
        List<LocationResponse> result = new ArrayList<>();
        locations.forEach(l -> result.add(l.toResponse()));
        return new LocationListResponse(result);
    }

    // 현재 위치 기반 조회
    public LocationListResponse findAllByPosition(double mapX, double mapY) {
        List<Location> locations = openApiManager.fetchByPosition(mapX, mapY, DEFAULT_RADIUS, DEFAULT_NUM_OF_ROWS);
        return makeResponseList(locations);
    }

    // 지역 기반 조회
    public LocationListResponse findAllByArea(int areaCode) {
        List<Location> locations = openApiManager.fetchByAreaCode(areaCode, DEFAULT_NUM_OF_ROWS);
        return makeResponseList(locations);
    }

    // 문화 기반 조회
    public LocationListResponse findAllByContent(int contentTypeId) {
        List<Location> locations = openApiManager.fetchByContent(contentTypeId, DEFAULT_NUM_OF_ROWS);
        return makeResponseList(locations);
    }

    // 지역 + 문화 기반 조회
    public LocationListResponse findAllByAreaContent(int areaCode, int contentTypeId) {
        List<Location> locations = openApiManager.fetchByAreaContent(areaCode, contentTypeId, DEFAULT_NUM_OF_ROWS);
        return makeResponseList(locations);
    }

}
