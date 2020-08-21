package com.log.rest.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.log.rest.model.UserLog;


@Repository
public interface LogsRepository extends ElasticsearchRepository<UserLog, String> {
	
}
