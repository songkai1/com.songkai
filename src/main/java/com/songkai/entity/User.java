package com.songkai.entity;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;

/**
 * @author songkai
 */
@ThriftStruct
public class User {
	private Long id;
	private String employeename;
	private String employeecode;
	
	@ThriftField(1)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@ThriftField(2)
	public String getEmployeename() {
		return employeename;
	}
	public void setEmployeename(String employeename) {
		this.employeename = employeename;
	}
	@ThriftField(3)
	public String getEmployeecode() {
		return employeecode;
	}
	public void setEmployeecode(String employeecode) {
		this.employeecode = employeecode;
	}
	
}
