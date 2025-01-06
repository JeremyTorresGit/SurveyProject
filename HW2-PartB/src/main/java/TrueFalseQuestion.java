import java.io.Serializable;

public class TrueFalseQuestion extends Question implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean correctAnswer;

	// constructor
	public TrueFalseQuestion(String prompt) {
		super(prompt);
	}

	// display true/false question
	@Override
	public void displayQuestion() {
		System.out.println(prompt + " \nT/F");
	}

	// takes answer from user for the true/false question
	@Override
	public Response takeAnswer() {
		System.out.print("Your answer (T/F): ");
		String answer = Main.scanner.nextLine().trim().toUpperCase();

		// Validate input and prompt again if necessary
		while (!answer.equals("T") && !answer.equals("F")) {
			System.out.print("Invalid input. Please enter 'T' for True or 'F' for False: ");
			answer = Main.scanner.nextLine().trim().toUpperCase();
		}

		Response response;
		if (answer.equals("T")) {
			response = new Response("True");
		} else {
			response = new Response("False");
		}
		return response;
	}

	// edits question prompt and correct answer
	@Override
	public void editQuestion() {
		System.out.println("Current prompt: " + prompt);
		System.out.print("Enter new prompt (or press Enter to keep current): ");
		String newPrompt = Main.scanner.nextLine();
		if (!newPrompt.isEmpty()) {
			setPrompt(newPrompt);
		}

		boolean validAnswer = false;
		while (!validAnswer) {
			System.out.print("Enter the correct answer (T/F): ");
			String correctAnswerInput = Main.scanner.nextLine().trim().toUpperCase();

			if (correctAnswerInput.equals("T")) {
				correctAnswer = true;
				validAnswer = true;
			} else if (correctAnswerInput.equals("F")) {
				correctAnswer = false;
				validAnswer = true;
			} else {
				System.out.println("Invalid input. Please enter 'T' for True or 'F' for False.");
			}
		}
	}

	// getters and setters
	public boolean getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(boolean correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
}