SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ge_account_user
-- ----------------------------
DROP TABLE IF EXISTS `ge_account_user`;
CREATE TABLE `ge_account_user`
(
    `account_user_id`        bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '账户用户编号',
    `phone`                  char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NOT NULL DEFAULT '' COMMENT '手机号',
    `password`               varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
    `tenant_data`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '租户信息',
    `last_login_tenant_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '最后登录租户代码',
    `create_time`            datetime(0) NOT NULL COMMENT '创建时间',
    `update_time`            datetime(0) NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`account_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ge_account_user
-- ----------------------------
INSERT INTO `ge_account_user`
VALUES (1, '18888888888', '$2a$10$Rx/tS8zBxlhHZVN/tVbIeueFnxcZUHY8T7vgPOmor8Vy9cBFjH6VK', 'sherly', 'sherly',
        '2022-05-05 16:29:56', '2022-05-05 16:29:56');

-- ----------------------------
-- Table structure for ge_error_code
-- ----------------------------
DROP TABLE IF EXISTS `ge_error_code`;
CREATE TABLE `ge_error_code`
(
    `error_id`       int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '错误编号',
    `error_code`     char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NOT NULL DEFAULT '' COMMENT '错误代码',
    `message`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '错误信息',
    `description`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '描述',
    `module_id`      int(11) UNSIGNED NOT NULL COMMENT '模块编号',
    `create_time`    datetime(0) NOT NULL COMMENT '创建时间',
    `update_time`    datetime(0) NOT NULL COMMENT '更新时间',
    `create_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '创建人编号',
    `update_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '更新人编号',
    `is_deleted`     tinyint(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除[enum]',
    PRIMARY KEY (`error_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ge_error_code
-- ----------------------------

-- ----------------------------
-- Table structure for ge_module
-- ----------------------------
DROP TABLE IF EXISTS `ge_module`;
CREATE TABLE `ge_module`
(
    `module_id`      int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '模块编号',
    `module_code`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '模块代码',
    `module_name`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '模块名称',
    `sort`           int(11) UNSIGNED NOT NULL COMMENT '排序',
    `parent_id`      int(11) UNSIGNED NOT NULL COMMENT '父模块编号',
    `create_time`    datetime(0) NOT NULL COMMENT '创建时间',
    `update_time`    datetime(0) NOT NULL COMMENT '更新时间',
    `create_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '创建人编号',
    `update_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '更新人编号',
    `is_deleted`     tinyint(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除[enum]',
    PRIMARY KEY (`module_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ge_module
-- ----------------------------

-- ----------------------------
-- Table structure for ge_tenant
-- ----------------------------
DROP TABLE IF EXISTS `ge_tenant`;
CREATE TABLE `ge_tenant`
(
    `tenant_id`      int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '租户编号',
    `tenant_code`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '租户代码',
    `tenant_name`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '租户名称',
    `contact_user`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '联系人',
    `contact_phone`  char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NOT NULL DEFAULT '' COMMENT '联系电话',
    `expire_time`    datetime(0) NOT NULL COMMENT '过期时间',
    `user_limit`     bigint(20) UNSIGNED NOT NULL COMMENT '用户上限',
    `create_time`    datetime(0) NOT NULL COMMENT '创建时间',
    `update_time`    datetime(0) NOT NULL COMMENT '更新时间',
    `create_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '创建人编号',
    `update_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '更新人编号',
    `is_deleted`     tinyint(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除[enum]',
    PRIMARY KEY (`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ge_tenant
-- ----------------------------
INSERT INTO `ge_tenant`
VALUES (1, 'sherly', 'sherly开源', '谷子毅', '18888888888', '2099-01-01 00:00:00', 1000, '2022-05-05 16:59:14',
        '2022-05-05 16:59:14', 1, 1, 0);

-- ----------------------------
-- Table structure for ge_tenant_package
-- ----------------------------
DROP TABLE IF EXISTS `ge_tenant_package`;
CREATE TABLE `ge_tenant_package`
(
    `tenant_package_id`   bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '租户套餐编号',
    `tenant_package_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户套餐名称',
    `description`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '描述',
    `enable`              tinyint(4) UNSIGNED NOT NULL COMMENT '启用禁用[enum]',
    `create_time`         datetime(0) NOT NULL COMMENT '创建时间',
    `update_time`         datetime(0) NOT NULL COMMENT '更新时间',
    `create_user_id`      bigint(20) UNSIGNED NOT NULL COMMENT '创建人编号',
    `update_user_id`      bigint(20) UNSIGNED NOT NULL COMMENT '更新人编号',
    `is_deleted`          tinyint(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除[enum]',
    PRIMARY KEY (`tenant_package_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ge_tenant_package
-- ----------------------------

-- ----------------------------
-- Table structure for ge_tenant_package_menu
-- ----------------------------
DROP TABLE IF EXISTS `ge_tenant_package_menu`;
CREATE TABLE `ge_tenant_package_menu`
(
    `id`                bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',
    `tenant_package_id` bigint(20) UNSIGNED NOT NULL COMMENT '租户套餐编号',
    `menu_id`           bigint(20) UNSIGNED NOT NULL COMMENT '菜单编号',
    `create_time`       datetime(0) NOT NULL COMMENT '创建时间',
    `update_time`       datetime(0) NOT NULL COMMENT '更新时间',
    `create_user_id`    bigint(20) UNSIGNED NOT NULL COMMENT '创建人编号',
    `update_user_id`    bigint(20) UNSIGNED NOT NULL COMMENT '更新人编号',
    `is_deleted`        tinyint(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除[enum]',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ge_tenant_package_menu
-- ----------------------------

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department`
(
    `department_id`   bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '部门编号',
    `department_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '部门名称',
    `description`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '描述',
    `parent_id`       bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '父部门编号',
    `sort`            int(11) UNSIGNED NOT NULL COMMENT '排序',
    `create_time`     datetime(0) NOT NULL COMMENT '创建时间',
    `update_time`     datetime(0) NOT NULL COMMENT '更新时间',
    `create_user_id`  bigint(20) UNSIGNED NOT NULL COMMENT '创建人编号',
    `update_user_id`  bigint(20) UNSIGNED NOT NULL COMMENT '更新人编号',
    `is_deleted`      tinyint(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除[enum]',
    PRIMARY KEY (`department_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_department
-- ----------------------------
INSERT INTO `sys_department`
VALUES (1, 'sherly开源', '', 0, 1, '2022-05-05 16:31:07', '2022-05-05 16:31:07', 1, 1, 0);
INSERT INTO `sys_department`
VALUES (2, '财务部', 'sherly财务部', 1, 3, '2022-05-05 16:31:07', '2022-05-05 16:31:07', 1, 1, 0);
INSERT INTO `sys_department`
VALUES (3, '人资部', 'sherly人资部', 1, 2, '2022-05-05 16:31:07', '2022-05-05 16:31:07', 1, 1, 0);
INSERT INTO `sys_department`
VALUES (4, '开发部', 'sherly开发部', 1, 3, '2022-05-05 16:31:07', '2022-05-05 16:31:07', 1, 1, 0);
INSERT INTO `sys_department`
VALUES (5, '前端开发组', 'sherly前端开发组', 4, 1, '2022-05-05 16:31:07', '2022-05-05 16:31:07', 1, 1, 0);
INSERT INTO `sys_department`
VALUES (6, '后端开发组', 'sherly后端开发组', 4, 2, '2022-05-05 16:31:07', '2022-05-05 16:31:07', 1, 1, 0);

-- ----------------------------
-- Table structure for sys_email_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_email_config`;
CREATE TABLE `sys_email_config`
(
    `id`             int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',
    `host`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '邮件服务器SMTP地址',
    `port`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '邮件服务器SMTP端口',
    `sender_user`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '发件人名称',
    `password`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
    `sender_email`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '发件人邮箱',
    `create_time`    datetime(0) NOT NULL COMMENT '创建时间',
    `update_time`    datetime(0) NOT NULL COMMENT '更新时间',
    `create_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '创建人编号',
    `update_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '更新人编号',
    `is_deleted`     tinyint(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除[enum]',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_email_config
-- ----------------------------

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log`
(
    `log_id`      bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '日志编号',
    `username`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '登录账号',
    `type`        int(11) UNSIGNED NOT NULL COMMENT '登录方式[enum]',
    `ip`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '请求ip',
    `address`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '请求地址',
    `os`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '请求系统',
    `browser`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '请求浏览器',
    `result`      tinyint(4) UNSIGNED NOT NULL COMMENT '登录结果[enum]',
    `create_time` datetime(0) NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `menu_id`        bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '菜单编号',
    `menu_type`      tinyint(4) UNSIGNED NOT NULL COMMENT '菜单类型[enum]',
    `permission`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '权限',
    `menu_name`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '菜单名称',
    `parent_id`      bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '父菜单编号',
    `link`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '菜单路径',
    `icon`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '菜单图标',
    `sort`           int(11) UNSIGNED NOT NULL COMMENT '排序',
    `create_time`    datetime(0) NOT NULL COMMENT '创建时间',
    `update_time`    datetime(0) NOT NULL COMMENT '更新时间',
    `create_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '创建人编号',
    `update_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '更新人编号',
    `is_deleted`     tinyint(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除[enum]',
    PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu`
VALUES (1, 1, '', '系统管理', 0, '', 'system', 1, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (2, 1, '', '系统监控', 0, '', 'visible', 2, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (3, 2, 'user:list_page', '用户管理', 1, 'system/user/index', 'blank', 3, '2022-05-05 16:35:17',
        '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (4, 3, 'user:get_one', '用户详情', 3, '', 'blank', 4, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (5, 3, 'user:ban_one', '用户禁用/启用', 3, '', 'blank', 5, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (6, 3, 'user:save_one', '用户新增', 3, '', 'blank', 6, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (7, 3, 'user:update_one', '用户更新', 3, '', 'blank', 7, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (8, 3, 'user:remove_one', '用户删除', 3, '', 'blank', 8, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (9, 2, 'role:list_page', '角色管理', 1, 'system/role/index', 'blank', 9, '2022-05-05 16:35:17',
        '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (10, 3, 'role:get_one', '角色详情', 9, '', 'blank', 10, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (11, 3, 'role:save_one', '角色新增', 9, '', 'blank', 11, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (12, 3, 'role:update_one', '角色更新', 9, '', 'blank', 12, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (13, 3, 'role:remove_one', '角色删除', 9, '', 'blank', 13, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (14, 2, '', '部门管理', 1, 'system/department/index', 'blank', 14, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1,
        1, 0);
INSERT INTO `sys_menu`
VALUES (15, 3, 'department:save_one', '部门新增', 14, '', 'blank', 15, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1,
        0);
INSERT INTO `sys_menu`
VALUES (16, 3, 'department:update_one', '部门更新', 14, '', 'blank', 16, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1,
        0);
INSERT INTO `sys_menu`
VALUES (17, 3, 'department:remove_one', '部门删除', 14, '', 'blank', 17, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1,
        0);
INSERT INTO `sys_menu`
VALUES (18, 2, '', '菜单管理', 1, 'system/menu/index', 'blank', 18, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (19, 3, 'menu:save_one', '菜单新增', 18, '', 'blank', 19, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (20, 3, 'menu:remove_one', '菜单删除', 18, '', 'blank', 20, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (21, 3, 'menu:update_one', '菜单修改', 18, '', 'blank', 21, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (22, 2, 'tenant:list_page', '租户管理', 1, 'system/tenant/index', 'blank', 22, '2022-05-05 16:35:17',
        '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (23, 3, 'tenant:save_one', '租户新增', 22, '', 'blank', 23, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (24, 3, 'tenant:update_one', '租户更新', 22, '', 'blank', 24, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (25, 3, 'tenant:remove_one', '租户删除', 22, '', 'blank', 25, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (26, 2, 'user_online:list_all', '在线用户', 2, 'system/user_online/index', 'blank', 26, '2022-05-05 16:35:17',
        '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (27, 2, 'operation_log:list_page', '操作日志', 2, 'system/operation_log/index', 'blank', 27, '2022-05-05 16:35:17',
        '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (28, 2, 'login_log:list_page', '登录日志', 2, 'system/login_log/index', 'blank', 28, '2022-05-05 16:35:17',
        '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (29, 1, '', '系统配置', 0, '', 'gear', 3, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (30, 1, '', '邮件管理', 29, '', 'blank', 1, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (31, 1, '', '对象存储', 29, '', 'blank', 2, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (32, 3, 'user:list_export', '用户导出', 3, '', 'blank', 3, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (33, 3, 'tenant:update_menu', '租户菜单更新', 22, '', 'blank', 15, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1,
        0);
INSERT INTO `sys_menu`
VALUES (34, 3, 'tenant:list_menu', '租户菜单列表', 22, '', 'blank', 15, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1,
        0);
INSERT INTO `sys_menu`
VALUES (35, 3, 'user_online:force_quit', '强制退出', 26, '', 'blank', 1, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1,
        0);
INSERT INTO `sys_menu`
VALUES (36, 3, 'operation_log:get_one', '操作日志详情', 27, '', 'blank', 1, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1,
        1, 0);
INSERT INTO `sys_menu`
VALUES (37, 3, 'operation_log:remove_all', '操作日志清空', 27, '', 'blank', 2, '2022-05-05 16:35:17', '2022-05-05 16:35:17',
        1, 1, 0);
INSERT INTO `sys_menu`
VALUES (38, 3, 'login_log:remove_all', '登陆日志清空', 28, '', 'blank', 1, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1,
        0);
INSERT INTO `sys_menu`
VALUES (39, 3, 'email_save_or_update', '邮件配置保存或修改', 52, '', 'blank', 1, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1,
        1, 0);
INSERT INTO `sys_menu`
VALUES (40, 2, 'email:send', '邮件发送', 30, 'system/email/email_send/index', 'blank', 2, '2022-05-05 16:35:17',
        '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (41, 2, 'oss_config:list_page', '存储配置', 31, 'system/oss_config/index', 'blank', 1, '2022-05-05 16:35:17',
        '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (42, 2, 'oss:list_page', '内容管理', 31, 'system/oss/index', 'blank', 2, '2022-05-05 16:35:17',
        '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (43, 3, 'oss_config:get_one', '对象存储配置详情', 41, '', 'blank', 1, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1,
        0);
INSERT INTO `sys_menu`
VALUES (44, 3, 'oss_config:enable_one', '对象存储配置激活', 41, '', 'blank', 2, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1,
        1, 0);
INSERT INTO `sys_menu`
VALUES (45, 3, 'oss_config:save_one', '对象存储配置新增', 41, '', 'blank', 3, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1,
        1, 0);
INSERT INTO `sys_menu`
VALUES (46, 3, 'oss_config:update_one', '对象存储配置更新', 41, '', 'blank', 4, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1,
        1, 0);
INSERT INTO `sys_menu`
VALUES (47, 3, 'oss_config:remove_one', '对象存储配置删除', 41, '', 'blank', 5, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1,
        1, 0);
INSERT INTO `sys_menu`
VALUES (48, 3, 'oss:upload_one', '文件上传', 42, '', 'blank', 1, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (49, 3, 'oss:download_one', '文件下载', 42, '', 'blank', 2, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (50, 3, 'oss:access_url', '文件链接', 42, '', 'blank', 3, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (51, 3, 'oss:remove_one', '文件删除', 42, '', 'blank', 4, '2022-05-05 16:35:17', '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (52, 2, 'email:get_one', '邮箱配置', 30, 'system/email/email_config/index', 'blank', 1, '2022-05-05 16:35:17',
        '2022-05-05 16:35:17', 1, 1, 0);
INSERT INTO `sys_menu`
VALUES (53, 2, '', '模块管理', 29, 'system/module_manage/index', 'blank', 3, '2022-11-16 16:03:15', '2022-11-16 16:03:15',
        1, 1, 0);
INSERT INTO `sys_menu`
VALUES (54, 2, '', '异常管理', 29, 'system/error_manage/index', 'blank', 4, '2022-11-16 16:03:46', '2022-11-16 16:03:46', 1,
        1, 0);

-- ----------------------------
-- Table structure for sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log`
(
    `log_id`         bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '日志编号',
    `type`           tinyint(4) UNSIGNED NOT NULL COMMENT '日志类型[enum]',
    `description`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '描述',
    `request_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '请求方式',
    `uri`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '请求uri',
    `request_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求参数',
    `ip`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '请求ip',
    `address`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '请求地址',
    `os`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '请求系统',
    `browser`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '请求浏览器',
    `duration`       int(11) UNSIGNED NOT NULL COMMENT '耗时',
    `exception`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '异常描述',
    `create_time`    datetime(0) NOT NULL COMMENT '创建时间',
    `create_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '创建人编号',
    PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_operation_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_oss_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss_config`;
CREATE TABLE `sys_oss_config`
(
    `config_id`      int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '配置编号',
    `config_name`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT '' COMMENT '配置名称',
    `type`           int(11) UNSIGNED NOT NULL COMMENT '存储类型[enum]',
    `description`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT '' COMMENT '描述',
    `enable`         tinyint(4) UNSIGNED NOT NULL COMMENT '启用禁用[enum]',
    `config`         varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '具体配置',
    `create_time`    datetime(0) NOT NULL COMMENT '创建时间',
    `update_time`    datetime(0) NOT NULL COMMENT '更新时间',
    `create_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '创建人编号',
    `update_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '更新人编号',
    `is_deleted`     tinyint(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除[enum]',
    PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_oss_config
-- ----------------------------

-- ----------------------------
-- Table structure for sys_oss_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss_file`;
CREATE TABLE `sys_oss_file`
(
    `file_id`        bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文件编号',
    `file_name`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文件名称',
    `config_id`      bigint(20) UNSIGNED NOT NULL COMMENT '配置编号',
    `path`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文件相对路径',
    `file_type`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文件类型',
    `size`           int(11) UNSIGNED NOT NULL COMMENT '文件大小',
    `create_time`    datetime(0) NOT NULL COMMENT '创建时间',
    `update_time`    datetime(0) NOT NULL COMMENT '更新时间',
    `create_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '创建人编号',
    `update_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '更新人编号',
    `is_deleted`     tinyint(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除[enum]',
    PRIMARY KEY (`file_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_oss_file
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `role_id`        bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '角色编号',
    `role_name`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '角色名称',
    `description`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '描述',
    `create_time`    datetime(0) NOT NULL COMMENT '创建时间',
    `update_time`    datetime(0) NOT NULL COMMENT '更新时间',
    `create_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '创建人编号',
    `update_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '更新人编号',
    `is_deleted`     tinyint(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除[enum]',
    PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role`
VALUES (1, '超级管理员', '', '2022-05-05 16:54:50', '2022-05-05 16:54:50', 1, 1, 0);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
    `id`             bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',
    `role_id`        bigint(20) UNSIGNED NOT NULL COMMENT '角色编号',
    `menu_id`        bigint(20) UNSIGNED NOT NULL COMMENT '菜单编号',
    `create_time`    datetime(0) NOT NULL COMMENT '创建时间',
    `update_time`    datetime(0) NOT NULL COMMENT '更新时间',
    `create_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '创建人编号',
    `update_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '更新人编号',
    `is_deleted`     tinyint(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除[enum]',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 107 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu`
VALUES (53, 1, 1, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (54, 1, 2, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (55, 1, 3, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (56, 1, 4, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (57, 1, 5, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (58, 1, 6, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (59, 1, 7, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (60, 1, 8, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (61, 1, 9, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (62, 1, 10, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (63, 1, 11, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (64, 1, 12, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (65, 1, 13, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (66, 1, 14, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (67, 1, 15, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (68, 1, 16, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (69, 1, 17, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (70, 1, 18, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (71, 1, 19, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (72, 1, 20, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (73, 1, 21, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (74, 1, 22, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (75, 1, 23, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (76, 1, 24, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (77, 1, 25, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (78, 1, 26, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (79, 1, 27, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (80, 1, 28, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (81, 1, 29, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (82, 1, 30, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (83, 1, 31, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (84, 1, 32, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (85, 1, 33, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (86, 1, 34, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (87, 1, 35, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (88, 1, 36, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (89, 1, 37, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (90, 1, 38, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (91, 1, 39, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (92, 1, 40, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (93, 1, 41, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (94, 1, 42, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (95, 1, 43, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (96, 1, 44, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (97, 1, 45, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (98, 1, 46, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (99, 1, 47, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (100, 1, 48, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (101, 1, 49, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (102, 1, 50, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (103, 1, 51, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (104, 1, 52, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (105, 1, 54, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);
INSERT INTO `sys_role_menu`
VALUES (106, 1, 53, '2022-11-16 16:04:17', '2022-11-16 16:04:17', 1, 1, 0);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `user_id`         bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户编号',
    `account_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '账户用户编号',
    `nickname`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '昵称',
    `real_name`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '姓名',
    `phone`           char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NOT NULL DEFAULT '' COMMENT '手机号',
    `avatar`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户头像',
    `email`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户邮箱',
    `gender`          tinyint(4) UNSIGNED NOT NULL COMMENT '性别[enum]',
    `department_id`   bigint(20) UNSIGNED NOT NULL COMMENT '部门编号',
    `enable`          tinyint(4) UNSIGNED NOT NULL COMMENT '启用禁用[enum]',
    `last_login_time` datetime(0) NOT NULL COMMENT '最后登录时间',
    `last_login_ip`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '最后登录IP',
    `create_time`     datetime(0) NOT NULL COMMENT '创建时间',
    `update_time`     datetime(0) NOT NULL COMMENT '更新时间',
    `create_user_id`  bigint(20) UNSIGNED NOT NULL COMMENT '创建人编号',
    `update_user_id`  bigint(20) UNSIGNED NOT NULL COMMENT '更新人编号',
    `is_deleted`      tinyint(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除[enum]',
    PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user`
VALUES (1, 1, 'Gucci', '谷子毅', '18888888888', '', '504305797@qq.com', 1, 1, 1, '2022-11-16 16:16:02', '127.0.0.1',
        '2022-05-05 17:04:20', '2022-11-15 23:12:39', 1, 1, 0);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `id`             bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',
    `user_id`        bigint(20) UNSIGNED NOT NULL COMMENT '用户编号',
    `role_id`        bigint(20) UNSIGNED NOT NULL COMMENT '角色编号',
    `create_time`    datetime(0) NOT NULL COMMENT '创建时间',
    `update_time`    datetime(0) NOT NULL COMMENT '更新时间',
    `create_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '创建人编号',
    `update_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '更新人编号',
    `is_deleted`     tinyint(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除[enum]',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role`
VALUES (1, 1, 1, '2022-08-01 05:03:29', '2022-08-01 05:03:29', 1, 1, 0);

SET FOREIGN_KEY_CHECKS = 1;
