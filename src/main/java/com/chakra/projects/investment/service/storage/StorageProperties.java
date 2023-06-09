package com.chakra.projects.investment.service.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {
    @Getter
    @Setter
    private String location = "upload-dir";

}
