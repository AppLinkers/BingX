package AppLinkers.BingX.common.repository;

import AppLinkers.BingX.common.domain.Guide;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuideRepository extends JpaRepository<Guide, Long> {

    List<Guide> findTop3ByOrderByCreatedAtDesc();

    List<Guide> findAllByOrderByCreatedAtDesc();
}
