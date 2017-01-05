// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package edu.kit.informatik.studyplan.client.model.system;

import java.util.Collection;

import edu.kit.informatik.studyplan.client.model.modules.Module;
import edu.kit.informatik.studyplan.client.model.modules.ModuleCollection;
import edu.kit.informatik.studyplan.client.model.system.FilterCollection;

/************************************************************/
/**
 * Die Seach-Collection, welche das Lazy-Loading der Module in der Suche
 * übernimmt. Hierbei werden in zehner Schritten nach und nach mehr Module
 * geladen und in einzelne SearchModuleCollections gespeichert. Beim Abruf
 * mittels getModules() werden diese dann in eine einzige Collection
 * zusammengeführt.
 */
// TODO: UPDATE Model!
public class SearchCollection extends ModuleCollection {
    /**
     * Die Filter, welche beim Abruf zu berücksichtigen sind
     */
    private FilterCollection filters;
    /**
     * Die ID des Plans zu welcher die Suche gehört oder null, falls die Suche
     * Plan-Unabhängig ist
     */
    private int planId;

    /**
     * Methode, welche die Filter neu setzt und im Zuge desse, die alten
     * SearchModuleCollections löscht und highestLoaded zurücksetzt.
     * Anschließend werden erneut Module geladen.
     * 
     * @param filters
     *            Die Filter, welche bei der Suche berücksichtigt werden sollen.
     */
    public void setFilters(final FilterCollection filters) {
    }

    @Override
    public String url() {
        // TODO Auto-generated method stub
        return null;
    }
};