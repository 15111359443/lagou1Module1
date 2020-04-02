package com.lagou.config;

import com.lagou.io.Resources;
import com.lagou.pojo.Configuration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XMLConfigBulider {

    private Configuration configuration;

    public XMLConfigBulider() {
        this.configuration = new Configuration();
    }

    /**
     *该方法就是使用dom4j将配置文件进行解析，封装Configuration
     */
    public Configuration barseConfig(InputStream inputStream) throws DocumentException, PropertyVetoException {

        Document document = new SAXReader().read(inputStream);
        // 获取根标签 <configuration>
        Element rootElement = document.getRootElement();
        List<Element> list = rootElement. selectNodes("//property");
        Properties properties = new Properties();
        for(Element element: list) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name, value);
        }

        // 创建连接池
        ComboPooledDataSource comboPooledDataSource=new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties. getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties. getProperty("jdbcUrl"));
        comboPooledDataSource. setUser(properties. getProperty("username"));
        comboPooledDataSource.setPassword(properties. getProperty("password"));

        configuration.setDataSource(comboPooledDataSource);

        // mapper.xml解析：拿到路径-—节输入流-—-domej进行解析
        List<Element> mapperList=rootElement.selectNodes("//mapper");
        for(Element element:mapperList){
            String mapperPath = element.attributeValue("resource");
            InputStream resourcesAsStream = Resources.getResourcesAsStream(mapperPath);
            XMLMapperBulider xmlMapperBulider = new XMLMapperBulider(configuration);
            xmlMapperBulider.parse(resourcesAsStream);
        }

        return configuration;
    }
}
