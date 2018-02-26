package com.king.mybatisHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import com.king.enums.SexEnum;

@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes(SexEnum.class)
public class SexEnumTypeHandler extends BaseTypeHandler<SexEnum> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, SexEnum parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setInt(i, parameter.getId());
	}

	@Override
	public SexEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return SexEnum.getSexById(rs.getInt(columnName));
	}

	@Override
	public SexEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return SexEnum.getSexById(rs.getInt(columnIndex));
	}

	@Override
	public SexEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return SexEnum.getSexById(cs.getInt(columnIndex));
	}

}
