package com.guzi.sherly.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.sql.*;

/**
 * @author 谷子毅
 * @date 2022/11/22
 */
class DepartmentControllerTest {

    @Test
    @SneakyThrows
    public void test1() {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://101.34.169.185:3306/sherly", "root", "gzy123");
        Statement statement = conn.createStatement();
        ResultSet resultSet1 = statement.executeQuery("select * from sys_user");
        System.out.println(resultSet1);
        PreparedStatement preparedStatement = conn.prepareStatement("select * from sys_user");
        ResultSet resultSet2 = preparedStatement.executeQuery();
        System.out.println(resultSet2);
        conn.close();
    }
}
