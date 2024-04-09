package com.example.demo.handler;

import cn.hutool.core.util.StrUtil;
import com.example.demo.util.AESUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AESHandler extends BaseTypeHandler<String> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String s, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, AESUtil.encrypt(s));
    }

    @Override
    public String getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        String encryptedValue = resultSet.getString(columnName);
        return StrUtil.isEmpty(encryptedValue) ? null : AESUtil.decrypt(encryptedValue);
    }

    @Override
    public String getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        String encryptedValue = resultSet.getString(columnIndex);
        return StrUtil.isEmpty(encryptedValue) ? null : AESUtil.decrypt(encryptedValue);
    }

    @Override
    public String getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        String encryptedValue = callableStatement.getString(columnIndex);
        return StrUtil.isEmpty(encryptedValue) ? null : AESUtil.decrypt(encryptedValue);
    }
}
