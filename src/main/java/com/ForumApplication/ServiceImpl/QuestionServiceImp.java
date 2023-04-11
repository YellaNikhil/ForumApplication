package com.ForumApplication.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;

import com.ForumApplication.Models.Answer;
import com.ForumApplication.Models.Question;
import com.ForumApplication.ModelsDTO.QuestionDTO;
import com.ForumApplication.Repository.QuestionRepository;
import com.ForumApplication.Service.QuestionService;

@Service
public class QuestionServiceImp implements QuestionService{

	@Autowired
	private QuestionRepository qrepo;
	
	@Override
	public QuestionDTO addQuestion(QuestionDTO quesdto) {
		Question ques = new Question();
		ques.setQuestiontext(quesdto.getQuestiontext());
		ques.setDomain(quesdto.getDomain());
		this.qrepo.save(ques);
		return quesdto;
	}

	@Override
	public List<Question> getAllQuestion() {
		
		return this.qrepo.findAll();
	}

	@Override
	public List<Question> getAllQuestionByDomain(String domain) {
		System.out.println(domain);
		List<Question> li=this.qrepo.findBydomain(domain);
		
		System.out.println(li.toString());
		return li;
	}

	@Override
	public boolean checkQuestionById(int id) {
		Optional<Question> q = this.qrepo.findById(id);
		if(q.isPresent())
		return true;
		else
		return false;
	}

	@Override
	public Question getQuestionById(int id) {
		Question q = this.qrepo.findById(id).get();
		return q;
	}

	@Override
	public List<Question> getAllQuestionsAnswered() {
		
		return this.qrepo.findAllQuestionAnswered();
	}

	@Override
	public List<Question> getAllQuestionByAnswerStatus(boolean stat) {
		
		List<Question> li = this.qrepo.findAllQuestionByAnswerStatus(stat);
		List<Question> filteredlist = new ArrayList<>();
		List<Answer> lians;
		for (Question l : li)
		{
			 lians=null;
			 lians=l.getListofanswers()
					 .stream()
					 .filter(p->p.isStatus()==stat)
					 .collect(Collectors.toList());
			  l.setListofanswers(lians);
			  filteredlist.add(l);
		}
		 return filteredlist;
	}

	
}
