package com.project.controller;

import com.project.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    public Map<String, Object> pdf_merge(
            @RequestParam("pdfs") List<MultipartFile> pdfs
    ) {
        System.out.println(pdfs);
        return null;
    }
}
