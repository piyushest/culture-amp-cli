package cli.com.cultureamp.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import cli.com.entity.SurveyAnswerInformation;
import cli.com.entity.SurveyAnswersStats;

@Component
public class SurveyAnswerParserImpl implements SurveyAnswersParser {

	/**
	 * This will parse the answers and calculate the stats for it. Considering
	 * if the timestamp is null we will consider the user did not submit the
	 * survey but if the date is not parsible we will consider it survey was
	 * submitted it is just we are not able to parse the date.
	 */
	@Override
	public SurveyAnswersStats parseSurveyAnswers(File surveyAnswersInputFile) {
		SurveyAnswersStats stats = new SurveyAnswersStats();
		int notParticipatedCount = 0;
		List<SurveyAnswerInformation> surveryAnswers = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(surveyAnswersInputFile)));
				CSVParser csvParser = new CSVParser(br, CSVFormat.EXCEL);) {
			for (CSVRecord rec : csvParser.getRecords()) {
				SurveyAnswerInformation surveyInfo = new SurveyAnswerInformation();
				int totalNumberOfFields = rec.size();
				if (totalNumberOfFields > 3) {
					if (rec.get(0) != null) {
						surveyInfo.setEmail(rec.get(0));
					}
					if (rec.get(1) != null) {
						surveyInfo.setEmpId(rec.get(1));
					}
					if (rec.get(2) != null && rec.get(2).length() > 0 && rec.get(2).trim().length() > 0) {
						parseDate(rec, surveyInfo);
					} else {
						notParticipatedCount = notParticipatedCount + 1;
						continue;
					}
				}
				parseAnswer(surveryAnswers, rec, surveyInfo, totalNumberOfFields);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		stats.setAllAnswers(surveryAnswers);
		stats.setNotParticipatedCount(notParticipatedCount);
		System.out.println("Total number of people participated " + surveryAnswers.size());
		System.out.println("Total number of people not participated " + notParticipatedCount);
		return stats;
	}

	private void parseDate(CSVRecord rec, SurveyAnswerInformation surveyInfo) throws ParseException {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+SS:SS");
			Date parsedDate = dateFormat.parse(rec.get(2));
			Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
			surveyInfo.setSubmittedAt(timestamp);
		} catch (Exception e) {
			System.out.println("Problem parsing the date");
		}
	}

	private void parseAnswer(List<SurveyAnswerInformation> surveryAnswers, CSVRecord rec,
			SurveyAnswerInformation surveyInfo, int totalNumberOfFields) {
		List<String> answers = new ArrayList<>();
		for (int index = 3; index < totalNumberOfFields; index++) {
			if (rec.get(index) == null || rec.get(index).length() == 0 || rec.get(index).trim().length() == 0) {
				answers.add("");
			} else {
				answers.add(rec.get(index));
			}
		}
		surveyInfo.setAnswers(answers);
		surveryAnswers.add(surveyInfo);
	}

}
