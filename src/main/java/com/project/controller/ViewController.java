package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/image-resize")
    public String image_resize() {
        return "image/image-resize";
    }

    @GetMapping("/image-cut")
    public String image_cut() {
        return "image/image-cut";
    }

    @GetMapping("/image-rotation")
    public String image_rotation() {
        return "image/image-rotation";
    }

    @GetMapping("/image-compression")
    public String image_compression() {
        return "image/image-compression";
    }

    @GetMapping("/url-encoder")
    public String url_encoder() {
        return "encode/url-encoder";
    }

    @GetMapping("/url-decoder")
    public String url_decoder() {
        return "encode/url-decoder";
    }

    @GetMapping("/md5")
    public String md5() {
        return "encode/md5";
    }

    @GetMapping("/sha256")
    public String sha256() {
        return "encode/sha256";
    }

    @GetMapping("/sha512")
    public String sha512() {
        return "encode/sha512";
    }

    @GetMapping("/pdf-text-or-image")
    public String pdf_text_or_image() {
        return "file/pdf-text-or-image";
    }

    @GetMapping("/pdf-merge")
    public String pdf_merge() {
        return "file/pdf-merge";
    }

    @GetMapping("/pdf-to-jpg")
    public String pdf_to_jpg() {
        return "file/pdf-to-jpg";
    }

}
