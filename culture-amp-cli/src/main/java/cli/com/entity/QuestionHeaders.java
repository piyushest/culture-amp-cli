package cli.com.entity;

import java.util.HashMap;
import java.util.Map;

public enum QuestionHeaders {

	THEME("theme"), TYPE("type"), TEXT("text");
	private String questionHeader;
    static Map<String,QuestionHeaders> stringToEnum = new HashMap<>();
	static{
		for(QuestionHeaders e : QuestionHeaders.values()){
        	stringToEnum.put(e.questionHeader, e);
        }
	}
	public String getQuestionHeader() {
		return questionHeader;
	}

	public void setQuestionHeader(String questionHeader) {
		this.questionHeader = questionHeader;
	}

	private QuestionHeaders(String questionHeader) {
		this.questionHeader = questionHeader;
	}
	
	public static QuestionHeaders getEnumByString(String code){
        for(QuestionHeaders e : QuestionHeaders.values()){
        	stringToEnum.put(e.questionHeader, e);
            if(code.equals(e.questionHeader)) return e;
        }
        return null;
    }
	
}
