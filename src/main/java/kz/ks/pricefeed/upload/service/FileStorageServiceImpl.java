package kz.ks.pricefeed.upload.service;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class FileStorageServiceImpl implements FilesStorageService{
    private final Path root = Paths.get("upload_files");
    @Override
    public String save(InputStream data, String fileName) {
        try {
            Files.copy(data, this.root.resolve(fileName));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        return generatedString;
    }
    //public byte[] read(String storageId) {

    //}
}
