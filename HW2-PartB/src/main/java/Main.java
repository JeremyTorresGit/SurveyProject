import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static final Scanner scanner = new Scanner(System.in);
	private static Survey currentSurvey = null;

	public static void main(String[] args) {
		runSurveyApplication();
	}

	// main menu
	private static void runSurveyApplication() {

		while (true) {
			System.out.println("\nMain Menu:");
			System.out.println("1) Create a new Survey");
			System.out.println("2) Display an existing Survey");
			System.out.println("3) Load an existing Survey");
			System.out.println("4) Save the current Survey");
			System.out.println("5) Take the current Survey");
			System.out.println("6) Modify the current Survey");
			System.out.println("7) Quit");

			int choice;
			while (true) {
				System.out.print("Select an option: ");
				if (scanner.hasNextInt()) {
					choice = scanner.nextInt();
					scanner.nextLine();
					if (choice >= 1 && choice <= 7) {
						break;
					} else {
						System.out.println("Invalid choice. Please try again.");
					}
				} else {
					System.out.println("Invalid input. Please enter a number between 1 and 7.");
					scanner.nextLine();
				}
			}

			switch (choice) {
			case 1:
				createSurvey();
				break;
			case 2:
				displaySurvey();
				break;
			case 3:
				loadSurvey();
				break;
			case 4:
				saveSurvey();
				break;
			case 5:
				takeSurvey();
				break;
			case 6:
				modifySurvey();
				break;
			case 7:
				System.out.println("Exiting program. Goodbye!");
				System.exit(0);
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	// initialize survey
	private static void createSurvey() {
		System.out.println("\nCreating a New Survey:");
		System.out.print("Enter the survey name: ");
		String surveyName = scanner.nextLine();
		currentSurvey = new Survey(surveyName);
		addQuestionsToSurvey();
	}

	// add question menu
	private static void addQuestionsToSurvey() {
		while (true) {
			System.out.println("\nAdd Questions Menu:");
			System.out.println("1) Add a new true/false question");
			System.out.println("2) Add a new multiple choice question");
			System.out.println("3) Add a new short answer question");
			System.out.println("4) Add a new essay question");
			System.out.println("5) Add a new date question");
			System.out.println("6) Add a new matching question");
			System.out.println("7) Return to previous menu");

			int choice;
			while (true) {
				System.out.print("Select an option: ");
				if (scanner.hasNextInt()) {
					choice = scanner.nextInt();
					scanner.nextLine();
					if (choice >= 1 && choice <= 7) {
						break;
					} else {
						System.out.println("Invalid choice. Please try again.");
					}
				} else {
					System.out.println("Invalid input. Please enter a number between 1 and 7.");
					scanner.nextLine();
				}
			}

			switch (choice) {
			case 1:
				currentSurvey.addQuestion(new TrueFalseQuestion(getPrompt("True/False")));
				break;
			case 2:
				currentSurvey.addQuestion(createMultipleChoiceQuestion());
				break;
			case 3:
				int responseLimit = 50;
				Boolean multipleAnswers = isMultipleAnswersAllowed();
				currentSurvey.addQuestion(
						new ShortAnswerQuestion(getPrompt("Short Answer"), responseLimit, multipleAnswers));
				break;
			case 4:
				int responseLimit2 = 500;
				Boolean multipleAnswers2 = isMultipleAnswersAllowed();
				currentSurvey.addQuestion(new EssayQuestion(getPrompt("Essay"), responseLimit2, multipleAnswers2));
				break;
			case 5:
				Boolean multipleAnswers3 = isMultipleAnswersAllowed();
				currentSurvey.addQuestion(new DateQuestion(getPrompt("Date"), "yyyy-MM-dd", multipleAnswers3));
				break;
			case 6:
				currentSurvey.addQuestion(createMatchingQuestion());
				break;
			case 7:
				runSurveyApplication();
				return;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	public static boolean isMultipleAnswersAllowed() {
		Scanner scanner = new Scanner(System.in);
		String input;

		while (true) {
			System.out.print("Allow multiple answers (true/false): ");
			input = scanner.nextLine().trim().toLowerCase();

			if ("true".equals(input)) {
				return true;
			} else if ("false".equals(input)) {
				return false;
			} else {
				System.out.println("Invalid input. Please enter 'true' or 'false'.");
			}
		}
	}

	// gets prompt input for a question
	private static String getPrompt(String questionType) {
		System.out.print("Enter the prompt for your " + questionType + " question: ");
		return scanner.nextLine();
	}

	// initialize multiple choice creation
	private static MultipleChoiceQuestion createMultipleChoiceQuestion() {
		String prompt = getPrompt("Multiple-Choice");
		int numChoices;
		while (true) {
			System.out.print("Enter the number of choices: ");
			if (scanner.hasNextInt()) {
				numChoices = scanner.nextInt();
				scanner.nextLine();
				if (numChoices > 0) {
					break;
				} else {
					System.out.println("Number of choices must be greater than 0.");
				}
			} else {
				System.out.println("Invalid input. Please enter a positive number.");
				scanner.nextLine();
			}
		}

		List<String> choices = new ArrayList<>();
		for (int i = 0; i < numChoices; i++) {
			System.out.print("Enter choice #" + (i + 1) + ": ");
			choices.add(scanner.nextLine());
		}

		boolean isMultipleAnswerAllowed;
		while (true) {
			System.out.print("Allow multiple answers (true/false): ");
			if (scanner.hasNextBoolean()) {
				isMultipleAnswerAllowed = scanner.nextBoolean();
				scanner.nextLine();
				break;
			} else {
				System.out.println("Invalid input. Please enter 'true' or 'false'.");
				scanner.nextLine();
			}
		}

		return new MultipleChoiceQuestion(prompt, choices.toArray(new String[0]), isMultipleAnswerAllowed);
	}

	// initializes matching questions
	private static MatchingQuestion createMatchingQuestion() {
		String prompt = getPrompt("Matching");

		int numItems;
		while (true) {
			System.out.print("Enter the number of items for matching: ");
			if (scanner.hasNextInt()) {
				numItems = scanner.nextInt();
				scanner.nextLine();
				if (numItems > 0) {
					break;
				} else {
					System.out.println("Number of items must be greater than 0.");
				}
			} else {
				System.out.println("Invalid input. Please enter a positive number.");
				scanner.nextLine();
			}
		}

		List<String> leftChoices = new ArrayList<>();
		List<String> rightChoices = new ArrayList<>();

		System.out.println("Enter left choices:");
		for (int i = 0; i < numItems; i++) {
			System.out.print("Left choice #" + (i + 1) + ": ");
			leftChoices.add(scanner.nextLine());
		}

		System.out.println("Enter right choices:");
		for (int i = 0; i < numItems; i++) {
			System.out.print("Right choice #" + (i + 1) + ": ");
			rightChoices.add(scanner.nextLine());
		}

		return new MatchingQuestion(prompt, leftChoices, rightChoices);
	}

	// initializes survey display
	private static void displaySurvey() {
		if (currentSurvey == null) {
			System.out.println("You must have a survey loaded in order to display it.");
		} else {
			currentSurvey.displaySurvey();
// debugging:
//			List<Response> responses = currentSurvey.getResponses();
//			if (responses != null && !responses.isEmpty()) {
//				System.out.println("\nResponses:");
//				for (Response response : responses) {
//					response.displayResponse();
//				}
//			} else {
//				System.out.println("No responses to display.");
//			}
		}
	}

	// initializes loading the survey
	private static void loadSurvey() {
		String filename = SurveyInput.promptUserForSurveyFile();
		if (filename != null) {
			currentSurvey = SurveyInput.loadSurvey(filename);
		}
	}

	// initializes saving the survey
	private static void saveSurvey() {
		if (currentSurvey == null) {
			System.out.println("You must have a survey loaded in order to save it.");
		} else {
			SurveyOutput.saveSurvey(currentSurvey);
			SurveyOutput.saveResponses(currentSurvey.getSurveyName(), currentSurvey.getResponses());
		}
	}

	// initializes taking the survey
	private static void takeSurvey() {
		if (currentSurvey == null) {
			System.out.println("You must have a survey loaded in order to take it.");
		} else {
			currentSurvey.takeSurvey();
		}
	}

	// initializes modifying the survey
	private static void modifySurvey() {
		if (currentSurvey == null) {
			System.out.println("You must have a survey loaded in order to modify it.");
			return;
		}

		if (currentSurvey.getQuestions().isEmpty()) {
			System.out.println("The current survey has no questions to modify.");
			return;
		}

		System.out.println("Select a question to modify:");
		List<Question> questions = currentSurvey.getQuestions();
		for (int i = 0; i < questions.size(); i++) {
			System.out.print((i + 1) + ". ");
			questions.get(i).displayQuestion();
			System.out.println();
		}

		int questionIndex;
		while (true) {
			System.out.print("Enter the number of the question to modify (or 0 to cancel): ");
			if (scanner.hasNextInt()) {
				questionIndex = scanner.nextInt() - 1;
				scanner.nextLine();
				if (questionIndex >= 0 && questionIndex < questions.size()) {
					break;
				} else if (questionIndex == -1) {
					System.out.println("Modification cancelled.");
					return;
				} else {
					System.out.println("Invalid question number. Please try again.");
				}
			} else {
				System.out.println("Invalid input. Please enter a valid number.");
				scanner.nextLine();
			}
		}

		Question selectedQuestion = questions.get(questionIndex);
		System.out.println("Modifying question: ");
		selectedQuestion.displayQuestion();
		selectedQuestion.editQuestion();

		System.out.println("Question modified successfully.");
	}
}