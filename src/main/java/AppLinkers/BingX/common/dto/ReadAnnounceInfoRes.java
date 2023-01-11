package AppLinkers.BingX.common.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ReadAnnounceInfoRes {

    private Long id;

    private String title;

    private String writerName;

    private String imgUrl;

    private LocalDate createdAt;

    private Integer viewCnt;
}
