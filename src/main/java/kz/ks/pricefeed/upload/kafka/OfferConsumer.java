package kz.ks.pricefeed.upload.kafka;

import kz.ks.pricefeed.upload.offer.OfferDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class OfferConsumer {
//    @KafkaListener(
//            topics = "pricefeed_offer",
//            clientIdPrefix = "offer")
    public void consume(OfferDTO offer) {
        log.info("Consumed: {}", offer.toString());
    }
}
