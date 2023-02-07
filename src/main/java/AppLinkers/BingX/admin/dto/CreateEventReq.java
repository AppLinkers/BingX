package AppLinkers.BingX.admin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
@Builder
public class CreateEventReq {

    private String title;

    private String content;

    private MultipartFile imgFile;
}
