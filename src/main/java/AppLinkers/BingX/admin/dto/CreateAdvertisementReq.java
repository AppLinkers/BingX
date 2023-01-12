package AppLinkers.BingX.admin.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CreateAdvertisementReq {

    private String title;

    private String linkUrl;

    private MultipartFile imgFile;
}
