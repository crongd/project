package com.project;

import com.project.service.FileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class ProjectApplicationTests {

    @Autowired
    FileService fileService;
    @Test
    void contextLoads() throws Exception {
//        fileService.pdf_text_or_image();
    }

}
