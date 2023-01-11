package AppLinkers.BingX.common.repository;

import AppLinkers.BingX.common.domain.Announce;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnounceRepository extends JpaRepository<Announce, Long> {

    List<Announce> findTop6ByOrderByCreatedAtDesc();

    List<Announce> findAllByOrderByCreatedAtDesc();
}
