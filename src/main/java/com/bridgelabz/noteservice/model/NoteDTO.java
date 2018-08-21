package com.bridgelabz.noteservice.model;

import java.io.Serializable;
import java.util.List;
import io.swagger.annotations.ApiModelProperty;

/************************************************************************************
 * @author Ankita Kalgutkar
 *
 * 
 *PURPOSE:NoteDTO Entity class.
 ************************************************************************************/

//@Document(collection = "note")
public class NoteDTO implements Serializable 
{
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(hidden = true)
	private String id;
		
	private String title;
	
	private String description;
	
	@ApiModelProperty(hidden = true)
	private boolean trash;
	
	@ApiModelProperty(hidden = true)
	private boolean archive;
	
	@ApiModelProperty(hidden = true)
	private String pinned;
	
	@ApiModelProperty(hidden = true)
	private String dateOfCreation;
	
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
	

	public NoteDTO()
	{
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}
	
	public String getTitle() 
	{
		return title;
	}

	public void setTitle(String title) 
	{
		this.title = title;
	}

	public String getDescription() 
	{
		return description;
	}

	public void setDescription(String description) 
	{
		this.description = description;
	}

	public String getDateOfCreation()
	{
		return dateOfCreation;
	}

	public void setDateOfCreation(String dateOfCreation) 
	{
		this.dateOfCreation = dateOfCreation;
	}

	public boolean isTrash()
	{
		return trash;
	}

	public void setTrash(boolean trash) 
	{
		this.trash = trash;
	}

	public boolean isArchive() 
	{
		return archive;
	}

	public void setArchive(boolean archive) 
	{
		this.archive = archive;
	}


	public String getPinned() 
	{
		return pinned;
	}

	public void setPinned(String pinned)
	{
		this.pinned = pinned;
	}
	
	@Override
	public String toString() 
	{
		return "NoteDTO [id=" + id + ", title=" + title + ", description=" + description + ", trash=" + trash
				+ ", archive=" + archive + ", pinned=" + pinned + ", dateOfCreation=" + dateOfCreation + ", lname="
				+ lname + "]";
	}
}
