package com.guzi.sherly.model.contants;

/**
 * 通用常量
 * <h1>"七七事变" 85周年祭：勿忘国耻 吾辈当自强！
 * @author 谷子毅
 * @date 2022/7/7
 */
public class CommonConstants {

    /*=============================布尔真假========================*/

    /** 真 */
    public static final String TRUE = "true";

    /** 假 */
    public static final String FALSE = "false";

    /*=============================逻辑删除========================*/

    /** 未删除 */
    public static final Integer EXIST = 0;

    /** 已删除 */
    public static final Integer DELETED = 1;

    /*=============================启用禁用========================*/

    /** 启用 */
    public static final Integer ENABLE = 1;

    /** 禁用 */
    public static final Integer DISABLE = 0;

    /*=============================树形结构========================*/

    /** 顶级节点的父id */
    public static final Long ROOT_PARENT_ID = 0L;

    /*==============================性别===========================*/

    /** 性别：女 */
    public static final Integer FEMALE = 0;

    /** 性别：男 */
    public static final Integer MALE = 1;

    /** 性别：未知 */
    public static final Integer NO_GENDER = 2;

    /*=============================菜单类型========================*/

    /** 菜单类型：目录 */
    public static final Integer DIR = 1;

    /** 菜单类型：菜单 */
    public static final Integer MENU = 2;

    /** 菜单类型：按钮 */
    public static final Integer BUTTON = 3;

    /*=============================日志类型========================*/

    /** 日志类型：正常 */
    public static final Integer NORMAL_LOG = 0;

    /** 日志类型：异常 */
    public static final Integer EXCEPTION_LOG = 1;

    /*=============================登录结果========================*/

    /** 登录结果：成功 */
    public static final Integer LOGIN_LOG_SUCCESS = 0;

    /** 登录结果：账号或密码不正确 */
    public static final Integer LOGIN_LOG_FAIL = 1;

    /** 登录结果：用户禁用 */
    public static final Integer LOGIN_LOG_DISABLE = 2;

    /** 登录结果：其他 */
    public static final Integer LOGIN_LOG_OTHER = 9;

    /*=============================登录方式========================*/

    /** 登录方式：密码 */
    public static final Integer LOGIN_TYPE_PASSWORD = 0;

    /** 登录方式：二维码 */
    public static final Integer LOGIN_TYPE_QRCODE = 1;

    /** 登录方式：QQ */
    public static final Integer LOGIN_TYPE_QQ = 2;

    /** 登录方式：微信 */
    public static final Integer LOGIN_TYPE_WX = 3;

    /** 登录方式：支付宝 */
    public static final Integer LOGIN_TYPE_ZFB = 4;

}
