package AppLinkers.BingX.admin.service;

import AppLinkers.BingX.admin.domain.User;
import AppLinkers.BingX.admin.dto.CreateGuideReq;
import AppLinkers.BingX.admin.repository.UserRepository;
import AppLinkers.BingX.common.domain.Guide;
import AppLinkers.BingX.common.dto.ReadEventInfoRes;
import AppLinkers.BingX.common.dto.ReadGuideDetailRes;
import AppLinkers.BingX.common.dto.ReadGuideInfoRes;
import AppLinkers.BingX.common.dto.ReadGuideMainRes;
import AppLinkers.BingX.common.repository.GuideRepository;
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
public class GuideService {

    private final GuideRepository guideRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;

    /**
     * 작성
     */
    @Transactional
    public void createGuide(CreateGuideReq request, String userLoginId) throws IOException {
        User user = userRepository.findUserByLoginId(userLoginId).get();

        String imgUrl = "https://bingx-image.s3.ap-northeast-2.amazonaws.com/bingx.png";
        if (!request.getImgFile().isEmpty()) {
            imgUrl = s3Service.upload(request.getImgFile(), "guide/thumbnail");
        }

        guideRepository.save(
                Guide.builder()
                        .title(request.getTitle())
                        .content(request.getContent())
                        .imgUrl(imgUrl)
                        .user(user)
                        .build()
        );

    }

    /**
     * 목록 - Main Page
     */
    @Transactional
    public List<ReadGuideMainRes> readGuideMainList() {
        List<ReadGuideMainRes> result = new ArrayList<>();

        for (Guide guide : guideRepository.findTop3ByOrderByCreatedAtDesc()) {
            result.add(
                    ReadGuideMainRes.builder()
                            .id(guide.getId())
                            .title(guide.getTitle())
                            .content(guide.getContent().replaceAll("<[^>]*>", "").replace("&nbsp;",""))
                            .writerName(guide.getUser().getTeamName())
                            .imgUrl(guide.getImgUrl())
                            .createdAt(guide.getCreatedAt().toLocalDate())
                            .viewCnt(guide.getViewCnt())
                            .build()
            );
        }

        return result;
    }

    /**
     * 목록
     */
    @Transactional
    public Page<ReadGuideInfoRes> readGuideList(Pageable pageable) {
        List<ReadGuideInfoRes> result = new ArrayList<>();

        for (Guide guide : guideRepository.findAllByOrderByCreatedAtDesc()) {
            result.add(
                    ReadGuideInfoRes.builder()
                            .id(guide.getId())
                            .title(guide.getTitle())
                            .writerName(guide.getUser().getTeamName())
                            .imgUrl(guide.getImgUrl())
                            .createdAt(guide.getCreatedAt().toLocalDate())
                            .viewCnt(guide.getViewCnt())
                            .build()
            );
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), result.size());
        return new PageImpl<>(result.subList(start, end), pageable, result.size());

    }

    /**
     * 조회
     */
    @Transactional
    public ReadGuideDetailRes readGuide(Long guideId) {

        Guide guide = guideRepository.findById(guideId).get();
        guide.setViewCnt(guide.getViewCnt() + 1);

        return ReadGuideDetailRes.builder()
                .id(guide.getId())
                .title(guide.getTitle())
                .content(guide.getContent())
                .writerName(guide.getUser().getTeamName())
                .imgUrl(guide.getImgUrl())
                .createdAt(guide.getCreatedAt().toLocalDate())
                .viewCnt(guide.getViewCnt())
                .build();

    }

    /**
     * 삭제
     */
    @Transactional
    public void deleteGuide(Long guideId) {
        guideRepository.deleteById(guideId);
    }

}
