package edu.kit.informatik.studyplan.server.generation.standard;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.kit.informatik.studyplan.server.model.moduledata.CycleType;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;

public class NodesListTest {
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
	@Before
	public void setUp() throws Exception {
		// creating modules
		
		gbi= new Module();
		gbi.setIdentifier("GBI");
		gbi.setCycleType(CycleType.BOTH);
		gbi.setCreditPoints(2.0);
		
		swt= new Module();
		swt.setIdentifier("SWT");
		swt.setCycleType(CycleType.SUMMER_TERM);
		swt.setCreditPoints(2.0);

		prog= new Module();
		prog.setIdentifier("PROG");
		prog.setCycleType(CycleType.BOTH);
		prog.setCreditPoints(2.0);

		
		tse= new Module();
		tse.setIdentifier("TSE");
		tse.setCycleType(CycleType.BOTH);
		tse.setCreditPoints(2.0);
		
		pse= new Module();
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
		nodes = new NodesList(plan);
		Node pseNode = new NodeWithOutput(pse);
		Node tseNode = new NodeWithOutput(tse);
		Node gbiNode = new NodeWithOutput(gbi);
		Node progNode = new NodeWithOutput(prog);
		Node swtNode = new NodeWithOutput(swt);
		Node la1Node = new NodeWithOutput(la1);
		Node la2Node = new NodeWithOutput(la2);
		
		progNode.addChild(gbiNode);
		gbiNode.addChild(swtNode);
		swtNode.addChild(tseNode);
		swtNode.addChild(pseNode);
		pseNode.addInnerNode(tseNode);
		la1Node.addChild(la2Node);

		nodes.getAllNodes().add(pseNode);
		nodes.getAllNodes().add(tseNode);
		nodes.getAllNodes().add(gbiNode);
		nodes.getAllNodes().add(progNode);
		nodes.getAllNodes().add(swtNode);
		nodes.getAllNodes().add(la1Node);
		nodes.getAllNodes().add(la2Node);
		nodes.add(tseNode);
		
	}

	@Test
	public void testSort() {
		List<Node> result = nodes.sort();
		System.out.println("Hillow");
		for(int i = 0; i < result.size(); i++) 
		System.out.println(result.get(i).getModule().getIdentifier());
		assertFalse(result.containsAll(nodes.getAllNodes()));
		for(int i = 0; i < result.size(); i++) {
			for(int j = i + 1; j < result.size(); j++) {
				assertFalse(result.get(i).getParents().contains(result.get(j)));
			}
		}
	}

}
