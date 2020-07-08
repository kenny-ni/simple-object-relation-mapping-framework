package com.jiageng.po;

import java.sql.*;
import java.util.*;

public class Sys_config {

	private java.sql.Timestamp set_time;
	private String set_by;
	private String variable;
	private String value;


	public java.sql.Timestamp getSet_time(){
		return set_time;
	}

	public void setSet_time(java.sql.Timestamp field){
		set_time = field;
	}

	public String getSet_by(){
		return set_by;
	}

	public void setSet_by(String field){
		set_by = field;
	}

	public String getVariable(){
		return variable;
	}

	public void setVariable(String field){
		variable = field;
	}

	public String getValue(){
		return value;
	}

	public void setValue(String field){
		value = field;
	}

}
