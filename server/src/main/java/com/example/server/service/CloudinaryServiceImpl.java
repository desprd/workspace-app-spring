package com.example.server.service;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl{
    private final Cloudinary cloudinary;
    public String uploadFile(MultipartFile file, String folderName){
        String url = "";
        try {
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", folderName);
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
            String publicId = uploadResult.get("public_id").toString();
            url = cloudinary.url().secure(true).generate(publicId);
        }catch (Exception e){
            throw  new RuntimeException("Failed to upload the image");
        }
        return url;
    }
}
