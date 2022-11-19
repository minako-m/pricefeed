package kz.ks.pricefeed.upload.job;

import kz.ks.pricefeed.upload.model.ProcessingState;
import kz.ks.pricefeed.upload.repository.FileDBRepository;
import kz.ks.pricefeed.upload.service.FilesInformationStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jobrunr.jobs.annotations.Job;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class FileProcessingJob {
    private final FilesInformationStorageService filesInformationStorageService;

    @Job(name = "file_processing_job")
    public void process(String id) throws InterruptedException {
        log.info("Started file processing {}", id);

        var file = filesInformationStorageService.getFile(id);

        log.info("Processing file {}", file.getName());
        for (int i = 0; i < 10; i++) {
            log.info("Round {}", i);
            Thread.sleep(5 * 1000);
        }

        file.setState(ProcessingState.PROCESSED);
        filesInformationStorageService.save(file);
    }
}
