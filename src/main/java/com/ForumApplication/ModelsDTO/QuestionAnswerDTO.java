package com.ForumApplication.ModelsDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAnswerDTO {

	@NotNull
	@Size(max = 15)
	private String domain;
	@NotNull
	@Size(max = 100)
	private String questiontext;
	@NotNull
	@Size(max = 500)
	private String answertext;
}
