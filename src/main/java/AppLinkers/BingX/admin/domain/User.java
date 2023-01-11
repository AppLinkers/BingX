package AppLinkers.BingX.admin.domain;

import AppLinkers.BingX.admin.domain.type.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String loginPw;

    @Column(nullable = false)
    private String teamName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(Long id, String loginId, String loginPw, String teamName, Role role) {
        this.id = id;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.teamName = teamName;
        this.role = role;
    }

}

