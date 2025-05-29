package backend.receipt.store.dto.response;

import backend.receipt.store.domain.Store;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreResponse {

    private final Long id;
    private final String name;
    private final String category;
    private final String address;

    private final Double latitude;
    private final Double longitude;

    private String description;
    private String phone;
    private String openingHours;

    public StoreResponse(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.category = store.getCategory();
        this.address = store.getAddress();
        this.latitude = store.getLatitude();
        this.longitude = store.getLongitude();
        this.description = store.getDescription();
        this.phone = store.getPhone();
        this.openingHours = store.getOpeningHours();
    }

}