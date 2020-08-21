package com.log.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.log.rest.model.UserLog;
import com.log.rest.service.LogService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("/v1/logs")
public class LogController {
	
	private LogService service;
	
	public LogController(LogService service) {
		this.service = service;
	}

	@ApiOperation(value = "Add a log")
	@ApiResponses(value = {
	        @ApiResponse(code = 201, message = "Successfully created a log"),
	        @ApiResponse(code = 400, message = "Bad request")}
	)
	@PostMapping(value = "/add")
	@ResponseStatus(value = HttpStatus.CREATED)
    public void addLog(@Valid @RequestBody UserLog log) {
        service.save(log);
    }
	
	@ApiOperation(value = "Search logs by property", responseContainer="List")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "success"),
	        @ApiResponse(code = 400, message = "Bad Request")
	})

	@GetMapping(value = "/search")
	@ResponseStatus(value = HttpStatus.OK)
    public List<UserLog> getLogById( @RequestParam(value = "userId", required = false) String userId,
    		@RequestParam(value = "type", required = false) String type,
    		@RequestParam(value = "time", required = false) String time) {
		return service.searchParam(userId, type, time);
        
    }
	
	
}
