package com.bridgelabz.noteservice.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.bridgelabz.noteservice.model.Label;

/************************************************************************************************
 * @author Ankita Kalgutkar
 *
 * 
 * PURPOSE:LabelRepository extends all the methods of Mongo repository
 *************************************************************************************************/
@Repository
public interface LabelRepository extends MongoRepository<Label, String>
{
	public Optional<Label> findLabelByLabelname(String labelname);

	public List<Label> findByUserId(String userId);
}
