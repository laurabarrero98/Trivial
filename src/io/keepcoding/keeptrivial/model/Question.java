package io.keepcoding.keeptrivial.model;

public class Question {
    private String question;
    private String[] answers;
    private int correctAnswer;

    public Question(String question, String[] answers, int correctAnswer) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String[] getAnswers() {
		return answers;
	}

	public void setAnswers(String[] answers) {
		this.answers = answers;
	}

	public int getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	
	public String getAnswer(int position) {
		return answers[position];
	}
	
	public String toString() {
		String str = "Pregunta: " + question + "\n";
		for (int i = 0; i < answers.length; i++) {
	        str += (i + 1) + ". " + answers[i] + "\n";
	    }
		return str;
	}
}
