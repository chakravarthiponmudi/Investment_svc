package com.chakra.projects.investment.service.storage;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface StorageService<T> {
    T store(MultipartFile file);
    void init();
}
