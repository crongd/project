package com.project.service;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

@Service
public class FileService {

    public Map<String, Object> pdf_text_or_image(MultipartFile pdf, int startPage, int endPage) throws IOException {
//        PDDocument document;
//        System.out.println(pdf);
        Map<String, Object> resultList = new HashMap<>();

//        String path = "C:\\";
        File test = multipartFileToFile(pdf);
//        System.out.println(test);

        PDDocument document = PDDocument.load(test);

        PDFTextStripperByArea stripper = new PDFTextStripperByArea();
        stripper.setSortByPosition(true);
        PDFTextStripper Tstripper = new PDFTextStripper();
        Tstripper.setStartPage(startPage);
        Tstripper.setEndPage(endPage);

        String summaryText = Tstripper.getText(document);

//        System.out.println(summaryText);



        resultList.put("text", summaryText);

        List<String> imageList = new ArrayList<>();
        PDFRenderer pdfRenderer = new PDFRenderer(document);


        for (int page = startPage; page < endPage; ++page) {
            BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(page, 300);
//            ImageIO.write(bufferedImage, "jpeg", new File("image" + page + ".jpg"));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpeg", baos);
            byte[] bytes = baos.toByteArray();

            String imageBase64 = Base64.getEncoder().encodeToString(bytes);

            imageList.add(imageBase64);
        }


        resultList.put("image", imageList);

        document.close();

        return resultList;


    }

    public InputStreamResource pdf_merge(List<MultipartFile> pdfs) throws IOException {
        PDFMergerUtility merger = new PDFMergerUtility();

        File[] files = new File[pdfs.size()];

        for (int i = 0; i < pdfs.size(); i++) {
            files[i] = multipartFileToFile(pdfs.get(i));
        }

        for (File file : files) {
            if (file.getName().endsWith(".pdf")){
                String pdfFilePath = file.getAbsolutePath(); // 절대경로
                merger.addSource(pdfFilePath);
            }
        }

        ByteArrayOutputStream mergedPdfOutputStream = new ByteArrayOutputStream();
        merger.setDestinationStream(mergedPdfOutputStream);

        merger.mergeDocuments(null);
        byte[] mergedPdfBytes = mergedPdfOutputStream.toByteArray();

//        merger.set;
//        InputStream result = merger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());


        return new InputStreamResource(new ByteArrayInputStream(mergedPdfBytes));
    }

    public List<byte[]> pdf_to_jpg (List<MultipartFile> pdfs, String startPageOpt, String endPageOpt) throws IOException {
        List<byte[]> result = new ArrayList<>();


        for (MultipartFile pdf : pdfs) {
            InputStream pdfStream = new ByteArrayInputStream(pdf.getBytes());

            PDDocument document = PDDocument.load(pdfStream);

            PDFRenderer renderer = new PDFRenderer(document);

            int startPage = 0;
            int endPage = document.getNumberOfPages() - 1;

            if (!Objects.isNull(startPageOpt)) {
                startPage = Integer.parseInt(startPageOpt) - 1;
            }

            if (!Objects.isNull(startPageOpt)) {
                endPage = Integer.parseInt(endPageOpt) - 1;
            }




            for (int page = startPage; page <= endPage; page++) {
                BufferedImage bi = renderer.renderImage(page);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                ImageIO.write(bi, "jpeg", baos);

                result.add(baos.toByteArray());
            }

            document.close();
        }


        return result;
    }

    public InputStreamResource image_to_pdf(List<MultipartFile> images) throws IOException {
        System.out.println("image_to_pdf start");

        PDDocument document = new PDDocument();

        for (MultipartFile image : images) {
            if (image.getContentType() != null && image.getContentType().startsWith("image/")) {
                PDPage page = new PDPage();
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                contentStream.drawImage(PDImageXObject.createFromByteArray(document, image.getBytes(), ""), 0, 0);
                contentStream.close();
            }
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.save(outputStream);
        document.close();

        return new InputStreamResource(new ByteArrayInputStream(outputStream.toByteArray()));

    }

    private File multipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File("C:\\" + Objects.requireNonNull(multipartFile.getOriginalFilename()));
        multipartFile.transferTo(file);
        return file;
    }



}
