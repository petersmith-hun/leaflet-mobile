package hu.psprog.leaflet.mobile.view.activity;

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
import hu.psprog.leaflet.mobile.model.EntrySummary;
import hu.psprog.leaflet.mobile.view.fragment.EntryDetailsFragment;
import hu.psprog.leaflet.mobile.view.fragment.EntryListFragment;
import hu.psprog.leaflet.mobile.view.helper.FragmentFactory;
import hu.psprog.leaflet.mobile.view.helper.NavigationMenuUpdater;
import io.reactivex.disposables.CompositeDisposable;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, EntryListFragment.OnEntryItemSelectedListener {

    private static final int MAX_NUMBER_OF_STEPS_BACK = 5;
    private final CompositeDisposable disposables = new CompositeDisposable();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private FragmentFactory fragmentFactory;
    private NavigationMenuUpdater navigationMenuUpdater;
    private Deque<Fragment> previousFragments = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        // TODO configure Dagger DI
        fragmentFactory = new FragmentFactory();
        navigationMenuUpdater = new NavigationMenuUpdater(this);

        ActionBarDrawerToggle toggle = getToggle();
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationMenuUpdater.updateMenu();
        changeFragment(new EntryListFragment());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Fragment previousFragment = previousFragments.poll();
            if (Objects.isNull(previousFragment)) {
                super.onBackPressed();
            } else {
                changeFragment(previousFragment, false);
            }
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

        fragmentFactory.getFragment(item)
                .ifPresent(this::changeFragment);

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

    private ActionBarDrawerToggle getToggle() {
        return new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    }

    private void changeFragment(Fragment targetFragment) {
        changeFragment(targetFragment, true);
    }

    private void changeFragment(Fragment targetFragment, boolean shouldSaveFragment) {
        if (shouldSaveFragment) {
            saveCurrentFragment();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.fragment_container, targetFragment)
                .commit();
    }

    private void saveCurrentFragment() {
        previousFragments.push(getSupportFragmentManager().findFragmentById(R.id.fragment_container));
        if (previousFragments.size() > MAX_NUMBER_OF_STEPS_BACK) {
            previousFragments.pollLast();
        }
    }
}
