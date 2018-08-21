package com.bridgelabz.noteservice.service;

import java.io.IOException;
import java.util.List;
import com.bridgelabz.noteservice.model.Label;
import com.bridgelabz.noteservice.model.Note;
import com.bridgelabz.noteservice.model.NoteDTO;

/*********************************************************************************
 * @author Ankita Kalgutkar
 *
 * 
 *PURPOSE:Methods to carry out service
 *********************************************************************************/

public interface NoteService
{
	public void createNote(NoteDTO note, String userId)throws IOException;

	public List<Note> displayAllNotes(String userId);

	public void deleteNote(String noteId);

	public List<Note> PinnedNotes(String pin);

	public List<Note> Archive(String userId);

	public Note setReminder(String userId, String id, String reminderTime) throws Exception;
	
	public void createlabel(Label label, String userId);
	
	public void addlabel(String noteid,String labelname);
	
	public void deletelabel(String labelid) ;
	
	public List<Label> displaylabels(String userId);

	public List<Note> getAllTrashedNotes(String userId);

	public void setPinNote(String noteId, String userId);
	
	public void archivenote(String noteId, String userId);	
	
	public List<Note> sortbyTitle(String userId) ;
	
	public List<Note> sortbyNoteId(String userId) ;
	
	public void deleteNotePermanently(String noteId) ;

	public void updateNote(String noteId, NoteDTO note) throws IOException;

	public void updateLabel(String labelid, Label label, String userId);	
}