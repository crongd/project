package com.project.controller;

import com.project.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    @PostMapping("/pdf-text-or-image")
    public Map<String, Object> pdf_text_or_image(
            @RequestParam("pdf") MultipartFile pdf,
            @RequestParam("startPage") int startPage,
            @RequestParam("endPage") int endPage
    ) throws IOException {
        System.out.println(pdf);
        System.out.println(startPage);
        System.out.println(endPage);

        return fileService.pdf_text_or_image(pdf, startPage, endPage);
    }

    @PostMapping("/pdf-merge")
    public ResponseEntity<InputStreamResource> pdf_merge(
            @RequestParam("pdfs") List<MultipartFile> pdfs
    ) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=merged.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(fileService.pdf_merge(pdfs));
    }

    @PostMapping("/pdf-to-jpg")
    public List<byte[]> pdf_to_jpg(
            @RequestParam("pdfs") List<MultipartFile> pdfs,
            @RequestParam(value = "startPage", required = false) String startPage,
            @RequestParam(value = "endPage", required = false) String endPage
    ) throws IOException {
        System.out.println(pdfs);
        System.out.println(startPage);
        System.out.println(endPage);

        return fileService.pdf_to_jpg(pdfs, startPage, endPage);
    }

    @PostMapping("/image-to-pdf")
    public ResponseEntity<InputStreamResource> image_to_pdf(
            @RequestParam("images") List<MultipartFile> images,
            @RequestParam("ratio") String ratio
    ) throws IOException {
        System.out.println("images : " + images);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "output.pdf");
        System.out.println(ratio);


        return ResponseEntity
                .ok()
                .headers(headers)
                .body(fileService.image_to_pdf(images, Objects.equals(ratio, "가로")));
    }
}
