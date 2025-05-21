package backend.receipt.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRequest {
    private String email;
    private String password;
    private String confirmPassword;
    private String name;
}
