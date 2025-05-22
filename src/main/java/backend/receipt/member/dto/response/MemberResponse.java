package backend.receipt.member.dto.response;

import backend.receipt.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberResponse {

    private final String email;
    private final String name;

    public MemberResponse(Member member) {
        this.email = member.getEmail();
        this.name = member.getName();
    }

}
