package edu.kit.informatik.studyplan.server.model.userdata.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.kit.informatik.studyplan.server.model.userdata.Plan;
import edu.kit.informatik.studyplan.server.model.userdata.User;
import edu.kit.informatik.studyplan.server.model.userdata.VerificationState;

public class HibernatePlanDaoTest {

	@Test
	public void test() {
		User user = new User();
		user.setUserName("admin");
		user = UserDaoFactory.getUserDao().findUser(user);
		Plan plan = new Plan();
		plan.setUser(user);
		plan.setVerificationState(VerificationState.NOT_VERIFIED);
		plan.setName("My Studyplan");
		String id = PlanDaoFactory.getPlanDao().updatePlan(plan);
		Plan plan2 = PlanDaoFactory.getPlanDao().getPlanById(id);
		assertEquals("My Studyplan", plan2.getName());
		plan.setName("Toller Plan");
		PlanDaoFactory.getPlanDao().updatePlan(plan);
		plan2 = PlanDaoFactory.getPlanDao().getPlanById(id);
		assertEquals("Toller Plan", plan2.getName());
	}

}
