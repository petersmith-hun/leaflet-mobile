package hu.psprog.leaflet.mobile.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Category list wrapper model.
 *
 * @author Peter Smith
 */
public class CategoryList implements Serializable {

    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CategoryList that = (CategoryList) o;

        return new EqualsBuilder()
                .append(categories, that.categories)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(categories)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("categories", categories)
                .toString();
    }

    public static CategoryListBuilder getBuilder() {
        return new CategoryListBuilder();
    }

    /**
     * Builder for {@link CategoryList}.
     */
    public static final class CategoryListBuilder {
        private List<Category> categories;

        private CategoryListBuilder() {
        }

        public CategoryListBuilder withCategories(List<Category> categories) {
            this.categories = categories;
            return this;
        }

        public CategoryList build() {
            CategoryList categoryList = new CategoryList();
            categoryList.categories = this.categories;
            return categoryList;
        }
    }
}
