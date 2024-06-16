package io.keepcoding.keeptrivial.model;

public class Team {
	private String name;
	private Quesito quesito;
	
	public Team(String name, Quesito quesito) {
		this.name = name;
		this.quesito = quesito;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Quesito getQuesito() {
		return quesito;
	}

	public void setQuesito(Quesito quesito) {
		this.quesito = quesito;
	}
	
	public void rightQuestion(String category) {
		quesito.fill(category);
	}
	
	public boolean hasWon() {
		return quesito.isFull();
	}
	
	public String toString() {
		return "Equipo: " + name + "\n" + quesito.toString();
	}
}
