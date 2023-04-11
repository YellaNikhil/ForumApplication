package com.ForumApplication.ServiceImpl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ForumApplication.Controllers.AnswerController;
import com.ForumApplication.Models.Answer;
import com.ForumApplication.Models.Question;
import com.ForumApplication.ModelsDTO.AnswerDTO;
import com.ForumApplication.Repository.AnswerRepository;
import com.ForumApplication.Repository.QuestionRepository;
import com.ForumApplication.Service.AnswerService;

@Service

public class AnswerServiceImpl implements AnswerService {

	private static final Logger logobj = LoggerFactory.getLogger(AnswerController.class);
	@Autowired
	private QuestionServiceImp qservice;

	@Autowired
	private AnswerRepository ansrepo;

	@Autowired
	private QuestionRepository qrepo;

	@Override
	public Answer addAnswer(int questionid, AnswerDTO ansdto) {
		try {
			Answer ans = new Answer();
			Question q = qrepo.findById(questionid).get();
			ans.setQuestion(qservice.getQuestionById(questionid));
			ans.setAnswertext(ansdto.getAnswertext());
			ans.setStatus(false);
			q.setanswer(ans);
			ansrepo.save(ans);
			logobj.info("Answer added succesfully"+ans);
			return ans;
		} catch (Exception ex) {
			logobj.error("Exception handled in addAnswer service method" + ex);
			return new Answer();
		}

	}

	@Override
	public Answer editAnswerandStatus(int questionid, int answerid, AnswerDTO ansdto) {
		try {
			Answer ans = ansrepo.findById(answerid).get();
			Question q = qrepo.findById(questionid).get();
			ans.setQuestion(qservice.getQuestionById(questionid));
			if (ansdto.getAnswertext() != null)
				ans.setAnswertext(ansdto.getAnswertext());
			ans.setStatus(ansdto.isStatus());
			q.setanswer(ans);
			ansrepo.save(ans);
			logobj.info("Answer Edited succesfully"+ans);
			return ans;
		} catch (Exception ex) {
			logobj.error("Exception handled in editAnswerandStatus service method" + ex);
			return new Answer();
		}

	}

	@Override
	public boolean checkAnswerById(int answerid) {
		try {
			Optional<Answer> a = this.ansrepo.findById(answerid);
			if (a.isPresent())
				return true;
			else
				return false;
		} catch (Exception ex) {
			logobj.error("Exception handled in checkAnswerById service method" + ex);
			return false;
		}

	}

}
