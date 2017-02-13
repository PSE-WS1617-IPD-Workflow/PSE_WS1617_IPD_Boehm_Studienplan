// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package edu.kit.informatik.studyplan.server.generation.objectivefunction;

import edu.kit.informatik.studyplan.server.model.userdata.Plan;

/************************************************************/
/**
 * AtomObjectiveFunction ist eine Teilzielfunktion, die nur eine Eigenschaft
 * berücksichtigt.
 */
public abstract class AtomObjectiveFunction implements PartialObjectiveFunction {
	
	private String descriptor;
	
	/*
	 * {@inheritDoc}
	 */
	public abstract double evaluate(Plan plan);

	/**
	 * @return the descriptor
	 */
	public String getDescriptor() {
		return descriptor;
	}

	/**
	 * @param descriptor the descriptor to set
	 */
	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}
	
};
