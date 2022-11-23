package kz.ks.pricefeed.upload.offer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class Availability {
    private boolean isAvailable;
    private String storeId;
}
