package AppLinkers.BingX.common.repository;

import AppLinkers.BingX.common.domain.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
}
