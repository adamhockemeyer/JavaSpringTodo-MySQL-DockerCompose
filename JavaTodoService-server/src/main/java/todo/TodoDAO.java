package todo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class TodoDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    class TodoRowMapper implements RowMapper<Todo> {
        @Override
        public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
            Todo todo = new Todo();
            todo.setId(rs.getInt("id"));
            todo.setTitle(rs.getString("title"));
            todo.setCompleted(rs.getBoolean("completed"));
            return todo;
        }
    }

    public List<Todo> findAll() {
        return jdbcTemplate.query("select * from todos", new TodoRowMapper());
    }

    public List<Todo> findAllCompleted() {
        return jdbcTemplate.query("select * from todos where completed = true", new TodoRowMapper());
    }

    public Optional<Todo> findById(Integer id) {
        return Optional.of(jdbcTemplate.queryForObject("select * from todos where id=?", new Object[] { id },
                new BeanPropertyRowMapper<Todo>(Todo.class)));
    }

    public int deleteById(Integer id) {
        return jdbcTemplate.update("delete from todos where id=?", new Object[] { id });
    }

    public int insert(Todo todo) {
        return jdbcTemplate.update("insert into todos (title, completed) " + "values(?, ?)",
                new Object[] { todo.getTitle(), todo.getCompleted() });
    }

    public int update(Todo todo, Integer id) {
        return jdbcTemplate.update("update todos " + " set completed = ?" + " where id = ?",
                new Object[] { todo.getCompleted(), id });
    }
}