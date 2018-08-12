package hu.psprog.leaflet.mobile.config.dagger.module;

import dagger.Binds;
import dagger.Module;
import hu.psprog.leaflet.mobile.repository.CategoryRepository;
import hu.psprog.leaflet.mobile.repository.DocumentRepository;
import hu.psprog.leaflet.mobile.repository.EntryRepository;
import hu.psprog.leaflet.mobile.repository.impl.CategoryRepositoryImpl;
import hu.psprog.leaflet.mobile.repository.impl.DocumentRepositoryImpl;
import hu.psprog.leaflet.mobile.repository.impl.EntryRepositoryImpl;

/**
 * Dagger Module configuration for repositories.
 *
 * @author Peter Smith
 */
@Module
public abstract class RepositoryModule {

    @Binds
    public abstract CategoryRepository bindCategoryRepository(CategoryRepositoryImpl dummyCategoryRepository);

    @Binds
    public abstract DocumentRepository bindDocumentRepository(DocumentRepositoryImpl dummyDocumentRepository);

    @Binds
    public abstract EntryRepository bindEntryRepository(EntryRepositoryImpl dummyEntryRepository);
}
