package backend.receipt.purchase.service;

import backend.receipt.member.domain.Member;
import backend.receipt.member.repository.MemberRepository;
import backend.receipt.purchase.domain.Purchase;
import backend.receipt.purchase.dto.PurchaseDto;
import backend.receipt.purchase.ocr.OcrClient;
import backend.receipt.purchase.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final OcrClient ocrClient;
    private final PurchaseRepository purchaseRepository;
    private final MemberRepository memberRepository;

    public PurchaseDto handleReceipt(MultipartFile file, Long memberId) throws Exception {
        PurchaseDto dto = ocrClient.extractInfo(file);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        purchaseRepository.save(
                Purchase.builder()
                        .storeName(dto.getStoreName())
                        .amount(dto.getAmount())
                        .paymentDate(dto.getPaymentDate())
                        .member(member)
                        .build()
        );

        return dto;
    }

}
