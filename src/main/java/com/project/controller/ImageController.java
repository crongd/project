package com.project.controller;

import com.project.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/resize")
    public ResponseEntity<List<Map<String, String>>> image_size(
            @RequestParam("images") List<MultipartFile> images,
            @RequestParam("width") int width,
            @RequestParam("height") int height,
            @RequestParam("check") boolean check
    ) throws IOException {
//        System.out.println(images);
        System.out.println(width);
        System.out.println(height);
        System.out.println(check);

//        MultipartFile img = images.get(0);

//        List<String> imageBytesList = new ArrayList<>();
//
//        for (MultipartFile image : images) {
//            String base64Image = Base64.getEncoder().encodeToString(image.getBytes());
//            imageBytesList.add(base64Image);
//        }

        return new ResponseEntity<>(imageService.image_resize(images, width, height, check), HttpStatus.OK);
    }

    @PostMapping("/cut")
    public ResponseEntity<List<Map<String, String>>> image_cut(
            @RequestParam("images") List<MultipartFile> images,
            @RequestParam("width") int width,
            @RequestParam("height") int height,
            @RequestParam("check") boolean check,
            @RequestParam("position") String position
    ) throws IOException {
        System.out.println(images);
        System.out.println(width);
        System.out.println(height);
        System.out.println(check);
        System.out.println(position);
        return new ResponseEntity<>(imageService.image_cut(images, width, height, check, position), HttpStatus.OK);
    }

    @PostMapping("/rotation")
    public ResponseEntity<List<Map<String, String>>> image_rotation(
            @RequestParam("images") List<MultipartFile> images,
            @RequestParam("rotation") int rotation
    ) throws IOException {
        System.out.println(images);
        System.out.println(rotation);
        return new ResponseEntity<>(imageService.image_rotation(images, rotation), HttpStatus.OK);
    }

    @PostMapping("/compression")
    public ResponseEntity<List<Map<String, String>>> image_compression(
            @RequestParam("images") List<MultipartFile> images
    ) throws IOException{
        System.out.println(images);
        return new ResponseEntity<>(imageService.image_compression(images), HttpStatus.OK);
    }

}
