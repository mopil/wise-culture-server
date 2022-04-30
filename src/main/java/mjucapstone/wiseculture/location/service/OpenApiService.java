package mjucapstone.wiseculture.location.service;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.location.config.AreaCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OpenApiService {

    private final OpenApiManager openApiManager;

    @Transactional
    public void fetchAllAreas() {
        openApiManager.fetchAllByArea(AreaCode.서울);
//        openApiManager.fetchAllByArea(AreaCode.강원도);
//        openApiManager.fetchAllByArea(AreaCode.경기도);
//        openApiManager.fetchAllByArea(AreaCode.충청북도);

    }
}
