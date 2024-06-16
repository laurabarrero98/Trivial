# KeepTrivial

KeepTrivial is a Java-based trivia game designed as part of a learning project.

## Game Description

KeepTrivial allows multiple teams to compete by answering questions from different categories. Each team progresses by filling a "quesito" for each category they answer correctly. The game ends when a team has completed all quesitos across all categories.

## Project Structure

The project is organized into the following main components:

- **`MainTrivial.java`**: Contains the core game logic, including team initialization, random selection of categories and questions, turn handling, and game end conditions.
- **`Question.java`**: Defines the structure of a question, including the prompt, answer options, and correct answer.
- **`Team.java`**: Represents a participating team in the game, maintaining its name and completed quesito statuses.
- **`Quesito.java`**: Manages each team's quesito completion status per category.

## Question File Structure

Questions are stored in text files within the `questions` directory. Each file corresponds to a category and contains questions formatted as follows:
 ```
Question 1
Option 1
Option 2
Option 3
Option 4
Number of the correct answer (1-4)
 ```
- To customize the game with different categories and questions, simply place text files, formatted as described above, in the 'questions' directory.
- You can add as many questions and categories as you like by creating additional text files in the `questions` directory.

## Running the Game
- Ensure you have Java JDK installed.
- Navigate to the root directory and compile the project if necessary.
