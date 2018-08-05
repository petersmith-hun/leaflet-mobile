package hu.psprog.leaflet.mobile.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Entry details model object.
 *
 * @author Peter Smith
 */
public class EntryDetails implements Serializable {

    private String title;
    private String content;
    private String author;
    private String createdDate;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        EntryDetails that = (EntryDetails) o;

        return new EqualsBuilder()
                .append(title, that.title)
                .append(content, that.content)
                .append(author, that.author)
                .append(createdDate, that.createdDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(title)
                .append(content)
                .append(author)
                .append(createdDate)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
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
        private String title;
        private String content;
        private String author;
        private String createdDate;

        private EntryDetailsBuilder() {
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
            entryDetails.title = this.title;
            entryDetails.author = this.author;
            entryDetails.createdDate = this.createdDate;
            entryDetails.content = this.content;
            return entryDetails;
        }
    }
}
