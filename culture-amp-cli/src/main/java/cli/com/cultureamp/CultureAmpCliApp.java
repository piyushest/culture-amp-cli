package cli.com.cultureamp;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import cli.com.cultureamp.services.SurveyAnswersParser;
import cli.com.cultureamp.services.SurveyParser;
import cli.com.entity.QuestionDetails;
import cli.com.entity.QuestionType;
import cli.com.entity.StatsDetails;
import cli.com.entity.SurveyAnswerInformation;
import cli.com.entity.SurveyAnswersStats;
import cli.com.entity.SurveyQuestionInformation;

@SpringBootApplication
@ComponentScan(basePackages = { "cli.com.cultureamp.services" })
public class CultureAmpCliApp implements CommandLineRunner {

	@Autowired
	private SurveyParser surveyParser;

	@Autowired
	private SurveyAnswersParser surveyAnswersParser;

	public static void main(String[] args) {

		SpringApplication.run(CultureAmpCliApp.class, args);
	}

	/**
	 * This is the main entry point of the application It will first parse the
	 * questions and then it will parse the answers I have tried to have the
	 * stats in the DS as it will help me in calculating the extended version
	 */
	@Override
	public void run(String... args) throws Exception {
		if (args.length > 0) {
			File inputFile = new File(args[0]);
			// File inputFile = new File(
			// "/Users/piyush.mittal/Documents/practiceCultureAmp/culture-amp-cli/src/main/resources/survey-2.csv");
			SurveyQuestionInformation questionsInfo = surveyParser.parseSurveyQuestions(inputFile);
			File inputFileForAnswers = new File(args[1]);
			// File inputFileForAnswers = new File(
			// "/Users/piyush.mittal/Documents/practiceCultureAmp/culture-amp-cli/src/main/resources/survey-2-responses.csv");
			SurveyAnswersStats answerStats = surveyAnswersParser.parseSurveyAnswers(inputFileForAnswers);
			float percentageOfPeopleParticipated = (answerStats.getAllAnswers().size())
					/ (answerStats.getAllAnswers().size() + answerStats.getNotParticipatedCount());
			System.out.println("Total Percentage of people participated " + percentageOfPeopleParticipated * 100);
			if (answerStats.getAllAnswers() == null || answerStats.getAllAnswers().size() == 0) {
				System.out.println("No Answers found");
				return;
			}
			printTheRatingOfEachQuestion(questionsInfo, answerStats.getAllAnswers());
		}
	}

	private void printTheRatingOfEachQuestion(SurveyQuestionInformation questionsInfo,
			List<SurveyAnswerInformation> allAnswers) {
		Map<Integer, QuestionDetails> questions = questionsInfo.getQuestions();
		Map<Integer, StatsDetails> questionToStats = new HashMap<>();
		int questionNumber = 1;
		for (Map.Entry<Integer, QuestionDetails> details : questions.entrySet()) {
			QuestionDetails question = details.getValue();
			if (question.getType().equals(QuestionType.RATING_QUESTION)) {
				questionToStats.put(questionNumber, new StatsDetails());
			}
			questionNumber = questionNumber + 1;
		}
		calculateStatsOfAllAnswers(allAnswers, questionToStats);
	}

	private void calculateStatsOfAllAnswers(List<SurveyAnswerInformation> allAnswers,
			Map<Integer, StatsDetails> questionToStats) {
		for (SurveyAnswerInformation ansInfo : allAnswers) {
			List<String> answers = ansInfo.getAnswers();
			for (Map.Entry<Integer, StatsDetails> singleStat : questionToStats.entrySet()) {
				int answerIndex = singleStat.getKey();
				try {
					if (answers.get(answerIndex - 1) != null && answers.get(answerIndex - 1) != "") {
						double grossRating = Double.parseDouble(answers.get(answerIndex - 1))
								+ singleStat.getValue().getTotalGrossRating();
						singleStat.getValue().setTotalGrossRating(grossRating);
						int numberOfResp = singleStat.getValue().getTotalNumberOfResponses() + 1;
						singleStat.getValue().setTotalNumberOfResponses(numberOfResp);
					}
				} catch (Exception e) {
					System.out.println("Problem Converting it into double");
				}
			}
		}
		for (Map.Entry<Integer, StatsDetails> singleStat : questionToStats.entrySet()) {
			double finalRating = singleStat.getValue().getTotalGrossRating()
					/ singleStat.getValue().getTotalNumberOfResponses();
			System.out.print("For question Number " + singleStat.getKey());
			System.out.println(" The average rating is " + finalRating);
		}
	}

}
