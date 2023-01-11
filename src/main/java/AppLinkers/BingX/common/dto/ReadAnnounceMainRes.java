package AppLinkers.BingX.common.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ReadAnnounceMainRes {

    private Long id;

    private String title;

    private String writerName;

    private LocalDate createdAt;
}
