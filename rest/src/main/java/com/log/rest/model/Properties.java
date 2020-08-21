package com.log.rest.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Document(indexName = "property_log", type = "property")
@Data
@ApiModel(description = "Properties of a user")
public class Properties {

    @Id
    private Long id;
    
    @ApiModelProperty
    private int locationX;
    
    @ApiModelProperty
    private int locationY;
    
    @ApiModelProperty
    private String viewedId;
    
    @ApiModelProperty
    private String pageFrom;
    
    @ApiModelProperty
    private String pageTo;
}
