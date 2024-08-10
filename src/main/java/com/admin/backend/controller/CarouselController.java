package com.admin.backend.controller;

import com.admin.backend.entity.Carousel;
import com.admin.backend.service.ICarouselService;
import com.admin.backend.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lxj
 * @since 2024-08-05
 */
@RestController
@RequestMapping("viva525/carousel")
public class CarouselController {
    @Autowired
    private ICarouselService carouselService;

    @GetMapping("/list")
    public Response list() {
        try {
            Object obj = carouselService.list();
            return Response.success(obj);
        } catch (Exception e) {
            return Response.fail("Failed to fetch data: " + e.getMessage());
        }

    }

    @Value("${file.upload-dir}") // 配置文件中指定的上传目录
    private String uploadDir;

    @PostMapping("/upload")
    public Response handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Response.fail("上传文件为空");
        }
        try {
            // 获取文件的原始名称
            String originalFilename = file.getOriginalFilename();
            // 生成唯一文件名
            String fileName = UUID.randomUUID().toString() + "_" + originalFilename;
            // 保存文件到本地目录
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDir + File.separator + fileName);
            Files.write(path, bytes);

            // 返回文件的访问路径，可以是相对路径或者完整URL
            return Response.success("/" + fileName);

        } catch (IOException e) {
            return Response.fail("Failed to upload file: " + e.getMessage());
        }
    }

    @PostMapping("/add")
    public Response addCarousel(@RequestBody Carousel carousel) {
        try {
            carouselService.save(carousel);
            return Response.success();
        } catch (Exception e) {
            return Response.fail("Failed to add data: " + e.getMessage());
        }
    }

    @PostMapping("/update")
    public Response updateCarousel(@RequestBody Carousel carousel) {
        try {
            // 先删除原图片
            if (carousel.getId() != null) {
                Carousel existingCarousel = carouselService.getById(carousel.getId());
                if (existingCarousel != null && existingCarousel.getPicture() != null) {
                    // 获取原图片的相对路径
                    String existingFileName = existingCarousel.getPicture();
                    // 构建原图片在文件系统中的路径
                    Path existingFilePath = Paths.get(uploadDir + existingFileName);
                    // 删除原图片
                    Files.deleteIfExists(existingFilePath);
                }
            }
            carouselService.updateById(carousel);
            return Response.success();
        } catch (Exception e) {
            return Response.fail("Failed to update data: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public Response deleteCarousel(Integer id) {
        try {
            carouselService.removeById(id);
            return Response.success();
        } catch (Exception e) {
            return Response.fail("Failed to delete data: " + e.getMessage());
        }
    }
}
