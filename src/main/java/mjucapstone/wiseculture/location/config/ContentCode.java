package mjucapstone.wiseculture.location.config;

public class ContentCode {
    public static final int 관광지 = 12;
    public static final int 문화시설 = 14;
    public static final int 행사_공연_축제 = 15;
    
    // 나머지 추가 예정

    public static String getContentName(Long contentCode) {
        if (contentCode == ContentCode.관광지) return "관광지";
        else if (contentCode == ContentCode.문화시설) return "문화시설";
        else if (contentCode == ContentCode.행사_공연_축제) return "행사/공연/축제";
        else return "기타";
    }
}
