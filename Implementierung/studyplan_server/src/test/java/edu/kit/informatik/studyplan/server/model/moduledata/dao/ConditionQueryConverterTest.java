package edu.kit.informatik.studyplan.server.model.moduledata.dao;

import org.junit.Test;

import edu.kit.informatik.studyplan.server.model.moduledata.Discipline;

public class ConditionQueryConverterTest {

	@Test
	public void categoryFilterTest() {
		Discipline discipline = ModuleDaoFactory.getModuleDao().getDisciplineById(1);
		//CategoryFilter filter = new CategoryFilter(0, discipline);
		//ConditionQueryConverter converter = new ConditionQueryConverter(filter.getConditions());
		//System.out.println(converter.getQueryString());
		
	}

}
