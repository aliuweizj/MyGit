package com.lw.bean;

import java.util.ArrayList;
import java.util.List;

public class Result {
	private int result;
	private List<Person> personData =  new ArrayList<Person>();
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public List<Person> getPersonData() {
		return personData;
	}
	public void setPersonData(List<Person> personData) {
		this.personData = personData;
	}
	
	
}
