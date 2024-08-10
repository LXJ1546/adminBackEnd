package com.admin.backend.generate;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import java.nio.file.Paths;

public class CodeGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/admin?useSSL=false&serverTimezone=UTC", "root", "114514")
                .globalConfig(builder -> builder
                        .author("lxj")
                        .outputDir(Paths.get(System.getProperty("user.dir")) + "/src/main/java")
                        .commentDate("yyyy-MM-dd")
                )
                .packageConfig(builder -> builder
                        .parent("com.admin.backend")
                        .entity("entity")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .xml("mapper.xml")
                        .controller("controller")


                )
                .strategyConfig(builder -> builder
                        .entityBuilder()
                        .enableLombok()
                        .enableTableFieldAnnotation()
                        .controllerBuilder()
                        .enableRestStyle()
                        .mapperBuilder()
                        .enableMapperAnnotation()
                )

                .execute();
    }
}

