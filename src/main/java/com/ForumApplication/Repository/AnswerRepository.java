package com.ForumApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ForumApplication.Models.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {

}
