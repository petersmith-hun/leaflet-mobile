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
 * Document details model object.
 *
 * @author Peter Smith
 */
@Entity(tableName = DatabaseConstants.TABLE_DOCUMENT_DETAILS)
public class DocumentDetails implements Serializable {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = DatabaseConstants.FIELD_LINK)
    private String link;

    @ColumnInfo(name = DatabaseConstants.FIELD_TITLE)
    private String title;

    @ColumnInfo(name = DatabaseConstants.FIELD_CONTENT)
    private String content;

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        DocumentDetails that = (DocumentDetails) o;

        return new EqualsBuilder()
                .append(link, that.link)
                .append(title, that.title)
                .append(content, that.content)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(link)
                .append(title)
                .append(content)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("link", link)
                .append("title", title)
                .append("content", content)
                .toString();
    }

    public static DocumentDetailsBuilder getBuilder() {
        return new DocumentDetailsBuilder();
    }

    /**
     * Builder for {@link DocumentDetails}.
     */
    public static final class DocumentDetailsBuilder {
        private String link;
        private String title;
        private String content;

        private DocumentDetailsBuilder() {
        }

        public DocumentDetailsBuilder withLink(String link) {
            this.link = link;
            return this;
        }

        public DocumentDetailsBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public DocumentDetailsBuilder withContent(String content) {
            this.content = content;
            return this;
        }

        public DocumentDetails build() {
            DocumentDetails documentDetails = new DocumentDetails();
            documentDetails.link = this.link;
            documentDetails.content = this.content;
            documentDetails.title = this.title;
            return documentDetails;
        }
    }
}
