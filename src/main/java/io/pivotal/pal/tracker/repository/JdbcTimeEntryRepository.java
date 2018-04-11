package io.pivotal.pal.tracker.repository;

import io.pivotal.pal.tracker.model.TimeEntry;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    DataSource dataSource;
    JdbcTemplate jdbcTemplate;

    public JdbcTimeEntryRepository(DataSource dataSource){
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
        insert.usingColumns("project_id", "user_id", "date", "hours")
                .withTableName("time_entries")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("project_id", timeEntry.getProjectId());
        parameters.put("user_id", timeEntry.getUserId());
        parameters.put("date", timeEntry.getDate());
        parameters.put("hours", timeEntry.getHours());

        timeEntry.setId(insert.executeAndReturnKey(parameters).longValue());
        return timeEntry;
    }

    @Override
    public TimeEntry find(long id) {
       List<TimeEntry> results = jdbcTemplate.query("select * from time_entries where id = ?",
               new Object[]{id}, new TimeEntryRowMapper());
       return results != null && !results.isEmpty() ? results.get(0) : null;
    }

    @Override
    public List<TimeEntry> list() {
        return jdbcTemplate.query("SELECT * FROM time_entries", new TimeEntryRowMapper());
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        String sql = "UPDATE time_entries SET project_id = ?, user_id = ?, date = ?, hours = ? WHERE id = ?";
        timeEntry.setId(id);
        jdbcTemplate.update(sql, new Object[]{timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry. getHours(), timeEntry.getId()});
        return timeEntry;
    }

    @Override
    public TimeEntry delete(long id) {
        String sql = "DELETE FROM time_entries WHERE id = ?";
        jdbcTemplate.update(sql, new Object[]{id});
        return null;
    }
}
