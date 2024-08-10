package com.admin.backend.controller;

import com.admin.backend.entity.Award;
import com.admin.backend.entity.User;
import com.admin.backend.service.IUserService;
import com.admin.backend.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lxj
 * @since 2024-08-05
 */
@RestController
@RequestMapping("viva525/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/list")
    public Response list() {
        try {
            Object obj = userService.list();
            return Response.success(obj);
        } catch (Exception e) {
            return Response.fail("Failed to fetch data: " + e.getMessage());
        }

    }

    @PostMapping("/login")
    public Response login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        User user = userService.findByUsernameAndPassword(username, password);
        if (user == null) {
            return Response.fail("201","账号或密码不正确");
        } else {
            return Response.success("200","登录成功",user);
        }
    }

    @GetMapping("/info")
    public Response getUserInfo(@RequestHeader("token") String token) {
        User user = userService.findByToken(token);
        if (user == null) {
            return Response.fail("201","获取用户信息失败");
        } else {
            return Response.success("200","获取成功",user);
        }
    }

    @PostMapping("/add")
    public Response addUser(@RequestBody User user) {
        try {
            userService.save(user);
            return Response.success();
        } catch (Exception e) {
            return Response.fail("Failed to add data: " + e.getMessage());
        }
    }

    @PostMapping("/update")
    public Response updateUser(@RequestBody User user) {
        try {
            userService.updateById(user);
            return Response.success();
        } catch (Exception e) {
            return Response.fail("Failed to update data: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public Response deleteUser(Integer id) {
        try {
            userService.removeById(id);
            return Response.success();
        } catch (Exception e) {
            return Response.fail("Failed to delete data: " + e.getMessage());
        }
    }
}
