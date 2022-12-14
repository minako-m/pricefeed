package kz.ks.pricefeed.upload.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.ks.pricefeed.upload.job.FileProcessingJob;
import kz.ks.pricefeed.upload.service.FilesInformationStorageService;
import kz.ks.pricefeed.upload.service.FilesStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import kz.ks.pricefeed.upload.message.ResponseFile;
import kz.ks.pricefeed.upload.message.ResponseMessage;
import kz.ks.pricefeed.upload.model.FileDB;

@Controller
@RequiredArgsConstructor
@Log4j2
@Tag(name = "Files", description = "Uploaded files information")
public class FilesController {

    private final FilesInformationStorageService informationStorageService;
    private final FilesStorageService storageService;
    private final JobScheduler jobScheduler;
    private final FileProcessingJob fileProcessingJob;

    @PostMapping(path = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload file")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") @RequestPart MultipartFile file) {
        String message = "";
        try {
            var fileInfo = informationStorageService.store(file);
            storageService.save(file.getInputStream(), fileInfo.getId());

            log.info("Starting job");
            jobScheduler.enqueue(() -> fileProcessingJob.process(fileInfo.getId()));
            log.info("Started job");

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/files")
    @Operation(summary = "Get all files list")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = informationStorageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getId())
                    .toUriString();
            return ResponseFile.builder()
                    .name(dbFile.getName())
                    .url(fileDownloadUri)
                    .type(dbFile.getType())
                    .state(dbFile.getState())
                    .build();
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/{id}")
    @Operation(summary = "Get file details information by id")
    public ResponseEntity<byte[]> getFile(@PathVariable @Parameter(description = "File id") String id) throws IOException {
        FileDB fileDB = informationStorageService.getFile(id);

        try (var is = storageService.openFile(id)) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                    .body(is.readAllBytes());
        }
    }
}