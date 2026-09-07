package com.sunbeam.tikito.utils;




import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(Include.NON_NULL)
public class Resp<T> {
	
	
	public static enum Status{
		success,error
	}
	
	
	private Status status;
	private T data;
	private String message;
	
	public static <T>Resp<T> success(T data){
		
		return new Resp<>(Status.success, data,null);
		
	}
	
	public static <T>Resp<T> error(String message){
		return new Resp<>(Status.error,null,message);
		
	}

}
