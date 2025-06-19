package backend.receipt.member.controller;


import backend.receipt.member.domain.Member;
import backend.receipt.member.dto.request.LoginRequest;
import backend.receipt.member.dto.request.MemberRequest;
import backend.receipt.member.dto.response.LoginResponse;
import backend.receipt.member.dto.response.MemberResponse;
import backend.receipt.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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

    @Operation(summary = "회원가입", description = "이메일, 패스워드, 이름을 받아 회원가입합니다.")
    @PostMapping("/sign-up")
    public ResponseEntity<MemberResponse> signUp(@RequestBody MemberRequest request) {
        MemberResponse memberResponse= memberService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberResponse);
    }

    @Operation(summary = "아이디 중복 검사", description = "아이디(email)이 중복이 되는지 확인합니다.")
    @PostMapping("/exists")
    public ResponseEntity<Boolean> checkEmailDuplicate(@RequestBody MemberRequest request) {
        String email = request.getEmail();

        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body(false);
        }

        return ResponseEntity.ok(memberService.checkDuplication(email));
    }
}

