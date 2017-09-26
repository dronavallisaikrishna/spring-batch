package com.springbatch.config;

import java.util.Date;


public class Customer {

	private  long id;
	
	private  String firstName;
	
	private  String lastName;
	
	private  Date birthdate;
	
	
	public Customer() {
	}
	
	public Customer(long id, String firstName, String lastName, java.util.Date date) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = date;
	}
	

	public long getId() {
		return id;
	}



	public String getFirstName() {
		return firstName;
	}



	public String getLastName() {
		return lastName;
	}



	public Date getBirthdate() {
		return birthdate;
	}



	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return " id is "+id+", Customer name is:- "+firstName+"Customer lastname is:-"+lastName+", date of birth is "+birthdate;
	}
	
}
