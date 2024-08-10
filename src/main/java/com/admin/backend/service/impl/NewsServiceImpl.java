package com.admin.backend.service.impl;

import com.admin.backend.entity.News;
import com.admin.backend.mapper.NewsMapper;
import com.admin.backend.service.INewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lxj
 * @since 2024-08-05
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements INewsService {

}
