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

@Log4j2
public class OfferReader extends DefaultHandler {

    private StringBuilder elementValue;
    private OfferDTO.OfferDTOBuilder offerDTOBuilder;
    private Availability.AvailabilityBuilder availabilityBuilder;

    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr) throws SAXException {
        switch (qName) {
            case "offer":
                offerDTOBuilder = OfferDTO.builder();
                offerDTOBuilder.sku(attr.getValue("sku"));
                break;
            case "brand":
                offerDTOBuilder = OfferDTO.builder();
                offerDTOBuilder.brand(qName);
            case "model":
                offerDTOBuilder = OfferDTO.builder();
                offerDTOBuilder.model(qName);
            case "availabilities":
                offerDTOBuilder = OfferDTO.builder();
                offerDTOBuilder.availabilityList(new ArrayList<>());
            case "availability":
                offerDTOBuilder = OfferDTO.builder();
                Availability availability = new Availability();
                availabilityBuilder = Availability.builder();

                offerDTOBuilder.availabilityList.add(new Availability());
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
            case "offer":
                processOffer(offerDTOBuilder.build());
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
