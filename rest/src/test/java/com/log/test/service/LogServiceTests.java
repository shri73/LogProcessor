package com.log.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.log.rest.model.Action;
import com.log.rest.model.Properties;
import com.log.rest.model.UserLog;
import com.log.rest.repository.LogsRepository;
import com.log.rest.service.LogService;

//@Testcontainers
@TestInstance(Lifecycle.PER_CLASS)
public class LogServiceTests {
	
	@Mock
    private LogsRepository repository;
	
	@Mock
    ElasticsearchTemplate estemplate;
    
    
	@InjectMocks
    private LogService service;

    @BeforeAll
    public void setUp(){
    	MockitoAnnotations.initMocks(this);
    	//elasticsearchContainer.start();
    	Mockito.when(repository.save(Mockito.any(UserLog.class))).then(AdditionalAnswers.returnsFirstArg());
        
    }

	
	/*
	 * @Container private static ElasticsearchContainer elasticsearchContainer = new
	 * LogElasticSearchContainer();
	 */
	  
	  
	  
		/*
		 * @BeforeEach void testIsContainerRunning() {
		 * assertTrue(elasticsearchContainer.isRunning()); recreateIndex(); }
		 * 
		 * private void recreateIndex() { if (estemplate.indexExists(UserLog.class)) {
		 * estemplate.deleteIndex(UserLog.class); estemplate.createIndex(UserLog.class);
		 * } }
		 */
    
    
    @Test
    void testCreateLog() {
    	UserLog testLog = createALog();
    	//when(repository.save(testLog)).thenReturn(testLog);
        UserLog createdlog = service.save(testLog);
        assertNotNull(createdlog);
        assertEquals("ABC123XYZ", createdlog.getUserId());
       
    }
    
    
    private UserLog createALog() {
    	UserLog log = new UserLog();
    	log.setUserId("ABC123XYZ");
    	log.setSessionId("ABC123XYZ");
        Action action= new Action();
        action.setType("CLICK");
        action.setTime(new Date());
        Properties properties = new Properties();
        properties.setLocationX(53);
        properties.setLocationY(56);
        action.setProperties(properties);
        List<Action> allActions = new ArrayList<>();
        allActions.add(action);
        log.setActions(allActions);
        return log;
    }


}
