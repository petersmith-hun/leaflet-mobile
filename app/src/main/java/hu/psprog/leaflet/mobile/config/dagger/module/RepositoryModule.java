package hu.psprog.leaflet.mobile.config.dagger.module;

import dagger.Binds;
import dagger.Module;
import hu.psprog.leaflet.mobile.repository.CategoryRepository;
import hu.psprog.leaflet.mobile.repository.DocumentRepository;
import hu.psprog.leaflet.mobile.repository.EntryRepository;
import hu.psprog.leaflet.mobile.repository.impl.DummyCategoryRepositoryImpl;
import hu.psprog.leaflet.mobile.repository.impl.DummyDocumentRepositoryImpl;
import hu.psprog.leaflet.mobile.repository.impl.DummyEntryRepositoryImpl;

/**
 * Dagger Module configuration for repositories.
 *
 * @author Peter Smith
 */
@Module
public abstract class RepositoryModule {

    @Binds
    public abstract CategoryRepository bindCategoryRepository(DummyCategoryRepositoryImpl dummyCategoryRepository);

    @Binds
    public abstract DocumentRepository bindDocumentRepository(DummyDocumentRepositoryImpl dummyDocumentRepository);

    @Binds
    public abstract EntryRepository bindEntryRepository(DummyEntryRepositoryImpl dummyEntryRepository);
}
