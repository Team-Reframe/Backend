package backend.receipt.member.service;

import backend.receipt.member.domain.Member;
import backend.receipt.member.dto.request.MemberRequest;
import backend.receipt.member.dto.response.MemberResponse;
import backend.receipt.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public MemberResponse signUp(MemberRequest request) {
        validatePasswordMatch(request); //비밀번호 일치 여부
        validateDuplicateEmail(request.getEmail()); // 이메일 중복 확인

        Member member = new Member(
                request.getEmail(),
                request.getPassword(),
                request.getName()
        );

        memberRepository.save(member);
        return new MemberResponse(member);
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
}
