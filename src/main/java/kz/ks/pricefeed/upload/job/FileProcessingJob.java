package kz.ks.pricefeed.upload.job;

import kz.ks.pricefeed.upload.job.xml.OfferReader;
import kz.ks.pricefeed.upload.model.ProcessingState;
import kz.ks.pricefeed.upload.service.FilesInformationStorageService;
import kz.ks.pricefeed.upload.service.FilesStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jobrunr.jobs.annotations.Job;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Log4j2
@Component
@RequiredArgsConstructor
public class FileProcessingJob {
    private final FilesInformationStorageService filesInformationStorageService;
    private final FilesStorageService filesStorageService;

    @Job(name = "file_processing_job")
    public void process(String id) throws InterruptedException {
        log.info("Started file processing {}", id);

        var file = filesInformationStorageService.getFile(id);

        log.info("Processing file {}", file.getName());
        try (var is = filesStorageService.openFile(id)) {
            parseFile(is);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            log.error(e);
        }

        file.setState(ProcessingState.PROCESSED);
        filesInformationStorageService.save(file);
    }

    protected void parseFile(InputStream is) throws SAXException, ParserConfigurationException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);
        SchemaFactory schemafactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try (var schemaStream = new ClassPathResource("kaspi.xsd").getInputStream()) {
            Source schemaSource = new StreamSource(schemaStream);
            Schema sc = schemafactory.newSchema(schemaSource);
            factory.setSchema(sc);
            factory.setFeature("http://xml.org/sax/features/validation", false);
            factory.setNamespaceAware(true);
            SAXParser saxParser = factory.newSAXParser();
            var reader = new OfferReader();
            saxParser.parse(is, reader);
        }
    }
}
