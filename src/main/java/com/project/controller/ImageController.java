package com.project.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {
    @PostMapping("/size")
    public ResponseEntity<List<String>> image_size(
            @RequestParam("images") List<MultipartFile> images,
            @RequestParam("width") int width,
            @RequestParam("height") int height
    ) throws IOException {
//        System.out.println(images);
//        System.out.println(width);
//        System.out.println(height);

//        MultipartFile img = images.get(0);

        List<String> imageBytesList = new ArrayList<>();

        for (MultipartFile image : images) {
            String base64Image = Base64.getEncoder().encodeToString(image.getBytes());
            imageBytesList.add(base64Image);
        }




        return new ResponseEntity<>(imageBytesList, HttpStatus.OK);

    }
}
