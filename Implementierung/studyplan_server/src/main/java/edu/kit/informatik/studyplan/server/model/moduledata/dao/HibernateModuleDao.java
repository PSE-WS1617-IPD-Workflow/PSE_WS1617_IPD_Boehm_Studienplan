package edu.kit.informatik.studyplan.server.model.moduledata.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import edu.kit.informatik.studyplan.server.filter.Filter;
import edu.kit.informatik.studyplan.server.model.HibernateUtil;
import edu.kit.informatik.studyplan.server.model.moduledata.Category;
import edu.kit.informatik.studyplan.server.model.moduledata.Discipline;
import edu.kit.informatik.studyplan.server.model.moduledata.Field;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.moduledata.ModuleType;

/**
 * Ein konkretes ModulDao, welches die Datenbankverbindung über Hibernate
 * herstellt. Es kann nur auf Module, Kategorien und Vertiefungsfächer des im
 * Konstruktur angebenen Studiengangs zugreifen
 */
class HibernateModuleDao implements ModuleDao {
	
	private Session session;
	
	public HibernateModuleDao() {
		session = HibernateUtil.getModuleDataSessionFactory().openSession();
	}
	
	@Override
	public Module getModuleById(String id) {
		session.beginTransaction();
		Module module = session.bySimpleNaturalId(Module.class).load(id);
		session.getTransaction().commit();
		return module;
	}

	@Override
	public List<Module> getModulesByFilter(Filter filter, Discipline discipline) {
		ConditionQueryConverter converter = new ConditionQueryConverter(filter.getConditions());
		session.beginTransaction();
		String queryString = "from Module m where " + converter.getQueryString() + " and m.discipline = :discipline";
		Query<Module> query = session.createQuery(queryString, Module.class);
		converter.setParameters(query);
		query.setParameter("discipline", discipline);
		List<Module> resultList = query.getResultList();
		session.getTransaction().commit();
		return resultList;
	}

	@Override
	public Module getRandomModuleByFilter(Filter filter, Discipline discipline) {
		List<Module> modulesByFilter = getModulesByFilter(filter, discipline);
		int randomIndex = (int) (Math.random() * modulesByFilter.size());
		return modulesByFilter.get(randomIndex);
	}

	@Override
	public List<Discipline> getDisciplines() {
		session.beginTransaction();
		List<Discipline> resultList = session.createQuery("from Discipline", Discipline.class).getResultList();
		session.getTransaction().commit();
		return resultList;
	}

	@Override
	public List<Category> getCategories(Discipline discipline) {
		String queryString = "select distinct category "
				+ "from Module as module  "
				+ "join module.categories as category "
				+ "where module.discipline = :discipline";
		session.beginTransaction();
		Query<Category> query = session.createQuery(queryString, Category.class);
		query.setParameter("discipline", discipline);
		List<Category> resultList = query.getResultList();
		session.getTransaction().commit();
		return resultList;
	}

	@Override
	public Discipline getDisciplineById(int disciplineId) {
		session.beginTransaction();
		Discipline discipline = session.byId(Discipline.class).load(disciplineId);
		session.getTransaction().commit();
		return discipline;
	}

	@Override
	public List<Field> getFields(Discipline discipline) {
		session.beginTransaction();
		String queryString = "from Field field where field.discipline = :discipline";
		Query<Field> query = session.createQuery(queryString, Field.class);
		query.setParameter("discipline", discipline);
		List<Field> resultList = query.getResultList();
		session.getTransaction().commit();
		return resultList;
	}

	@Override
	public List<ModuleType> getModuleTypes() {
		session.beginTransaction();
		List<ModuleType> resultList = session.createQuery("from ModuleType", ModuleType.class).getResultList();
		session.getTransaction().commit();
		return resultList;
	}
	
	@Override
	public List<Category> getSubjects(Field field) {
		session.beginTransaction();
		Query<Category> query = session.createQuery("select distinct category "
				+ "from Field as field  "
				+ "join field.modules as module "
				+ "join module.categories as category "
				+ "where field.fieldId = :id "
				+ "and category.isSubject = true", Category.class);
		List<Category> result = query.setParameter("id", field.getFieldId()).getResultList();
		session.getTransaction().commit();
		return result;
	}

	@Override
	public Category getCategoryById(int id) {
		session.beginTransaction().commit();
		Category category = session.byId(Category.class).load(id);
		session.getTransaction().commit();
		return category;
	}

	@Override
	public Field getFieldById(int id) {
		session.beginTransaction();
		Field field = session.byId(Field.class).load(id);
		session.getTransaction().commit();
		return field;
	}

	@Override
	public void cleanUp() {
		session.close();
	}

};
