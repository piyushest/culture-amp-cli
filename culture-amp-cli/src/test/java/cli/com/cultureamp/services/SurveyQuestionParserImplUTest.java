package cli.com.cultureamp.services;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import cli.com.entity.SurveyQuestionInformation;

public class SurveyQuestionParserImplUTest {
	@InjectMocks
	private SurveyQuestionParserImpl surveyQuestionParser;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void parseSurveyQuestionsTest() {
		File input = new File(
				"/Users/piyush.mittal/Documents/practiceCultureAmp/culture-amp-cli/src/test/resources/survey-1.csv");
		SurveyQuestionInformation questions = surveyQuestionParser.parseSurveyQuestions(input);
		assertTrue(questions.getHeaders().size() == 3);
		assertTrue(questions.getQuestions().size() == 5);
	}
}
