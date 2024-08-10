package com.admin.backend.service.impl;

import com.admin.backend.entity.Member;
import com.admin.backend.mapper.MemberMapper;
import com.admin.backend.service.IMemberService;
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
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {

}
