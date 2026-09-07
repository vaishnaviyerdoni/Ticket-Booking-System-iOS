package com.sunbeam.tikito.exceptions;

public class InvalidShowException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public InvalidShowException(String message)
	{
		super(message);
	}
}
