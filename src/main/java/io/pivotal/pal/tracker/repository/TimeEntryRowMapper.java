package io.pivotal.pal.tracker.repository;

import io.pivotal.pal.tracker.model.TimeEntry;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TimeEntryRowMapper implements RowMapper {

    public TimeEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
        TimeEntry te = new TimeEntry();
        te.setId(rs.getLong("id"));
        te.setHours(rs.getInt("hours"));
        te.setProjectId(rs.getLong("project_id"));
        te.setUserId(rs.getLong("user_id"));
        te.setDate(rs.getDate("date").toLocalDate());
        return te;
    }
}
