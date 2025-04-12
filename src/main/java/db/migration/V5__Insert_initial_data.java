package db.migration;

import com.su.ac.th.project.grader.util.CommonUtil;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;
import java.util.List;

public class V5__Insert_initial_data extends BaseJavaMigration {

    private JdbcTemplate jdbcTemplate;
    private static final String NOW = CommonUtil.getDateTimeNow();

    @Override
    public void migrate(Context context) {
        DataSource dataSource = new SingleConnectionDataSource(context.getConnection(), true);
        this.jdbcTemplate = new JdbcTemplate(dataSource);

        insertData("INSERT INTO problems (problem_id, title, description, difficulty, type, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)",
                List.of(
                        new Object[]{1, "Sum of Two Numbers", "Write a program to find the sum of two numbers.", "EASY", "MATH", NOW, NOW},
                        new Object[]{2, "String Reversal", "Write a program to reverse a string.", "MEDIUM", "STRING", NOW, NOW}
                )
        );

        insertData("INSERT INTO testcases (problem_id, input_data, expected_output, created_at, updated_at) VALUES (?, ?, ?, ?, ?)",
                List.of(
                        // Problem 1: Sum of Two Numbers
                        new Object[]{1, "3 5", "8", NOW, NOW},
                        new Object[]{1, "10 20", "30", NOW, NOW},
                        new Object[]{1, "-1 1", "0", NOW, NOW},
                        new Object[]{1, "0 0", "0", NOW, NOW},

                        // Problem 2: String Reversal
                        new Object[]{2, "hello", "olleh", NOW, NOW},
                        new Object[]{2, "world", "dlrow", NOW, NOW},
                        new Object[]{2, "abcdef", "fedcba", NOW, NOW},
                        new Object[]{2, "12345", "54321", NOW, NOW}
                )
        );

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        insertData(
                "INSERT INTO users (username, password, email, role, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)",
                List.of(
                        new Object[]{"admin", passwordEncoder.encode("admin123"), "admin@gmail.com", "ADMIN", NOW, NOW},
                        new Object[]{"admin2", passwordEncoder.encode("admin123"), "admin2@gmail.com", "ADMIN", NOW, NOW},
                        new Object[]{"user", passwordEncoder.encode("user123"), "user@gmail.com", "USER", NOW, NOW}
                )
        );
    }

    private void insertData(String query, List<Object[]> data) {
        jdbcTemplate.batchUpdate(query, data);
    }
}
