package kz.ks.pricefeed.upload.job.xml;

import kz.ks.pricefeed.upload.kafka.OfferProducer;
import kz.ks.pricefeed.upload.mongo.Offer;
import kz.ks.pricefeed.upload.mongo.repository.OfferRepository;
import kz.ks.pricefeed.upload.offer.Availability;
import kz.ks.pricefeed.upload.offer.CityPrice;
import kz.ks.pricefeed.upload.offer.OfferDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class OfferReader extends DefaultHandler {
    private final OfferProducer offerProducer;
    private final OfferRepository offerRepository;
    private StringBuilder elementValue;
    private OfferDTO.OfferDTOBuilder offerDTOBuilder;
    private List<Availability> availabilities;
    private List<CityPrice> cityprices;
    private String cityPriceAttr;
    
    private final String OFFER_TAG_NAME = "offer";
    private final String AVAILABILITIES_TAG_NAME = "availabilities";
    private final String CITY_PRICES_LIST_TAG_NAME = "cityprices";
    private final String CITY_PRICE_TAG_NAME = "cityprice";

    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr) throws SAXException {
        switch (qName) {
            case OFFER_TAG_NAME:
                offerDTOBuilder = OfferDTO.builder();
                offerDTOBuilder.sku(attr.getValue("sku"));
                break;
            case AVAILABILITIES_TAG_NAME:
                availabilities = new ArrayList<>();
            case "availability":
                availabilities.add(
                        Availability.builder()
                                .isAvailable("yes".equals(attr.getValue("available")))
                                .storeId(attr.getValue("storeId"))
                                .build()
                );
//            case "brand":
//                offerDTOBuilder.brand(qName);
//            case "model":
//                offerDTOBuilder.model(qName);
//            case CITY_PRICES_LIST_TAG_NAME:
//                offerDTOBuilder = OfferDTO.builder();
//                cityprices = new ArrayList<>();
//            case CITY_PRICE_TAG_NAME:
//                cityPriceAttr = attr.getValue("cityId");
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (elementValue == null) {
            elementValue = new StringBuilder();
        } else {
            elementValue.append(ch, start, length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case OFFER_TAG_NAME:
                processOffer(offerDTOBuilder.build());
                break;
            case AVAILABILITIES_TAG_NAME:
                offerDTOBuilder.availabilityList(availabilities);
                break;
//            case CITY_PRICES_LIST_TAG_NAME:
//                offerDTOBuilder.cityPriceList(cityprices);
//            case CITY_PRICE_TAG_NAME:
//                cityprices.add(
//                        CityPrice.builder()
//                                .cityId(cityPriceAttr)
//                                .price(elementValue)
//                                .build()
//                );
        }
    }

    protected void processOffer(OfferDTO offer) {
        //offerProducer.send(offer);
        offerRepository.save(Offer.builder()
                .sku(offer.getSku())
                .model(offer.getModel())
                .brand(offer.getBrand())
                .id(offer.getSku())
                .build());
    }

    @Override
    public void warning(SAXParseException e) throws SAXException {
        log.warn(e.getMessage());
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        log.error(e.getMessage());
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        log.error(e.getMessage());
    }
}
