import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchingQuestion extends Question implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<String> leftChoices;
	private List<String> rightChoices;

	// constructor
	public MatchingQuestion(String prompt, List<String> leftChoices, List<String> rightChoices) {
		super(prompt);
		this.leftChoices = new ArrayList<>(leftChoices);
		this.rightChoices = new ArrayList<>(rightChoices);
	}

	// display matching question with both lists of choices
	@Override
	public void displayQuestion() {
		System.out.println(prompt);
		System.out.println("Match the following items:");

		// Determine the max length of left and right choice labels for proper alignment
		int leftMaxLength = 0;
		for (String choice : leftChoices) {
			if (choice.length() > leftMaxLength) {
				leftMaxLength = choice.length();
			}
		}

		int rightMaxLength = 0;
		for (String choice : rightChoices) {
			if (choice.length() > rightMaxLength) {
				rightMaxLength = choice.length();
			}
		}

		// displays the left and right choices
		char leftLabel = 'A';
		int rightLabel = 1;
		int rows = Math.max(leftChoices.size(), rightChoices.size());
		for (int i = 0; i < rows; i++) {
			String leftItem = "";
			String rightItem = "";

			if (i < leftChoices.size()) {
				leftItem = leftChoices.get(i);
			}

			if (i < rightChoices.size()) {
				rightItem = rightChoices.get(i);
			}

			System.out.printf("%-1s) %-" + (leftMaxLength + 2) + "s %-1d) %s%n", String.valueOf(leftLabel++), leftItem,
					rightLabel++, rightItem);
		}
	}

	// takes answer for the matching question
	@Override
	public Response takeAnswer() {
		System.out.println("Please provide your matching answers (e.g., Match for A: 1)");

		Map<String, String> userMatches = new HashMap<>();
		for (int i = 0; i < leftChoices.size(); i++) {
			char leftLabel = (char) ('A' + i);
			System.out.print("Match for " + leftLabel + ": ");
			String match = Main.scanner.nextLine().trim();

			// Validate and add match
			if (isValidRightChoice(match)) {
				userMatches.put(String.valueOf(leftLabel), match);
			} else {
				System.out.println("Invalid choice. Skipping this match.");
			}
		}

		return new Response(userMatches);
	}

	// validates the entered match
	private boolean isValidRightChoice(String match) {
		try {
			int choice = Integer.parseInt(match);
			return choice > 0 && choice <= rightChoices.size();
		} catch (NumberFormatException e) {
			return false;
		}
	}

	// edits the question prompt, left choices, or right choices
	@Override
	public void editQuestion() {
		System.out.println("Current prompt: " + prompt);
		System.out.print("Enter new prompt (or press Enter to keep current): ");
		String newPrompt = Main.scanner.nextLine();
		if (!newPrompt.isEmpty()) {
			setPrompt(newPrompt);
		}

		System.out.print("Would you like to edit the left choices? (yes/no): ");
		if (Main.scanner.nextLine().equalsIgnoreCase("yes")) {
			editChoices(leftChoices, "left");
		}

		System.out.print("Would you like to edit the right choices? (yes/no): ");
		if (Main.scanner.nextLine().equalsIgnoreCase("yes")) {
			editChoices(rightChoices, "right");
		}
	}

	// helper method to edit a list of choices
	private void editChoices(List<String> choices, String side) {
		for (int i = 0; i < choices.size(); i++) {
			System.out.println("Current " + side + " choice " + (i + 1) + ": " + choices.get(i));
			System.out.print("Enter new choice (or press Enter to keep current): ");
			String newChoice = Main.scanner.nextLine();
			if (!newChoice.isEmpty()) {
				choices.set(i, newChoice);
			}
		}
	}

	// getters and setters
	public List<String> getLeftChoices() {
		return leftChoices;
	}

	public void setLeftChoices(List<String> leftChoices) {
		this.leftChoices = leftChoices;
	}

	public List<String> getRightChoices() {
		return rightChoices;
	}

	public void setRightChoices(List<String> rightChoices) {
		this.rightChoices = rightChoices;
	}
}