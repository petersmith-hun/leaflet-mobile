package hu.psprog.leaflet.mobile.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Entry summary model object for entry lists.
 *
 * @author Peter Smith
 */
@Entity(tableName = DatabaseConstants.TABLE_ENTRY_SUMMARIES)
public class EntrySummary {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = DatabaseConstants.FIELD_LINK)
    private String link;

    @ColumnInfo(name = DatabaseConstants.FIELD_TITLE)
    private String title;

    @ColumnInfo(name = DatabaseConstants.FIELD_PROLOGUE)
    private String prologue;

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public String getPrologue() {
        return prologue;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrologue(String prologue) {
        this.prologue = prologue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        EntrySummary that = (EntrySummary) o;

        return new EqualsBuilder()
                .append(link, that.link)
                .append(title, that.title)
                .append(prologue, that.prologue)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(link)
                .append(title)
                .append(prologue)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("link", link)
                .append("title", title)
                .append("prologue", prologue)
                .toString();
    }

    public static EntrySummaryBuilder getBuilder() {
        return new EntrySummaryBuilder();
    }

    /**
     * Builder for {@link EntrySummary}.
     */
    public static final class EntrySummaryBuilder {
        private String link;
        private String title;
        private String prologue;

        private EntrySummaryBuilder() {
        }

        public EntrySummaryBuilder withLink(String link) {
            this.link = link;
            return this;
        }

        public EntrySummaryBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public EntrySummaryBuilder withPrologue(String prologue) {
            this.prologue = prologue;
            return this;
        }

        public EntrySummary build() {
            EntrySummary entrySummary = new EntrySummary();
            entrySummary.title = this.title;
            entrySummary.link = this.link;
            entrySummary.prologue = this.prologue;
            return entrySummary;
        }
    }
}
