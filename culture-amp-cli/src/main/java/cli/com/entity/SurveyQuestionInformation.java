package cli.com.entity;

import java.util.List;
import java.util.Map;

public class SurveyQuestionInformation {

	private List<QuestionHeaders> headers;
	public List<QuestionHeaders> getHeaders() {
		return headers;
	}

	public void setHeaders(List<QuestionHeaders> headers) {
		this.headers = headers;
	}

	private Map<Integer, QuestionDetails> questions;


	public Map<Integer, QuestionDetails> getQuestions() {
		return questions;
	}

	public void setQuestions(Map<Integer, QuestionDetails> questions) {
		this.questions = questions;
	}

}