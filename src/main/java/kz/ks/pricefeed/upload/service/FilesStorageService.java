package kz.ks.pricefeed.upload.service;

import java.io.InputStream;

public interface FilesStorageService {
    String save(InputStream data);
    byte[] read(String storageId);
}
