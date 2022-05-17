package mjucapstone.wiseculture.location.controller;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.location.dto.LocationListResponse;
import mjucapstone.wiseculture.location.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static mjucapstone.wiseculture.util.dto.RestResponse.success;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    // 현재 위치 기반으로 검색
    // mapX와 mapY를 쿼리 파라미터로 넘길때 무조건 소수점을 넣어서 넘겨야 인식함 (매우 중요)
    @GetMapping("/position/{mapX}/{mapY}")
    public ResponseEntity<?> findAllByPosition(@PathVariable double mapX, @PathVariable double mapY) {
        LocationListResponse allByPosition = locationService.findAllByPosition(mapX, mapY);
        return success(allByPosition);
    }

    // 문화 기반 조회
    @GetMapping("/content/{contentTypeId}")
    public ResponseEntity<?> findAllByContent(@PathVariable int contentTypeId) {
        LocationListResponse allByContent = locationService.findAllByContent(contentTypeId);
        return success(allByContent);
    }

    // 지역 기반 조회
    @GetMapping("/area/{areaCode}")
    public ResponseEntity<?> findAllByArea(@PathVariable int areaCode) {
        LocationListResponse allByPosition = locationService.findAllByArea(areaCode);
        return success(allByPosition);
    }

    // 지역 + 문화 기반 조회
    @GetMapping("/area/{areaCode}/content/{contentTypeId}")
    public ResponseEntity<?> findAllByAreaContent(@PathVariable int areaCode, @PathVariable int contentTypeId) {
        LocationListResponse allByPosition = locationService.findAllByAreaContent(areaCode, contentTypeId);
        return success(allByPosition);
    }
}
