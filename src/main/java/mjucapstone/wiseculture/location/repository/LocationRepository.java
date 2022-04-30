package mjucapstone.wiseculture.location.repository;

import mjucapstone.wiseculture.location.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
