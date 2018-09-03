package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import hu.psprog.leaflet.mobile.model.Category;

import java.util.List;

/**
 * DAO for {@link Category} operations.
 *
 * @author Peter Smith
 */
@Dao
public interface CategoryDAO {

    /**
     * Stores given {@link List} of {@link Category} objects.
     *
     * @param categoryList {@link List} of {@link Category} objects to store
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Category> categoryList);

    /**
     * Retrieves all stored {@link Category} objects.
     *
     * @return List of stored {@link Category} objects, or empty list if none present
     */
    @Query("select * from cache_categories;")
    List<Category> getAllCategories();
}
