package kz.ks.pricefeed.upload.mongo;

import kz.ks.pricefeed.upload.offer.Availability;
import kz.ks.pricefeed.upload.offer.CityPrice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("raw_offer")
public class Offer {
    @Id
    private String id;
    private String sku;
    private String model;
    private String brand;
}
