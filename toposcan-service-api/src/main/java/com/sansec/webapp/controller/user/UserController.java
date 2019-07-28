package com.sansec.webapp.controller.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sansec.feign.user.UserFeignClient;
import com.sansec.module.user.Employee;
import com.util.page.Page;
import com.util.page.SearchResult;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private Log logger = LogFactory.getLog(UserController.class);
	
	@Autowired
	private UserFeignClient userFeignClient;
	
	@GetMapping("queryById")
	public Employee queryById(int id) {
		return userFeignClient.queryById(id);
	}
	
	@GetMapping("query")
	public SearchResult<Employee> query(@RequestParam(name="queryParam",defaultValue="") String keyWord,
			@RequestParam(name = "offset",defaultValue="0") 
			int offset,
			@RequestParam(name = "limit",defaultValue="15") 
			int limit){
		SearchResult<Employee> sr = new SearchResult<Employee>();
		int totalCount = userFeignClient.count(keyWord);
		if(totalCount > 0) {
			sr.setList(userFeignClient.query(keyWord, offset, limit));
		}
		if(limit > 0) {
			Page page = new Page(limit, offset);
			page.setTotalCount(totalCount);
			sr.setPage(page);
		}
		return sr;
	}
	
	@PostMapping("insert")
	public int insert(@RequestBody Employee emp) {
		logger.info(emp.getUsername());
		return 10;
	}
}
