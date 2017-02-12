package edu.kit.informatik.studyplan.server.generation.standard;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import edu.kit.informatik.studyplan.server.model.moduledata.Module;

public class NodeWithOutputTest {
	Node node;
	Module module;
	@Before
	public void setUp() throws Exception {
		module = mock(Module.class);
		when(mo)
		node = new NodeWithOutput(module);
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
