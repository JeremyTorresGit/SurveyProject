import java.io.Serializable;

public class Response implements Serializable {
	private static final long serialVersionUID = 1L;

	private Object userAnswer;

	// constructor
	public Response(Object userAnswer) {
		this.userAnswer = userAnswer;
	}

	// getters and setters
	public Object getUserAnswer() {
		return userAnswer;
	}

	public void setUserAnswer(Object userAnswer) {
		this.userAnswer = userAnswer;
	}
}