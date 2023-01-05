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

    /** 顶级节点的父编号 */
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

}
