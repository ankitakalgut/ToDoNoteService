package com.bridgelabz.noteservice.model;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import io.swagger.annotations.ApiModelProperty;

/*********************************************************************************
 * @author Ankita Kalgutkar
 *
 * 
 *PURPOSE:Note Entity class.
 ************************************************************************************/

@Document(indexName = "notes", type = "note")
public class Note 
{	
	@Id
	@ApiModelProperty(hidden = true)
	private String  id;
	
	private String title;
	
	@ApiModelProperty(hidden = true)
	private boolean pin;
	
	private Description description;

	public Description getDescription()
	{
		return description;
	}
	public void setDescription(Description description)
	{
		this.description = description;
	}
	@ApiModelProperty(hidden = true)
	private boolean archive;
	
	@ApiModelProperty(hidden = true)
	private String createdDate;
	
	@ApiModelProperty(hidden = true)
	private String userId;
	
	@ApiModelProperty(hidden = true)
	private boolean trash=true;	
	
	@ApiModelProperty(hidden = true)
	private List<Label> lname;
	
	
	public List<Label> getLname() 
	{
		return lname;
	}

	public void setLname(List<Label> lname)
	{
		this.lname = lname;
	}
	
	public boolean isPin() 
	{
		return pin;
	}

	public void setPin(boolean pin) 
	{
		this.pin = pin;
	}

	public boolean isArchive()
	{
		return archive;
	}

	public void setArchive(boolean archive)
	{
		this.archive = archive;
	}

	public boolean isTrash() 
	{
		return trash;
	}

	public String getId() 
	{
		return id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	public void setTrash(boolean trash) 
	{
		this.trash = trash;
	}

	public String getTitle() 
	{
		return title;
	}

	public Note()
	{
		
	}
	public void setTitle(String title) 
	{
		this.title = title;
	}
	
	public String getCreatedDate() 
	{
		return createdDate;
	}

	public void setCreatedDate(String createdDate )
	{
		this.createdDate = createdDate;
	}
	
	public String getUserId()
	{
		return userId;
	}
	
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	@Override
	public String toString() 
	{
		return "Note [id=" + id + ", title=" + title + ", pin=" + pin + ", description=" + description + ", archive="
				+ archive + ", createdDate=" + createdDate + ", userId=" + userId + ", trash=" + trash + "]";
	}
}