package kz.ks.pricefeed.upload.offer;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OfferDTO {
    private String sku;
    private String model;
    private String brand;
    private List<Availability> availabilityList;
}
