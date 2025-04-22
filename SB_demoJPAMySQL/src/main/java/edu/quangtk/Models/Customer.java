package edu.quangtk.Models;

import jakarta.persistence.Entity;

@Entity

public class Customer {
	private int id;
	public Customer(int id)
	{
	this.id = id;
	}
	
}
