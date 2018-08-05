package hu.psprog.leaflet.mobile.viewmodel;

import android.arch.lifecycle.ViewModel;
import hu.psprog.leaflet.mobile.model.CategoryList;
import hu.psprog.leaflet.mobile.repository.CategoryRepository;
import io.reactivex.Observable;

/**
 * {@link ViewModel} implementation for category listing operations.
 *
 * @author Peter Smith
 */
public class CategoryListViewModel extends ViewModel {

    private CategoryRepository categoryRepository;

    public CategoryListViewModel(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Observable<CategoryList> getCategoriesForMenu() {
        return categoryRepository.getPublicCategories();
    }
}
