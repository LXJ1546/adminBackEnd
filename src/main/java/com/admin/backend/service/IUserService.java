package com.admin.backend.service;

import com.admin.backend.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lxj
 * @since 2024-08-05
 */
public interface IUserService extends IService<User> {

    User findByUsernameAndPassword(String username, String password);

    User findByToken(String token);
}
