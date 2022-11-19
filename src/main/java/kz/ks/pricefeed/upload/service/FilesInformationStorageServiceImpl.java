package kz.ks.pricefeed.upload.service;

import java.io.IOException;
import java.util.stream.Stream;

import kz.ks.pricefeed.upload.model.ProcessingState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import kz.ks.pricefeed.upload.model.FileDB;
import kz.ks.pricefeed.upload.repository.FileDBRepository;

@Service
public class FilesInformationStorageServiceImpl implements FilesInformationStorageService {
    @Autowired
    private FileDBRepository fileDBRepository;

    public FileDB store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB fileDB = FileDB.builder()
                .name(fileName)
                .type(file.getContentType())
                .state(ProcessingState.NEW)
                .build();

        return fileDBRepository.save(fileDB);
    }

    @Override
    public void save(FileDB file) {
        fileDBRepository.save(file);
    }

    public FileDB getFile(String id) {
        return fileDBRepository.findById(id).get();
    }
    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }
}