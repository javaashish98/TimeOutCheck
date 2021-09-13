package com.copart.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Entity
@Getter
@Setter
@ToString
@Table(name = "employeebo")
public class Emoloyee {
	@Id
	@GeneratedValue
	private Integer eid;
	private String ename;
	private double sal;
	private String email;
	private String city;
	private String status;

}
