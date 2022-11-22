package kz.ks.pricefeed.upload.service;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface FilesStorageService {
    String save(InputStream data, String name);
    InputStream openFile(String id) throws FileNotFoundException;
   // byte[] read(String storageId);
}
