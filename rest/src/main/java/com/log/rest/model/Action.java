package com.log.rest.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Document(indexName = "action_log", type = "action")
@Data
@ApiModel(description = "Actions of a user")
public class Action {
	
	@Id
	private Long id;
	
	@ApiModelProperty
    private Date time;
	
	@ApiModelProperty
    private String type;
	
	@ApiModelProperty
    private Properties properties;

}
