package cli.com.cultureamp.services;

import java.io.File;

import cli.com.entity.SurveyAnswersStats;

public interface SurveyAnswersParser {
	public SurveyAnswersStats parseSurveyAnswers(File surveyQuestionsInputFile);
}
