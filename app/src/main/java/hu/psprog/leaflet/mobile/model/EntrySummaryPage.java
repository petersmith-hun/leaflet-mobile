package hu.psprog.leaflet.mobile.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Model for a list of entries with necessary paging information.
 * Optional {@link Category} indicates that the list was originally filtered by given category.
 *
 * @author Peter Smith
 */
@Entity(tableName = DatabaseConstants.TABLE_ENTRY_SUMMARY_PAGES,
        primaryKeys = {
                DatabaseConstants.FIELD_CATEGORY_ID,
                DatabaseConstants.FIELD_PAGE})
public class EntrySummaryPage implements Serializable {

    @Ignore
    private List<EntrySummary> entrySummaryList;

    @NonNull
    @ColumnInfo(name = DatabaseConstants.FIELD_CATEGORY_ID)
    @ForeignKey(entity = Category.class,
            parentColumns = DatabaseConstants.FIELD_ID,
            childColumns = DatabaseConstants.FIELD_CATEGORY_ID)
    private long categoryID;

    @NonNull
    @ColumnInfo(name = DatabaseConstants.FIELD_PAGE)
    private int page;

    @ColumnInfo(name = DatabaseConstants.FIELD_HAS_PREVIOUS)
    private boolean hasPrevious;

    @ColumnInfo(name = DatabaseConstants.FIELD_HAS_NEXT)
    private boolean hasNext;

    public List<EntrySummary> getEntrySummaryList() {
        return entrySummaryList;
    }

    public long getCategoryID() {
        return categoryID;
    }

    public int getPage() {
        return page;
    }

    public boolean hasPrevious() {
        return hasPrevious;
    }

    public boolean hasNext() {
        return hasNext;
    }

    public void setCategoryID(long categoryID) {
        this.categoryID = categoryID;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        EntrySummaryPage that = (EntrySummaryPage) o;

        return new EqualsBuilder()
                .append(page, that.page)
                .append(hasPrevious, that.hasPrevious)
                .append(hasNext, that.hasNext)
                .append(entrySummaryList, that.entrySummaryList)
                .append(categoryID, that.categoryID)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(entrySummaryList)
                .append(categoryID)
                .append(page)
                .append(hasPrevious)
                .append(hasNext)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("entrySummaryList", entrySummaryList)
                .append("categoryID", categoryID)
                .append("page", page)
                .append("hasPrevious", hasPrevious)
                .append("hasNext", hasNext)
                .toString();
    }

    public static EntrySummaryPageBuilder getBuilder() {
        return new EntrySummaryPageBuilder();
    }

    /**
     * Builder for {@link EntrySummaryPage}.
     */
    public static final class EntrySummaryPageBuilder {
        private List<EntrySummary> entrySummaryList;
        private long categoryID;
        private int page;
        private boolean hasPrevious;
        private boolean hasNext;

        private EntrySummaryPageBuilder() {
        }

        public EntrySummaryPageBuilder withEntrySummaryList(List<EntrySummary> entrySummaryList) {
            this.entrySummaryList = entrySummaryList;
            return this;
        }

        public EntrySummaryPageBuilder withCategoryID(long categoryID) {
            this.categoryID = categoryID;
            return this;
        }

        public EntrySummaryPageBuilder withPage(int page) {
            this.page = page;
            return this;
        }

        public EntrySummaryPageBuilder withHasPrevious(boolean hasPrevious) {
            this.hasPrevious = hasPrevious;
            return this;
        }

        public EntrySummaryPageBuilder withHasNext(boolean hasNext) {
            this.hasNext = hasNext;
            return this;
        }

        public EntrySummaryPage build() {
            EntrySummaryPage entrySummaryPage = new EntrySummaryPage();
            entrySummaryPage.categoryID = this.categoryID;
            entrySummaryPage.page = this.page;
            entrySummaryPage.hasPrevious = this.hasPrevious;
            entrySummaryPage.hasNext = this.hasNext;
            entrySummaryPage.entrySummaryList = this.entrySummaryList;
            return entrySummaryPage;
        }
    }
}
