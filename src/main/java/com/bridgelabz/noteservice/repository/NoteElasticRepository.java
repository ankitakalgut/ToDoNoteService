package com.bridgelabz.noteservice.repository;

import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import com.bridgelabz.noteservice.model.Note;

/*******************************************************************************
 * @author Ankita Kalgutkar
 *
 * PURPOSE:NoteElasticRepository
 ********************************************************************************/
public interface NoteElasticRepository extends ElasticsearchRepository<Note, String>
{
	public List<Note> findNotesByUserId(String userId);
	
	public Note findByTitle(String id);
	
	public List<Note> findByPin(String pin);
	
	public Note findByid(String noteId);
}