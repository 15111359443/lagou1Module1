package com.lagou.config;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XMLMapperBulider {

    private Configuration configuration;

    public XMLMapperBulider(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream inputStream) throws DocumentException {
        // 获取Document
        Document document = new SAXReader().read(inputStream);
        // 获取根节点
        Element rootElement = document.getRootElement();
        // 获取根节点的namespace属性值
        String namespace = rootElement.attributeValue("namespace");

        // 解析mapper.xml，并对其进行封装 - select标签
        List<Element> selectList = rootElement.selectNodes("//select");
        for(Element element: selectList){
            addMappedStatement(namespace, element);
        }

        // 解析mapper.xml，并对其进行封装 - insert标签
        List<Element> insertList = rootElement.selectNodes("//insert");
        for(Element element: insertList){
            addMappedStatement(namespace, element);
        }

        // 解析mapper.xml，并对其进行封装 - update标签
        List<Element> updateList = rootElement.selectNodes("//update");
        for(Element element: updateList){
            addMappedStatement(namespace, element);
        }

        // 解析mapper.xml，并对其进行封装 - delete标签
        List<Element> deleteList = rootElement.selectNodes("//delete");
        for(Element element: deleteList){
            addMappedStatement(namespace, element);
        }
    }


    private void addMappedStatement(String namespace, Element element) throws DocumentException{
        String id = element.attributeValue("id");
        String resultType = element.attributeValue("resultType");
        String paramterType = element.attributeValue("parameterType");
        String sqlText = element.getTextTrim();

        MappedStatement mappedStatement = new MappedStatement();
        mappedStatement.setId(id);
        mappedStatement.setResultType(resultType);
        mappedStatement.setParamterType(paramterType);
        mappedStatement.setSql(sqlText);

        String key = namespace +"."+ id;
        configuration.getMappedStatementMap().put(key, mappedStatement);
    }
}
