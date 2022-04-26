CREATE TABLE `sys_department`
(
    `dept_id`        bigint UNSIGNED  NOT NULL AUTO_INCREMENT COMMENT '部门id',
    `dept_name`      varchar(255)     NULL COMMENT '部门名称',
    `description`    varchar(255)     NULL COMMENT '描述',
    `parent_id`      bigint UNSIGNED  NULL DEFAULT 0 COMMENT '父部门id',
    `create_time`    datetime         NULL COMMENT '创建时间',
    `update_time`    datetime         NULL COMMENT '更新时间',
    `create_user_id` bigint UNSIGNED  NULL COMMENT '创建人id',
    `update_user_id` bigint UNSIGNED  NULL COMMENT '更新人id',
    `is_deleted`     tinyint UNSIGNED NULL DEFAULT 0 COMMENT '0未删除 1已删除',
    PRIMARY KEY (`dept_id`)
);

CREATE TABLE `sys_menu`
(
    `menu_id`        bigint UNSIGNED  NOT NULL AUTO_INCREMENT COMMENT '菜单id',
    `menu_name`      varchar(255)     NULL COMMENT '菜单名称',
    `parent_id`      bigint UNSIGNED  NULL DEFAULT 0 COMMENT '父菜单id',
    `link`           varchar(255)     NULL COMMENT '菜单路径',
    `icon`           varchar(255)     NULL COMMENT '菜单图标',
    `sort`           int              NULL COMMENT '排序',
    `create_time`    datetime         NULL COMMENT '创建时间',
    `update_time`    datetime         NULL COMMENT '更新时间',
    `create_user_id` bigint UNSIGNED  NULL COMMENT '创建人id',
    `update_user_id` bigint UNSIGNED  NULL COMMENT '更新人id',
    `is_deleted`     tinyint UNSIGNED NULL DEFAULT 0 COMMENT '0未删除 1已删除',
    PRIMARY KEY (`menu_id`)
);

CREATE TABLE `sys_role`
(
    `role_id`        bigint UNSIGNED  NOT NULL AUTO_INCREMENT COMMENT '角色id',
    `role_name`      varchar(255)     NULL COMMENT '角色名称',
    `description`    varchar(255)     NULL COMMENT '描述',
    `create_time`    datetime         NULL COMMENT '创建时间',
    `update_time`    datetime         NULL COMMENT '更新时间',
    `create_user_id` bigint UNSIGNED  NULL COMMENT '创建人id',
    `update_user_id` bigint UNSIGNED  NULL COMMENT '更新人id',
    `is_deleted`     tinyint UNSIGNED NULL DEFAULT 0 COMMENT '0未删除 1已删除',
    PRIMARY KEY (`role_id`)
);

CREATE TABLE `sys_role_menu`
(
    `id`             bigint UNSIGNED  NOT NULL AUTO_INCREMENT COMMENT 'id',
    `role_id`        bigint UNSIGNED  NULL COMMENT '角色id',
    `menu_id`        bigint UNSIGNED  NULL COMMENT '菜单id',
    `create_time`    datetime         NULL COMMENT '创建时间',
    `update_time`    datetime         NULL COMMENT '更新时间',
    `create_user_id` bigint UNSIGNED  NULL COMMENT '创建人id',
    `update_user_id` bigint UNSIGNED  NULL COMMENT '更新人id',
    `is_deleted`     tinyint UNSIGNED NULL DEFAULT 0 COMMENT '0未删除 1已删除',
    PRIMARY KEY (`id`)
);

CREATE TABLE `sys_tenant`
(
    `tenant_id`       bigint UNSIGNED  NOT NULL AUTO_INCREMENT COMMENT '租户id',
    `tenant_code`     varchar(255)     NULL COMMENT '租户code',
    `tenant_name`     varchar(255)     NULL COMMENT '租户名称',
    `contact_user_id` bigint UNSIGNED  NULL COMMENT '联系人id',
    `expire_time`     datetime         NULL COMMENT '过期时间',
    `user_limit`      bigint(255)      NULL COMMENT '用户上限',
    `create_time`     datetime         NULL COMMENT '创建时间',
    `update_time`     datetime         NULL COMMENT '更新时间',
    `create_user_id`  bigint UNSIGNED  NULL COMMENT '创建人id',
    `update_user_id`  bigint UNSIGNED  NULL COMMENT '更新人id',
    `is_deleted`      tinyint UNSIGNED NULL DEFAULT 0 COMMENT '0未删除 1已删除',
    PRIMARY KEY (`tenant_id`)
);

CREATE TABLE `sys_user`
(
    `user_id`         bigint UNSIGNED  NOT NULL AUTO_INCREMENT COMMENT '用户id',
    `nickname`        varchar(255)     NULL COMMENT '昵称',
    `real_name`       varchar(255)     NULL COMMENT '姓名',
    `phone`           char(11)         NULL COMMENT '手机号',
    `password`        varchar(255)     NULL COMMENT '密码',
    `avater`          varchar(255)     NULL COMMENT '用户头像',
    `email`           varchar(255)     NULL COMMENT '用户邮箱',
    `gender`          tinyint UNSIGNED NULL COMMENT '性别',
    `dept_id`         bigint UNSIGNED  NULL COMMENT '部门id',
    `identity`        tinyint UNSIGNED NULL COMMENT '0超级管理员 1管理员 2普通用户',
    `enable`          tinyint UNSIGNED NULL COMMENT '0不可用 1可用',
    `last_login_time` datetime         NULL COMMENT '最后登录时间',
    `last_login_ip`   char(15)         NULL COMMENT '最后登录IP',
    `create_time`     datetime         NULL COMMENT '创建时间',
    `update_time`     datetime         NULL COMMENT '更新时间',
    `create_user_id`  bigint UNSIGNED  NULL COMMENT '创建人id',
    `update_user_id`  bigint UNSIGNED  NULL COMMENT '更新人id',
    `is_deleted`      tinyint UNSIGNED NULL DEFAULT 0 COMMENT '0未删除 1已删除',
    PRIMARY KEY (`user_id`)
);

CREATE TABLE `sys_user_role`
(
    `id`             bigint UNSIGNED  NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id`        bigint UNSIGNED  NULL COMMENT '用户id',
    `role_id`        bigint UNSIGNED  NULL COMMENT '角色id',
    `create_time`    datetime         NULL COMMENT '创建时间',
    `update_time`    datetime         NULL COMMENT '更新时间',
    `create_user_id` bigint UNSIGNED  NULL COMMENT '创建人id',
    `update_user_id` bigint UNSIGNED  NULL COMMENT '更新人id',
    `is_deleted`     tinyint UNSIGNED NULL DEFAULT 0 COMMENT '0未删除 1已删除',
    PRIMARY KEY (`id`)
);

