package com.lagou.sqlSession;

import com.lagou.config.XMLConfigBulider;
import com.lagou.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

public class sqlSessionFactoryBulider {

    public SqlSessionFactory bulider(InputStream in) throws DocumentException, PropertyVetoException {
        //第一：使用dom4j解析配置文件，将解析出来的内容封装到Configuration中
        XMLConfigBulider xmlConfigBulider = new XMLConfigBulider();
        Configuration configuration = xmlConfigBulider.barseConfig(in);

        //第二：创建sqlSessionFactory对象：工厂类：生产 sqlSession：会话对象
        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);

        return defaultSqlSessionFactory;
    }
}
