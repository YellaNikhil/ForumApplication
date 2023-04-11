package com.ForumApplication.Controllers;

import java.util.List;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ForumApplication.Models.Question;
import com.ForumApplication.ModelsDTO.QuestionDTO;
import com.ForumApplication.Repository.QuestionRepository;
import com.ForumApplication.Service.QuestionService;
import com.ForumApplication.helper.ValidationException;

@RestController
@RequestMapping("/question")
@CrossOrigin
public class QuestionController {


	private final QuestionService qservice;
	private final QuestionRepository qrepo;
	private final Validator getvalidator;

	private static final Logger logobj = LoggerFactory.getLogger(QuestionController.class);
	@Autowired
	public QuestionController(QuestionService qservice, QuestionRepository qrepo, Validator getvalidator){
		this.qservice = qservice;
		this.qrepo = qrepo;
		this.getvalidator = getvalidator;
	}

	@PostMapping("/addquestion")
	public ResponseEntity<QuestionDTO> addQuestion(@RequestBody QuestionDTO quesdto) {
		try {
			Set constrains = getvalidator.validate(quesdto);
			if (!constrains.isEmpty())
				throw new ValidationException("Model validation failed! ,Please enter proper fields");
			QuestionDTO qu = this.qservice.addQuestion(quesdto);
			logobj.info("Question is added " + qu);
			return new ResponseEntity<QuestionDTO>(qu, HttpStatus.CREATED);
		}catch (ValidationException ex) {
			return new ResponseEntity("Model validation failed! ,Please enter proper fields ", HttpStatus.BAD_REQUEST);
		}
		catch (Exception ex) {
			logobj.error("Exception handled in addQuestion Method" + ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getAllQuestions")
	public ResponseEntity<List<Question>> getAllQuestion() {
		try {
			List<Question> qlist = this.qservice.getAllQuestion();
			return new ResponseEntity<List<Question>>(qlist, HttpStatus.FOUND);
		} catch (Exception ex) {
			logobj.error("Exception handled in getAllQuestion Method" + ex);
			return (new ResponseEntity<List<Question>>(HttpStatus.NOT_FOUND));
		}

	}

	@GetMapping("/getQuestionBydomain/{domain}")
	public ResponseEntity<List<Question>> getAllQuestionByDomain(@PathVariable("domain") String domain) {
		try {
			List<Question> qlist = this.qservice.getAllQuestionByDomain(domain.trim().toUpperCase());
			System.out.println(qlist);
			logobj.info("Questions by domain are found " + qlist.toString());
			return new ResponseEntity<List<Question>>(qlist, HttpStatus.FOUND);
		} catch (Exception ex) {
			logobj.error("Exception handled in getAllQuestionByDomain Method" + ex);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/getquestionbyid/{id}")
	public ResponseEntity<Question> getQuestionById(@PathVariable int id) {
		try {
			Question q = this.qservice.getQuestionById(id);
			return new ResponseEntity<Question>(q, HttpStatus.FOUND);
		} catch (Exception ex) {
			logobj.error("Exception handled in getQuestionById Method" + ex);
			return new ResponseEntity<Question>(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/getAllQuestionsAnswered")
	public ResponseEntity<List<Question>> getAllQuestionsAnswered() {
		try {
			List<Question> qlist = this.qservice.getAllQuestionsAnswered();
			return new ResponseEntity<List<Question>>(qlist, HttpStatus.FOUND);
		} catch (Exception ex) {

			logobj.error("Exception handled in getAllQuestionsAnswered Method" + ex);
			return (new ResponseEntity<List<Question>>(HttpStatus.NOT_FOUND));
		}

	}

	@GetMapping("/getAllQuestionByAnswerStatus/{stat}")
	public ResponseEntity<List<Question>> getAllQuestionByAnswerStatus(@PathVariable boolean stat) {
		try {
			List<Question> qlist = this.qservice.getAllQuestionByAnswerStatus(stat);
			return new ResponseEntity<List<Question>>(qlist, HttpStatus.FOUND);
		} catch (Exception ex) {

			logobj.error("Exception handled in getAllQuestionByAnswerStatus Method" + ex);
			return (new ResponseEntity<List<Question>>(HttpStatus.NOT_FOUND));
		}

	}

}
