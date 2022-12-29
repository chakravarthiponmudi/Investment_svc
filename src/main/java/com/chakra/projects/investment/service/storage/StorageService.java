package com.chakra.projects.investment.service.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService<T> {
    T store(MultipartFile file);
    void init();
}
