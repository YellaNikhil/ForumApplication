package com.ForumApplication.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
//@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)//, property="questionid"
//@ToString
public class Question  {

	
	@Id
	@GeneratedValue
	private int questionid;

	private String questiontext;
	private String domain;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="question", fetch = FetchType.LAZY) //
	@JsonIgnore
	@JsonManagedReference   //to fetch nested json from database
	private List<Answer> listofanswers = new ArrayList<Answer>();
	
	public void setQuestionid(int questionid) {
		this.questionid = questionid;
	}

	public void setQuestiontext(String questiontext) {
		this.questiontext = questiontext;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public void setanswer(Answer addanswer) {
		listofanswers.add(addanswer);
	}

	public void setListofanswers(List<Answer> listofanswers) {
		this.listofanswers = listofanswers;
	}


	
	
	
}
