import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Survey implements Serializable {
	private static final long serialVersionUID = 1L;

	private String surveyName;
	private List<Question> questions;
	private List<Response> responses;

	// constructor
	public Survey(String surveyName) {
		this.surveyName = surveyName;
		this.questions = new ArrayList<>();
		this.responses = new ArrayList<>();
	}

	// getters
	public String getSurveyName() {
		return surveyName;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public List<Response> getResponses() {
		return responses;
	}

	// adds a question
	public void addQuestion(Question question) {
		questions.add(question);
		System.out.println("Question added: " + question.getPrompt());
	}

	// adds a response
	public void addResponse(Response response) {
		responses.add(response);
	}

	// prints the survey
	public void displaySurvey() {
		if (questions.isEmpty()) {
			System.out.println("This survey has no questions.");
			return;
		}

		System.out.println("Survey: " + surveyName);
		for (int i = 0; i < questions.size(); i++) {
			System.out.print((i + 1) + ". ");
			questions.get(i).displayQuestion();
		}
	}

	// allows user to take the survey
	public void takeSurvey() {
		responses.clear();
		System.out.println("Starting survey: " + surveyName);
		int i = 0;

		for (Question question : questions) {
			questions.get(i).displayQuestion();
			i += 1;
			Response response = question.takeAnswer();
			responses.add(response);
		}
		System.out.println("Survey completed. Responses saved.");
	}

	// modifies the question
	public void modifyQuestion(int index) {
		if (index < 0 || index >= questions.size()) {
			System.out.println("Invalid question number.");
			return;
		}

		Question question = questions.get(index);
		System.out.println("Modifying question: " + question.getPrompt());
		question.editQuestion();
	}
}