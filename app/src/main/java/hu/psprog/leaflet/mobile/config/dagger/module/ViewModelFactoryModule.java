package hu.psprog.leaflet.mobile.config.dagger.module;

import dagger.Module;
import dagger.Provides;
import hu.psprog.leaflet.mobile.repository.CategoryRepository;
import hu.psprog.leaflet.mobile.repository.DocumentRepository;
import hu.psprog.leaflet.mobile.repository.EntryRepository;
import hu.psprog.leaflet.mobile.repository.Repository;
import hu.psprog.leaflet.mobile.repository.SupportRepository;
import hu.psprog.leaflet.mobile.viewmodel.factory.DependencyInjectingViewModelFactory;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;

/**
 * Provides Dagger dependency injection configuration for ViewModel factory.
 *
 * @author Peter Smith
 */
@Module
public class ViewModelFactoryModule {

    private static final String REPOSITORIES = "repositories";

    @Provides
    @Singleton
    @Named(REPOSITORIES)
    public List<Repository> provideRepositories(CategoryRepository categoryRepository, DocumentRepository documentRepository,
                                                EntryRepository entryRepository, SupportRepository supportRepository) {
        return Arrays.asList(categoryRepository, documentRepository, entryRepository, supportRepository);
    }

    @Provides
    @Singleton
    public DependencyInjectingViewModelFactory provideDependencyInjectingViewModelFactory(@Named(REPOSITORIES) List<Repository> repositories) {
        return new DependencyInjectingViewModelFactory(repositories);
    }
}
