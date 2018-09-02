package hu.psprog.leaflet.mobile.model;

/**
 * Database constants (table and field names).
 *
 * @author Peter Smith
 */
class DatabaseConstants {

    private static final String TABLE_NAME_PREFIX = "cache_";

    static final String TABLE_CATEGORIES = TABLE_NAME_PREFIX + "categories";
    static final String TABLE_DOCUMENT_DETAILS = TABLE_NAME_PREFIX + "document_details";
    static final String TABLE_ENTRY_DETAILS = TABLE_NAME_PREFIX + "entry_details";
    static final String TABLE_ENTRY_SUMMARIES = TABLE_NAME_PREFIX + "entry_summaries";
    static final String TABLE_ENTRY_SUMMARY_PAGES = TABLE_NAME_PREFIX + "entry_summary_pages";
    static final String TABLE_ENTRY_SUMMARY_TO_SUMMARY_PAGE = TABLE_NAME_PREFIX + "entry_summaries_summary_pages";

    static final String FIELD_ID = "id";
    static final String FIELD_NAME = "name";
    static final String FIELD_LINK = "link";
    static final String FIELD_TITLE = "title";
    static final String FIELD_CONTENT = "content";
    static final String FIELD_AUTHOR = "author";
    static final String FIELD_CREATED_DATE = "created_date";
    static final String FIELD_PROLOGUE = "prologue";
    static final String FIELD_PAGE = "page";
    static final String FIELD_HAS_NEXT = "has_next";
    static final String FIELD_HAS_PREVIOUS = "has_previous";
    static final String FIELD_CATEGORY_ID = "category_id";
    static final String FIELD_ORDER = "order_seqn";

    private DatabaseConstants() {
        // prevent instantiation
    }
}
