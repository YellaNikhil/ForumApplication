package com.ForumApplication.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ForumApplication.Models.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

	//public List<Question> findByDomain(String domain);
	
	@Query(value="select * from question where domain =:domain", nativeQuery = true)
	public List<Question> findBydomain(@Param("domain") String domain);
	
	@Query(value="select * from question where questionid IN ( select questionid from answer)",nativeQuery=true)
	public List<Question> findAllQuestionAnswered();
	
	@Query(value="select * from question where questionid IN ( select questionid from answer where status =:stat)",nativeQuery=true)
	public List<Question> findAllQuestionByAnswerStatus(@Param("stat") boolean stat);
	
	//@Query(value="select q.domain,q.questiontext,a.answertext from question as q, answer as a "
	//		+ "where q.questionid =a.questionid and a.status=:stat",nativeQuery=true)
	//public List<Question> findAllQuestionByAnswerStatus(@Param("stat") boolean stat);
	
	//public List<Question> findByListofanswersAndStatus(boolean stat);
	
}
