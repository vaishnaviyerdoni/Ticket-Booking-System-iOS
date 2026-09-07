package com.sunbeam.tikito.exceptions;

public class InvalidSeatsException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public InvalidSeatsException(String message)
	{
		super(message);
	}
}
