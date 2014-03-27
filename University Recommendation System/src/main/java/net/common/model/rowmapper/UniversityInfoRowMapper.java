package net.common.model.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import net.common.model.UniversityInfo;

public class UniversityInfoRowMapper implements RowMapper<UniversityInfo> {
	public UniversityInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		UniversityInfo universityinfo = new UniversityInfo();
		universityinfo.setUnivId(rs.getString("univId"));
		universityinfo.setUniversityName(rs.getString("universityName"));
		return universityinfo;	
	}
}
