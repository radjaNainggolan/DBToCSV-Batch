package com.one.db_to_csv_batch;

import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;

public class EmployeeLineAggregator extends DelimitedLineAggregator<Employee>{

	public EmployeeLineAggregator() {
		BeanWrapperFieldExtractor<Employee> beanWrapperFieldExtractor = new BeanWrapperFieldExtractor<Employee>();
		beanWrapperFieldExtractor.setNames(new String[] {"id", "firstName", "lastName"});
		setDelimiter(",");
		setFieldExtractor(beanWrapperFieldExtractor);
	}
	
}
