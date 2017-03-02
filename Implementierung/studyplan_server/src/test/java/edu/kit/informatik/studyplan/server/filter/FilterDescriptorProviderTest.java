package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.model.moduledata.Discipline;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

public class FilterDescriptorProviderTest {
    @Test
    public void getDescriptorFromUriIdentifierInvariant() throws Exception {
        Discipline discipline = mock(Discipline.class);
        FilterDescriptorProvider provider = new FilterDescriptorProvider(discipline);
        // provider invariant:
        provider.values().forEach(descriptor ->
                assertSame(descriptor,
                        provider.getDescriptorFromUriIdentifier(descriptor.getFilterUriIdentifier())));
    }

    @Test
    public void distinctUriIdentifiers() throws Exception {
        Discipline discipline = mock(Discipline.class);
        FilterDescriptorProvider provider = new FilterDescriptorProvider(discipline);
        provider.values().forEach(descriptor ->
                assertEquals(1,
                        provider.values().stream()
                                .filter(item -> item.getFilterUriIdentifier().equals(descriptor.getFilterUriIdentifier()))
                                .count()));
    }

    @Test
    public void distinctIds() throws Exception {
        Discipline discipline = mock(Discipline.class);
        FilterDescriptorProvider provider = new FilterDescriptorProvider(discipline);
        provider.values().forEach(descriptor ->
                assertEquals(1,
                        provider.values().stream()
                                .filter(item -> item.getId() == descriptor.getId())
                                .count()));
    }
}