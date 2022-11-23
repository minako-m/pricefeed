package kz.ks.pricefeed.upload.job.xml;

import kz.ks.pricefeed.upload.offer.Availability;
import kz.ks.pricefeed.upload.offer.OfferDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class OfferReader extends DefaultHandler {

    private StringBuilder elementValue;
    private OfferDTO.OfferDTOBuilder offerDTOBuilder;
    private List<Availability> availabilities;
    
    private final String OFFER_TAG_NAME = "offer";
    private final String AVAILABILITIES_TAG_NAME = "availabilities";

    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr) throws SAXException {
        switch (qName) {
            case OFFER_TAG_NAME:
                offerDTOBuilder = OfferDTO.builder();
                offerDTOBuilder.sku(attr.getValue("sku"));
                break;
            case "brand":
                offerDTOBuilder = OfferDTO.builder();
                offerDTOBuilder.brand(qName);
            case "model":
                offerDTOBuilder = OfferDTO.builder();
                offerDTOBuilder.model(qName);
            case AVAILABILITIES_TAG_NAME:
                offerDTOBuilder = OfferDTO.builder();
                availabilities = new ArrayList<>();
            case "availability":
                availabilities.add(
                        Availability.builder()
                                .isAvailable("yes".equals(attr.getValue("available")))
                                .storeId(attr.getValue("storeId"))
                                .build()
                );
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
        }
    }

    protected void processOffer(OfferDTO offer) {
        log.info(offer);
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
