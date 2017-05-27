package com.rengu.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

/**
 * Created by hanchangming on 2017/5/24.
 */
public class Tools {

    public static <T> T jsonConvertToEntity(String jsonString, Class<T> classType) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        return objectMapper.readValue(jsonString, classType);
    }

    public static String entityConvertToJsonString(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        return objectMapper.writeValueAsString(object);
    }

    public static String getHttpRequestBody(HttpServletRequest httpServletRequest) {
        String httpRequestBodyString = "";
        try {
            BufferedReader bufferedReader = httpServletRequest.getReader();
            String tempString;
            while ((tempString = bufferedReader.readLine()) != null) {
                httpRequestBodyString = httpRequestBodyString + tempString;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpRequestBodyString;
    }

    public static Properties getProperties() {
        Properties properties = new Properties();
        try {
            InputStream inputStream = Tools.class.getResourceAsStream("/Database.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static boolean executeSQLForUpdate(String databaseType, String companyName, String SQLString) throws ClassNotFoundException, SQLException {
        String databaseUrl = getProperties().getProperty(companyName + "DatabaseUrl");
        String databaseUsername = getProperties().getProperty(companyName + "DatabaseUsername");
        String databasePassword = getProperties().getProperty(companyName + "DatabasePassword");
        String databaseDriver = getProperties().getProperty(databaseType + "Driver");

        Class.forName(databaseDriver);
        Connection connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
        Statement statement = connection.createStatement();
        boolean resultFlag = statement.execute(SQLString);
        statement.close();
        connection.close();
        return resultFlag;
    }

    public static List executeSQLForResultSet(String databaseType, String companyName, String SQLString) throws ClassNotFoundException, SQLException {
        String databaseUrl = getProperties().getProperty(companyName + "DatabaseUrl");
        String databaseUsername = getProperties().getProperty(companyName + "DatabaseUsername");
        String databasePassword = getProperties().getProperty(companyName + "DatabasePassword");
        String databaseDriver = getProperties().getProperty(databaseType + "Driver");

        Class.forName(databaseDriver);
        Connection connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQLString);
        List list = resultSetConvertToList(resultSet);
        statement.close();
        connection.close();
        return list;
    }

    public static List resultSetConvertToList(ResultSet resultSet) throws SQLException {
        List list = new ArrayList();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();//获取键名
        int columnCount = resultSetMetaData.getColumnCount();//获取行的数量
        while (resultSet.next()) {
            Map rowData = new HashMap();//声明Map
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));//获取键名及值
            }
            list.add(rowData);
        }
        return list;
    }
}
