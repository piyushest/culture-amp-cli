package cli.com.cultureamp.services;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import cli.com.entity.SurveyAnswersStats;

public class SurveyAnswersParserImplUTest {

	@InjectMocks
	private SurveyAnswerParserImpl surveyAnswersParser;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void parseSurveyAnswersTest() {
		File input = new File(
				"/Users/piyush.mittal/Documents/practiceCultureAmp/culture-amp-cli/src/test/resources/survey-1-responses.csv");
		SurveyAnswersStats stats = surveyAnswersParser.parseSurveyAnswers(input);
		assertTrue(stats.getNotParticipatedCount() == 1);
		assertTrue(6 == stats.getNotParticipatedCount() + stats.getAllAnswers().size());
	}
}
