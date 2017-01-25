package edu.kit.informatik.studyplan.server.model.moduledata.dao;

import java.util.List;

import org.junit.Test;

import edu.kit.informatik.studyplan.server.model.moduledata.Category;
import edu.kit.informatik.studyplan.server.model.moduledata.Field;

public class HibernateModuleDaoTest {

	@Test
	public void test() {
		HibernateModuleDao dao = new HibernateModuleDao();
		Field field = new Field();
		field.setFieldId(1);
		List<Category> subjects = dao.getSubjects(field);
		for (Category category : subjects) {
			System.out.println(category.getName());
		}
	};

}
