package AppLinkers.BingX.admin.service;


import AppLinkers.BingX.admin.domain.User;
import AppLinkers.BingX.admin.dto.CreateGuideReq;
import AppLinkers.BingX.admin.repository.UserRepository;
import AppLinkers.BingX.common.domain.Event;
import AppLinkers.BingX.common.domain.Guide;
import AppLinkers.BingX.common.dto.*;
import AppLinkers.BingX.common.repository.EventRepository;
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
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;

    @Transactional
    public void createEvent(CreateGuideReq request, String userLoginId) throws IOException {
        User user = userRepository.findUserByLoginId(userLoginId).get();

        String imgUrl = "https://bingx-image.s3.ap-northeast-2.amazonaws.com/bingx.png";
        if (!request.getImgFile().isEmpty()) {
            imgUrl = s3Service.upload(request.getImgFile(), "event/thumbnail");
        }

        eventRepository.save(
                Event.builder()
                        .title(request.getTitle())
                        .content(request.getContent())
                        .imgUrl(imgUrl)
                        .user(user)
                        .build()
        );

    }


    @Transactional
    public List<ReadEventMainRes> readEventMainRes() {
        List<ReadEventMainRes> result = new ArrayList<>();

        for (Event event : eventRepository.findTop3ByOrderByCreatedAtDesc()) {
            result.add(
                    ReadEventMainRes.builder()
                            .id(event.getId())
                            .title(event.getTitle())
                            .content(event.getContent().replaceAll("<[^>]*>", "").replace("&nbsp;",""))
                            .writerName(event.getUser().getTeamName())
                            .imgUrl(event.getImgUrl())
                            .createdAt(event.getCreatedAt().toLocalDate())
                            .viewCnt(event.getViewCnt())
                            .build()
            );
        }

        return result;
    }



    /**
     * 목록
     */
    @Transactional
    public Page<ReadEventInfoRes> readEventList(Pageable pageable) {
        List<ReadEventInfoRes> result = new ArrayList<>();

        for (Event event : eventRepository.findAllByOrderByCreatedAtDesc()) {
            result.add(
                    ReadEventInfoRes.builder()
                            .id(event.getId())
                            .title(event.getTitle())
                            .writerName(event.getUser().getTeamName())
                            .imgUrl(event.getImgUrl())
                            .createdAt(event.getCreatedAt().toLocalDate())
                            .viewCnt(event.getViewCnt())
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
    public ReadEventDetailRes readEvent(Long eventId) {

        Event event = eventRepository.findById(eventId).get();
        event.setViewCnt(event.getViewCnt() + 1);

        return ReadEventDetailRes.builder()
                .id(event.getId())
                .title(event.getTitle())
                .content(event.getContent())
                .writerName(event.getUser().getTeamName())
                .imgUrl(event.getImgUrl())
                .createdAt(event.getCreatedAt().toLocalDate())
                .viewCnt(event.getViewCnt())
                .build();

        }

    /**
     * 삭제
     */
    @Transactional
    public void deleteEvent(Long guideId) {
        eventRepository.deleteById(guideId);
    }

        }
