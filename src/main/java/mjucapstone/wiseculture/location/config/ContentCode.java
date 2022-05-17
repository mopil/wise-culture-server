package mjucapstone.wiseculture.location.config;

public class ContentCode {
    public static final int 관광지 = 12;
    public static final int 문화시설 = 14;
    public static final int 행사_공연_축제 = 15;
    public static final int 여행코스 = 25;
    public static final int 레포츠 = 28;
    public static final int 숙박 = 32;
    public static final int 쇼핑 = 38;
    public static final int 음식점 = 39;

    public static String getContentName(Long contentCode) {
        if (contentCode == ContentCode.관광지) return "관광지";
        else if (contentCode == ContentCode.문화시설) return "문화시설";
        else if (contentCode == ContentCode.행사_공연_축제) return "행사/공연/축제";
        else if (contentCode == ContentCode.여행코스) return "여행코스";
        else if (contentCode == ContentCode.레포츠) return "레포츠";
        else if (contentCode == ContentCode.숙박) return "숙박";
        else if (contentCode == ContentCode.쇼핑) return "쇼핑";
        else if (contentCode == ContentCode.음식점) return "음식점";
        else return "기타";
    }
}
