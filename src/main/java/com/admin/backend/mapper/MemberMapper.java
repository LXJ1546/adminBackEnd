package com.admin.backend.mapper;

import com.admin.backend.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lxj
 * @since 2024-08-05
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {

}
