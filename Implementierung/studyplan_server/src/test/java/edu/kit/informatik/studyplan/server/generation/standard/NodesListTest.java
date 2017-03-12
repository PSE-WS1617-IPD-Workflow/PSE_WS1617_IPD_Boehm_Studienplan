package edu.kit.informatik.studyplan.server.generation.standard;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.kit.informatik.studyplan.server.model.moduledata.CycleType;
import edu.kit.informatik.studyplan.server.model.moduledata.Discipline;
import edu.kit.informatik.studyplan.server.model.moduledata.Field;
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
	Field field1;
	@Before
	public void setUp() {
		generator = new SimpleGenerator();

		// creating modules
		field1 = new Field();
		field1.setFieldId(1);
		
		gbi = new Module();
		gbi.setIdentifier("GBI");
		gbi.setCycleType(CycleType.BOTH);
		gbi.setCreditPoints(2.0);
		gbi.setField(field1);

		swt = new Module();
		swt.setIdentifier("SWT");
		swt.setCycleType(CycleType.SUMMER_TERM);
		swt.setCreditPoints(2.0);
		swt.setField(field1);
		
		prog = new Module();
		prog.setIdentifier("PROG");
		prog.setCycleType(CycleType.BOTH);
		prog.setCreditPoints(2.0);
		prog.setField(field1);

		tse = new Module();
		tse.setIdentifier("TSE");
		tse.setCycleType(CycleType.BOTH);
		tse.setCreditPoints(2.0);
		tse.setField(field1);
		
		pse = new Module();
		pse.setIdentifier("PSE");
		pse.setCycleType(CycleType.BOTH);
		pse.setCreditPoints(2.0);
		pse.setField(field1);

		la1 = new Module();
		la1.setIdentifier("LA1");
		la1.setCycleType(CycleType.BOTH);
		la1.setCreditPoints(2.0);
		la1.setField(field1);

		la2 = new Module();
		la2.setIdentifier("LA2");
		la2.setCycleType(CycleType.BOTH);
		la2.setCreditPoints(2.0);
		la2.setField(field1);

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

		nodes.add(pseNode, false);
		nodes.add(tseNode, false);
		nodes.add(gbiNode, false);
		nodes.add(progNode, false);
		nodes.add(swtNode, false);
		nodes.add(la1Node, false);
		nodes.add(la2Node, false);
		
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
	
	@Test
	public void testAdd() {
		assertFalse(nodes.add(tseNode, false));
	}
	@Test
	public void getCreditPoints() {
		Field field = new Field();
		field.setFieldId(0);
		field.getModules().add(la1);
		la1.setField(field);
		assertTrue(nodes.getCreditPoints(field) == 2);
	}
}
