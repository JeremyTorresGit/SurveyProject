import java.io.Serializable;

public abstract class Question implements Serializable {
	private static final long serialVersionUID = 1L;

	protected String prompt;

	// constructor
	public Question(String prompt) {
		this.prompt = prompt;
	}

	// getters and setters
	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	// abstract methods for subclass implementation
	public abstract void displayQuestion();

	public abstract Response takeAnswer();

	public abstract void editQuestion();
}