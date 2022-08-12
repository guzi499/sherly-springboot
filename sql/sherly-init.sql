
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ge_account_user
-- ----------------------------
DROP TABLE IF EXISTS `ge_account_user`;
CREATE TABLE `ge_account_user`  (
  `account_user_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '账户用户id',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `tenant_data` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户信息',
  `last_login_tenant_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后登录租户代码',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`account_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ge_account_user
-- ----------------------------
INSERT INTO `ge_account_user` VALUES (1, '18888888888', '$2a$10$Rx/tS8zBxlhHZVN/tVbIeueFnxcZUHY8T7vgPOmor8Vy9cBFjH6VK', 'sherly', 'sherly', '2022-05-05 16:29:56', '2022-05-05 16:29:56');

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department`  (
  `department_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `department_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `parent_id` bigint(20) UNSIGNED NULL DEFAULT 0 COMMENT '父部门id',
  `sort` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人id',
  `update_user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '更新人id',
  `is_deleted` tinyint(3) UNSIGNED NULL DEFAULT 0 COMMENT '逻辑删除[enum]',
  PRIMARY KEY (`department_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_department
-- ----------------------------
INSERT INTO `sys_department` VALUES (1, 'sherly开源', '', 0, 1, '2022-05-05 16:31:07', '2022-07-25 10:28:25', 1, 1, 0);
INSERT INTO `sys_department` VALUES (2, '财务部', 'sherly财务部', 1, 3, '2022-05-05 16:31:07', '2022-05-20 14:05:31', 1, 1, 0);
INSERT INTO `sys_department` VALUES (3, '人资部', 'sherly人资部', 1, 2, '2022-05-05 16:31:07', '2022-07-24 19:32:46', 1, 1, 0);
INSERT INTO `sys_department` VALUES (4, '开发部', 'sherly开发部', 1, 3, '2022-05-05 16:31:07', '2022-05-05 16:31:07', 1, 1, 0);
INSERT INTO `sys_department` VALUES (5, '前端开发组', 'sherly前端开发组', 4, 1, '2022-05-05 16:31:07', '2022-05-05 16:31:07', 1, 1, 0);
INSERT INTO `sys_department` VALUES (6, '后端开发组', 'sherly后端开发组', 4, 2, '2022-05-05 16:31:07', '2022-07-10 00:24:07', 1, 1, 0);

-- ----------------------------
-- Table structure for sys_email_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_email_config`;
CREATE TABLE `sys_email_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮件服务器SMTP地址',
  `port` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮件服务器SMTP端口',
  `sender_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发件人名称',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `sender_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发件人邮箱',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  `update_user_id` bigint(20) NULL DEFAULT NULL COMMENT '更新人id',
  `is_deleted` tinyint(255) NULL DEFAULT NULL COMMENT '逻辑删除[enum]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_email_config
-- ----------------------------
INSERT INTO `sys_email_config` VALUES (15, 'smtp.163.com', '465', '谷帅', 'IOHETGYKPBWFYDRP', 'dengdd202206@163.com', '2022-07-07 23:44:00', '2022-07-13 11:38:06', 1, 1, 0);

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log`  (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录账号',
  `type` int(255) NULL DEFAULT NULL COMMENT '登录方式[enum]',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求ip',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求地址',
  `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求系统',
  `browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求浏览器',
  `result` int(255) NULL DEFAULT NULL COMMENT '登录结果[enum]',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 200 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `menu_type` tinyint(4) NULL DEFAULT NULL COMMENT '菜单类型[enum]',
  `permission` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限',
  `menu_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) UNSIGNED NULL DEFAULT 0 COMMENT '父菜单id',
  `link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单路径',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `sort` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人id',
  `update_user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '更新人id',
  `is_deleted` tinyint(3) UNSIGNED NULL DEFAULT 0 COMMENT '逻辑删除[enum]',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 97 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 1, '', '系统管理', 0, '', 'system', 1, '2022-05-05 16:35:17', '2022-08-01 06:28:01', 1, 4, 0);
INSERT INTO `sys_menu` VALUES (2, 1, '', '系统监控', 0, '', 'visible', 2, '2022-05-05 16:35:17', '2022-08-01 06:28:19', 1, 4, 0);
INSERT INTO `sys_menu` VALUES (3, 2, 'user:list_page', '用户管理', 1, 'system/user/index', 'blank', 3, '2022-05-05 16:35:17', '2022-07-31 23:43:32', 1, 4, 0);
INSERT INTO `sys_menu` VALUES (4, 3, 'user:get_one', '用户详情', 3, '', 'blank', 4, '2022-05-05 16:35:17', '2022-07-20 15:48:08', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (5, 3, 'user:ban_one', '用户禁用/启用', 3, '', 'blank', 5, '2022-05-05 16:35:17', '2022-07-20 15:48:12', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (6, 3, 'user:save_one', '用户新增', 3, '', 'blank', 6, '2022-05-05 16:35:17', '2022-07-20 15:48:17', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (7, 3, 'user:update_one', '用户更新', 3, '', 'blank', 7, '2022-05-05 16:35:17', '2022-07-20 15:48:22', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (8, 3, 'user:remove_one', '用户删除', 3, '', 'blank', 8, '2022-05-05 16:35:17', '2022-07-20 15:48:27', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (9, 2, 'role:list_page', '角色管理', 1, 'system/role/index', 'blank', 9, '2022-05-05 16:35:17', '2022-07-31 19:37:28', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (10, 3, 'role:get_one', '角色详情', 9, '', 'blank', 10, '2022-05-05 16:35:17', '2022-07-20 15:56:07', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (11, 3, 'role:save_one', '角色新增', 9, '', 'blank', 11, '2022-05-05 16:35:17', '2022-07-20 15:56:12', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (12, 3, 'role:update_one', '角色更新', 9, '', 'blank', 12, '2022-05-05 16:35:17', '2022-07-20 15:56:16', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (13, 3, 'role:remove_one', '角色删除', 9, '', 'blank', 13, '2022-05-05 16:35:17', '2022-07-20 15:56:23', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (14, 2, '', '部门管理', 1, 'system/department/index', 'blank', 14, '2022-05-05 16:35:17', '2022-07-31 19:34:12', 1, 4, 0);
INSERT INTO `sys_menu` VALUES (15, 3, 'department:save_one', '部门新增', 14, '', 'blank', 15, '2022-05-05 16:35:17', '2022-07-20 16:01:18', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (16, 3, 'department:update_one', '部门更新', 14, '', 'blank', 16, '2022-05-05 16:35:17', '2022-07-20 16:01:25', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (17, 3, 'department:remove_one', '部门删除', 14, '', 'blank', 17, '2022-05-05 16:35:17', '2022-07-20 16:01:36', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (18, 2, '', '菜单管理', 1, 'system/menu/index', 'blank', 18, '2022-05-05 16:35:17', '2022-07-31 19:35:01', 1, 4, 0);
INSERT INTO `sys_menu` VALUES (19, 3, 'menu:save_one', '菜单新增', 18, '', 'blank', 19, '2022-05-05 16:35:17', '2022-07-20 16:02:17', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (20, 3, 'menu:remove_one', '菜单删除', 18, '', 'blank', 20, '2022-05-05 16:35:17', '2022-07-20 16:02:29', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (21, 3, 'menu:update_one', '菜单修改', 18, '', 'blank', 21, '2022-05-05 16:35:17', '2022-07-20 16:02:34', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (22, 2, 'tenant:list_page', '租户管理', 1, 'system/tenant/index', 'blank', 22, '2022-05-05 16:35:17', '2022-07-20 17:27:44', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (23, 3, 'tenant:save_one', '租户新增', 22, '', 'blank', 23, '2022-05-05 16:35:17', '2022-07-20 16:16:36', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (24, 3, 'tenant:update_one', '租户更新', 22, '', 'blank', 24, '2022-05-05 16:35:17', '2022-07-20 16:16:41', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (25, 3, 'tenant:remove_one', '租户删除', 22, '', 'blank', 25, '2022-05-05 16:35:17', '2022-07-20 16:16:46', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (26, 2, 'user_online:list_all', '在线用户', 2, 'system/user_online/index', 'blank', 26, '2022-05-05 16:35:17', '2022-07-20 17:19:32', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (27, 2, 'operation_log:list_page', '操作日志', 2, 'system/operation_log/index', 'blank', 27, '2022-05-05 16:35:17', '2022-07-20 17:19:53', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (28, 2, 'login_log:list_page', '登录日志', 2, 'system/login_log/index', 'blank', 28, '2022-05-05 16:35:17', '2022-07-20 17:20:03', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (41, 1, '', '系统配置', 0, NULL, 'gear', 3, '2022-07-03 01:56:47', '2022-08-01 06:28:28', 1, 4, 0);
INSERT INTO `sys_menu` VALUES (42, 1, '', '邮件管理', 41, '', 'blank', 1, '2022-07-03 01:58:38', '2022-07-30 20:31:12', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (49, 1, '', '对象存储', 41, '', 'blank', 3, '2022-07-08 00:01:29', '2022-07-20 16:57:05', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (52, 3, 'user:list_export', '用户导出', 3, NULL, 'blank', 3, '2022-07-20 15:55:07', '2022-07-22 14:23:24', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (53, 3, 'tenant:update_menu', '租户菜单更新', 22, NULL, 'blank', 15, '2022-07-20 16:17:21', NULL, 1, NULL, 0);
INSERT INTO `sys_menu` VALUES (54, 3, 'tenant:list_menu', '租户菜单列表', 22, NULL, 'blank', 15, '2022-07-20 16:18:00', NULL, 1, NULL, 0);
INSERT INTO `sys_menu` VALUES (55, 3, 'user_online:force_quit', '强制退出', 26, NULL, 'blank', 1, '2022-07-20 16:28:15', '2022-07-22 14:16:49', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (56, 3, 'operation_log:get_one', '操作日志详情', 27, NULL, 'blank', 1, '2022-07-20 16:30:12', NULL, 1, NULL, 0);
INSERT INTO `sys_menu` VALUES (57, 3, 'operation_log:remove_all', '操作日志清空', 27, NULL, 'blank', 2, '2022-07-20 16:31:06', NULL, 1, NULL, 0);
INSERT INTO `sys_menu` VALUES (58, 3, 'login_log:remove_all', '登陆日志清空', 28, NULL, 'blank', 1, '2022-07-20 16:31:49', NULL, 1, NULL, 0);
INSERT INTO `sys_menu` VALUES (59, 3, 'email_save_or_update', '邮件配置保存或修改', 72, NULL, 'blank', 1, '2022-07-20 16:33:27', '2022-07-20 17:02:22', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (60, 2, 'email:send', '邮件发送', 42, 'system/email/email_send/index', 'blank', 2, '2022-07-20 16:34:31', '2022-07-22 11:50:42', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (61, 2, 'oss_config:list_page', '存储配置', 49, 'system/oss_config/index', 'blank', 1, '2022-07-20 16:37:24', '2022-07-20 16:56:18', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (62, 2, 'oss:list_page', '内容管理', 49, 'system/oss/index', 'blank', 2, '2022-07-20 16:41:12', '2022-07-20 16:56:09', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (63, 3, 'oss_config:get_one', '对象存储配置详情', 61, NULL, 'blank', 1, '2022-07-20 16:43:15', NULL, 1, NULL, 0);
INSERT INTO `sys_menu` VALUES (64, 3, 'oss_config:enable_one', '对象存储配置激活', 61, NULL, 'blank', 2, '2022-07-20 16:43:45', NULL, 1, NULL, 0);
INSERT INTO `sys_menu` VALUES (65, 3, 'oss_config:save_one', '对象存储配置新增', 61, NULL, 'blank', 3, '2022-07-20 16:44:25', NULL, 1, NULL, 0);
INSERT INTO `sys_menu` VALUES (66, 3, 'oss_config:update_one', '对象存储配置更新', 61, NULL, 'blank', 4, '2022-07-20 16:44:58', NULL, 1, NULL, 0);
INSERT INTO `sys_menu` VALUES (67, 3, 'oss_config:remove_one', '对象存储配置删除', 61, NULL, 'blank', 5, '2022-07-20 16:45:54', '2022-07-20 16:46:17', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (68, 3, 'oss:upload_one', '文件上传', 62, NULL, 'blank', 1, '2022-07-20 16:50:14', NULL, 1, NULL, 0);
INSERT INTO `sys_menu` VALUES (69, 3, 'oss:download_one', '文件下载', 62, NULL, 'blank', 2, '2022-07-20 16:51:19', NULL, 1, NULL, 0);
INSERT INTO `sys_menu` VALUES (70, 3, 'oss:access_url', '文件链接', 62, NULL, 'blank', 3, '2022-07-20 16:52:03', NULL, 1, NULL, 0);
INSERT INTO `sys_menu` VALUES (71, 3, 'oss:remove_one', '文件删除', 62, NULL, 'blank', 4, '2022-07-20 16:54:16', NULL, 1, NULL, 0);
INSERT INTO `sys_menu` VALUES (72, 2, 'email:get_one', '邮箱配置', 42, 'system/email/email_config/index', 'blank', 1, '2022-07-20 17:02:08', '2022-07-22 11:50:33', 1, 1, 0);

-- ----------------------------
-- Table structure for sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log`  (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志类型[enum]',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `request_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方式',
  `uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求uri',
  `request_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求参数',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求ip',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求地址',
  `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求系统',
  `browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求浏览器',
  `duration` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '耗时',
  `exception` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '异常描述',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15046 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_oss_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss_config`;
CREATE TABLE `sys_oss_config`  (
  `config_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '配置id',
  `config_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置名称',
  `type` int(255) NULL DEFAULT NULL COMMENT '存储类型[enum]',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `enable` tinyint(3) NULL DEFAULT NULL COMMENT '启用禁用[enum]',
  `config` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '具体配置',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  `update_user_id` bigint(20) NULL DEFAULT NULL COMMENT '更新人id',
  `is_deleted` tinyint(3) NULL DEFAULT NULL COMMENT '逻辑删除[enum]',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_oss_config
-- ----------------------------
INSERT INTO `sys_oss_config` VALUES (3, 'minio docker', 5, 'minio', 1, '{\"@class\":\"com.guzi.sherly.storage.client.s3.S3OssClientConfig\",\"endpoint\":\"http://101.34.169.185:9000\",\"domainName\":\"http://101.34.169.185:9000\",\"bucket\":\"sherly\",\"accessKey\":\"AKIAIOSFODNN7EXAMPLE\",\"accessSecret\":\"wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY\",\"region\":null}', '2022-06-27 17:41:36', '2022-07-14 00:52:46', 1, 1, 0);

-- ----------------------------
-- Table structure for sys_oss_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss_file`;
CREATE TABLE `sys_oss_file`  (
  `file_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '文件id',
  `config_id` bigint(20) NULL DEFAULT NULL COMMENT '配置id',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件相对路径',
  `file_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件类型',
  `size` int(11) NULL DEFAULT NULL COMMENT '文件大小',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  `update_user_id` bigint(20) NULL DEFAULT NULL COMMENT '更新人id',
  `is_deleted` tinyint(4) NULL DEFAULT NULL COMMENT '逻辑删除[enum]',
  PRIMARY KEY (`file_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 96 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人id',
  `update_user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '更新人id',
  `is_deleted` tinyint(3) UNSIGNED NULL DEFAULT 0 COMMENT '逻辑删除[enum]',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', '', '2022-05-05 16:54:50', '2022-08-01 12:51:25', 1, 1, 0);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '角色id',
  `menu_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '菜单id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人id',
  `update_user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '更新人id',
  `is_deleted` tinyint(3) UNSIGNED NULL DEFAULT 0 COMMENT '逻辑删除[enum]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1474 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (32, 4, 1, '2022-05-20 14:02:50', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (33, 4, 3, '2022-05-20 14:02:50', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (34, 4, 4, '2022-05-20 14:02:50', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (35, 4, 5, '2022-05-20 14:02:50', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (36, 4, 6, '2022-05-20 14:02:50', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (37, 4, 7, '2022-05-20 14:02:50', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (38, 4, 8, '2022-05-20 14:02:50', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (39, 4, 9, '2022-05-20 14:02:50', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (40, 4, 10, '2022-05-20 14:02:50', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (41, 4, 11, '2022-05-20 14:02:50', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (42, 4, 12, '2022-05-20 14:02:50', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (43, 4, 13, '2022-05-20 14:02:50', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (44, 4, 14, '2022-05-20 14:02:50', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (45, 4, 15, '2022-05-20 14:02:50', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (46, 4, 16, '2022-05-20 14:02:50', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (47, 4, 17, '2022-05-20 14:02:50', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (48, 4, 18, '2022-05-20 14:02:50', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (49, 4, 19, '2022-05-20 14:02:50', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (50, 4, 20, '2022-05-20 14:02:50', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (51, 4, 21, '2022-05-20 14:02:50', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (52, 4, 22, '2022-05-20 14:02:50', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (53, 4, 23, '2022-05-20 14:02:50', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (54, 4, 24, '2022-05-20 14:02:50', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (55, 4, 25, '2022-05-20 14:02:50', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (761, 6, 52, '2022-07-28 00:50:52', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (762, 6, 4, '2022-07-28 00:50:52', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (846, 5, 3, '2022-07-31 02:56:38', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (847, 5, 52, '2022-07-31 02:56:38', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (848, 5, 1, '2022-07-31 02:56:38', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (881, 9, 52, '2022-07-31 03:28:45', NULL, 4, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (882, 9, 3, '2022-07-31 03:28:45', NULL, 4, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (883, 9, 5, '2022-07-31 03:28:45', NULL, 4, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (884, 9, 1, '2022-07-31 03:28:45', NULL, 4, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (994, 2, 52, '2022-07-31 04:03:54', NULL, 4, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (995, 2, 3, '2022-07-31 04:03:54', NULL, 4, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (996, 2, 6, '2022-07-31 04:03:54', NULL, 4, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (997, 2, 7, '2022-07-31 04:03:54', NULL, 4, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (998, 2, 1, '2022-07-31 04:03:54', NULL, 4, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (999, 3, 52, '2022-08-01 03:31:16', NULL, 4, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1000, 3, 4, '2022-08-01 03:31:16', NULL, 4, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1001, 3, 5, '2022-08-01 03:31:16', NULL, 4, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1002, 3, 1, '2022-08-01 03:31:16', NULL, 4, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1369, 1, 48, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1370, 1, 1, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1371, 1, 3, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1372, 1, 6, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1373, 1, 7, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1374, 1, 8, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1375, 1, 9, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1376, 1, 10, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1377, 1, 11, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1378, 1, 12, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1379, 1, 13, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1380, 1, 14, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1381, 1, 15, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1382, 1, 16, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1383, 1, 17, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1384, 1, 18, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1385, 1, 19, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1386, 1, 20, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1387, 1, 21, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1388, 1, 22, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1389, 1, 53, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1390, 1, 54, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1391, 1, 23, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1392, 1, 24, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1393, 1, 25, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1394, 1, 2, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1395, 1, 26, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1396, 1, 55, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1397, 1, 27, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1398, 1, 56, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1399, 1, 57, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1400, 1, 28, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1401, 1, 58, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1402, 1, 41, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1403, 1, 42, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1404, 1, 72, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1405, 1, 59, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1406, 1, 60, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1407, 1, 49, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1408, 1, 61, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1409, 1, 63, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1410, 1, 64, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1411, 1, 65, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1412, 1, 66, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1413, 1, 67, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1414, 1, 62, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1415, 1, 68, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1416, 1, 69, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1417, 1, 70, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1418, 1, 71, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1419, 1, 4, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1420, 1, 52, '2022-08-01 05:41:36', NULL, 1, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1421, 1, 48, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1422, 1, 1, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1423, 1, 3, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1424, 1, 6, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1425, 1, 7, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1426, 1, 8, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1427, 1, 9, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1428, 1, 10, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1429, 1, 11, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1430, 1, 12, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1431, 1, 13, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1432, 1, 14, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1433, 1, 15, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1434, 1, 16, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1435, 1, 17, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1436, 1, 18, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1437, 1, 19, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1438, 1, 20, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1439, 1, 21, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1440, 1, 22, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1441, 1, 53, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1442, 1, 54, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1443, 1, 23, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1444, 1, 24, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1445, 1, 25, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1446, 1, 2, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1447, 1, 26, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1448, 1, 55, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1449, 1, 27, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1450, 1, 56, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1451, 1, 57, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1452, 1, 28, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1453, 1, 58, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1454, 1, 41, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1455, 1, 42, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1456, 1, 72, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1457, 1, 59, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1458, 1, 60, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1459, 1, 49, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1460, 1, 61, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1461, 1, 63, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1462, 1, 64, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1463, 1, 65, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1464, 1, 66, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1465, 1, 67, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1466, 1, 62, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1467, 1, 68, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1468, 1, 69, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1469, 1, 70, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1470, 1, 71, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1471, 1, 4, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1472, 1, 52, '2022-08-01 12:51:25', NULL, 1, NULL, 0);
INSERT INTO `sys_role_menu` VALUES (1473, 1, 5, '2022-08-01 12:51:25', NULL, 1, NULL, 0);

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant`  (
  `tenant_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '租户id',
  `tenant_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户code',
  `tenant_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户名称',
  `contact_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `contact_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  `user_limit` bigint(255) NULL DEFAULT NULL COMMENT '用户上限',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人id',
  `update_user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '更新人id',
  `is_deleted` tinyint(3) UNSIGNED NULL DEFAULT 0 COMMENT '逻辑删除[enum]',
  PRIMARY KEY (`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------
INSERT INTO `sys_tenant` VALUES (1, 'sherly', 'sherly开源', '谷子毅', '18888888888', '2099-01-01 00:00:00', 1000, '2022-05-05 16:59:14', '2022-08-01 13:03:05', 1, 1, 0);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `account_user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '账户用户id',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `gender` tinyint(3) UNSIGNED NULL DEFAULT NULL COMMENT '性别[enum]',
  `department_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '部门id',
  `enable` tinyint(3) UNSIGNED NULL DEFAULT NULL COMMENT '启用禁用[enum]',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后登录IP',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人id',
  `update_user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '更新人id',
  `is_deleted` tinyint(3) NULL DEFAULT 0 COMMENT '逻辑删除[enum]',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 1, 'Gucci', '谷子毅', '18888888888', '1659163985769$avatar$file', '504305797@qq.com', 1, 1, 1, '2022-08-01 12:52:25', '171.113.48.112', '2022-05-05 17:04:20', '2022-08-01 05:03:29', 1, 1, 0);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '用户id',
  `role_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '角色id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人id',
  `update_user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '更新人id',
  `is_deleted` tinyint(3) UNSIGNED NULL DEFAULT 0 COMMENT '逻辑删除[enum]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 111 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1, '2022-08-01 05:03:29', NULL, 1, NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
