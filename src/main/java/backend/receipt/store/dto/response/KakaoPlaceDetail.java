package backend.receipt.store.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoPlaceDetail {
    private String phone;
    private String description;
    private String openingHours;
}