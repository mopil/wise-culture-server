package mjucapstone.wiseculture.location.config;

public class AreaCode {
    public static final int 서울 = 1;
    public static final int 경기도 = 31; 
    public static final int 강원도 = 32; 
    public static final int 충청북도 = 33; 
    
    // 나머지 추가 예정

    public static String getAreaName(Long areaCode) {
        if (areaCode == AreaCode.서울) return "서울";
        else if (areaCode == AreaCode.경기도) return "경기도";
        else if (areaCode == AreaCode.강원도) return "강원도";
        else if (areaCode == AreaCode.충청북도) return "충청북도";
        else return "기타";
    }

}
