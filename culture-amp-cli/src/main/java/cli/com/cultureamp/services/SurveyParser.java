package cli.com.cultureamp.services;

import java.io.File;

import cli.com.entity.SurveyQuestionInformation;

public interface SurveyParser {
	public SurveyQuestionInformation parseSurveyQuestions(File surveyQuestionsInputFile);
	
}
