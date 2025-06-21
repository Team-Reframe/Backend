package backend.receipt.purchase.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseDto {

        private int amount;
        private String storeName;
        private String paymentDate;

}
