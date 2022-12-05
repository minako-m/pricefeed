package kz.ks.pricefeed.upload.mongo.repository;

import kz.ks.pricefeed.upload.mongo.Offer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OfferRepository extends MongoRepository<Offer, String> {

}
