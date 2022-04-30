package mjucapstone.wiseculture.location.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjucapstone.wiseculture.location.service.OpenApiManager;
import mjucapstone.wiseculture.location.service.OpenApiService;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static mjucapstone.wiseculture.util.dto.RestResponse.success;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OpenApiController {
    private final OpenApiService openApiService;
    private final OpenApiManager openApiManager;

    @GetMapping("open-api")
    public ResponseEntity<?> setData() {
        openApiService.fetchAllAreas();
        return success(true);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() throws ParseException {
        return success(openApiManager.fetchForTest());
    }

}
