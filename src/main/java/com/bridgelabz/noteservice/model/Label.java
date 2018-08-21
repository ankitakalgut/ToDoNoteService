package com.bridgelabz.noteservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import io.swagger.annotations.ApiModelProperty;

/************************************************************************************************
 * @author Ankita Kalgutkar
 *
 * 
 * PURPOSE:Business Entity class for label
 *************************************************************************************************/
@Document(indexName = "labels", type = "label")
public class Label
{
	@Id
	@ApiModelProperty(hidden=true)
	private String labelid;
	
	@ApiModelProperty(hidden=true)
	private String userId;
	
	private String labelname;

	public String getLabelid() 
	{
		return labelid;
	}
	
	public void setLabelid(String labelid) 
	{
		this.labelid = labelid;
	}
	
	public String getUserId()
	{
		return userId;
	}
	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getLabelname() 
	{
		return labelname;
	}
	
	public void setLabelname(String labelname) 
	{
		this.labelname = labelname;
	}
	
	@ApiModelProperty(hidden=true)
	public boolean isTrash()
	{
		return false;
	}

	@Override
	public String toString() 
	{
		return "Label [labelid=" + labelid + ", userId=" + userId + ", labelname=" + labelname + "]";
	}
}
