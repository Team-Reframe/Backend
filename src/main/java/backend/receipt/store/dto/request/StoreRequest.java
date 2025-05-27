package backend.receipt.store.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreRequest {
    private String query;
    private String name;
    private Double x;
    private Double y;
    private Integer radius;
}
