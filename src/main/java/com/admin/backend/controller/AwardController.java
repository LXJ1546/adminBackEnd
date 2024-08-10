package com.admin.backend.controller;

import com.admin.backend.entity.Award;
import com.admin.backend.service.IAwardService;
import com.admin.backend.utils.Response;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lxj
 * @since 2024-08-05
 */
@RestController
@RequestMapping("viva525/award")
public class AwardController {
    @Autowired
    private IAwardService awardService;

    @Value("${file.upload-dir}") // 配置文件中指定的上传目录
    private String uploadDir;

    @GetMapping("/list")
    public Response list() {
        try {
            Object obj = awardService.list();
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
    public Response addAward(@RequestBody Award award) {
        try {
            awardService.save(award);
            return Response.success();
        } catch (Exception e) {
            return Response.fail("Failed to add data: " + e.getMessage());
        }
    }

    @PostMapping("/update")
    public Response updateAward(@RequestBody Award award) {
        try {
            // 先删除原图片,不过得判断前端是否更改了图片，没有更改则不需要
            if (award.getId() != null) {
                Award existingMember = awardService.getById(award.getId());
                if (existingMember != null && !Objects.equals(existingMember.getPicture(), award.getPicture())) {
                    // 获取原图片的相对路径
                    String existingFileName = existingMember.getPicture();
                    // 构建原图片在文件系统中的路径
                    Path existingFilePath = Paths.get(uploadDir + existingFileName);
                    // 删除原图片
                    Files.deleteIfExists(existingFilePath);
                }
            }
            awardService.updateById(award);
            return Response.success();
        } catch (Exception e) {
            return Response.fail("Failed to update data: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public Response deleteAward(Integer id) {
        try {
            // 删除操作同样会删除本地文件夹中的图片
            Award existingAward = awardService.getById(id);
            // 这里同样需要判断是否保存有路径，没有则不需要删除图片
            if (existingAward != null && !Objects.equals(existingAward.getPicture(), "")) {
                // 获取原图片的相对路径
                String existingFileName = existingAward.getPicture();
                // 构建原图片在文件系统中的路径
                Path existingFilePath = Paths.get(uploadDir + existingFileName);
                // 删除原图片
                Files.deleteIfExists(existingFilePath);
            }
            awardService.removeById(id);
            return Response.success();
        } catch (Exception e) {
            return Response.fail("Failed to delete data: " + e.getMessage());
        }
    }

    //模糊查询
    @PostMapping("/listP")
    public List<Award> listP(@RequestBody Award award) {
        LambdaQueryWrapper<Award> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Award::getTitle, award.getTitle());
        return awardService.list(lambdaQueryWrapper);
    }
}
