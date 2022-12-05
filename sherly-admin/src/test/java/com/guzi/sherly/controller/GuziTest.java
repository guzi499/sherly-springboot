package com.guzi.sherly.controller;

import com.guzi.sherly.mapper.UserMapper;
import com.guzi.sherly.model.admin.User;
import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.sql.*;

/**
 * @author 谷子毅
 * @date 2022/11/22
 */
class GuziTest {

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

    @Test
    @SneakyThrows
    public void test2() {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 1.获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 2.获取sqlSession对象
        SqlSession openSession = sqlSessionFactory.openSession();
        // 3.获取接口的实现类代理对象MapperProxy
        UserMapper mapper = openSession.getMapper(UserMapper.class);
        // 4.代理对象执行CRUD
        User user = mapper.selectById(1);
    }
}
