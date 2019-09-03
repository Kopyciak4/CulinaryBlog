package com.culinaryblog.services.file;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface FileService {

    String save(MultipartFile photo, int id);

    byte[] loadPhotosByPaths(List<String> recipePhotoList);
}
