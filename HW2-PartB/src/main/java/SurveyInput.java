import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class SurveyInput {
	private static final String DIRECTORY_PATH = "surveys/";

	// loads Survey object from a file selected by user
	public static Survey loadSurvey(String filename) {
		Survey survey = null;
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(DIRECTORY_PATH + filename))) {
			survey = (Survey) in.readObject();
			System.out.println("Survey loaded successfully.");
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Error loading survey: " + e.getMessage());
		}
		return survey;
	}

	// lists available survey files in the specified directory
	public static List<String> listSurveyFiles() {
		File directory = new File(DIRECTORY_PATH);
		File[] files = directory.listFiles((dir, name) -> name.endsWith(".ser"));
		List<String> fileNames = new ArrayList<>();
		if (files != null) {
			for (File file : files) {
				fileNames.add(file.getName());
			}
		}
		return fileNames;
	}

	// displays a menu of available survey files for the user to select
	public static String promptUserForSurveyFile() {
		List<String> surveyFiles = listSurveyFiles();
		if (surveyFiles.isEmpty()) {
			System.out.println("No survey files found.");
			return null;
		}

		System.out.println("Available Surveys:");
		for (int i = 0; i < surveyFiles.size(); i++) {
			System.out.println((i + 1) + ") " + surveyFiles.get(i));
		}
		System.out.print("Select a file to load by entering the number: ");

		int choice = Main.scanner.nextInt();
		Main.scanner.nextLine();
		if (choice > 0 && choice <= surveyFiles.size()) {
			return surveyFiles.get(choice - 1);
		} else {
			System.out.println("Invalid selection.");
			return null;
		}
	}
}