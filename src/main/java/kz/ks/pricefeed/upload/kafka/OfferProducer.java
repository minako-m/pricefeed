package kz.ks.pricefeed.upload.kafka;

import kz.ks.pricefeed.upload.offer.OfferDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OfferProducer {
    private final KafkaTemplate<String, OfferDTO> kafkaTemplate;

    public void send(OfferDTO offerDTO) {
        kafkaTemplate.send("pricefeed_offer", offerDTO.getSku(), offerDTO);
    }
}
