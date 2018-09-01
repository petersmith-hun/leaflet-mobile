package hu.psprog.leaflet.mobile.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Many-to-many join entity between {@link EntrySummary} and {@link EntrySummaryPage}.
 *
 * @author Peter Smith
 */
@Entity(tableName = DatabaseConstants.TABLE_ENTRY_SUMMARY_TO_SUMMARY_PAGE,
        primaryKeys = {
                DatabaseConstants.FIELD_CATEGORY_ID,
                DatabaseConstants.FIELD_PAGE,
                DatabaseConstants.FIELD_LINK})
public class EntrySummarySummaryPageJoin {

    @NonNull
    @ColumnInfo(name = DatabaseConstants.FIELD_CATEGORY_ID)
    @ForeignKey(entity = Category.class, parentColumns = DatabaseConstants.FIELD_ID, childColumns = DatabaseConstants.FIELD_CATEGORY_ID)
    private long categoryID;

    @NonNull
    @ColumnInfo(name = DatabaseConstants.FIELD_PAGE)
    private int page;

    @NonNull
    @ColumnInfo(name = DatabaseConstants.FIELD_LINK)
    @ForeignKey(entity = EntrySummary.class, parentColumns = DatabaseConstants.FIELD_LINK, childColumns = DatabaseConstants.FIELD_LINK)
    private String link;

    public long getCategoryID() {
        return categoryID;
    }

    public int getPage() {
        return page;
    }

    public String getLink() {
        return link;
    }

    public void setCategoryID(long categoryID) {
        this.categoryID = categoryID;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        EntrySummarySummaryPageJoin that = (EntrySummarySummaryPageJoin) o;

        return new EqualsBuilder()
                .append(page, that.page)
                .append(categoryID, that.categoryID)
                .append(link, that.link)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(categoryID)
                .append(page)
                .append(link)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("categoryID", categoryID)
                .append("page", page)
                .append("link", link)
                .toString();
    }

    public static EntrySummarySummaryPageJoinBuilder getBuilder() {
        return new EntrySummarySummaryPageJoinBuilder();
    }

    /**
     * Builder for {@link EntrySummarySummaryPageJoin}.
     */
    public static final class EntrySummarySummaryPageJoinBuilder {
        private long categoryID;
        private int page;
        private String link;

        private EntrySummarySummaryPageJoinBuilder() {
        }

        public EntrySummarySummaryPageJoinBuilder withCategoryID(long categoryID) {
            this.categoryID = categoryID;
            return this;
        }

        public EntrySummarySummaryPageJoinBuilder withPage(int page) {
            this.page = page;
            return this;
        }

        public EntrySummarySummaryPageJoinBuilder withLink(String link) {
            this.link = link;
            return this;
        }

        public EntrySummarySummaryPageJoin build() {
            EntrySummarySummaryPageJoin entrySummarySummaryPageJoin = new EntrySummarySummaryPageJoin();
            entrySummarySummaryPageJoin.categoryID = this.categoryID;
            entrySummarySummaryPageJoin.link = this.link;
            entrySummarySummaryPageJoin.page = this.page;
            return entrySummarySummaryPageJoin;
        }
    }
}
