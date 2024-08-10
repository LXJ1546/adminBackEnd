package com.admin.backend.controller;

import com.admin.backend.entity.News;
import com.admin.backend.service.INewsService;
import com.admin.backend.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
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
@RequestMapping("viva525/news")
public class NewsController {
    @Autowired
    private INewsService newsService;

    @Value("${file.upload-dir}") // 配置文件中指定的上传目录
    private String uploadDir;

    @GetMapping("/list")
    public Response list() {
        try {
            Object obj = newsService.list();
            return Response.success(obj);
        } catch (Exception e) {
            return Response.fail("Failed to fetch data: " + e.getMessage());
        }

    }

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
    public Response addNews(@RequestBody News news) {
        try {
            newsService.save(news);
            return Response.success();
        } catch (Exception e) {
            return Response.fail("Failed to add data: " + e.getMessage());
        }
    }

    @PostMapping("/update")
    public Response updateNews(@RequestBody News news) {
        try {
            // 先删除原图片,不过得判断前端是否更改了图片，没有更改则不需要
            if (news.getId() != null) {
                News existingMember = newsService.getById(news.getId());
                if (existingMember != null && !Objects.equals(existingMember.getPicture(), news.getPicture())) {
                    // 获取原图片的相对路径
                    String existingFileName = existingMember.getPicture();
                    // 构建原图片在文件系统中的路径
                    Path existingFilePath = Paths.get(uploadDir + existingFileName);
                    // 删除原图片
                    Files.deleteIfExists(existingFilePath);
                }
            }
            newsService.updateById(news);
            return Response.success();
        } catch (Exception e) {
            return Response.fail("Failed to update data: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public Response deleteNews(Integer id) {
        try {
            // 删除操作同样会删除本地文件夹中的图片
            News existingNews = newsService.getById(id);
            // 这里同样需要判断是否保存有路径，没有则不需要删除图片
            if (existingNews != null && !Objects.equals(existingNews.getPicture(), "")) {
                // 获取原图片的相对路径
                String existingFileName = existingNews.getPicture();
                // 构建原图片在文件系统中的路径
                Path existingFilePath = Paths.get(uploadDir + existingFileName);
                // 删除原图片
                Files.deleteIfExists(existingFilePath);
            }
            newsService.removeById(id);
            return Response.success();
        } catch (Exception e) {
            return Response.fail("Failed to delete data: " + e.getMessage());
        }
    }
}
