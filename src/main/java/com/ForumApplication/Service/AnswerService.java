package com.ForumApplication.Service;

import com.ForumApplication.Models.Answer;
import com.ForumApplication.ModelsDTO.AnswerDTO;

public interface AnswerService {

	public Answer addAnswer(int questionid, AnswerDTO ansdto);

	public Answer editAnswerandStatus(int questionid,int answerid, AnswerDTO ansdto);

	public boolean checkAnswerById(int answerid);
   
}
