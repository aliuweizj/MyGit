package com.lw.bean;

public class Girl {
	private String name;
	private int age;
	private String school;
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return "Girl [age=" + age + ", name=" + name + ", school=" + school
				+ "]";
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	
}
