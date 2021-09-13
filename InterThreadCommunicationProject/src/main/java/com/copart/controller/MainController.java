package com.copart.controller;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.copart.entity.Emoloyee;
import com.copart.repository.EmployeeDataRepo;
import com.copart.service.SaveService;

@RestController
@RequestMapping("/v1")
public class MainController {
 
	@Autowired
	private EmployeeDataRepo dao;
	
	@Autowired
	private SaveService service;
	
	@Autowired
	private EntityManager em;
	
	
	
	//we are planing to change some changes
	@PostMapping("/save")
	public String save() throws InterruptedException {
		return service.checkEmployeeUpdateStatus();
		/*
		Emoloyee emp1= dao.getById(1);
		System.out.println(emp1);
		System.out.println(System.identityHashCode(emp1));
		
		em.detach(emp1);
		Thread.sleep(30*1000);
		
		Emoloyee emp2= dao.getById(1);
		System.out.println(emp2);
		System.out.println(System.identityHashCode(emp2));
	
		
		return "success";
		*/
	}
}
