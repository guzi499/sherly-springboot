package com.guzi.upr.security.model;

import com.guzi.upr.model.admin.OnlineUser;
import lombok.Data;

import java.util.List;

/**
 * SecurityModel缓存
 *
 * @author 谷子毅
 * @date 2022/5/23
 */
@Data
public class RedisSecurityModel {

    private SecurityModel securityModel;

    private List<String> permissions;

    private OnlineUser onlineUser;
}
