package backend.receipt.love.dto;

import backend.receipt.store.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class LoveResponse {
    private Long storeId;
    private String name;
    private String address;
}
