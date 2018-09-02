package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import hu.psprog.leaflet.mobile.model.Category;
import hu.psprog.leaflet.mobile.model.DocumentDetails;
import hu.psprog.leaflet.mobile.model.EntryDetails;
import hu.psprog.leaflet.mobile.model.EntrySummary;
import hu.psprog.leaflet.mobile.model.EntrySummaryPage;
import hu.psprog.leaflet.mobile.model.EntrySummarySummaryPageJoin;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.dao.CategoryDAO;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.dao.DocumentDAO;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.dao.EntryDAO;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.dao.EntrySummaryDAO;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.dao.EntrySummaryPageDAO;

/**
 * Room database descriptor class.
 *
 * @author Peter Smith
 */
@Database(version = 1,
        exportSchema = false,
        entities = {
                Category.class,
                DocumentDetails.class,
                EntryDetails.class,
                EntrySummary.class,
                EntrySummaryPage.class,
                EntrySummarySummaryPageJoin.class})
public abstract class LeafletLocalCacheDatabase extends RoomDatabase {

    public abstract CategoryDAO categoryDAO();

    public abstract DocumentDAO documentDAO();

    public abstract EntryDAO entryDAO();

    public abstract EntrySummaryDAO entrySummaryDAO();

    public abstract EntrySummaryPageDAO entrySummaryPageDAO();
}
