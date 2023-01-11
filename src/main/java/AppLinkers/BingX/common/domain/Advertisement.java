package AppLinkers.BingX.common.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String imgUrl;

    @Builder
    public Advertisement(String title, String imgUrl) {
        this.title = title;
        this.imgUrl = imgUrl;
    }
}
