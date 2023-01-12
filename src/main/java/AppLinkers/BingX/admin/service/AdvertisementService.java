package AppLinkers.BingX.admin.service;

import AppLinkers.BingX.admin.dto.CreateAdvertisementReq;
import AppLinkers.BingX.common.domain.Advertisement;
import AppLinkers.BingX.common.dto.ReadAdvertisementInfoRes;
import AppLinkers.BingX.common.dto.ReadAdvertisementMainRes;
import AppLinkers.BingX.common.repository.AdvertisementRepository;
import AppLinkers.BingX.common.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final S3Service s3Service;

    /**
     * 작성
     */
    @Transactional
    public void createAdvertisement(CreateAdvertisementReq request) throws IOException {

        String imgUrl = "";
        if (!request.getImgFile().isEmpty()) {
            s3Service.upload(request.getImgFile(), "advertisement");
        }

        advertisementRepository.save(
                Advertisement.builder()
                        .title(request.getTitle())
                        .linkUrl(request.getLinkUrl())
                        .imgUrl(imgUrl)
                        .build()
        );

    }

    /**
     * 목록 - Main Page
     */
    @Transactional
    public List<ReadAdvertisementMainRes> readAdvertisementMainList() {
        List<ReadAdvertisementMainRes> result = new ArrayList<>();

        for (Advertisement advertisement : advertisementRepository.findAll()) {
            result.add(
                    ReadAdvertisementMainRes.builder()
                            .linkUrl(advertisement.getLinkUrl())
                            .imgUrl(advertisement.getImgUrl())
                            .build()
            );
        }

        return result;
    }

    /**
     * 목록
     */
    @Transactional
    public Page<ReadAdvertisementInfoRes> readAdvertisementList(Pageable pageable) {
        List<ReadAdvertisementInfoRes> result = new ArrayList<>();

        for (Advertisement advertisement : advertisementRepository.findAll()) {
            result.add(
                    ReadAdvertisementInfoRes.builder()
                            .id(advertisement.getId())
                            .title(advertisement.getTitle())
                            .linkUrl(advertisement.getLinkUrl())
                            .imgUrl(advertisement.getImgUrl())
                            .build()
            );
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), result.size());
        return new PageImpl<>(result.subList(start, end), pageable, result.size());

    }

    /**
     * 삭제
     */
    @Transactional
    public void deleteAdvertisement(Long advertisementId) {
        advertisementRepository.deleteById(advertisementId);
    }
}
