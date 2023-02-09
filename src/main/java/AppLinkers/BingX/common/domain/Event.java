package AppLinkers.BingX.common.domain;


import AppLinkers.BingX.admin.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String imgUrl;

    @Column(nullable = false)
    private Integer viewCnt;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Event(String title, String content, String imgUrl, User user) {
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
        this.viewCnt = 0;
        this.user = user;
    }

    public void addViewCnt() {
        this.viewCnt += 1;
    }
}
