package cli.com.entity;

import java.util.List;

public class SurveyAnswersStats {

	private int notParticipatedCount;
	List<SurveyAnswerInformation> allAnswers;

	public int getNotParticipatedCount() {
		return notParticipatedCount;
	}

	public void setNotParticipatedCount(int notParticipatedCount) {
		this.notParticipatedCount = notParticipatedCount;
	}

	public List<SurveyAnswerInformation> getAllAnswers() {
		return allAnswers;
	}

	public void setAllAnswers(List<SurveyAnswerInformation> allAnswers) {
		this.allAnswers = allAnswers;
	}
}
