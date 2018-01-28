package cli.com.cultureamp.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import cli.com.entity.QuestionDetails;
import cli.com.entity.QuestionHeaders;
import cli.com.entity.QuestionType;
import cli.com.entity.SurveyQuestionInformation;

@Component
public class SurveyQuestionParserImpl implements SurveyParser {

	/**
	 * This will parse the question and it will calculate the headers.
	 * 
	 */
	@Override
	public SurveyQuestionInformation parseSurveyQuestions(File surveyQuestionsInputFile) {
		SurveyQuestionInformation information = new SurveyQuestionInformation();
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(surveyQuestionsInputFile)));
				CSVParser csvParser = new CSVParser(br, CSVFormat.EXCEL);) {
			List<QuestionHeaders> headers = new ArrayList<>();
			Map<Integer, QuestionDetails> questionDetails = new HashMap<>();
			boolean isFirst = true;
			int questionNumber = 1;
			for (CSVRecord rec : csvParser.getRecords()) {
				int totalNumberOfFields = rec.size();
				// header values
				if (isFirst) {
					parseQuestions(headers, rec, totalNumberOfFields);
					isFirst = false;
				} else {
					QuestionDetails singleQuestion = new QuestionDetails();
					for (int index = 0; index < totalNumberOfFields; index++) {
						addInfoInQuestionDetail(rec.get(index), singleQuestion, headers, index);
					}
					questionDetails.put(questionNumber, singleQuestion);
					questionNumber = questionNumber + 1;
				}

			}
			information.setHeaders(headers);
			information.setQuestions(questionDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return information;
	}

	private void parseQuestions(List<QuestionHeaders> headers, CSVRecord rec, int totalNumberOfFields) {
		for (int index = 0; index < totalNumberOfFields; index++) {
			QuestionHeaders presentHeader = QuestionHeaders.getEnumByString(rec.get(index).trim().toLowerCase());
			if (presentHeader != null) {
				headers.add(index, presentHeader);
			}

		}
	}

	private void addInfoInQuestionDetail(String infoFromQuestion, QuestionDetails singleQuestion,
			List<QuestionHeaders> headers, int index) {
		QuestionHeaders presentHeader = headers.get(index);
		if (presentHeader.equals(QuestionHeaders.THEME)) {
			singleQuestion.setTheme(infoFromQuestion);
		}
		if (presentHeader.equals(QuestionHeaders.TEXT)) {
			singleQuestion.setText(infoFromQuestion);
		}
		if (presentHeader.equals(QuestionHeaders.TYPE)) {
			singleQuestion.setType(QuestionType.getEnumByString(infoFromQuestion.trim().toLowerCase()));
		}
	}

}
