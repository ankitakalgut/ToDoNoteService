package com.bridgelabz.noteservice.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.noteservice.model.Label;
import com.bridgelabz.noteservice.model.Note;
import com.bridgelabz.noteservice.model.NoteDTO;
import com.bridgelabz.noteservice.service.NoteServiceImpl;
import com.bridgelabz.noteservice.service.FeignService;
import com.bridgelabz.noteservice.utility.Response;

/****************************************************************************
 @author Ankita Kalgutkar
*
*
*	
*Purpose:Controller class  to create api's.
******************************************************************************/

@RefreshScope
@RestController
@RequestMapping(value="/notes")
public class NoteController 
{
	private static final Logger logger = LoggerFactory.getLogger(NoteController.class);
	
	@Autowired
	NoteServiceImpl noteService;
	
	@Autowired
	FeignService feign;
	
	/**
	 * @param note
	 * @param req
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/createnote", method = RequestMethod.POST)
	public ResponseEntity<Response> createNote(@RequestBody NoteDTO note,HttpServletRequest req) throws IOException 
	{	
		String userId=req.getHeader("userId");
		logger.info("Creating a note for the user with userId="+userId);
		noteService.createNote(note, userId);
		return new ResponseEntity<>(new Response("Note Created", HttpStatus.CREATED), HttpStatus.OK);
	}
	
	/**
	 * @param noteId
	 * @param note
	 * @param req
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/updatenote/{noteId}", method = RequestMethod.PUT)
	public ResponseEntity<Response> updateNote(@PathVariable String noteId,@RequestBody NoteDTO note) throws IOException
	{	
		noteService.updateNote(noteId,note);
		logger.info("The note updated is"+noteId);
		return new ResponseEntity<>(new Response("Note Updated", HttpStatus.ACCEPTED), HttpStatus.OK);		
	}
	
	/**
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deletenote", method = RequestMethod.DELETE)
	public ResponseEntity<Response> deleteNote(@RequestParam String id)
	{
		 noteService.deleteNote(id);
		 logger.info("Note is deleted and present in trash");
		return new ResponseEntity<>(new Response("Note Deleted", HttpStatus.ACCEPTED), HttpStatus.OK);		
	}
	/**
	 * @param id
	 * @return
	 */
	@RequestMapping(value="deletenoteforever", method =RequestMethod.DELETE)
	public ResponseEntity<Response> deletePermanent(@RequestParam String id)
	{
		 noteService.deleteNotePermanently(id);
		 logger.info("Note with id"+id+"is deleted permanently");
		return new ResponseEntity<>(new Response("Note Deleted", HttpStatus.ACCEPTED), HttpStatus.OK);		
	}
	/**
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/gettrashednotes", method = RequestMethod.GET)
	public List<Note> getAllTrashedNotes(HttpServletRequest req)
	{
		String userId=req.getHeader("userId");
		List<Note> list = noteService.getAllTrashedNotes(userId);
		logger.info("Trahed notes list"+list);
		return list;
	}
		
	/**
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/viewnotes", method = RequestMethod.GET)
	public List<Note> getAllNotes(HttpServletRequest req)
	{
		String userId=req.getHeader("userId");
		List<Note> list = noteService.displayAllNotes(userId);
		logger.info("List of all notes"+list);
		return list;
	}
	
	/**
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/sortnotesbytitle",method=RequestMethod.GET)
	public List<Note> getSortedByTitle(HttpServletRequest req)
	{
		String userId=req.getHeader("userId");
		List<Note> list=noteService.sortbyTitle(userId);
		logger.info("Notes sorted by title"+list);
		return list;
		
	}
	
	/**
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/sortnotesbynoteid",method=RequestMethod.GET)
	public List<Note> getSortedByNoteId(HttpServletRequest req)
	{
		String userId=req.getHeader("userId");
		List<Note> list=noteService.sortbyNoteId(userId);
		logger.info("Notes sorted by noteId"+list);
		return list;
	}
	
	/**
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getpinnednotes", method = RequestMethod.GET)
	public List<Note> getPinnedNotes(HttpServletRequest req)
	{
		String userId=req.getHeader("userId");
		List<Note> list = noteService.PinnedNotes(userId);
		logger.info("List of pinned notes"+list);
		return list;
	}
	
	/**
	 * @param noteId
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/setPin", method = RequestMethod.GET)
	public ResponseEntity<Response> setPin(@RequestParam String noteId,HttpServletRequest req)
	{
		String userId=req.getHeader("userId");
		noteService.setPinNote(noteId,userId);
		logger.info("the pinned note is"+noteId);
		return new ResponseEntity<>(new Response("Note Pinned", HttpStatus.ACCEPTED), HttpStatus.OK);
	}

	/**
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getarchivenotes", method = RequestMethod.GET)
	public List<Note> getArchievednotes(HttpServletRequest req)
	{
		String userId=req.getHeader("userId");
		List<Note> list = noteService.Archive(userId);
		logger.info("List of archived notes"+list);
		return list;
	}	
	
	/**
	 * @param noteId
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/setarchive", method = RequestMethod.PUT)
	public ResponseEntity<Response> archiveNote(@RequestParam String noteId,HttpServletRequest req)
	{
		String userId=req.getHeader("userId");
		noteService.archivenote(noteId,userId);
		logger.info("the archived note is"+noteId);
		return new ResponseEntity<>(new Response("Note Archived", HttpStatus.ACCEPTED), HttpStatus.OK);
	}
	
	/**
	 * @param req
	 * @param id
	 * @param reminderTime
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reminder", method = RequestMethod.POST)
	public ResponseEntity<Response> reminder(HttpServletRequest req,@RequestParam String id, @RequestParam String reminderTime) throws Exception
	{	
		String userId=req.getHeader("userId");
		Note note =noteService.setReminder(userId, id, reminderTime);
		return new ResponseEntity<>(new Response("Reminder"+"-"+note.toString(), HttpStatus.ACCEPTED), HttpStatus.OK);		
	}
	
	/**
	 * @param label
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/createlabel", method = RequestMethod.POST)
	public ResponseEntity<Response> createlabel(@RequestBody Label label,HttpServletRequest req)
	{
		String userId=req.getHeader("userId");
		noteService.createlabel(label,userId);
		logger.info("label is created with label name"+label.getLabelname());
		return new ResponseEntity<>(new Response("Label Created", HttpStatus.CREATED), HttpStatus.OK);
	}
	
	/**
	 * @param noteid
	 * @param labelname
	 * @return
	 */
	@RequestMapping(value = "/addlabeltonote", method = RequestMethod.POST)
	public ResponseEntity<Response> addlabel(@RequestParam String noteid,@RequestParam String labelname)
	{
		noteService.addlabel(noteid,labelname);
		return new ResponseEntity<>(new Response("Label added.....", HttpStatus.CREATED), HttpStatus.OK);
	}
	
	/**
	 * @param labelid
	 * @return
	 */
	@RequestMapping(value = "/deletelabel/{labelid}", method = RequestMethod.DELETE)
	public ResponseEntity<Response> deletelabel(@PathVariable String labelid)
	{
		noteService.deletelabel(labelid);
		return new ResponseEntity<>(new Response("Label Deleted", HttpStatus.ACCEPTED), HttpStatus.OK);		
	}
	
	/**
	 * @param labelid
	 * @param labelname
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/updatelabel/{labelid}", method = RequestMethod.PUT)
	public ResponseEntity<Response> updateNote(@PathVariable String labelid,Label label,HttpServletRequest req)
	{
		String userId=req.getHeader("userId");
		noteService.updateLabel(labelid,label,userId);
		return new ResponseEntity<>(new Response("label Updated", HttpStatus.ACCEPTED), HttpStatus.OK);
	}
			
	/**
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/viewlabels", method = RequestMethod.GET)
	public List<Label> getAllNotess(HttpServletRequest req)
	{
		String userId=req.getHeader("userId");
		List<Label> list=noteService.displaylabels(userId);
		return list;
	}
	
	/**
	 * @return list of users
	 */
	@RequestMapping(value = "/getUsers", method = RequestMethod.GET)
	public  List<?> getAllUsers()
	{
		List<?> list=feign.getAllUsers();
		return list;
	}	 
}