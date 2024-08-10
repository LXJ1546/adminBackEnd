package com.admin.backend.controller;

import com.admin.backend.entity.News;
import com.admin.backend.entity.Publication;
import com.admin.backend.service.IPublicationService;
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
@RequestMapping("viva525/publication")
public class PublicationController {
    @Autowired
    private IPublicationService publicationService;

    @Value("${file.upload-dir}") // 配置文件中指定的上传目录
    private String uploadDir;

    @GetMapping("/list")
    public Response list() {
        try {
            Object obj = publicationService.list();
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
    public Response addPublication(@RequestBody Publication publication) {
        try {
            publicationService.save(publication);
            return Response.success();
        } catch (Exception e) {
            return Response.fail("Failed to add data: " + e.getMessage());
        }
    }

    @PostMapping("/update")
    public Response updatePublication(@RequestBody Publication publication) {
        try {
            // 先删除原图片,不过得判断前端是否更改了图片，没有更改则不需要
            if (publication.getId() != null) {
                Publication existingMember = publicationService.getById(publication.getId());
                if (existingMember != null && !Objects.equals(existingMember.getPicture(), publication.getPicture())) {
                    // 获取原图片的相对路径
                    String existingFileName = existingMember.getPicture();
                    // 构建原图片在文件系统中的路径
                    Path existingFilePath = Paths.get(uploadDir + existingFileName);
                    // 删除原图片
                    Files.deleteIfExists(existingFilePath);
                }
            }
            publicationService.updateById(publication);
            return Response.success();
        } catch (Exception e) {
            return Response.fail("Failed to update data: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public Response deletePublication(Integer id) {
        try {
            // 删除操作同样会删除本地文件夹中的图片
            Publication existingPublication = publicationService.getById(id);
            // 这里同样需要判断是否保存有路径，没有则不需要删除图片
            if (existingPublication != null && !Objects.equals(existingPublication.getPicture(), "")) {
                // 获取原图片的相对路径
                String existingFileName = existingPublication.getPicture();
                // 构建原图片在文件系统中的路径
                Path existingFilePath = Paths.get(uploadDir + existingFileName);
                // 删除原图片
                Files.deleteIfExists(existingFilePath);
            }
            publicationService.removeById(id);
            return Response.success();
        } catch (Exception e) {
            return Response.fail("Failed to delete data: " + e.getMessage());
        }
    }
}
