import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceQuestion extends Question {
	private static final long serialVersionUID = 1L;

	private List<String> choices;
	private boolean isMultipleAnswerAllowed;

	// constructor
	public MultipleChoiceQuestion(String prompt, String[] choices, boolean isMultipleAnswerAllowed) {
		super(prompt);
		this.choices = new ArrayList<>(List.of(choices));
		this.isMultipleAnswerAllowed = isMultipleAnswerAllowed;
	}

	// displays question with choices
	@Override
	public void displayQuestion() {
		System.out.println(prompt);
		if (isMultipleAnswerAllowed) {
			System.out.println("You may select multiple answers (e.g., A B C).");
		} else {
			System.out.println("Select one answer.");
		}

		StringBuilder choicesLine = new StringBuilder();
		char option = 'A';

		for (String choice : choices) {
			choicesLine.append(String.format("%c) %-10s   ", option++, choice));
		}

		System.out.println(choicesLine.toString().trim());
	}

	// takes answer of multiple choice question
	@Override
	public Response takeAnswer() {
		System.out.println("Your answer(s) (leave blank and press enter to finish): ");

		List<String> answers = new ArrayList<>();
		while (true) {
			String answer = Main.scanner.nextLine().trim();

			if (answer.isEmpty()) {
				break;
			}

			if (isMultipleAnswerAllowed) {
				if (answer.length() == 1 && answer.charAt(0) >= 'A' && answer.charAt(0) < 'A' + choices.size()) {
					answers.add(answer);
				} else {
					System.out.println("Invalid answer. Please choose a valid option.");
				}
			} else {
				// If only a single answer is allowed
				if (answer.length() == 1 && answer.charAt(0) >= 'A' && answer.charAt(0) < 'A' + choices.size()) {
					return new Response(answer);
				} else {
					System.out.println("Invalid answer. Please choose a valid option.");
				}
			}
		}

		if (answers.isEmpty()) {
			System.out.println("No answers were entered.");
			return null;
		}

		return new Response(answers);
	}

	// edits question prompt or choices
	@Override
	public void editQuestion() {
		System.out.println("Current prompt: " + prompt);
		System.out.print("Enter new prompt (or press Enter to keep current): ");
		String newPrompt = Main.scanner.nextLine();
		if (!newPrompt.isEmpty()) {
			setPrompt(newPrompt);
		}

		String modifyChoices;
		while (true) {
			System.out.print("Would you like to modify the choices? (yes/no): ");
			modifyChoices = Main.scanner.nextLine().trim().toLowerCase();
			if (modifyChoices.equals("yes") || modifyChoices.equals("no")) {
				break;
			} else {
				System.out.println("Invalid input. Please enter 'yes' or 'no'.");
			}
		}

		if (modifyChoices.equalsIgnoreCase("yes")) {
			for (int i = 0; i < choices.size(); i++) {
				System.out.println("Current choice " + (i + 1) + ": " + choices.get(i));
				String newChoice;
				while (true) {
					System.out.print("Enter new choice (or press Enter to keep current): ");
					newChoice = Main.scanner.nextLine();
					if (!newChoice.isEmpty()) {
						break;
					} else if (choices.get(i).equals(newChoice)) {
						break;
					}
					System.out.println("Choice cannot be empty. Please try again.");
				}
				choices.set(i, newChoice);
			}
		}

		// edits multiple-answer permission
		boolean validInput = false;
		while (!validInput) {
			System.out.print("Allow multiple answers? (true/false): ");
			String input = Main.scanner.nextLine().trim().toLowerCase();
			if (input.equals("true") || input.equals("false")) {
				isMultipleAnswerAllowed = Boolean.parseBoolean(input);
				validInput = true;
			} else {
				System.out.println("Invalid input. Please enter 'true' or 'false'.");
			}
		}
	}

	// getters and setters
	public List<String> getChoices() {
		return choices;
	}

	public void setChoices(List<String> choices) {
		this.choices = choices;
	}

	public boolean isMultipleAnswerAllowed() {
		return isMultipleAnswerAllowed;
	}

	public void setMultipleAnswerAllowed(boolean multipleAnswerAllowed) {
		isMultipleAnswerAllowed = multipleAnswerAllowed;
	}
}