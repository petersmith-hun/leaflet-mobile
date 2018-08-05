package hu.psprog.leaflet.mobile.viewmodel.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import hu.psprog.leaflet.mobile.repository.CategoryRepository;
import hu.psprog.leaflet.mobile.repository.DocumentRepository;
import hu.psprog.leaflet.mobile.repository.EntryRepository;
import hu.psprog.leaflet.mobile.repository.Repository;
import hu.psprog.leaflet.mobile.repository.impl.DummyCategoryRepositoryImpl;
import hu.psprog.leaflet.mobile.repository.impl.DummyDocumentRepositoryImpl;
import hu.psprog.leaflet.mobile.repository.impl.DummyEntryRepositoryImpl;
import hu.psprog.leaflet.mobile.viewmodel.CategoryListViewModel;
import hu.psprog.leaflet.mobile.viewmodel.DocumentDetailsViewModel;
import hu.psprog.leaflet.mobile.viewmodel.EntryDetailsViewModel;
import hu.psprog.leaflet.mobile.viewmodel.EntryListViewModel;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Factory for ViewModels providing Dagger based dependency injection.
 *
 * @author Peter Smith
 */
@Singleton
public class DependencyInjectingViewModelFactory implements ViewModelProvider.Factory {

    private static final Map<Class<? extends ViewModel>, Class<? extends Repository>> VIEW_MODEL_TO_REPOSITORY_MAPPING = new HashMap<>();
    private static final Map<Class<? extends ViewModel>, Function<Repository, ViewModel>> VIEW_MODEL_CREATOR_MAPPING = new HashMap<>();

    static {
        VIEW_MODEL_TO_REPOSITORY_MAPPING.put(CategoryListViewModel.class, DummyCategoryRepositoryImpl.class);
        VIEW_MODEL_TO_REPOSITORY_MAPPING.put(DocumentDetailsViewModel.class, DummyDocumentRepositoryImpl.class);
        VIEW_MODEL_TO_REPOSITORY_MAPPING.put(EntryDetailsViewModel.class, DummyEntryRepositoryImpl.class);
        VIEW_MODEL_TO_REPOSITORY_MAPPING.put(EntryListViewModel.class, DummyEntryRepositoryImpl.class);

        VIEW_MODEL_CREATOR_MAPPING.put(CategoryListViewModel.class, repository -> new CategoryListViewModel((CategoryRepository) repository));
        VIEW_MODEL_CREATOR_MAPPING.put(DocumentDetailsViewModel.class, repository -> new DocumentDetailsViewModel((DocumentRepository) repository));
        VIEW_MODEL_CREATOR_MAPPING.put(EntryDetailsViewModel.class, repository -> new EntryDetailsViewModel((EntryRepository) repository));
        VIEW_MODEL_CREATOR_MAPPING.put(EntryListViewModel.class, repository -> new EntryListViewModel((EntryRepository) repository));
    }

    private Map<Class<? extends Repository>, Repository> repositoryMap;

    @Inject
    public DependencyInjectingViewModelFactory(List<Repository> repositories) {
        this.repositoryMap = repositories.stream()
                .collect(Collectors.toMap(Repository::getClass, Function.identity()));
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (!VIEW_MODEL_TO_REPOSITORY_MAPPING.containsKey(modelClass)) {
            throw new IllegalArgumentException("Specified ViewModel class is unknown - " + modelClass.getName());
        }

        Repository repository = repositoryMap.get(VIEW_MODEL_TO_REPOSITORY_MAPPING.get(modelClass));

        return (T) VIEW_MODEL_CREATOR_MAPPING.get(modelClass).apply(repository);
    }
}
