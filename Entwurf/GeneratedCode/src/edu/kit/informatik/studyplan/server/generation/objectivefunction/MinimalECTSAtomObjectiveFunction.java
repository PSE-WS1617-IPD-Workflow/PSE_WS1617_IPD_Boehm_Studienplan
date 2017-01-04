// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package edu.kit.informatik.studyplan.server.generation.objectivefunction;

import edu.kit.informatik.studyplan.server.model.userdata.Plan;

/************************************************************/
/**
 * Je geringer die Gesamtanzahl der ECTS in einem Studienplan, desto besser ist
 * die Bewertung von MinimalECTSAtomObjectiveFunction. Zu beachten ist jedoch,
 * dass sobald ein gewisser Schwellwert unterschritten wird alle Studienpläne
 * die Bestnote erhalten.
 */
public class MinimalECTSAtomObjectiveFunction extends AtomObjectiveFunction {
  private final int threshold;

  /**
   * Setzt den Schwellwert auf 0, der Studienplan mit den
   * wenigsten ECTS wird somit immer am besten bewertet.
   */
  public MinimalECTSAtomObjectiveFunction() {
    threshold = 0;
  }

  /**
   * @param minimalECTS
   *          ist die Menge an ECTS ab der diese Funktion den Bestwert
   *          zurückgibt - niedrieger bringt also in diesem Fall dann nichts.
   */
  public MinimalECTSAtomObjectiveFunction(final int threshold) {
    this.threshold = threshold;
  }

  /*
   * {@inheritDoc}
   */
  @Override
  public double evaluate(final Plan plan) {
    // TODO Auto-generated method stub
    return 0;
  }
};
