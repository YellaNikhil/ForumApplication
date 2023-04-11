package com.ForumApplication.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.RequestEntity;

import com.ForumApplication.Models.Question;
import com.ForumApplication.ModelsDTO.QuestionDTO;

public interface QuestionService {

	public QuestionDTO addQuestion(QuestionDTO ques);

	public List<Question> getAllQuestion();

	public List<Question> getAllQuestionByDomain(String domain);
	
	public boolean checkQuestionById(int id);
	
	public Question getQuestionById(int id);

	public List<Question> getAllQuestionsAnswered();

	public List<Question> getAllQuestionByAnswerStatus(boolean stat);

	
}
