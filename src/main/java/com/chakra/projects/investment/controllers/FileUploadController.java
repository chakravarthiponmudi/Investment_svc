package com.chakra.projects.investment.controllers;

import com.chakra.projects.investment.Domain.cams.CAS;
import com.chakra.projects.investment.service.storage.StorageService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@RestController
@RequestMapping(path="/upload",produces = "application/json")
public class FileUploadController {

    @Autowired
    private StorageService<Path> storageService;


    @PostMapping(path="/cams")
    public CAS processCamsJSON(@RequestParam("file") MultipartFile file) throws IOException {
        Path p = storageService.store(file);
        CAS cas = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .readerFor(CAS.class).readValue(file.getInputStream());
        return cas;
    }
}
