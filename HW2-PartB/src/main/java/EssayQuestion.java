import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EssayQuestion extends Question implements Serializable {
	private static final long serialVersionUID = 1L;

	private int responseLimit;
	private boolean isMultipleAnswersAllowed;

	// constructor
	public EssayQuestion(String prompt) {
		super(prompt);
	}

	// constructor with a response limit
	public EssayQuestion(String prompt, int responseLimit, boolean isMultipleAnswersAllowed) {
		super(prompt);
		this.responseLimit = responseLimit;
		this.isMultipleAnswersAllowed = isMultipleAnswersAllowed;
	}

	// displays essay question prompt
	@Override
	public void displayQuestion() {
		System.out.println(prompt);
		if (responseLimit > 0) {
			System.out.println("Note: Response should not exceed " + responseLimit + " characters.");
		}
	}

	// takes user's answer for the essay question
	@Override
	public Response takeAnswer() {
		List<String> answers = new ArrayList<>();

		System.out.println("Your answer(s) (leave blank and press enter to finish): ");

		while (true) {
			String answer = Main.scanner.nextLine().trim();

			if (answer.isEmpty()) {
				break;
			}

			if (responseLimit > 0 && answer.length() > responseLimit) {
				System.out.println("Your response exceeds the limit. Only the first " + responseLimit
						+ " characters will be saved.");
				answer = answer.substring(0, responseLimit);
			}

			if (isMultipleAnswersAllowed) {
				answers.add(answer);
			} else {
				return new Response(answer);
			}
		}

		if (answers.isEmpty()) {
			System.out.println("No answers were entered.");
			return null;
		}

		return new Response(answers);
	}

	// edits the question prompt or response limit
	@Override
	public void editQuestion() {
		System.out.println("Current prompt: " + prompt);
		System.out.print("Enter new prompt (or press Enter to keep current): ");
		String newPrompt = Main.scanner.nextLine();
		if (!newPrompt.isEmpty()) {
			setPrompt(newPrompt);
		}

		System.out.print("Enter new response limit (or press Enter to keep current): ");
		String newLimit = Main.scanner.nextLine();
		if (!newLimit.isEmpty()) {
			try {
				responseLimit = Integer.parseInt(newLimit);
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Response limit remains unchanged.");
			}
		}
	}

	// getters and setters
	public int getResponseLimit() {
		return responseLimit;
	}

	public void setResponseLimit(int responseLimit) {
		this.responseLimit = responseLimit;
	}
}