package kz.ks.pricefeed.upload.service;

import java.io.InputStream;

public interface FilesStorageService {
    String save(InputStream data, String name);
   // byte[] read(String storageId);
}
