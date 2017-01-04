// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package edu.kit.informatik.studyplan.server.generation.objectivefunction;

import java.util.Collection;

import edu.kit.informatik.studyplan.server.model.userdata.Plan;

/************************************************************/
/**
 * Eine Zielfunktion dient zur Sammlung von Teilzielfunktionen, die dann alle ausgewertet werden.
 */
public abstract class ObjectiveFunction implements PartialObjectiveFunction {
  private Collection<PartialObjectiveFunction> subFunctions;

  public ObjectiveFunction() {
    // TODO Auto-generated constructor stub
  }

  /*
   * Wertet alle beeinhalteten Funktionen aus und rechnet diese in irgendeiner
   * Weise zusammen. 
   * Sonstige funktionalit�t wie in {@link PartialObjectiveFunction#evaluate}
   */
  @Override
  public abstract double evaluate(final Plan plan);

  /**
   * Getter für subFunctions.
   * 
   * @return die Collection subFunctions, bestehend aus
   *         PartialObjectiveFunctions
   */
  public Collection<PartialObjectiveFunction> getSubFunctions() {
    return subFunctions;
  }
  
  /**
   * Fügt eine Teilzielfunktion zu dieser Zielfunktion hinzu.
   * @param objective 
   *            die hinzuzufügende Teilzielfunktion
   */
  public void add(final PartialObjectiveFunction objective){
    // TODO Auto-generated method stub
  }
  /**
   * Entfernt eine TeilZielfunktion von dieser Zielfunktion
   * @param objective 
   *            die zu entfernende Zielfunktion
   * @return die entfernte Zielfunktion
   */
  public PartialObjectiveFunction remove(final PartialObjectiveFunction objective) {
    // TODO Auto-generated method stub
    return null;
  }
};
