package com.bridgelabz.noteservice.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/*********************************************************************************
 * @author Ankita Kalgutkar
 *
 * 
 *PURPOSE:creating token
 ************************************************************************************/
public class JWToken 
{
	public static Claims verifyToken(String token) 
	{
		try
		{
			Claims claims = Jwts.parser()
					.setSigningKey("passKey")
					.parseClaimsJws(token).
					getBody();
			return claims;
		} 
		catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) 
		{
			throw new JWTException("Error in verifying JW Token");
		} 
		catch (ExpiredJwtException e) 
		{
			throw new JWTException("Token Expired");
		}
	}
}
