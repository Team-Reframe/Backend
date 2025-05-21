package backend.receipt.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String userEmail;
    private String userId;
    private String userPassword;
    private String userName;

    public Member(String userEmail, String userId, String userPassword, String userName) {
        this.userEmail = userEmail;
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
    }

}
