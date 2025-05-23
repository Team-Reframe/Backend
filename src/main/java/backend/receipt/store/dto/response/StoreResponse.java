package backend.receipt.store.dto.response;

import backend.receipt.store.domain.Store;
import lombok.Getter;

@Getter
public class StoreResponse {

    private final Long id;
    private final String name;
    private final String category;
    private final String address;

    public StoreResponse(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.category = store.getCategory();
        this.address = store.getAddress();
    }
}