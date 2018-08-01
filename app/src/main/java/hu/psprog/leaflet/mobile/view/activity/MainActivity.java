package hu.psprog.leaflet.mobile.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import hu.psprog.leaflet.mobile.R;
import hu.psprog.leaflet.mobile.model.Category;
import hu.psprog.leaflet.mobile.model.EntrySummary;
import hu.psprog.leaflet.mobile.view.fragment.EntryDetailsFragment;
import hu.psprog.leaflet.mobile.view.fragment.EntryListFragment;
import hu.psprog.leaflet.mobile.viewmodel.CategoryListViewModel;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, EntryListFragment.OnEntryItemSelectedListener {

    private static final String INTENT_ACTION_FILTER_BY_CATEGORY = "FILTER_BY_CATEGORY";
    private static final int CATEGORIES_MENU_SECTION_INDEX = 2;

    public static final String INTENT_PARAMETER_CATEGORY_ID = "category_id";

    private final CompositeDisposable disposables = new CompositeDisposable();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private CategoryListViewModel categoryListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        categoryListViewModel = ViewModelProviders.of(this).get(CategoryListViewModel.class);

        ActionBarDrawerToggle toggle = getToggle();
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        updateMenu();
        changeFragment(new EntryListFragment());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment targetFragment = getTargetFragment(item);
        if (Objects.nonNull(targetFragment)) {
            changeFragment(targetFragment);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(EntrySummary item) {
        changeFragment(EntryDetailsFragment.newInstance(item.getLink()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }

    private void updateMenu() {
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

    private ActionBarDrawerToggle getToggle() {
        return new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    }

    private Fragment getTargetFragment(MenuItem item) {

        Fragment targetFragment = null;
        if (Objects.isNull(item.getIntent())) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    targetFragment = new EntryListFragment();
                    break;
            }
        } else {
            switch (item.getIntent().getAction()) {
                case INTENT_ACTION_FILTER_BY_CATEGORY:
                    targetFragment = new EntryListFragment();
                    targetFragment.setArguments(item.getIntent().getExtras());
                    break;
            }
        }

        return targetFragment;
    }

    private void changeFragment(Fragment targetFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.fragment_container, targetFragment)
                .commit();
    }
}
