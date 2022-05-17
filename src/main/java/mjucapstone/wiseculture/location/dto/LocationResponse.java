package mjucapstone.wiseculture.location.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LocationResponse {

    /**
     * 서버에서 안드로이드 클라이언트로 내려주는 로케이션 
     * OpenApiDto 랑 차이점 = areaCode 와 contentTypeId 를 문자열로 변환해서 보기좋게 내려줌
     */
    private String title;
    private String address;
    private String areaName;
    private String contentName;
    private String firstImage;
    private double mapX;
    private double mapY;
}
