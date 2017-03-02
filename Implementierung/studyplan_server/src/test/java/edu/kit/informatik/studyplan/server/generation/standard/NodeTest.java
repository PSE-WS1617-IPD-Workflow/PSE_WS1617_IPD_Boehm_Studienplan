package edu.kit.informatik.studyplan.server.generation.standard;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import edu.kit.informatik.studyplan.server.model.moduledata.CycleType;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;
import edu.kit.informatik.studyplan.server.model.userdata.User;

public class NodeTest {
	SimpleGenerator generator;
	NodesList nodes;
	Plan plan;
	Module swt;
	Module tse;
	Module pse;
	Node swtNode;
	Node pseNode;
	Node tseNode;

	@Before
	public void setUp() throws Exception {
		generator = new SimpleGenerator();

		// creating modules
		swt = new Module();
		swt.setIdentifier("SWT");
		swt.setCycleType(CycleType.SUMMER_TERM);
		swt.setCreditPoints(2.0);

		tse = new Module();
		tse.setIdentifier("TSE");
		tse.setCycleType(CycleType.BOTH);
		tse.setCreditPoints(2.0);

		pse = new Module();
		pse.setIdentifier("PSE");
		pse.setCycleType(CycleType.BOTH);
		pse.setCreditPoints(2.0);

		// adding nodes to list
		plan = mock(Plan.class);
		User user = new User();
		when(plan.getUser()).thenReturn(user);
		pseNode = new NodeWithOutput(pse, plan, generator);
		tseNode = new NodeWithOutput(tse, plan, generator);
		swtNode = new NodeWithOutput(swt, plan, generator);
		swtNode.addChild(tseNode);
		swtNode.addChild(pseNode);
		pseNode.addInnerNode(tseNode);
	}

	@Test
	public void testRemoveInnerNode() {
		tseNode.removeInnerNode(pseNode);
		assertFalse(tseNode.hasOuterNode());

	}

}
