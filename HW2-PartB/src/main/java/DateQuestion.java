import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class DateQuestion extends Question implements Serializable {
	private static final long serialVersionUID = 1L;
	private final Boolean isMultipleAnswersAllowed;

	private String dateFormat;
	private transient DateTimeFormatter formatter;

	// constructor
	public DateQuestion(String prompt, String dateFormat, Boolean isMultipleAnswersAllowed) {
		super(prompt);
		this.dateFormat = dateFormat;
		this.formatter = DateTimeFormatter.ofPattern(dateFormat);
		this.isMultipleAnswersAllowed = isMultipleAnswersAllowed;
	}

	// display date question prompt with required format
	@Override
	public void displayQuestion() {
		System.out.println(prompt);
		System.out.println("Please enter the date in the following format: " + dateFormat);
	}

	// takes answer from user for the date question
	@Override
	public Response takeAnswer() {
		List<String> answers = new ArrayList<>();

		System.out.println("Your answer(s) (leave blank and press enter to finish): ");

		while (true) {
			System.out.print("Your answer (" + dateFormat + "): ");
			String answer = Main.scanner.nextLine().trim();

			if (answer.isEmpty()) {
				break;
			}

			while (!isValidDate(answer)) {
				System.out.print("Invalid date format. Please enter the date in the format " + dateFormat + ": ");
				answer = Main.scanner.nextLine().trim();
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

	// edits the question prompt or date format
	@Override
	public void editQuestion() {
		System.out.println("Current prompt: " + prompt);
		System.out.print("Enter new prompt (or press Enter to keep current): ");
		String newPrompt = Main.scanner.nextLine();
		if (!newPrompt.isEmpty()) {
			setPrompt(newPrompt);
		}

		System.out.print("Enter new date format (or press Enter to keep current): ");
		String newFormat = Main.scanner.nextLine();
		if (!newFormat.isEmpty()) {
			this.setDateFormat(newFormat);
		}
	}

	// validates if the given date string matches the expected format
	private boolean isValidDate(String date) {
		try {
			LocalDate.parse(date, formatter);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}

	// getters and setters
	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
		this.formatter = DateTimeFormatter.ofPattern(dateFormat);
	}

	// deserialization logic to initialize formatter
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		formatter = DateTimeFormatter.ofPattern(dateFormat);
	}
}