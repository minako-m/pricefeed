package kz.ks.pricefeed.upload.offer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class Availability {
    private String isAvailable;
    private String storeId;
}
