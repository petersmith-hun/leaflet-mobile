package hu.psprog.leaflet.mobile.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Entry details model object.
 *
 * @author Peter Smith
 */
public class EntryDetails {

    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        EntryDetails that = (EntryDetails) o;

        return new EqualsBuilder()
                .append(title, that.title)
                .append(content, that.content)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(title)
                .append(content)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("title", title)
                .append("content", content)
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

        public EntryDetails build() {
            EntryDetails entryDetails = new EntryDetails();
            entryDetails.title = this.title;
            entryDetails.content = this.content;
            return entryDetails;
        }
    }
}
