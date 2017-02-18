package edu.kit.informatik.studyplan.server.generation.standard;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.kit.informatik.studyplan.server.model.moduledata.CycleType;
import edu.kit.informatik.studyplan.server.model.moduledata.Discipline;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;
import edu.kit.informatik.studyplan.server.model.userdata.User;
/**
 * This Use Case tests the methods of the NodesList class.
 * @author Nada_Chatti
 *
 */
public class NodesListTest {
	SimpleGenerator generator;
	NodesList nodes;
	Plan plan;
	Module gbi;
	Module swt;
	Module prog;
	Module tse;
	Module pse;
	Module la1;
	Module la2;
	Module ph;
	Node swtNode;
	Node pseNode;
	Node tseNode;
	@Before
	public void setUp() {
		generator = new SimpleGenerator();

		// creating modules
		gbi = new Module();
		gbi.setIdentifier("GBI");
		gbi.setCycleType(CycleType.BOTH);
		gbi.setCreditPoints(2.0);
		
		swt = new Module();
		swt.setIdentifier("SWT");
		swt.setCycleType(CycleType.SUMMER_TERM);
		swt.setCreditPoints(2.0);

		prog = new Module();
		prog.setIdentifier("PROG");
		prog.setCycleType(CycleType.BOTH);
		prog.setCreditPoints(2.0);

		
		tse = new Module();
		tse.setIdentifier("TSE");
		tse.setCycleType(CycleType.BOTH);
		tse.setCreditPoints(2.0);
		
		pse = new Module();
		pse.setIdentifier("PSE");
		pse.setCycleType(CycleType.BOTH);
		pse.setCreditPoints(2.0);

		
		la1 = new Module();
		la1.setIdentifier("LA1");
		la1.setCycleType(CycleType.BOTH);
		la1.setCreditPoints(2.0);
		
		la2 = new Module();
		la2.setIdentifier("LA2");
		la2.setCycleType(CycleType.BOTH);
		la2.setCreditPoints(2.0);

		ph = new Module();
		ph.setIdentifier("PH");
		ph.setCycleType(CycleType.BOTH);
		ph.setCreditPoints(2.0);

		//adding nodes to list
		plan = mock(Plan.class);
		User user = new User();
		when(plan.getUser()).thenReturn(user);
		nodes = new NodesList(plan, generator);
		pseNode = new NodeWithOutput(pse, plan, generator);
		tseNode = new NodeWithOutput(tse, plan, generator);
		Node gbiNode = new NodeWithOutput(gbi, plan, generator);
		Node progNode = new NodeWithOutput(prog, plan, generator);
		Node la1Node = new NodeWithOutput(la1, plan, generator);
		Node la2Node = new NodeWithOutput(la2, plan, generator);
		swtNode = new NodeWithOutput(swt, plan, generator);
		
		progNode.addChild(gbiNode);
		gbiNode.addChild(swtNode);
		swtNode.addChild(tseNode);
		swtNode.addChild(pseNode);
		pseNode.addInnerNode(tseNode);
		la1Node.addChild(la2Node);

		nodes.add(pseNode);
		nodes.add(tseNode);
		nodes.add(gbiNode);
		nodes.add(progNode);
		nodes.add(swtNode);
		nodes.add(la1Node);
		nodes.add(la2Node);
		nodes.add(tseNode);
		
	}

	@Test
	public void testSort() {
		List<Node> result = nodes.sort();
		assertFalse(result.containsAll(nodes));
		for (int i = 0; i < result.size(); i++) {
			for (int j = i + 1; j < result.size(); j++) {
				assertFalse(result.get(i).getParents().contains(result.get(j)));
			}
		}
	}
	
	@Test
	public void removeTest() {
		nodes.remove(swtNode);
		assertFalse(nodes.contains(swtNode));
		assertFalse(nodes.contains(pseNode));
		assertFalse(nodes.contains(tseNode));
	}

}
