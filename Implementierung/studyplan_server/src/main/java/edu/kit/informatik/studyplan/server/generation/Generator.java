package edu.kit.informatik.studyplan.server.generation;

import java.util.Map;

import edu.kit.informatik.studyplan.server.generation.objectivefunction.PartialObjectiveFunction;
import edu.kit.informatik.studyplan.server.model.moduledata.Category;
import edu.kit.informatik.studyplan.server.model.moduledata.Field;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDao;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;

/************************************************************/
/**
 * Das Interface Generator bietet die allgemeine Struktur eines Generierers.
 */
public interface Generator {

	/**
	 * Die Methode generate generiert einen vollständigen, optimierten und
	 * korrekten Studienplan. Hierzu nimmt sie einen angefangenen Studienplan
	 * entgegen, vervollständigt diesen zunächst nach System-Constraints und
	 * Zufall und optimiert ihn dann mit Hilfe der übergebenen Zielfunktion
	 * objectiveFunktion. Die benötigten Module werden über übergebenen
	 * ModuleDao erreicht.
	 * 
	 * @return Zurückgegeben wird ein vollständiger, korrekter und optimierter
	 *         Studienplan vom Typ Plan
	 * @param objectiveFunction
	 *            Die Zielfunktion, anhand der optimiert werden soll
	 * @param currentPlan
	 *            der bereits bestehende Plan
	 */
	public Plan generate(PartialObjectiveFunction objectiveFunction, Plan currentPlan, 
			ModuleDao moduleDAO, Map<Field, Category> preferredSubjects, int maxECTSperSemester);
}
