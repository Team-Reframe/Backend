package backend.receipt.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRequest {
    private String userEmail;
    private String userId;
    private String userPassword;
    private String confirmPassword;
    private String userName;

}
