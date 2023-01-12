package AppLinkers.BingX.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadAdvertisementInfoRes {

    private Long id;

    private String linkUrl;

    private String title;

    private String imgUrl;
}
