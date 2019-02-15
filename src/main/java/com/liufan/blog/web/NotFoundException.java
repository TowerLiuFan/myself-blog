package com.liufan.blog.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

			public NotFoundException() {
				super();
				// TODO Auto-generated constructor stub
			}
		
		
			public NotFoundException(String message) {
				super(message);
				// TODO Auto-generated constructor stub
			}
		
			public NotFoundException(Throwable cause) {
				super(cause);
				// TODO Auto-generated constructor stub
			}

	
			
}
