package com.bridgelabz.noteservice.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**************************************************************************************
 * @author Ankita Kalgutkar
 *
 * 
 *Purpose:RestPreConditions to check conditions
 *************************************************************************************/

public final class RestPrecondition 
{	
	@Autowired
	static	Environment environment;
	
	@Autowired
	static
	Messages message;
	
	
    private  RestPrecondition() 
    {
        throw new AssertionError();
    }
   
    public static <T>  T checkNull(T resource,String errorMessage)
    {
      if(resource==null)   
      {   
    	  return resource;
      }
      else    
    	  
      throw new NoteExceptionHandler(errorMessage);
    }
    
    public static <T>  T checkNotNull(T resource,String errorMessage)
    {
	      if(resource==null)   
	      {   
	    	  throw new NoteExceptionHandler(errorMessage);
	      }
	  return resource;   
    }
}
