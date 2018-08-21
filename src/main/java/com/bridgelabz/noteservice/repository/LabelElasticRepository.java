package com.bridgelabz.noteservice.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import com.bridgelabz.noteservice.model.Label;

/************************************************************************************
 * @author Ankita Kalgutkar
 *
 * purpose:Elastic repository
 ************************************************************************************/
public interface LabelElasticRepository extends ElasticsearchRepository<Label, String>
{
	public Optional<Label> findLabelByLabelname(String labelname);

	public List<Label> findByUserId(String userId);
}
