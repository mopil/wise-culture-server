package mjucapstone.wiseculture.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LocationListResponse {
    private List<LocationResponse> locations;
}
