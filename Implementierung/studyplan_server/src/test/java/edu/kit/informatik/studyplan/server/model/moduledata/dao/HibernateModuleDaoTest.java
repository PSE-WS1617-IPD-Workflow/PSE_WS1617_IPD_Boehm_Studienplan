package edu.kit.informatik.studyplan.server.model.moduledata.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.kit.informatik.studyplan.server.filter.CategoryFilter;
import edu.kit.informatik.studyplan.server.filter.CompulsoryFilter;
import edu.kit.informatik.studyplan.server.filter.CreditPointsFilter;
import edu.kit.informatik.studyplan.server.filter.CycleTypeFilter;
import edu.kit.informatik.studyplan.server.filter.Filter;
import edu.kit.informatik.studyplan.server.filter.ModuleTypeFilter;
import edu.kit.informatik.studyplan.server.filter.MultiFilter;
import edu.kit.informatik.studyplan.server.filter.NameFilter;
import edu.kit.informatik.studyplan.server.model.moduledata.Discipline;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;

public class HibernateModuleDaoTest {

	@Test
	public void test() {
		HibernateModuleDao dao = new HibernateModuleDao();
		Discipline discipline = dao.getDisciplineById(1);
		CategoryFilter filter = new CategoryFilter(0, discipline);
		List<Module> list = dao.getModulesByFilter(filter, discipline);
		list.stream().map(module -> module.getName()).forEach(System.out::println);
	}
	
	@Test
	public void test2() {
		HibernateModuleDao dao = new HibernateModuleDao();
		Discipline discipline = dao.getDisciplineById(1);
		Filter filter = new CreditPointsFilter(7, 9, 0, 478);
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(filter);
		filter = new CompulsoryFilter(1);
		filters.add(filter);
		filters.add(new NameFilter("nik"));
		Filter multiFilter = new MultiFilter(filters);
		List<Module> list = dao.getModulesByFilter(multiFilter, discipline);
		list.stream().map(module -> module.getName()).forEach(System.out::println);

	}
	
	@Test
	public void test3() {
		HibernateModuleDao dao = new HibernateModuleDao();
		Discipline discipline = dao.getDisciplineById(1);
		Filter filter = new ModuleTypeFilter(0);
		List<Module> list = dao.getModulesByFilter(filter, discipline);
		list.stream().map(module -> module.getName()).forEach(System.out::println);
	}
	
	@Test
	public void test4() {
		HibernateModuleDao dao = new HibernateModuleDao();
		Discipline discipline = dao.getDisciplineById(1);
		Filter filter = new CycleTypeFilter(0);
		List<Module> list = dao.getModulesByFilter(filter, discipline);
		list.stream().map(module -> module.getName()).forEach(System.out::println);
	}
	
	public static void main (String[] args) {
		
		new HibernateModuleDaoTest().test4();
	}

}
