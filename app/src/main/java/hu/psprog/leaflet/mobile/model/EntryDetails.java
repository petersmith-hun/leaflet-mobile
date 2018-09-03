package hu.psprog.leaflet.mobile.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Entry details model object.
 *
 * @author Peter Smith
 */
@Entity(tableName = DatabaseConstants.TABLE_ENTRY_DETAILS)
public class EntryDetails implements Serializable {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = DatabaseConstants.FIELD_LINK)
    private String link;

    @ColumnInfo(name = DatabaseConstants.FIELD_TITLE)
    private String title;

    @ColumnInfo(name = DatabaseConstants.FIELD_CONTENT)
    private String content;

    @ColumnInfo(name = DatabaseConstants.FIELD_AUTHOR)
    private String author;

    @ColumnInfo(name = DatabaseConstants.FIELD_CREATED_DATE)
    private String createdDate;

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        EntryDetails that = (EntryDetails) o;

        return new EqualsBuilder()
                .append(link, that.link)
                .append(title, that.title)
                .append(content, that.content)
                .append(author, that.author)
                .append(createdDate, that.createdDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(link)
                .append(title)
                .append(content)
                .append(author)
                .append(createdDate)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("link", link)
                .append("title", title)
                .append("content", content)
                .append("author", author)
                .append("createdDate", createdDate)
                .toString();
    }

    public static EntryDetailsBuilder getBuilder() {
        return new EntryDetailsBuilder();
    }

    /**
     * Builder for {@link EntryDetails}.
     */
    public static final class EntryDetailsBuilder {
        private String link;
        private String title;
        private String content;
        private String author;
        private String createdDate;

        private EntryDetailsBuilder() {
        }

        public EntryDetailsBuilder withLink(String link) {
            this.link = link;
            return this;
        }

        public EntryDetailsBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public EntryDetailsBuilder withContent(String content) {
            this.content = content;
            return this;
        }

        public EntryDetailsBuilder withAuthor(String author) {
            this.author = author;
            return this;
        }

        public EntryDetailsBuilder withCreatedDate(String createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public EntryDetails build() {
            EntryDetails entryDetails = new EntryDetails();
            entryDetails.link = this.link;
            entryDetails.title = this.title;
            entryDetails.author = this.author;
            entryDetails.createdDate = this.createdDate;
            entryDetails.content = this.content;
            return entryDetails;
        }
    }
}
