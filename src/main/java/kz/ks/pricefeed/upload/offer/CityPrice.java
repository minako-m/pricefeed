package kz.ks.pricefeed.upload.offer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class CityPrice {
    private String cityId;
    private String price;
}
