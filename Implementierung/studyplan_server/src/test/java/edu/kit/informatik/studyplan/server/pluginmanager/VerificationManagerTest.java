package edu.kit.informatik.studyplan.server.pluginmanager;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.kit.informatik.studyplan.server.pluginmanager.VerificationManager;
import edu.kit.informatik.studyplan.server.verification.standard.StandardVerifier;

public class VerificationManagerTest {
	VerificationManager manager;

	@Before
	public void setUp() throws Exception {
		manager = new VerificationManager();
	}

	@Test
	public void testGetVerifier() {
		assertTrue(manager.getVerifier() instanceof StandardVerifier);
	}

}
