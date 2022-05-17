package mjucapstone.wiseculture.location.config;

public class AreaCode {
    public static final int 서울 = 1;
    public static final int 경기도 = 31; 
    public static final int 강원도 = 32; 
    public static final int 충청북도 = 33;
    public static final int 충청남도 = 34;
    public static final int 경상북도 = 35;
    public static final int 경상남도 = 36;
    public static final int 전라북도 = 37;
    public static final int 전라남도 = 38;
    public static final int 제주도 = 39;

    public static String getAreaName(Long areaCode) {
        if (areaCode == AreaCode.서울) return "서울";
        else if (areaCode == AreaCode.경기도) return "경기도";
        else if (areaCode == AreaCode.강원도) return "강원도";
        else if (areaCode == AreaCode.충청북도) return "충청북도";
        else if (areaCode == AreaCode.충청남도) return "충청남도";
        else if (areaCode == AreaCode.경상북도) return "경상북도";
        else if (areaCode == AreaCode.경상남도) return "경상남도";
        else if (areaCode == AreaCode.전라북도) return "전라북도";
        else if (areaCode == AreaCode.전라남도) return "전라남도";
        else if (areaCode == AreaCode.제주도) return "제주도";
        else return "기타";
    }

}
