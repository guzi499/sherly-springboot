/*
 Navicat Premium Data Transfer

 Source Server         : 服务器
 Source Server Type    : MySQL
 Source Server Version : 50651
 Source Host           : 101.34.169.185:3306
 Source Schema         : sherly-1.1

 Target Server Type    : MySQL
 Target Server Version : 50651
 File Encoding         : 65001

 Date: 05/05/2022 17:09:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ge_account_user
-- ----------------------------
DROP TABLE IF EXISTS `ge_account_user`;
CREATE TABLE `ge_account_user`
(
    `account_user_id`        bigint(20) UNSIGNED                                           NOT NULL COMMENT '账户用户id',
    `phone`                  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
    `tenant_data`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户信息',
    `last_login_tenant_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后登录租户代码',
    `create_time`            datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`            datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`account_user_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ge_account_user
-- ----------------------------
INSERT INTO `ge_account_user`
VALUES (1, '18888888888', 'sherly', 'sherly', '2022-05-05 16:29:56', '2022-05-05 16:29:58');

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department`
(
    `department_id`   bigint(20) UNSIGNED                                           NOT NULL AUTO_INCREMENT COMMENT '部门id',
    `department_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门名称',
    `description`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
    `parent_id`       bigint(20) UNSIGNED                                           NULL DEFAULT 0 COMMENT '父部门id',
    `sort`            int(10) UNSIGNED                                              NULL DEFAULT NULL COMMENT '排序',
    `create_time`     datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`     datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    `create_user_id`  bigint(20) UNSIGNED                                           NULL DEFAULT NULL COMMENT '创建人id',
    `update_user_id`  bigint(20) UNSIGNED                                           NULL DEFAULT NULL COMMENT '更新人id',
    `is_deleted`      tinyint(3) UNSIGNED                                           NULL DEFAULT 0 COMMENT '0未删除 1已删除',
    PRIMARY KEY (`department_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_department
-- ----------------------------
INSERT INTO `sys_department`
VALUES (1, 'sherly总部', 'sherly总部', 0, 1, '2022-05-05 16:31:07', '2022-05-05 16:31:07', 1, 1, 0);
INSERT INTO `sys_department`
VALUES (2, '财务部', 'sherly财务部', 1, 1, '2022-05-05 16:31:07', '2022-05-05 16:31:07', 1, 1, 0);
INSERT INTO `sys_department`
VALUES (3, '人资部', 'sherly人资部', 1, 2, '2022-05-05 16:31:07', '2022-05-05 16:31:07', 1, 1, 0);
INSERT INTO `sys_department`
VALUES (4, '开发部', 'sherly开发部', 1, 3, '2022-05-05 16:31:07', '2022-05-05 16:31:07', 1, 1, 0);
INSERT INTO `sys_department`
VALUES (5, '前端开发组', 'sherly前端开发组', 4, 1, '2022-05-05 16:31:07', '2022-05-05 16:31:07', 1, 1, 0);
INSERT INTO `sys_department`
VALUES (6, '后端开发组', 'sherly后端开发组', 4, 2, '2022-05-05 16:31:07', '2022-05-05 16:31:07', 1, 1, 0);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `menu_id`        bigint(20) UNSIGNED                                           NOT NULL AUTO_INCREMENT COMMENT '菜单id',
    `menu_type`      tinyint(4)                                                    NULL DEFAULT NULL COMMENT '菜单类型 1目录 2菜单 3按钮',
    `permission`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限',
    `menu_name`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
    `parent_id`      bigint(20) UNSIGNED                                           NULL DEFAULT 0 COMMENT '父菜单id',
    `link`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单路径',
    `icon`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
    `sort`           int(10) UNSIGNED                                              NULL DEFAULT NULL COMMENT '排序',
    `create_time`    datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    `create_user_id` bigint(20) UNSIGNED                                           NULL DEFAULT NULL COMMENT '创建人id',
    `update_user_id` bigint(20) UNSIGNED                                           NULL DEFAULT NULL COMMENT '更新人id',
    `is_deleted`     tinyint(3) UNSIGNED                                           NULL DEFAULT 0 COMMENT '0未删除 1已删除',
    PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 14
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu`
VALUES (1, 1, NULL, '系统管理', 0, NULL, NULL, 1, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (2, 1, NULL, '系统监控', 0, NULL, NULL, 2, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (3, 2, 'system:user', '用户管理', 1, '/system/user', NULL, NULL, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1,
        0);
INSERT INTO `sys_menu`
VALUES (4, 2, 'system:role', '角色管理', 1, '/system/role', NULL, NULL, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1,
        0);
INSERT INTO `sys_menu`
VALUES (5, 2, 'system:department', '部门管理', 1, '/system/department', NULL, NULL, '2022-05-05 16:35:17',
        '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (6, 2, 'system:menu', '菜单管理', 1, '/system/menu', NULL, NULL, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1,
        0);
INSERT INTO `sys_menu`
VALUES (7, 2, 'system:tenant', '租户管理', 1, '/system/tenant', NULL, NULL, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1,
        1, 0);
INSERT INTO `sys_menu`
VALUES (8, 2, 'monitor:online', '在线用户', 2, '/monitor/online', NULL, NULL, '2022-05-05 16:35:17', '2022-05-05 16:35:17',
        1, 1, 0);
INSERT INTO `sys_menu`
VALUES (9, 2, 'monitor:log:operate', '操作日志', 2, '/monitor/log/operate', NULL, NULL, '2022-05-05 16:35:17',
        '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (10, 2, 'monitor:log:exception', '异常日志', 2, '/monitor/log/exception', NULL, NULL, '2022-05-05 16:35:17',
        '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (11, 1, NULL, '测试一级目录', 0, NULL, NULL, NULL, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (12, 2, NULL, '测试二级目录', 11, NULL, NULL, NULL, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (13, 2, NULL, '测试三级目录', 12, '/test', NULL, NULL, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `role_id`        bigint(20) UNSIGNED                                           NOT NULL AUTO_INCREMENT COMMENT '角色id',
    `role_name`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
    `description`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
    `create_time`    datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    `create_user_id` bigint(20) UNSIGNED                                           NULL DEFAULT NULL COMMENT '创建人id',
    `update_user_id` bigint(20) UNSIGNED                                           NULL DEFAULT NULL COMMENT '更新人id',
    `is_deleted`     tinyint(3) UNSIGNED                                           NULL DEFAULT 0 COMMENT '0未删除 1已删除',
    PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role`
VALUES (1, '超级管理员', '拥有最高权限', '2022-05-05 16:54:50', '2022-05-05 16:54:50', 1, 1, 0);
INSERT INTO `sys_role`
VALUES (2, '平台管理员', '拥有操作租户全部权限', '2022-05-05 16:54:50', '2022-05-05 16:54:50', 1, 1, 0);
INSERT INTO `sys_role`
VALUES (3, '管理员', '拥有当前租户全部权限', '2022-05-05 16:54:50', '2022-05-05 16:54:50', 1, 1, 0);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
    `id`             bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `role_id`        bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '角色id',
    `menu_id`        bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '菜单id',
    `create_time`    datetime(0)         NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime(0)         NULL DEFAULT NULL COMMENT '更新时间',
    `create_user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人id',
    `update_user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '更新人id',
    `is_deleted`     tinyint(3) UNSIGNED NULL DEFAULT 0 COMMENT '0未删除 1已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 14
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu`
VALUES (1, 1, 1, '2022-05-05 16:57:44', '2022-05-05 16:57:44', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (2, 1, 2, '2022-05-05 16:57:44', '2022-05-05 16:57:44', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (3, 1, 3, '2022-05-05 16:57:44', '2022-05-05 16:57:44', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (4, 1, 4, '2022-05-05 16:57:44', '2022-05-05 16:57:44', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (5, 1, 5, '2022-05-05 16:57:44', '2022-05-05 16:57:44', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (6, 1, 6, '2022-05-05 16:57:44', '2022-05-05 16:57:44', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (7, 1, 7, '2022-05-05 16:57:44', '2022-05-05 16:57:44', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (8, 1, 8, '2022-05-05 16:57:44', '2022-05-05 16:57:44', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (9, 1, 9, '2022-05-05 16:57:44', '2022-05-05 16:57:44', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (10, 1, 10, '2022-05-05 16:57:44', '2022-05-05 16:57:44', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (11, 1, 11, '2022-05-05 16:57:44', '2022-05-05 16:57:44', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (12, 1, 12, '2022-05-05 16:57:44', '2022-05-05 16:57:44', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (13, 1, 13, '2022-05-05 16:57:44', '2022-05-05 16:57:44', 1, 1, 0);

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant`
(
    `tenant_id`      bigint(20) UNSIGNED                                           NOT NULL AUTO_INCREMENT COMMENT '租户id',
    `tenant_code`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户code',
    `tenant_name`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户名称',
    `contact_user`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人',
    `contact_phone`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
    `expire_time`    datetime(0)                                                   NULL DEFAULT NULL COMMENT '过期时间',
    `user_limit`     bigint(255)                                                   NULL DEFAULT NULL COMMENT '用户上限',
    `create_time`    datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    `create_user_id` bigint(20) UNSIGNED                                           NULL DEFAULT NULL COMMENT '创建人id',
    `update_user_id` bigint(20) UNSIGNED                                           NULL DEFAULT NULL COMMENT '更新人id',
    `is_deleted`     tinyint(3) UNSIGNED                                           NULL DEFAULT 0 COMMENT '0未删除 1已删除',
    PRIMARY KEY (`tenant_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------
INSERT INTO `sys_tenant`
VALUES (1, 'sherly', 'sherly开源', '谷子毅', '18888888888', '2099-01-01 00:00:00', 9999, '2022-05-05 16:59:14',
        '2022-05-05 16:59:16', 1, 1, 0);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `user_id`         bigint(20) UNSIGNED                                           NOT NULL AUTO_INCREMENT COMMENT '用户id',
    `account_user_id` bigint(20) UNSIGNED                                           NULL DEFAULT NULL COMMENT '账户用户id',
    `nickname`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
    `real_name`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
    `phone`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
    `password`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
    `avatar`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像',
    `email`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户邮箱',
    `gender`          tinyint(3) UNSIGNED                                           NULL DEFAULT NULL COMMENT '性别',
    `department_id`   bigint(20) UNSIGNED                                           NULL DEFAULT NULL COMMENT '部门id',
    `enable`          tinyint(3) UNSIGNED                                           NULL DEFAULT NULL COMMENT '0不可用 1可用',
    `last_login_time` datetime(0)                                                   NULL DEFAULT NULL COMMENT '最后登录时间',
    `last_login_ip`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后登录IP',
    `create_time`     datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`     datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    `create_user_id`  bigint(20) UNSIGNED                                           NULL DEFAULT NULL COMMENT '创建人id',
    `update_user_id`  bigint(20) UNSIGNED                                           NULL DEFAULT NULL COMMENT '更新人id',
    `is_deleted`      tinyint(3) UNSIGNED                                           NULL DEFAULT 0 COMMENT '0未删除 1已删除',
    PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user`
VALUES (1, 1, 'gucci', '谷子毅', '18888888888', '$2a$10$Rx/tS8zBxlhHZVN/tVbIeueFnxcZUHY8T7vgPOmor8Vy9cBFjH6VK', NULL,
        '504305797@qq.com', 1, 1, 1, NULL, NULL, '2022-05-05 17:04:20', '2022-05-05 17:04:22', 1, 1, 0);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `id`             bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id`        bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '用户id',
    `role_id`        bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '角色id',
    `create_time`    datetime(0)         NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime(0)         NULL DEFAULT NULL COMMENT '更新时间',
    `create_user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人id',
    `update_user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '更新人id',
    `is_deleted`     tinyint(3) UNSIGNED NULL DEFAULT 0 COMMENT '0未删除 1已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role`
VALUES (1, 1, 1, '2022-05-05 17:04:36', '2022-05-05 17:04:38', 1, 1, 0);

SET FOREIGN_KEY_CHECKS = 1;
