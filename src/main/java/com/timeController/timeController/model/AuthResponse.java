package com.timeController.timeController.model;

import java.io.Serializable;

public class AuthResponse implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -9181375385349799587L;
    private final String jwttoken;

	public AuthResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}
    
}
