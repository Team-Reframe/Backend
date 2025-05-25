package backend.receipt.member.controller;


import backend.receipt.member.domain.Member;
import backend.receipt.member.dto.request.MemberRequest;
import backend.receipt.member.dto.response.MemberResponse;
import backend.receipt.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/member")
@Tag(name = "Member", description = "사용자 API")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/sign-up")
    public ResponseEntity<MemberResponse> signUp(@RequestBody MemberRequest request) {
        MemberResponse memberResponse= memberService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberResponse);
    }
}
