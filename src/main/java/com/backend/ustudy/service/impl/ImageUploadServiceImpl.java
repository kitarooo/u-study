package com.backend.ustudy.service.impl;

import com.backend.ustudy.exception.EmptyFileException;
import com.backend.ustudy.service.ImageUploadService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;
@Service
public class ImageUploadServiceImpl implements ImageUploadService {
    @SneakyThrows
    public String saveImage(MultipartFile multipartfile) {
        if (multipartfile.isEmpty()) {
            throw new EmptyFileException("File is empty");
        }

        final String urlKey = "cloudinary://429488699555469:ikzaa8wUnIvSZJr7h917mJPTdKU@dovfdzzuz";


        Cloudinary cloudinary = new Cloudinary((urlKey));

        File saveFile = Files.createTempFile(
                        System.currentTimeMillis() + "",
                        Objects.requireNonNull
                                (multipartfile.getOriginalFilename(), "File must have an extension")
                )
                .toFile();

        multipartfile.transferTo(saveFile);


        Map upload = cloudinary.uploader().upload(saveFile, ObjectUtils.emptyMap());

        return (String) upload.get("url");
    }
    @SneakyThrows
    public String saveMedia(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new EmptyFileException("File is empty");
        }

        String contentType = multipartFile.getContentType();
        if (contentType == null || !(contentType.startsWith("image/") || contentType.startsWith("video/"))) {
            throw new IllegalArgumentException("Invalid file type");
        }

        final String urlKey = "cloudinary://429488699555469:ikzaa8wUnIvSZJr7h917mJPTdKU@dovfdzzuz";
        Cloudinary cloudinary = new Cloudinary(urlKey);

        File saveFile = Files.createTempFile(
                System.currentTimeMillis() + "",
                Objects.requireNonNull(multipartFile.getOriginalFilename(), "File must have an extension")
        ).toFile();

        multipartFile.transferTo(saveFile);

        Map<String, Object> uploadParams = ObjectUtils.asMap(
                "resource_type", contentType.startsWith("video/") ? "video" : "image"
        );

        Map<String, Object> uploadResult = cloudinary.uploader().upload(saveFile, uploadParams);

        saveFile.delete();

        return (String) uploadResult.get("url");
    }
}
