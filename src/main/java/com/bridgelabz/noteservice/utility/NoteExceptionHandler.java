package com.bridgelabz.noteservice.utility;

/*********************************************************************************
 * @author Ankita Kalgutkar
 *
 * 
 *PURPOSE:To catch the note exceptions.
 ************************************************************************************/

public class NoteExceptionHandler extends RuntimeException
{
	private static final long serialVersionUID = 8037704665476484234L;

	public NoteExceptionHandler(String message) 
	{
		super(message);
	}	
}