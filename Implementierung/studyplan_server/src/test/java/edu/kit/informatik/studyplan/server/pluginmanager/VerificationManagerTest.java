package edu.kit.informatik.studyplan.server.pluginmanager;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.kit.informatik.studyplan.server.verification.standard.StandardVerifier;
/**
 * This use case tests the methods of the VerificationManager Class
 * @author Nada_Chatti
 *
 */
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
