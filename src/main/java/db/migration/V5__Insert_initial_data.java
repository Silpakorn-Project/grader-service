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

        String sumProblemDescription = """
                Given two integers, return their sum.

                Write a program that takes two integers as input and outputs their sum.

                ## Example 1:

                ```
                Input: a = 3, b = 5
                Output: 8
                ```

                ## Example 2:

                ```
                Input: a = -1, b = 1
                Output: 0
                ```

                ## Example 3:

                ```
                Input: a = 10, b = 20
                Output: 30
                ```

                ## Constraints:

                * -2^31 <= a, b <= 2^31 - 1
                * The result will be within the range of a 32-bit integer
                """;

        String reverseProblemDescription = """
                Given a string, return it reversed.

                Write a program that takes a string as input and returns a new string with the characters in reverse order.

                ## Example 1:

                ```
                Input: s = "hello"
                Output: "olleh"
                ```

                ## Example 2:

                ```
                Input: s = "world"
                Output: "dlrow"
                ```

                ## Example 3:

                ```
                Input: s = "12345"
                Output: "54321"
                ```

                ## Constraints:

                * 1 <= s.length <= 100
                * s consists of printable ASCII characters
                """;

        insertData("INSERT INTO problems (problem_id, title, description, difficulty, type, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)",
                List.of(
                        new Object[]{1, "Sum of Two Numbers", sumProblemDescription, "EASY", "MATH", NOW, NOW},
                        new Object[]{2, "String Reversal", reverseProblemDescription, "MEDIUM", "STRING", NOW, NOW}
                )
        );

        insertData("INSERT INTO testcases (problem_id, input_data, expected_output, created_at, updated_at) VALUES (?, ?, ?, ?, ?)",
                List.of(
                        // Problem 1: Sum of Two Numbers
                        new Object[]{1, "3\n5", "8", NOW, NOW},
                        new Object[]{1, "10\n20", "30", NOW, NOW},
                        new Object[]{1, "-1\n1", "0", NOW, NOW},
                        new Object[]{1, "0\n0", "0", NOW, NOW},

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