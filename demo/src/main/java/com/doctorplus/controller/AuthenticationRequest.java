package com.doctorplus.controller;

//Aquí codificamos los getter/setter de la clase AuthenticationRequest

public class AuthenticationRequest {

	
	private String id; 

    private String password;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
