package backend.receipt.member.controller;


import backend.receipt.member.domain.Member;
import backend.receipt.member.dto.request.MemberRequest;
import backend.receipt.member.dto.response.MemberResponse;
import backend.receipt.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody MemberRequest request) {
        try {
            Member member = memberService.register(request);
            return ResponseEntity
                    .status(201)
                    .body(new MemberResponse(member));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MemberResponse("회원가입 실패")); // 실패 응답
        }
    }
}
