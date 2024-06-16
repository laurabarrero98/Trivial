package io.keepcoding.keeptrivial.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Quesito {
	private Map<String, Boolean> gaps;

	public Quesito(List<String> categories) {
		gaps = new HashMap<>();
		for (String category : categories) {
			gaps.put(category, false);
		}
	}

	public void fill(String category) {
		gaps.put(category, true);
	}

	public boolean isFull() {
		for (boolean state : gaps.values()) {
			if (!state)
				return false;
		}
		return true;
	}

	public boolean isGapFull(String category) {
		return gaps.get(category);
	}
	
	public String toString() {
		String str = "";
		int totalFull = 0;

		for (String category : gaps.keySet()) {
			boolean hasWonCategory = isGapFull(category);
			str += "- " + category + ": " + (hasWonCategory ? "1" : "0") + "\n";
			if (hasWonCategory) {
				totalFull++;
			}
		}
		str += "Total: " + totalFull + "\n";
		return str;
	}
}
