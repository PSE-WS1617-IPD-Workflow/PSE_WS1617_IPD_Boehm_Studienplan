package edu.kit.informatik.studyplan.server.generation.objectivefunction;

import edu.kit.informatik.studyplan.server.model.userdata.Plan;

/**
 * Multipliziert die Ergebnise der Auswertung Teilzielfunktionen aufeinander
 * auf.
 *
 */
public class MultiplicationObjectiveFunction extends ObjectiveFunction {

	/*
	 * (non-Javadoc) Alle Zielfunktionen werden aufmultipliziert, eine
	 * Zielfunktion vom Wert 0 sorgt also automatisch dafür, dass die gesamte
	 * Zielfunktion den Wert 0 annimmt.
	 */
	@Override
	public double evaluate(final Plan plan) {
		double[] values =  this.getSubFunctions().stream().mapToDouble(function -> function.evaluate(plan)).toArray();
		double product = 1;
		for(int i = 0; i<values.length; i++){
			product *= values[i];
		}
		return product;
	}

}
