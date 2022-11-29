package kz.ks.pricefeed.upload.offer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Availability {
    private boolean isAvailable;
    private String storeId;
}
