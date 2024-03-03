package com.project.service;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Position;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.print.DocFlavor;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;

@Service
public class ImageService {



    public List<Map<String, String>> image_resize(List<MultipartFile> images, int width, int height, boolean check) throws IOException {
        List<Map<String, String>> resizedImages = new ArrayList<>();

        for (MultipartFile image : images) {
            InputStream in = new ByteArrayInputStream(image.getBytes());
            BufferedImage originalImage = ImageIO.read(in);
            String[] format = Objects.requireNonNull(image.getOriginalFilename()).split("\\.");

            BufferedImage resizedImage = Thumbnails.of(originalImage)
                    .size(width, height) // 사이즈 지정
                    .keepAspectRatio(!check) // 비율 무시
                    .asBufferedImage();

            String contentType = image.getContentType();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, format[format.length-1], baos);
            byte[] bytes = baos.toByteArray();

            String base64ResizedImage = Base64.getEncoder().encodeToString(bytes);
            String base64ResizedImageWithMime = "data:" + contentType + ";base64," + base64ResizedImage;

            Map<String, String> imageMap = new HashMap<>();
            imageMap.put("name", "resize_" + image.getOriginalFilename());
            imageMap.put("image", base64ResizedImageWithMime);
            resizedImages.add(imageMap);
        }

        return resizedImages;
    }

    public List<Map<String, String>> image_cut(List<MultipartFile> images, int width, int height, boolean check, String position) throws IOException {
        List<Map<String, String>> cuttingImages = new ArrayList<>();

        for (MultipartFile image : images) {
            InputStream in = new ByteArrayInputStream(image.getBytes());
            BufferedImage originalImage = ImageIO.read(in);
            String[] format = Objects.requireNonNull(image.getOriginalFilename()).split("\\.");

            BufferedImage cuttingImage = Thumbnails.of(originalImage)
                    .sourceRegion(image_position(position), width, height)
                    .size(width, height) // 사이즈 지정
                    .keepAspectRatio(!check) // 비율 무시
                    .asBufferedImage();

            String contentType = image.getContentType();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(cuttingImage, format[format.length-1], baos);
            byte[] bytes = baos.toByteArray();

            String base64CuttingImage = Base64.getEncoder().encodeToString(bytes);
            String base64CuttingImageWithMime = "data:" + contentType + ";base64," + base64CuttingImage;

            Map<String, String> imageMap = new HashMap<>();
            imageMap.put("name", "cropped_" + image.getOriginalFilename());
            imageMap.put("image", base64CuttingImageWithMime);
            cuttingImages.add(imageMap);
        }

        return cuttingImages;
    }

    public List<Map<String, String>> image_rotation(List<MultipartFile> images, int rotation) throws IOException {
        List<Map<String, String>> rotationImages = new ArrayList<>();

        for (MultipartFile image : images) {
            InputStream in = new ByteArrayInputStream(image.getBytes());
            BufferedImage originalImage = ImageIO.read(in);
            String[] format = Objects.requireNonNull(image.getOriginalFilename()).split("\\.");

            BufferedImage rotationImage = Thumbnails.of(originalImage)
                    .rotate(rotation)
                    .size(originalImage.getWidth(), originalImage.getHeight())
                    .outputFormat(format[format.length-1])
                    .asBufferedImage();

            if ("jpeg".equals(format[format.length-1]) || "jpg".equals(format[format.length-1])) {
                rotationImage = new BufferedImage(
                        originalImage.getWidth(),
                        originalImage.getHeight(),
                        BufferedImage.TYPE_INT_RGB
                );
                Graphics2D g = rotationImage.createGraphics();
                g.drawImage(originalImage, 0, 0, Color.WHITE, null);
                g.dispose();
            }

//            System.out.println("rota" + rotationImage);

            String contentType = image.getContentType();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

//            System.out.println(Arrays.toString(format));
            ImageIO.write(rotationImage, format[format.length-1], baos);
//            System.out.println("baos" +baos);
            byte[] bytes = baos.toByteArray();
//            System.out.println("bytes" + Arrays.toString(bytes));

            String base64RotationImage = Base64.getEncoder().encodeToString(bytes);
            String base64RotationImageWithMime = "data:" + contentType + ";base64," + base64RotationImage;

            Map<String, String> imageMap = new HashMap<>();
            imageMap.put("name", "rotation_" + image.getOriginalFilename());
            imageMap.put("image", base64RotationImageWithMime);
            rotationImages.add(imageMap);
        }

        return rotationImages;
    }

    public List<Map<String, String>> image_compression(List<MultipartFile> images) throws IOException {
        List<Map<String, String>> compressionImages = new ArrayList<>();

        for (MultipartFile image : images) {
            InputStream in = new ByteArrayInputStream(image.getBytes());
            BufferedImage originalImage = ImageIO.read(in);
            // 이미지를 새로 생성하면서 메타데이터 제거
            BufferedImage newImage = new BufferedImage(
                    originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            newImage.createGraphics().drawImage(originalImage, 0, 0, Color.WHITE, null);

            String[] format = Objects.requireNonNull(image.getOriginalFilename()).split("\\.");

            // 이미지 품질 조절
            BufferedImage compressionImage = Thumbnails.of(newImage)
                    .scale(1) // 이미지 원본 크기를 유지
                    .outputQuality(0.1) // 이미지 품질을 10%로 설정함
                    .asBufferedImage();

            String contentType = image.getContentType();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(compressionImage, format[format.length-1], baos);
            byte[] bytes = baos.toByteArray();

            String base64CompressionImage = Base64.getEncoder().encodeToString(bytes);
            String base64CompressionImageWithMime = "data:" + contentType + ";base64," + base64CompressionImage;

            Map<String, String> imageMap = new HashMap<>();
            imageMap.put("name", "compression_" + image.getOriginalFilename());
            imageMap.put("image", base64CompressionImageWithMime);
            imageMap.put("size", String.valueOf(bytes.length));
            compressionImages.add(imageMap);
        }

        return compressionImages;
    }


    private Positions image_position(String position) {
        return switch (position) {
            case "center" -> Positions.CENTER;
            case "top_right" -> Positions.TOP_RIGHT;
            case "top_left" -> Positions.TOP_LEFT;
            case "top_center" -> Positions.TOP_CENTER;
            case "center_left" -> Positions.CENTER_LEFT;
            case "center_right" -> Positions.CENTER_RIGHT;
            case "bottom_left" -> Positions.BOTTOM_LEFT;
            case "bottom_center" -> Positions.BOTTOM_CENTER;
            case "bottom_right" -> Positions.BOTTOM_RIGHT;
            default -> null;
        };

    }
}
