package com.one.db_to_csv_batch;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;

public class EmployeeDbReader extends JdbcCursorItemReader<Employee>{

	public EmployeeDbReader(DataSource dataSource) {
		setDataSource(dataSource);
		setSql("SELECT * FROM EMPLOYEE");
		setRowMapper(new EmployeeRowMapper());
	}
	
}
