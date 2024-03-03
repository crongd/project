package com.project.controller;

import com.project.service.EncodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/encode")
public class EncodeController {

    private final EncodeService encodeService;

    @PostMapping("/url-encoder")
    public String url_encoder(@RequestBody String url) throws UnsupportedEncodingException {
        System.out.println(url);
        return URLEncoder.encode(url, StandardCharsets.UTF_8);
    }

    @PostMapping("/url-decoder")
    public String url_decoder(@RequestBody String url) {
        System.out.println(url);
        return URLDecoder.decode(url, StandardCharsets.UTF_8);
    }

    @PostMapping("/md5")
    public String md5(@RequestBody String text) {
        return encodeService.md5(text);
    }

    @PostMapping("/sha256")
    public String sha256(@RequestBody String text) {
        return encodeService.sha256(text);
    }

    @PostMapping("/sha512")
    public String sha512(@RequestBody String text) {
        return encodeService.sha512(text);
    }


}
