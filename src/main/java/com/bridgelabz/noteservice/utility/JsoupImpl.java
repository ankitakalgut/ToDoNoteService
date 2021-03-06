package com.bridgelabz.noteservice.utility;

import java.io.IOException;
import java.net.URL;
import org.jsoup.Jsoup;

/***************************************************************************
 * @author Ankita Kalgutkar
 *
 * Purpose:Implementation class for jsoup
 ***************************************************************************/
public class JsoupImpl 
{
	public static String getTitle(String url) throws IOException 
	{
		return Jsoup.connect(url).get().title();
	}

	public static String getImage(String url) throws IOException 
	{
		return Jsoup.connect(url).get().select("img").first().attr("abs:src");
	}
	
	public static String getDomain(String url) throws IOException
	{
		return new URL(url).getHost();
	}
}

