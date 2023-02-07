package AppLinkers.BingX.common.repository;

import AppLinkers.BingX.common.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findTop3ByOrderByCreatedAtDesc();

    List<Event> findAllByOrderByCreatedAtDesc();
}
