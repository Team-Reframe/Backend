package backend.receipt.member.service;

import backend.receipt.member.domain.Member;
import backend.receipt.member.dto.request.MemberRequest;
import backend.receipt.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member register(MemberRequest request) {
        if (!request.getUserPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        if (memberRepository.existsByUserId(request.getUserId())) {
            throw new IllegalArgumentException("이미 사용 중인 ID입니다.");
        }

        if (memberRepository.existsByUserEmail(request.getUserEmail())) {
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }

        Member member = new Member(
                request.getUserEmail(),
                request.getUserId(),
                request.getUserPassword(),
                request.getUserName()
        );

        return memberRepository.save(member);
    }
}
