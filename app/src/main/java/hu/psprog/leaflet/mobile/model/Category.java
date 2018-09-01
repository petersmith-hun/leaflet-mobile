package hu.psprog.leaflet.mobile.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Category model.
 *
 * @author Peter Smith
 */
@Entity(tableName = DatabaseConstants.TABLE_CATEGORIES)
public class Category {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = DatabaseConstants.FIELD_ID)
    private long id;

    @ColumnInfo(name = DatabaseConstants.FIELD_NAME)
    private String name;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        return new EqualsBuilder()
                .append(id, category.id)
                .append(name, category.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .toString();
    }

    public static CategoryBuilder getBuilder() {
        return new CategoryBuilder();
    }

    /**
     * Builder for {@link Category}.
     */
    public static final class CategoryBuilder {
        private long id;
        private String name;

        private CategoryBuilder() {
        }

        public CategoryBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public CategoryBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public Category build() {
            Category category = new Category();
            category.id = this.id;
            category.name = this.name;
            return category;
        }
    }
}
