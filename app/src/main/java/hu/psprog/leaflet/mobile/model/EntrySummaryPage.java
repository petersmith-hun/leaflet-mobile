package hu.psprog.leaflet.mobile.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Model for a list of entries with necessary paging information.
 * Optional {@link Category} indicates that the list was originally filtered by given category.
 *
 * @author Peter Smith
 */
public class EntrySummaryPage implements Serializable {

    private List<EntrySummary> entrySummaryList;
    private Optional<Category> category;
    private int page;
    private boolean hasPrevious;
    private boolean hasNext;

    public List<EntrySummary> getEntrySummaryList() {
        return entrySummaryList;
    }

    public Optional<Category> getCategory() {
        return category;
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
                .append(category, that.category)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(entrySummaryList)
                .append(category)
                .append(page)
                .append(hasPrevious)
                .append(hasNext)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("entrySummaryList", entrySummaryList)
                .append("category", category)
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
        private Optional<Category> category = Optional.empty();
        private int page;
        private boolean hasPrevious;
        private boolean hasNext;

        private EntrySummaryPageBuilder() {
        }

        public EntrySummaryPageBuilder withEntrySummaryList(List<EntrySummary> entrySummaryList) {
            this.entrySummaryList = entrySummaryList;
            return this;
        }

        public EntrySummaryPageBuilder withCategory(Category category) {
            this.category = Optional.ofNullable(category);
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
            entrySummaryPage.category = this.category;
            entrySummaryPage.page = this.page;
            entrySummaryPage.hasPrevious = this.hasPrevious;
            entrySummaryPage.hasNext = this.hasNext;
            entrySummaryPage.entrySummaryList = this.entrySummaryList;
            return entrySummaryPage;
        }
    }
}
