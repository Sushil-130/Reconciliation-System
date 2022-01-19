package org.ps.reconciliation.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class FileService {

    @Value("${file.location}")
    private String fileLocation;

    public String save(MultipartFile multipartFile,String targetFileName) throws IOException {
        File file= Paths.get(fileLocation,targetFileName).toFile();
        multipartFile.transferTo(file);
        return file.getPath();
    }
}
