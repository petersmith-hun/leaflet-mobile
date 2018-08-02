package hu.psprog.leaflet.mobile.view.loader;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Interface for content loading helper classes.
 * Every implementation should be able to populate its attached fragment.
 *
 * @author Peter Smith
 */
public interface ContentLoader {

    /**
     * Loads contents into the view of its attached fragment.
     */
    void loadContent();

    /**
     * Returns attached fragment.
     *
     * @return attached fragment
     */
    Fragment getFragment();

    /**
     * Returns attached view.
     *
     * @return attached view
     */
    View getView();
}
