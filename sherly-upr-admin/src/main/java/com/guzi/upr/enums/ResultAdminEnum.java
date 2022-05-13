package com.guzi.upr.enums;

import com.guzi.upr.exception.IBaseError;

/**
 * @author 谷子毅
 * @date 2022/3/23
 */
public enum ResultAdminEnum implements IBaseError {

    /**
     * 错误示例
     */
    ERROR("999999", "服务器错误"),

    TENANT_REPEAT("100005", "该租户已存在！"),
    USER_REPEAT("100006", "该用户已存在！"),
    ROLE_REPEAT("100007", "该角色已存在！"),
    DEPARTMENT_REPEAT("100008", "该部门已存在！"),
    MENU_BOUND_ROLE("100009", "当前菜单已绑定角色，禁止删除！"),
    MENU_HAS_CHILDREN("100010", "当前菜单存在子菜单，禁止删除！"),
    MENU_PARENT_SELF("100011", "上级菜单不可为当前编辑菜单！"),
    ROLE_BOUND_USER("100012", "当前角色已绑定用户，禁止删除！");

    private final String code;
    private final String message;

    ResultAdminEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
