package cli.com.entity;

import java.util.HashMap;
import java.util.Map;

public enum QuestionType {

	RATING_QUESTION("ratingquestion"), SINGLE_SELECT("singleselect");
	private String type;
	static Map<String, QuestionType> stringToEnum = new HashMap<>();
	static {
		for (QuestionType e : QuestionType.values()) {
			stringToEnum.put(e.type, e);
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private QuestionType(String type) {
		this.type = type;
	}

	public static QuestionType getEnumByString(String code) {
		for (QuestionType e : QuestionType.values()) {
			if (code.equals(e.type))
				return e;
		}
		return null;
	}
}
