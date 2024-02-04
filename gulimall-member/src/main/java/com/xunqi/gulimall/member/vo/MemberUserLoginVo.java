package com.xunqi.gulimall.member.vo;

import lombok.Data;
import org.apache.ibatis.annotations.Param;

/**
 * @Description:
 *
 * @Created: with IntelliJ IDEA.
 * @author: 夏沫止水
 * @createTime: 2020-06-27 19:29
 **/

@Data
public class MemberUserLoginVo {

    //用户名
    private String loginacct;

    private String password;

}
