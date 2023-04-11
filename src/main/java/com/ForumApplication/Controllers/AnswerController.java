package com.ForumApplication.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ForumApplication.Models.Answer;
import com.ForumApplication.ModelsDTO.AnswerDTO;
import com.ForumApplication.Repository.AnswerRepository;
import com.ForumApplication.Service.AnswerService;
import com.ForumApplication.Service.QuestionService;
import com.ForumApplication.helper.ValidationException;

import java.util.Set;

import javax.validation.Validator;

@RestController
@RequestMapping("/answer")
public class AnswerController {

	private static final Logger logobj = LoggerFactory.getLogger(AnswerController.class);
	private final AnswerService ansservice;
	private final QuestionService qservice;
	private final Validator getvalidator;

	public AnswerController(AnswerService ansservice, QuestionService questionService, Validator getvalidator){
		this.ansservice = ansservice;
		this.qservice = questionService;
		this.getvalidator = getvalidator;
	}

	@PostMapping("/addanswer/{questionid}")
	public ResponseEntity<Answer> addAnswer(@PathVariable int questionid, @RequestBody AnswerDTO ansdto) {
		try {
			Set constrains = getvalidator.validate(ansdto);
			if (!constrains.isEmpty())
				throw new ValidationException("Model validation failed! ,Please enter proper fields");
			if (qservice.checkQuestionById(questionid)) {
				Answer ans = this.ansservice.addAnswer(questionid, ansdto);
				logobj.info("Succesfully created");
				return new ResponseEntity<Answer>(ans, HttpStatus.CREATED);
			} else {
				logobj.error("There is no such question id " + questionid + " to map answer");
				return new ResponseEntity<Answer>(HttpStatus.NOT_FOUND);
			}
		} catch (ValidationException ex) {
			return new ResponseEntity("Model validation failed! ,Please enter proper fields ", HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			logobj.error("Exception handled in Addanswer Method" + ex);
			return new ResponseEntity<Answer>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping("/editAnswerandStatus/{questionid}/{answerid}")
	public ResponseEntity<Answer> editAnswerandStatus(@PathVariable int questionid, @PathVariable int answerid,
			@RequestBody AnswerDTO ansdto) {
		try {
			Set constrains = getvalidator.validate(ansdto);
			if (!constrains.isEmpty())
				throw new ValidationException("Model validation failed! ,Please enter proper fields");
			if (qservice.checkQuestionById(questionid) && ansservice.checkAnswerById(answerid)) {
				Answer ans = this.ansservice.editAnswerandStatus(questionid, answerid, ansdto);
				System.out.println(ans);
				return new ResponseEntity<Answer>(ans, HttpStatus.CREATED);
			} else {
				logobj.error("There is no such question id /answer id to update");
				return new ResponseEntity<Answer>(HttpStatus.NOT_FOUND);
			}
		} catch (ValidationException ex) {
			return new ResponseEntity("Model validation failed! ,Please enter proper fields ", HttpStatus.BAD_REQUEST);
		}

		catch (Exception ex) {
			logobj.error("Exception handled in editAnswerandStatus" + ex);
			return new ResponseEntity<Answer>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
