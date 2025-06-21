package backend.receipt.purchase.domain;

import backend.receipt.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="purchase_id")
    private Long id;

    private int amount;

    @Column(length = 255)
    private String storeName;    // 가맹점명

    private String paymentDate;  // 결제 일자 (yyyy-MM-dd)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
