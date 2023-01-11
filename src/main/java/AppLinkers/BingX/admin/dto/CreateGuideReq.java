package AppLinkers.BingX.admin.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CreateGuideReq {

    private String title;

    private String content;

    private MultipartFile imgFile;
}

