package kz.ks.pricefeed.upload.service;

import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FilesStorageService {
    private final Path root = Paths.get("upload_files");
    @Override
    public String save(InputStream data, String name) {
        try {
            Files.copy(data, this.root.resolve(name));

            return name;
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public InputStream openFile(String id) throws FileNotFoundException {
        return new FileInputStream(this.root.resolve(id).toFile());
    }


}