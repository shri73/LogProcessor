package com.log.rest.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Document(indexName = "userlogs", type = "userlog")
@Data
@ApiModel(description = "Log of a user")
public class UserLog {
	
	@Id
	private String id;
	
	@ApiModelProperty
    private String userId;
	
	@ApiModelProperty
    private String sessionId;
	
	@ApiModelProperty
    private List<Action> actions;

}
