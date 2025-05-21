package backend.receipt.member.dto.response;

import backend.receipt.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberResponse {

    private String status;
    private String message;


    public MemberResponse(Member member) {
        this.status = "201";
        this.message = "회원가입 성공";
    }


    public MemberResponse(String message) {
        this.status = "400";
        this.message = message;
    }

}
