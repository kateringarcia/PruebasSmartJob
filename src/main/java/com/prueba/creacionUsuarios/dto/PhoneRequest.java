package com.prueba.creacionUsuarios.dto;

import lombok.Data;

@Data
public class PhoneRequest {
	
    private Long id;
    private String number;
    private String citycode;
    private String contrycode;
    
	public PhoneRequest(String number, String citycode, String contrycode) {
		super();
		this.number = number;
		this.citycode = citycode;
		this.contrycode = contrycode;
	}
    
    

}
