package AppLinkers.BingX.admin.service;

import AppLinkers.BingX.admin.domain.User;
import AppLinkers.BingX.admin.dto.CreateAnnounceReq;
import AppLinkers.BingX.admin.repository.UserRepository;
import AppLinkers.BingX.common.domain.Announce;
import AppLinkers.BingX.common.dto.ReadAnnounceDetailRes;
import AppLinkers.BingX.common.dto.ReadAnnounceInfoRes;
import AppLinkers.BingX.common.dto.ReadAnnounceMainRes;
import AppLinkers.BingX.common.repository.AnnounceRepository;
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
public class AnnounceService {

    private final AnnounceRepository announceRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;

    /**
     * 작성
     */
    @Transactional
    public void createAnnounce(CreateAnnounceReq request, String userLoginId) throws IOException {
        User user = userRepository.findUserByLoginId(userLoginId).get();

        String imgUrl = "https://bingx-image.s3.ap-northeast-2.amazonaws.com/bingx.png";
        if (!request.getImgFile().isEmpty()) {
            imgUrl=s3Service.upload(request.getImgFile(), "announce/thumbnail");
        }

        announceRepository.save(
                Announce.builder()
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
    public List<ReadAnnounceMainRes> readAnnounceMainList() {
        List<ReadAnnounceMainRes> result = new ArrayList<>();

        for (Announce announce : announceRepository.findTop6ByOrderByCreatedAtDesc()) {
            result.add(
                    ReadAnnounceMainRes.builder()
                            .id(announce.getId())
                            .title(announce.getTitle())
                            .writerName(announce.getUser().getTeamName())
                            .createdAt(announce.getCreatedAt().toLocalDate())
                            .build()
            );
        }

        return result;
    }

    /**
     * 목록
     */
    @Transactional
    public Page<ReadAnnounceInfoRes> readAnnounceList(Pageable pageable) {
        List<ReadAnnounceInfoRes> result = new ArrayList<>();

        for (Announce announce : announceRepository.findAllByOrderByCreatedAtDesc()) {
            result.add(
                    ReadAnnounceInfoRes.builder()
                            .id(announce.getId())
                            .title(announce.getTitle())
                            .writerName(announce.getUser().getTeamName())
                            .imgUrl(announce.getImgUrl())
                            .createdAt(announce.getCreatedAt().toLocalDate())
                            .viewCnt(announce.getViewCnt())
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
    public ReadAnnounceDetailRes readAnnounce(Long announceId) {

        Announce announce = announceRepository.findById(announceId).get();
        announce.setViewCnt(announce.getViewCnt() + 1);

        return ReadAnnounceDetailRes.builder()
                .id(announce.getId())
                .title(announce.getTitle())
                .content(announce.getContent())
                .writerName(announce.getUser().getTeamName())
                .imgUrl(announce.getImgUrl())
                .createdAt(announce.getCreatedAt().toLocalDate())
                .viewCnt(announce.getViewCnt())
                .build();

    }

    /**
     * 삭제
     */
    @Transactional
    public void deleteAnnounce(Long announceId) {
        announceRepository.deleteById(announceId);
    }
}
