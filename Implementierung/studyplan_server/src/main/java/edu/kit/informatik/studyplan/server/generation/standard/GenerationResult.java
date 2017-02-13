package edu.kit.informatik.studyplan.server.generation.standard;

import edu.kit.informatik.studyplan.server.model.userdata.Plan;

public class GenerationResult {

	 /**
	     * The first element of this <code>Pair</code>
	     */
	    private Plan plan;

	    /**
	     * The second element of this <code>Pair</code>
	     */
	    private NodesList nodesList;

	    /**
	     * Constructs a new <code>Pair</code> with the given values.
	     * 
	     * @param first  the first element
	     * @param second the second element
	     */
	    public GenerationResult(Plan plan, NodesList nodesList) {

	        this.setPlan(plan);
	        this.setNodesList(nodesList);
	    }

		/**
		 * @return the plan
		 */
		public Plan getPlan() {
			return plan;
		}

		/**
		 * @param plan the plan to set
		 */
		public void setPlan(Plan plan) {
			this.plan = plan;
		}

		/**
		 * @return the nodesList
		 */
		public NodesList getNodesList() {
			return nodesList;
		}

		/**
		 * @param nodesList the nodesList to set
		 */
		public void setNodesList(NodesList nodesList) {
			this.nodesList = nodesList;
		}
}
