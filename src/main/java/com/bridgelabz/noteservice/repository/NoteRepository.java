package com.bridgelabz.noteservice.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.bridgelabz.noteservice.model.Note;

/*********************************************************************************
 * @author Ankita Kalgutkar
 *
 * 
 *PURPOSE:Repository class to extend the MongoRepository
 ***********************************************************************************/

@Repository
public interface NoteRepository extends MongoRepository<Note, String>
{
	public List<Note> findNotesByUserId(int userId);
	
	public List<Note> findNotesByUserId(String userId);
	
	public Note findByTitle(String id);
	
	public List<Note> findByPin(String pin);
	
	public Note findByid(String noteId);
	
	public List<Note> findByuserIdAndTrash(String userId, boolean b);
}