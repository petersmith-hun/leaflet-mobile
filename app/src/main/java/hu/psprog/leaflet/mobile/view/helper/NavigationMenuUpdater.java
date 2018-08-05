package hu.psprog.leaflet.mobile.view.helper;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import hu.psprog.leaflet.mobile.R;
import hu.psprog.leaflet.mobile.model.Category;
import hu.psprog.leaflet.mobile.viewmodel.CategoryListViewModel;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Updates main navigation drawer menu with the existing categories provided by the backend application.
 *
 * @author Peter Smith
 */
public class NavigationMenuUpdater {

    static final String INTENT_ACTION_FILTER_BY_CATEGORY = "FILTER_BY_CATEGORY";
    public static final String INTENT_PARAMETER_CATEGORY_ID = "category_id";

    private static final int CATEGORIES_MENU_SECTION_INDEX = 2;

    private final CompositeDisposable disposables = new CompositeDisposable();
    private final CategoryListViewModel categoryListViewModel;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    public NavigationMenuUpdater(FragmentActivity fragmentActivity, ViewModelProvider.Factory viewModelFactory) {
        this.categoryListViewModel = ViewModelProviders.of(fragmentActivity, viewModelFactory).get(CategoryListViewModel.class);
        ButterKnife.bind(this, fragmentActivity);
    }

    /**
     * Executes menu update.
     */
    public void updateMenu() {
        Disposable subscriptionResult = categoryListViewModel.getCategoriesForMenu()
                .subscribe(categoryList -> categoryList.getCategories().forEach(this::createMenuItem));
        getCategoriesMenuSection().setVisible(true);
        disposables.add(subscriptionResult);
    }

    private void createMenuItem(Category category) {
        getCategoriesMenuSection()
                .getSubMenu()
                .add(category.getName())
                .setIntent(buildCategoryMenuItemIntent(category))
                .setIcon(R.drawable.ic_radio_button_checked_black_24dp);
    }

    private Intent buildCategoryMenuItemIntent(Category category) {
        return new Intent()
                .setAction(INTENT_ACTION_FILTER_BY_CATEGORY)
                .putExtra(INTENT_PARAMETER_CATEGORY_ID, category.getId());
    }

    private MenuItem getCategoriesMenuSection() {
        return navigationView.getMenu()
                .getItem(CATEGORIES_MENU_SECTION_INDEX);
    }
}
