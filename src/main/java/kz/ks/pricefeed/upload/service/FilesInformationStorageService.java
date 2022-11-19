package kz.ks.pricefeed.upload.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import kz.ks.pricefeed.upload.model.FileDB;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesInformationStorageService {
    FileDB store(MultipartFile file) throws IOException;
    void save(FileDB file);
    FileDB getFile(String id);
    Stream<FileDB> getAllFiles();
}