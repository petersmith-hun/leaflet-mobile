package hu.psprog.leaflet.mobile.repository.impl;

import hu.psprog.leaflet.mobile.model.Category;
import hu.psprog.leaflet.mobile.model.CategoryList;
import hu.psprog.leaflet.mobile.repository.CategoryRepository;
import io.reactivex.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Implementation of {@link CategoryRepository}.
 *
 * @author Peter Smith
 */
@Singleton
public class DummyCategoryRepositoryImpl implements CategoryRepository {

    private static final int NUMBER_OF_CATEGORIES = 4;
    private static final String CATEGORY_PATTERN = "Category #%s";

    private ObservableFactory observableFactory;

    @Inject
    public DummyCategoryRepositoryImpl(ObservableFactory observableFactory) {
        this.observableFactory = observableFactory;
    }

    @Override
    public Observable<CategoryList> getPublicCategories() {
        return observableFactory.create(() -> CategoryList.getBuilder()
                .withCategories(IntStream.range(0, NUMBER_OF_CATEGORIES)
                        .mapToObj(index -> Category.getBuilder()
                                .withId(index)
                                .withName(String.format(CATEGORY_PATTERN, index))
                                .build())
                        .collect(Collectors.toList()))
                .build());
    }
}
