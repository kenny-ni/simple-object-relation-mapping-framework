package com.jiageng.po;

import java.sql.*;
import java.util.*;

public class Employee {

	private Integer ID;
	private Integer Age;
	private String Name;
	private String Contact;


	public Integer getID(){
		return ID;
	}

	public void setID(Integer field){
		ID = field;
	}

	public Integer getAge(){
		return Age;
	}

	public void setAge(Integer field){
		Age = field;
	}

	public String getName(){
		return Name;
	}

	public void setName(String field){
		Name = field;
	}

	public String getContact(){
		return Contact;
	}

	public void setContact(String field){
		Contact = field;
	}

}
