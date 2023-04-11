package com.ForumApplication.ModelsDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class QuestionDTO {

	@NotNull
	@Size(max = 100)
	private String questiontext;
	@NotNull
	@Size(max = 15)
	private String domain;

}