/*
	파일명:AttendanceRestController.java 
	설명: 근태관리 컨트롤러
	작성일 : 2020-12-28   
	수정일 : 2020-01-07
	작성자 : 심재형 
 */
package com.olive.attendance.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.velocity.app.event.ReferenceInsertionEventHandler.referenceInsertExecutor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.olive.attendance.service.AnnualService;
import com.olive.dto.Document;

import paging.Criteria;
import paging.Pagination;
import paging.PagingService;


@RestController
@RequestMapping("/Annual/")
public class AnnualRestController {
	 
	private AnnualService service;
	@Autowired
	public void setAttendanceService(AnnualService annualservice) {
		this.service = annualservice ;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	@Autowired
	private PagingService pagingService;
	

//=================== 휴가 캘린더 select ===================// 
	
	
	@ResponseBody
	@RequestMapping(value = "calendarList.do", method = RequestMethod.GET)
	public List<Document> calendarList() {
		List<Document> calendarList = null;
		System.out.println("휴가캘린더");
		calendarList = service.calendarList();
		System.out.println("캘린더리스트"+calendarList);
		return calendarList;
	}		
	
//=================== 휴가 테이블  ===================// 
@RequestMapping(value = "analPage.do", method = RequestMethod.POST)
public JSONObject attPage(Criteria cri) {
		
		cri.setCriteria("annual_diff", "docno", "desc");
		int totalCount = pagingService.getListCount(cri);
		Pagination pagination = new Pagination(cri, totalCount);
		List<Map<String, Object>> result = pagingService.getList(cri);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", result);
		jsonObject.put("pagination", pagination);
		jsonObject.put("criteria", cri);
		System.out.println("휴가테이블1" + result);
	
	
	return jsonObject;
	
}
//=================== 휴가 사용자별 캘린더 ===================// 
@ResponseBody
@RequestMapping(value = "annualUser.do", method = RequestMethod.POST)
 public List<Document> annualUser (String empno) {
	System.out.println("휴가사용");
	List<Document> annualUser = null;
	annualUser = service.annualUser(empno);
	
	return annualUser;
}
 
	
	

}
	


