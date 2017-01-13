package edu.kit.informatik.studyplan.server.rest;

/**
 * Diese Klasse repräsentiert dir Student-Resource.
 */
public class StudentResource {

	/**
	 * Erstellt eine Student-Resource.
	 */
	public StudentResource() {

	}

	/**
	 * PUT-Anfrage: Ersetzt Informationen über einen Student und löscht die
	 * Verifikationsinformationen.
	 * 
	 * @param jsonStudentInformation
	 *            die Informationen über den Student als JSON Objekt.
	 * @return Student mit neue Informationen als JSON Objekt.
	 */
	public JSONObject replaceInformation(JSONObject jsonStudentInformation) {
		return null;

	}

	/**
	 * GET-Anfrage: Gibt die Studentinformationen zurück.
	 * 
	 * @return die Studentinformationen als JSON Objekt.
	 */
	public JSONObject getInformation() {

		return null;
	}

	/**
	 * DELETE-Anfrage: Löscht den Student.
	 */
	public void deleteStudent() {

	}
}
