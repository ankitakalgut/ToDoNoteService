package com.bridgelabz.noteservice.utility;

/*********************************************************************************
 * @author Ankita Kalgutkar
 *
 * 
 *PURPOSE:To catch the token exceptions.
 ************************************************************************************/
public class JWTException extends RuntimeException 
{
	private static final long serialVersionUID = 4328986007283940610L;

	public JWTException(String message)
	{
		super(message);
	}
}
