package com.log.rest.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import com.log.rest.model.UserLog;
import com.log.rest.repository.LogsRepository;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LogService {

	private LogsRepository repository;

	private ElasticsearchTemplate esTemplate;

	public LogService(LogsRepository repository, ElasticsearchTemplate esTemplate) {
		this.repository = repository;
		this.esTemplate = esTemplate;

	}

	public List<UserLog> searchParam(String userId, String type, String from) {
		if (userId != null) {
			if (type != null) {
				return (from != null) ? searchAll(userId, type, from): searchIdAndType(userId, type);
				
			} else {
				return (from != null) ? searchIdAndDate(userId, from): searchId(userId);
			}
		} else {
			if (type != null) {
				return (from != null) ? searchTypeDate(type, from): searchType(type);
				
			} else {
				return (from != null) ? searchDate(from): StreamSupport.stream(repository.findAll().spliterator(), false).limit(5)
						.collect(Collectors.toList());
			}
		}
	}

	private List<UserLog> searchAll(String userId, String type, String from) {
		BoolQueryBuilder criteria = QueryBuilders.boolQuery();
		criteria.must()
				.addAll(List.of(QueryBuilders.matchQuery("userId", userId),
						QueryBuilders.matchQuery("actions.type", type),
						QueryBuilders.rangeQuery("actions.time").gte(convertStringToDate(from))));
		return esTemplate.queryForList(new NativeSearchQueryBuilder().withQuery(criteria).build(), UserLog.class);

	}

	private List<UserLog> searchIdAndType(String userId, String type) {
		BoolQueryBuilder criteria = QueryBuilders.boolQuery();
		criteria.must().addAll(
				List.of(QueryBuilders.matchQuery("userId", userId), QueryBuilders.matchQuery("actions.type", type)));
		return esTemplate.queryForList(new NativeSearchQueryBuilder().withQuery(criteria).build(), UserLog.class);

	}

	private List<UserLog> searchIdAndDate(String userId, String from) {
		BoolQueryBuilder criteria = QueryBuilders.boolQuery();
		criteria.must().addAll(List.of(QueryBuilders.matchQuery("userId", userId),
				QueryBuilders.rangeQuery("actions.time").gte(convertStringToDate(from))));
		return esTemplate.queryForList(new NativeSearchQueryBuilder().withQuery(criteria).build(), UserLog.class);

	}

	private List<UserLog> searchId(String userId) {
		BoolQueryBuilder criteria = QueryBuilders.boolQuery();
		criteria.must().addAll(List.of(QueryBuilders.matchQuery("userId", userId)));
		return esTemplate.queryForList(new NativeSearchQueryBuilder().withQuery(criteria).build(), UserLog.class);

	}

	private List<UserLog> searchType(String type) {
		BoolQueryBuilder criteria = QueryBuilders.boolQuery();
		criteria.must().addAll(List.of(QueryBuilders.matchQuery("actions.type", type)));
		return esTemplate.queryForList(new NativeSearchQueryBuilder().withQuery(criteria).build(), UserLog.class);

	}

	private List<UserLog> searchDate(String from) {
		BoolQueryBuilder criteria = QueryBuilders.boolQuery();
		criteria.must().addAll(List.of(QueryBuilders.rangeQuery("actions.time").gte(convertStringToDate(from))));
		return esTemplate.queryForList(new NativeSearchQueryBuilder().withQuery(criteria).build(), UserLog.class);

	}

	private List<UserLog> searchTypeDate(String type, String from) {
		BoolQueryBuilder criteria = QueryBuilders.boolQuery();
		criteria.must().addAll(List.of(QueryBuilders.matchQuery("actions.type", type),
				QueryBuilders.rangeQuery("actions.time").gte(convertStringToDate(from))));
		return esTemplate.queryForList(new NativeSearchQueryBuilder().withQuery(criteria).build(), UserLog.class);

	}

	private static Date convertStringToDate(@NonNull String dateInString) {
		log.debug("string to date : " + dateInString);
		Date date = null;
		date = org.apache.http.client.utils.DateUtils.parseDate(dateInString,
				new String[] { "yyyy-MM-dd'T'HH:mm:ss:SSSZ" });
		return date;
	}

	public UserLog save(@NonNull UserLog user) {
		log.info("creating log for user : " + user.getUserId());
		repository.save(user);
		return user;
	}

}
