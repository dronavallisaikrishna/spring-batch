package com.springbatch.config;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "select c from Customer c")
})
public class Customer {
	
	@Id
	private  long id;
	@Column(name="firstName")
	private  String firstName;
	@Column(name="lastName")
	private  String lastName;
	@Column(name="birthdate")
	private  Date birthdate;
	
	
	public Customer() {
	}
	
	public Customer(long id, String firstName, String lastName, Date birthdate) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
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
