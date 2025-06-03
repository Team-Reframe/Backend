package backend.receipt.member.service;

import backend.receipt.member.domain.Member;
import backend.receipt.member.dto.request.MemberRequest;
import backend.receipt.member.dto.response.LoginResponse;
import backend.receipt.member.dto.response.MemberResponse;
import backend.receipt.member.repository.MemberRepository;
import backend.receipt.member.security.TokenBlacklist;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenBlacklist tokenBlacklist;

    public MemberResponse signUp(MemberRequest request) {
        validatePasswordMatch(request);
        validateDuplicateEmail(request.getEmail());

        Member member = new Member(
                request.getEmail(),
                request.getPassword(),
                request.getName()
        );

        memberRepository.save(member);
        return new MemberResponse(member);
    }

    public boolean checkDuplication(String email) {
        return memberRepository.existsByEmail(email);
    }

    public LoginResponse login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일을 찾을 수 없습니다."));

        if (!member.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(member.getId(), member.getEmail());
        return new LoginResponse(token);
    }

    private void validatePasswordMatch(MemberRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    private void validateDuplicateEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }
    }

    public void logout(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
        tokenBlacklist.add(token);
    }

}