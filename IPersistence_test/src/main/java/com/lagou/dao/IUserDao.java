package com.lagou.dao;

import com.lagou.pojo.User;

import java.util.List;

public interface IUserDao {

    // 查询所有用户
    public List<User> findAll() throws Exception;

    // 根据条件进行用户查询
    public User findByCondition(User user) throws Exception;

    // 新增
    public Integer saveUser(User user) throws Exception;

    // 根据id进行修改
    public Integer updateUser(User user) throws Exception;

    // 根据id进行删除
    public Integer deleteUser(int id) throws Exception;

}
