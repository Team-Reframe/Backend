package backend.receipt.store.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreRequest {
    @Schema(description = "검색 쿼리", example = "카페", required = true)
    private String query;

    @Schema(description = "가맹점 이름", example = "스타벅스", required = true)
    private String name;

    @Schema(description = "기본 좌표 x (경도)", example = "127.0276", required = false)
    private Double x;

    @Schema(description = "기본 좌표 y (위도)", example = "37.4979", required = false)
    private Double y;

    @Schema(description = "검색 반경 (미터)", example = "1000", required = false)
    private Integer radius;
}
