package kz.ks.pricefeed.upload.service;

import java.io.InputStream;

public interface FilesStorageService {
    String save(InputStream data, String fileName);
   // byte[] read(String storageId);
}
