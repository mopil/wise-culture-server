package mjucapstone.wiseculture.location.config;

// 오픈 API 를 사용할 때 필요한 정보를 담아놓음
public class OpenApiConst {
    public static final String ENDPOINT = "http://api.visitkorea.or.kr/openapi/service/rest/KorService";
    public static final String SERVICE_KEY = "?ServiceKey=p/YqHmd4Vr0146ztMaJuM9vCY1WHPTaLCoOXuiKgvBEJkbOYwk/CECUtDuxq7qU55eGYhIEhLj6gVlDAg2yjoQ==";
    public static final String DEFAULT_QUERY_PARAMS = "&MobileOS=ETC&MobileApp=AppTest&_type=json";
    public static final String SORT_BY_POPULAR = "&arrange=B"; // 가장 인기 있는(조회수가 가장 많은) 게시물만 가져오기
}
