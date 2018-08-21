package com.bridgelabz.noteservice.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.bridgelabz.noteservice.model.Description;
import com.bridgelabz.noteservice.model.Label;
import com.bridgelabz.noteservice.model.Link;
import com.bridgelabz.noteservice.model.Note;
import com.bridgelabz.noteservice.model.NoteDTO;
import com.bridgelabz.noteservice.repository.LabelElasticRepository;
import com.bridgelabz.noteservice.repository.LabelRepository;
import com.bridgelabz.noteservice.repository.NoteElasticRepository;
import com.bridgelabz.noteservice.repository.NoteRepository;
import com.bridgelabz.noteservice.utility.JsoupImpl;
import com.bridgelabz.noteservice.utility.Messages;
import com.bridgelabz.noteservice.utility.NoteExceptionHandler;
import com.bridgelabz.noteservice.utility.RestPrecondition;

/*********************************************************************************
 * @author Ankita Kalgutkar
 *
 * 
 *PURPOSE:Methods to perform note operations
 ************************************************************************************/

@Service
public class NoteServiceImpl implements NoteService 
{
	@Autowired
	Messages messages;
	  
	@Autowired
	LabelRepository labelRepository;
	
	@Autowired
	NoteRepository noteRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	NoteElasticRepository elasticnoterepo;
	
	@Autowired
	LabelElasticRepository elasticlabelrepo;
	
	/**
	 * purpose:create a new note
	 * @param note,userId
	 * @return
	 */
	@Override
	public void createNote(NoteDTO note, String userId)  throws IOException
	{
		System.out.println("createnote..........."+userId);
		if (note.getTitle() == null && note.getDescription() == null)
		throw new NoteExceptionHandler(messages.get("114"));
		Note notes=new Note();
		notes = modelMapper.map(note,Note.class);
		if(!note.getDescription().equals(""))
	    {           
	           String noteDescription=note.getDescription();       
	           notes.setDescription(Descriptionfield(noteDescription));
	    }
		notes.setCreatedDate(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
		notes.setTitle(note.getTitle() );
		notes.setUserId(userId);
		notes.setTrash(false);
		notes.setArchive(false);
		notes.setPin(false);
		try 
		{
			noteRepository.save(notes);
			elasticnoterepo.save(notes);
		}
		catch (DataIntegrityViolationException | ConstraintViolationException e)
		{
			throw new NoteExceptionHandler(messages.get("115"));
		}
	}
	
	/**
	 * purpose:description method for url
	 * @param noteId,note
	 * @return
	 */
	public static Description Descriptionfield(String noteDescription) throws IOException
    {
        Description desc= new Description();
        List<Link> linkList=new ArrayList<>();
        List<String>simpleList=new ArrayList<>();
        String[] descriptionArray= noteDescription.split(" ");
        for(int i=0;i<descriptionArray.length; i++)
        {
            if(descriptionArray[i]. startsWith("http://") || descriptionArray[i]. startsWith("https://") )
            { 
                Link link= new Link();
                link.setLinkTitle(JsoupImpl.getTitle(descriptionArray[i])) ;
                link.setLinkDomainName(JsoupImpl.getDomain( descriptionArray[i]));
                link.setLinkImage(JsoupImpl. getImage(descriptionArray[i])) ;
                System.out.println(link);
                linkList.add(link);                                                           
            }   
            else if(!descriptionArray[i]. equals("") && (!descriptionArray[i]. startsWith("http://") || !descriptionArray[i]. startsWith("https://")) )
            {
                simpleList.add( descriptionArray[i]);
            }
        }
        desc.setSimpleDescription( simpleList);
        desc.setLinkDescription( linkList);
        return desc;
    }
	
	/**
	 * purpose:delete a note
	 * @param noteId
	 * @return
	 */
	@Override 
	public void deleteNote(String noteId)
	{
		Note note = elasticnoterepo.findByid(noteId);	
		RestPrecondition.checkNotNull(note,messages.get("118"));
		if (note.isTrash() == false)
		{
			note.setTrash(true);
			noteRepository.save(note);
			return;
		}
		noteRepository.deleteById(noteId);
		elasticnoterepo.deleteById(noteId);
	  }
	
	/**
	 * purpose:get trashed list of notes
	 * @param userId
	 * @return
	 */
	@Override
	public List<Note> getAllTrashedNotes(String userId) 
	{
		List<Note> noteList = noteRepository.findByuserIdAndTrash(userId, true);
		if(noteList==null)
		{
			throw new NoteExceptionHandler(messages.get("123"));
		}
		return noteList;
	}
	
	/**
	 * purpose:display all notes 
	 * @param userId
	 * @return
	 */
	@Override
	public List<Note> displayAllNotes(String userId) 
	{
		List<Note> noteList = elasticnoterepo.findNotesByUserId(userId);
		if(noteList==null)
		{
			throw new NoteExceptionHandler(messages.get("123"));
		}            
		return noteList;
	}	
	
	/**
	 * purpose:sort notes by title
	 * @param userId
	 * @return
	 */
	@Override
	public List<Note> sortbyTitle(String userId) 
	{
		List<Note> list = elasticnoterepo.findNotesByUserId(userId);
		if(list==null)
		{
			throw new NoteExceptionHandler(messages.get("123"));
		}
		List<Note> stream = list.stream()
		.sorted((t1,t2)->t1.getTitle().compareToIgnoreCase(t2.getTitle())).
		collect(Collectors.toList());
		return stream;	
	}
	
	/**
	 * purpose:get all pinned notes
	 * @param userId
	 * @return
	 */
	@Override
	public List<Note>PinnedNotes(String userId)
	{
		List<Note> noteList = elasticnoterepo.findNotesByUserId(userId);
		noteList= noteList.stream().filter(x -> x.isPin()==true).collect(Collectors.toList());
		return noteList;		
	}
	
	/**
	 * purpose:update a note
	 * @param noteId,note
	 * @return
	 */
	@Override
	public void updateNote(String noteId,NoteDTO note) throws IOException 
	{
		Optional<Note> optionalnote =  elasticnoterepo.findById(noteId);
		if (!optionalnote.isPresent())
		{
			throw new NoteExceptionHandler(messages.get("118"));
		}
		Note notes=optionalnote.get();
		notes.setTitle(note.getTitle());
		if(!note.getDescription().equals(""))
	    {           
	           String noteDescription=note.getDescription();       
	           notes.setDescription(Descriptionfield(noteDescription));
	    }
		noteRepository.save(notes);	
		elasticnoterepo.save(notes);
	}
	
	/**
	 * purpose:get all Archive notes
	 * @param userId
	 * @return
	 */
		
	@Override
	public List<Note> Archive(String userId)
	{
		List<Note> noteList =  elasticnoterepo.findNotesByUserId(userId);
		if(noteList==null)
		{
			throw new NoteExceptionHandler(messages.get("124"));
		}
		List<Note> stream = noteList.stream()
		.filter(pair-> pair.isArchive()==true).
		collect(Collectors.toList());
		return stream;
	}	
	
	@Override
	public Note setReminder(String userId, String id, String reminderTime) throws Exception  
	{	
	   Timer timer = null;
	   Optional<Note> note =  elasticnoterepo.findById(id);
	   if (note.isPresent()) 
	   {
	      Date reminder = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(reminderTime);
	      long timeDifference = reminder.getTime() - new Date().getTime();
	      timer = new Timer();
	      timer.schedule(new TimerTask() 
	      {
	          @Override
	          public void run() 
	          {
	             System.out.println("Reminder task:" + note.toString());

	           }
	        }, timeDifference);
	     }
	     else
	     {
	        throw new NoteExceptionHandler(messages.get("118"));
	     }
	   	 return note.get();
	 }
	
	@Override
	public void createlabel(Label label, String userId)
	{
		if (label == null)
		throw new NoteExceptionHandler(messages.get("114"));
		List<Label> list=labelRepository.findAll();
		for(Label l:list)
		{
			if(l.getLabelname().equals(	label.getLabelname()))
			throw new NoteExceptionHandler(messages.get("119"));
		}
		//label.setUserId(user.getId());
		label.setUserId(userId);
		label.setLabelname(label.getLabelname());
		try 
		{
			labelRepository.save(label);
			elasticlabelrepo.save(label);
		}
		catch (DataIntegrityViolationException | ConstraintViolationException e)
		{
			throw new NoteExceptionHandler(messages.get("115"));
		}
	}
		
	@Override
	public void addlabel(String noteid, String labelname) 
	{
		Label label = new Label();
		Optional<Note> note =  elasticnoterepo.findById(noteid);
		if (note.get().getLname() == null) 
		{
			List<Label> newLabelList = new ArrayList<>();
			note.get().setLname(newLabelList);
		}
		List<Label> list=labelRepository.findAll();
		for(Label l:list)
		{	
			if(l.getLabelname().equals(labelname))
			{
				Optional<Note> optionalnote =  elasticnoterepo.findById(noteid);
				if (!optionalnote.isPresent())
				{
					throw new NoteExceptionHandler(messages.get("118"));
				}
				label.setLabelname(labelname);
				//label.setUserId(optionalnote.get().getUserId());
				//label.setLabelid(l.getLabelid());
				note.get().getLname().add(label);
				noteRepository.save(note.get());
				elasticnoterepo.save(note.get());
			}		
	     }  	
	  }
	
	/**
	 * Purpose:Delete a label
	 * @param labelid
	 * @return
	 */
	
	@Override
	public void deletelabel(String labelid) 
	{
		Optional<Label> optionalnote = elasticlabelrepo.findById(labelid);
		if (!optionalnote.isPresent())
		{
			throw new NoteExceptionHandler(messages.get("118"));
		}	
		labelRepository.deleteById(labelid);
		elasticlabelrepo.deleteById(labelid);
	}
	
	/**
	 * purpose:update a label
	 * @param noteId,userId
	 * 
	 **/
	
	@Override
	public void updateLabel(String labelid,Label label,String userId) 
	{
		if (userId == null)
			throw new NoteExceptionHandler(messages.get("120"));
		Optional<Label> optionalnote = elasticlabelrepo.findById(labelid);
		if (!optionalnote.isPresent())
		{
			throw new NoteExceptionHandler(messages.get("121"));
		}
		Label labels=optionalnote.get();
		labels.setLabelname(label.getLabelname());
		labelRepository.save(labels);
		elasticlabelrepo.save(labels);
	}	
	
	/**
	 * purpose:display all labels
	 * @param userId
	 * 
	 **/
	@Override
	public List<Label> displaylabels(String userId)
	{
		List<Label> labellist =elasticlabelrepo.findByUserId(userId);
		if(labellist==null)
		{
			throw new NoteExceptionHandler("Labels not found");
		}
		Stream<List<Label>> s = Stream.of(labellist);
        s.forEach(System.out::println);
        List<Label> stream = labellist.stream()
        .filter(pair -> pair.getUserId().equals(userId)).
        collect(Collectors.toList());
        return stream;
	}
	
	/**
	 * purpose:pin a note
	 * @param noteId,userId
	 * 
	 **/
	@Override
	public void setPinNote(String noteId, String userId) 
	{
		Note note = elasticnoterepo.findByid(noteId);
		System.out.println(note);
		if (note == null)
			throw new NoteExceptionHandler(messages.get("120"));
		if (note.isPin()==false)
		{
			note.setPin(true);
			noteRepository.save(note);
			elasticnoterepo.save(note);	
			return;
		} 
		else 
		{
			note.setPin(true);
			noteRepository.save(note);
			elasticnoterepo.save(note);
			return;
		}
	 } 
	
	/**
	 * purpose:set archive note
	 * @param noteId,userId
	 * 
	 **/
	@Override
	public void archivenote(String noteId, String userId)
	{
		Note note = elasticnoterepo.findByid(noteId);
		RestPrecondition.checkNotNull(note,messages.get("118"));
		if (note.isArchive()== false)
		{
			note.setArchive(true);
			noteRepository.save(note);
			elasticnoterepo.save(note);
			return;
		} 
		else
		{
			note.setArchive(true);
			noteRepository.save(note);
			elasticnoterepo.save(note);
			return;
		}
	}
	
	/**
	 * purpose:sort notes by noteId
	 * @param userId
	 * 
	 **/
	@Override
	public List<Note> sortbyNoteId(String userId) 
	{
		List<Note> list= elasticnoterepo.findNotesByUserId(userId);
		if(list==null)
		{
			throw new NoteExceptionHandler(messages.get("123"));
		}
	
		List<Note> stream = list.stream()
		.sorted((t1,t2)->t1.getId().compareTo(t2.getId())).
		collect(Collectors.toList());
		return stream;
	}
	
	/**
	 * purpose:delete notes
	 * @param noteId
	 * 
	 **/
	public void deleteNotePermanently(String noteId) 
	{
		Note note = elasticnoterepo.findByid(noteId);	
		RestPrecondition.checkNotNull(note,messages.get("118"));
		if (note.isTrash() == true)
		{
			noteRepository.deleteById(noteId);
			elasticnoterepo.deleteById(noteId);
		}
	}	
}

		



