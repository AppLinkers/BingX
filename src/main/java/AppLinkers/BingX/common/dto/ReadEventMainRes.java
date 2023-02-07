package AppLinkers.BingX.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Builder
public class ReadEventMainRes {

    private Long id;

    private String title;

    private String content;

    private String writerName;

    private String imgUrl;

    private LocalDate createdAt;

    private Integer viewCnt;
}
