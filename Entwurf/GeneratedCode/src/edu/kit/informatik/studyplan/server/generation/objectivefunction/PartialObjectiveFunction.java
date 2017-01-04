// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package edu.kit.informatik.studyplan.server.generation.objectivefunction;

import edu.kit.informatik.studyplan.server.model.userdata.Plan;

/************************************************************/
/**
 * "PartialObjectiveFunction" ist eine Teilzielfunktion. Da jede Zielfunktion
 * teil einer anderen Zielfunktion sein kann, ist jede Zielfunktion auch eine
 * Teilzielfunktion.
 */
public interface PartialObjectiveFunction {

  /**
   * Evaluate wertet einen Studienplan aus und gibt dementsprechend eine Zahl
   * zwischen 0 und 1 zurück.
   * 
   * @return Wert zwischen 0 und 1 der den Plan evaluiert, wobei ein Plan mit
   *         der Bewertung 1 ein idealer Plan ist.
   * @param plan
   *          der zu bewertende Plan
   */
  public double evaluate(Plan plan);
};
