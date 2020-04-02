package com.lagou.test;

import com.lagou.dao.IUserDao;
import com.lagou.io.Resources;
import com.lagou.pojo.User;
import com.lagou.sqlSession.SqlSession;
import com.lagou.sqlSession.SqlSessionFactory;
import com.lagou.sqlSession.sqlSessionFactoryBulider;
import org.dom4j.DocumentException;
import org.junit.Before;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;

public class IPersistenceTest {

    private SqlSession sqlSession;

    private IUserDao userDao;

    @Before
    public void before() throws PropertyVetoException, DocumentException {
        InputStream resourcesAsStream = Resources.getResourcesAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new sqlSessionFactoryBulider().bulider(resourcesAsStream);
        sqlSession = sqlSessionFactory.openSession();
        userDao = sqlSession.getMapper(IUserDao.class);
    }

    // region 【User 增删改查测试 - 使用动态代理方式】

    /**
     * 查询所有信息 - 使用动态代理方式
     */
    @Test
    public void test1() throws Exception {
        List<User> userList = userDao.findAll();
        for (User user : userList) {
            System.out.println(user);
        }
    }

    /**
     * 根据条件查询单条信息 - 使用动态代理方式
     */
    @Test
    public void test2() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("张三");
        User user1 = userDao.findByCondition(user);
        System.out.println(user1);
    }

    /**
     * 新增 - 使用动态代理方式
     */
    @Test
    public void test3() throws Exception {
        User user = new User();
        user.setId(2);
        user.setUsername("aaa");
        int count = userDao.saveUser(user);
        System.out.println(count > 0 ? "新增成功！" : "新增失败！");
    }

    /**
     * 根据id进行修改 - 使用动态代理方式
     */
    @Test
    public void test4() throws Exception {
        User user = new User();
        user.setId(2);
        user.setUsername("bbb");
        int count = userDao.updateUser(user);
        System.out.println(count > 0 ? "修改成功！" : "修改失败！");
    }

    /**
     * 根据id进行删除 - 使用动态代理方式
     */
    @Test
    public void test5() throws Exception {
        int count = userDao.deleteUser(2);
        System.out.println(count > 0 ? "删除成功！" : "删除失败！");
    }

    // endregion


    // region 【User 增删改查测试 - 传统方式】

    /**
     * 查询所有信息
     */
    @Test
    public void test6() throws Exception {
        List<User> userList = sqlSession.selectList("com.lagou.dao.IUserDao.findAll");
        for (User user : userList) {
            System.out.println(user);
        }
    }

    /**
     * 根据条件查询单条信息
     */
    @Test
    public void test7() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("张三");
        User user1 = sqlSession.selectOne("com.lagou.dao.IUserDao.findByCondition", user);
        System.out.println(user1);
    }

    /**
     * 新增
     */
    @Test
    public void test8() throws Exception {
        User user = new User();
        user.setId(2);
        user.setUsername("aaa");
        int count = sqlSession.update("com.lagou.dao.IUserDao.saveUser", user);
        System.out.println(count > 0 ? "新增成功！" : "新增失败！");
    }

    /**
     * 根据id进行修改
     */
    @Test
    public void test9() throws Exception {
        User user = new User();
        user.setId(2);
        user.setUsername("bbb");
        int count = sqlSession.update("com.lagou.dao.IUserDao.updateUser", user);
        System.out.println(count > 0 ? "修改成功！" : "修改失败！");
    }

    /**
     * 根据id进行删除
     */
    @Test
    public void test10() throws Exception {
        int count = sqlSession.update("com.lagou.dao.IUserDao.deleteUser", 2);
        System.out.println(count > 0 ? "删除成功！" : "删除失败！");
    }

    // endregion

}
