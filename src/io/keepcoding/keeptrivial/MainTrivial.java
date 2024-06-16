package io.keepcoding.keeptrivial;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import io.keepcoding.keeptrivial.model.Quesito;
import io.keepcoding.keeptrivial.model.Question;
import io.keepcoding.keeptrivial.model.Team;

public class MainTrivial {
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		// initialize questions
		Map<String, List<Question>> questions = getQuestions();
		// initialize categories
		List<String> categories = new ArrayList<>(questions.keySet());
		// initialize teams
		List<Team> teams = initializeTeams(categories);

		// start game
		boolean gameEnded = false;
		int currentTeamIndex = 0;

		do {
			Team currentPlayer = teams.get(currentTeamIndex);

			playTurn(currentPlayer, categories, questions);

			// Check if there is a winner
			if (currentPlayer.hasWon()) {
				gameEnded = true;
			} else {
				// End of turn and move on to the next team
				currentTeamIndex = (currentTeamIndex + 1) % teams.size();
			}

			// show current ranking
			showScoreboard(teams);

		} while (!gameEnded);
		System.out.println();
		title("El equipo: " + teams.get(currentTeamIndex).getName() + " ha ganado");

		scanner.close();

	}

	private static Map<String, List<Question>> getQuestions() {
		/*
		 * A map to store the list of questions, where the key is the name of the topic
		 * (category) and the value is the list of questions associated with that
		 * category
		 */
		Map<String, List<Question>> questionsList = new HashMap<>();

		File folder = new File("questions");
		if (!folder.exists()) {
			title("Error al cargar el fichero");
		} else {
			File[] filesList = folder.listFiles();

			for (File file : filesList) {
				if (file.isFile() && file.getName().endsWith(".txt")) {
					String topicName = file.getName().substring(0, file.getName().length() - 4);
					// We add a new category (topicNmae) if it does not exist
					questionsList.putIfAbsent(topicName, new ArrayList<>());

					// Read the question
					try (BufferedReader br = new BufferedReader(new FileReader(file))) {
						String line;
						List<String> block = new ArrayList<>();

						while ((line = br.readLine()) != null) {
							block.add(line);

							if (block.size() == 6) { // número de lineas que componen una pregunta
								String question = block.get(0);
								String[] answers = new String[] { block.get(1), block.get(2), block.get(3),
										block.get(4) };
								int rightOption = Integer.parseInt(block.get(5));
								// we add the question
								questionsList.get(topicName).add(new Question(question, answers, rightOption));
								block.clear();
							}
						}

					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}
		}

		return questionsList;
	}

	private static List<Team> initializeTeams(List<String> categories) {
		List<Team> teams = new ArrayList<>();
		Set<String> teamNames = new HashSet<>();

		String input;
		do {
			System.out.print("Introduce el nombre del equipo (o 'q' para terminar): ");
			input = scanner.nextLine().trim();

			if (!input.equalsIgnoreCase("q")) {
				if (teamNames.contains(input)) {
					System.out.println("Ya existe un equipo con ese nombre. Introduce otro nombre.");
				} else {
					teams.add(new Team(input, new Quesito(categories)));
					teamNames.add(input);
				}
			}
		} while (!input.equalsIgnoreCase("q"));

		return teams;
	}

	private static void playTurn(Team currentPlayer, List<String> categories, Map<String, List<Question>> questions) {
		// Show turn
		title("Turno del equipo: " + currentPlayer.getName());

		// Select category and show question
		String selectedCategory = getRandomCategory(categories, currentPlayer);
		title("Categoria: " + selectedCategory);

		Question selectedQuestion = getRandomQuestion(questions.get(selectedCategory));
		System.out.println(selectedQuestion);

		// Ask the answer and validate the format
		int answer = getValidAnswer();

		// Show if they got the question right
		if (checkAnswer(selectedQuestion, answer)) {
			title("Respuesta correcta!");
			currentPlayer.rightQuestion(selectedCategory);
		} else {
			title("Respuesta incorrecta :(");
			System.out.println("La respuesta correcta es: " + selectedQuestion.getCorrectAnswer() + ". "
					+ selectedQuestion.getAnswers()[selectedQuestion.getCorrectAnswer() - 1]);
			System.out.println();
		}

	}

	private static int getValidAnswer() {
		int answer = -1;

		do {
			System.out.print("Introduce el número de tu respuesta (1-4): ");
			String answerInput = scanner.nextLine();
			System.out.println();

			try {
				answer = Integer.parseInt(answerInput);
				if (answer < 1 || answer > 4) {
					title("Formato incorrecto. Por favor, introduce un número entre 1 y 4.");
					answer = -1;
				}
			} catch (NumberFormatException e) {
				title("Formato incorrecto. Por favor, introduce un número entre 1 y 4.");
			}
		} while (answer == -1);

		return answer;

	}

	private static String getRandomCategory(List<String> categories, Team team) {
		// View the categories not completed by the team
		List<String> availableCategories = new ArrayList<>();
		for (String category : categories) {
			if (!team.getQuesito().isGapFull(category)) {
				availableCategories.add(category);
			}
		}
		// Randomly choose from the available categories
		int randomIndex = getRandomInt(availableCategories.size());
		return availableCategories.get(randomIndex);
	}

	private static Question getRandomQuestion(List<Question> questions) {
		// Randomly choose from all the questions in the category
		return questions.get(getRandomInt(questions.size()));
	}

	private static boolean checkAnswer(Question question, int answer) {
		return answer == question.getCorrectAnswer();
	}

	private static void showScoreboard(List<Team> teams) {
		title("Clasificación:");

		for (Team team : teams)
			System.out.println(team);
	}

	public static void title(String text) {
		int length = text.length();
		printHashtagLine(length + 4); // Bordes

		System.out.println("# " + text + " #");

		printHashtagLine(length + 4);
		System.out.println();
	}

	public static void printHashtagLine(int length) {
		for (int i = 0; i < length; i++) {
			System.out.print("#");
		}
		System.out.println();
	}

	private static int getRandomInt(int max) {
		return new Random().nextInt(max);
	}

}
