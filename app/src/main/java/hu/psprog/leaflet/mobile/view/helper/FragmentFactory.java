package hu.psprog.leaflet.mobile.view.helper;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.MenuItem;
import hu.psprog.leaflet.mobile.R;
import hu.psprog.leaflet.mobile.view.fragment.DocumentDetailsFragment;
import hu.psprog.leaflet.mobile.view.fragment.EntryListFragment;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import static hu.psprog.leaflet.mobile.view.helper.NavigationMenuUpdater.INTENT_ACTION_FILTER_BY_CATEGORY;

/**
 * Factory to create a fragment based on given {@link MenuItem}.
 * Mappings can be populated in the FRAGMENTS_BY_MENU_ITEM_ID and FRAGMENTS_BY_INTENT_ACTION maps.
 * For menu items without intent, mapping will be retrieved from the former, while for menu items with
 * intent, mapping will be retrieved from the latter.
 *
 * @author Peter Smith
 */
@Singleton
public class FragmentFactory {

    private static final int SPARSE_ARRAY_INITIAL_CAPACITY = 1;
    private static final SparseArray<Supplier<Fragment>> FRAGMENTS_BY_MENU_ITEM_ID = new SparseArray<>(SPARSE_ARRAY_INITIAL_CAPACITY);
    private static final Map<String, Supplier<Fragment>> FRAGMENTS_BY_INTENT_ACTION = new HashMap<>();

    static {
        FRAGMENTS_BY_MENU_ITEM_ID.put(R.id.nav_home, EntryListFragment::new);
        FRAGMENTS_BY_MENU_ITEM_ID.put(R.id.nav_introduction,
                () -> DocumentDetailsFragment.newInstance(DocumentDetailsFragment.DocumentType.INTRODUCTION));

        FRAGMENTS_BY_INTENT_ACTION.put(INTENT_ACTION_FILTER_BY_CATEGORY, EntryListFragment::new);
    }

    @Inject
    public FragmentFactory() {
    }

    /**
     * Retrieves a new, instantiated fragment for given {@link MenuItem}.
     *
     * @param menuItem {@link MenuItem} to retrieve fragment for
     * @return created fragment wrapped as {@link Optional}, or {@link Optional#empty()} if no mapping found
     */
    public Optional<Fragment> getFragment(MenuItem menuItem) {

        Fragment fragment;
        if (Objects.isNull(menuItem.getIntent())) {
            fragment = getFragment(menuItem.getItemId());
        } else {
            fragment = getFragment(menuItem.getIntent());
        }

        return Optional.ofNullable(fragment);
    }

    private Fragment getFragment(int menuItemID) {
        return Optional.ofNullable(FRAGMENTS_BY_MENU_ITEM_ID.get(menuItemID))
                .map(Supplier::get)
                .orElse(null);
    }

    private Fragment getFragment(Intent intent) {
        return Optional.ofNullable(FRAGMENTS_BY_INTENT_ACTION.get(intent.getAction()))
                .map(fragmentSupplier -> {
                    Fragment fragment = fragmentSupplier.get();
                    fragment.setArguments(intent.getExtras());

                    return fragment;
                })
                .orElse(null);
    }
}
