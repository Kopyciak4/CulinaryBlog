package com.culinaryblog.services.file;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileServiceImpl implements FileService {
    Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);



    @Override
    public String save(MultipartFile photo, int recipeId) {
        try(InputStream photoInputStream = photo.getInputStream()) {
            Path directories =  Paths.get("RecipePhotos", "" + recipeId);
            if (Files.notExists(directories)) {
                Files.createDirectories(directories);
            }
            Path filePath = directories.resolve( photo.getOriginalFilename());
            Files.copy(photoInputStream, filePath);
            return filePath.toString();
        } catch(IOException ex) {
            logger.error("Something went wrong with file during saving", ex);
        }
        return null;
    }

    @Override
    public byte[] loadPhotosByPaths(List<String> photosPaths) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        ZipOutputStream zipOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
            zipOutputStream = new ZipOutputStream(bufferedOutputStream);
            for (String photo : photosPaths) {
                File file = new File(photo);
                zipOutputStream.putNextEntry(new ZipEntry(photo.replace("RecipePhotos\\", "")));
                FileInputStream fileInputStream = new FileInputStream(file);

                IOUtils.copy(fileInputStream, zipOutputStream);

                fileInputStream.close();
                zipOutputStream.closeEntry();
            }
            return byteArrayOutputStream.toByteArray();
        } catch(IOException ex) {
            logger.error("something went wrong with file loading", ex);
        } finally {
            try {
                if (zipOutputStream != null) {
                    zipOutputStream.finish();
                    zipOutputStream.flush();
                    IOUtils.closeQuietly(zipOutputStream);
                }
                IOUtils.closeQuietly(bufferedOutputStream);
                IOUtils.closeQuietly(byteArrayOutputStream);
            } catch (IOException ex2) {
                logger.error("something went wrong with file loading", ex2);
            }
        }

        return new byte[0];
    }

}
