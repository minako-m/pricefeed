package kz.ks.pricefeed.upload.offer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfferDTO {
    private String sku;
    private String model;
    private String brand;
    private List<Availability> availabilityList;
    private List<CityPrice> cityPriceList;
}
