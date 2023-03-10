package com.guzi.upr.model.exception.enums;


import com.guzi.upr.model.exception.IBaseError;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 谷子毅
 * @date 2022/3/23
 */
@Getter
@AllArgsConstructor
public enum AdminErrorEnum implements IBaseError {

    /*=============================租户管理========================*/
    TENANT_REPEAT("000-001-001", "该租户已存在！"),
    USER_LIMIT("000-001-002", "用户注册已达上限！"),
    TENANT_EXPIRED("000-001-003", "最近登录【%s】授权已到期，请联系服务商！"),
    NOT_IN_ACCOUNT("000-001-004", "该账号在当前选择的租户下未授权！"),
    TENANT_MISS("000-001-005", "当前租户不存在！"),
    NO_TENANT("000-001-007", "当前账号无可登录的租户！"),
    DELETE_TENANT_ERROR("000-001-008", "顶级租户禁止删除！"),
    /*=============================用户管理========================*/
    USER_REPEAT("000-002-001", "该用户已存在！"),
    FORBIDDEN("000-002-002", "当前账号已被禁用！"),
    NO_REGISTER("000-002-003", "当前账号未注册！"),
    ERR_USR_PWD("000-002-004", "用户名或密码错误！"),
    DELETE_USER_ERROR("000-002-005", "顶级用户禁止删除！"),
    BAN_USER_ERROR("000-002-006", "顶级用户禁止禁用！"),
    UPDATE_USER_ERROR("000-002-007", "顶级用户禁止修改！"),
    /*=============================角色管理========================*/
    ROLE_REPEAT("000-003-001", "该角色已存在！"),
    ROLE_BOUND_USER("000-003-002", "当前角色已绑定用户，禁止删除！"),
    DELETE_ROLE_ERROR("000-003-003", "顶级角色禁止删除！"),
    /*=============================菜单管理========================*/
    MENU_BOUND_ROLE("000-004-001", "当前菜单已绑定角色，禁止删除！"),
    MENU_HAS_CHILDREN("000-004-002", "当前菜单存在子菜单，禁止删除！"),
    MENU_PARENT_SELF("000-004-003", "上级菜单不可为当前编辑菜单！"),
    /*=============================部门管理========================*/
    DEPARTMENT_REPEAT("000-005-001", "该部门已存在！"),
    DELETE_DEPT_ERROR("000-005-002", "顶级部门禁止删除！"),
    UPDATE_DEPT_ERROR("000-005-003", "顶级部门禁止修改！"),
    /*============================个人中心=======================*/
    USER_PASSWORD_ERROR("000-006-001", "旧密码错误！"),
    USER_PASSWORD_REPEAT("000-006-002", "新旧密码相同，禁止修改！"),
    /*============================邮件管理=======================*/
    NO_EMAIL_CONFIG("000-050-001", "失败，请检查邮箱配置！"),
    /*============================对象存储=======================*/
    NO_OSS_CONFIG("000-051-001", "失败，请检查存储配置！"),
    ;

    private final String code;
    private final String message;
}
