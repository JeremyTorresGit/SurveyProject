import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SurveyOutput {

	private static final String DIRECTORY_PATH = "surveys/";
	private static final String DIRECTORY_PATH_ANS = "responses/";
	private static String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());

	// if folder doesn't exist, new folder is created
	private static void ensureSurveyFolderExists() {
		File directory = new File(DIRECTORY_PATH);
		if (!directory.exists()) {
			directory.mkdirs();
		}
	}

	private static void ensureResponseFolderExists() {
		File directory = new File(DIRECTORY_PATH_ANS);
		if (!directory.exists()) {
			directory.mkdirs();
		}
	}

	// save survey to a serializable file
	// each file is replaceable
	public static void saveSurvey(Survey survey) {
		ensureSurveyFolderExists();
		String filename = DIRECTORY_PATH + survey.getSurveyName() + "_survey" + ".ser";
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
			out.writeObject(survey);
			System.out.println("Survey saved successfully as " + filename);
		} catch (IOException e) {
			System.out.println("Error saving survey: " + e.getMessage());
		}
	}

	// save responses to a serializable file
	// each file is irreplaceable/unique
	public static void saveResponses(String surveyName, List<Response> responses) {
		ensureResponseFolderExists();
		String filename = DIRECTORY_PATH_ANS + surveyName + "_response_" + timestamp + ".ser";
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
			out.writeObject(responses);
			System.out.println("Survey saved successfully as " + filename);
		} catch (IOException e) {
			System.out.println("Error saving survey: " + e.getMessage());
		}
	}

}