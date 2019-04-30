package com.example.david.ertosql.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class ERDiagramContract {
    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.example.david.ertosql";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.android.pets/pets/ is a valid path for
     * looking at pet data. content://com.example.android.pets/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_DIAGRAMS = "diagrams";

    private ERDiagramContract() {
    }

    public static final class ERDiagramEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_DIAGRAMS);

        /** Name of database table for ERS */
        public final static String TABLE_NAME = "diagrams";
        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of pets.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DIAGRAMS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DIAGRAMS;


        public final static String _ID = BaseColumns._ID;


        public final static String COLUMN_ERDIAGRAM_NAME ="name";
        public final static String COLUMN_ERDIAGRAM_SQL_CODE ="sqlCode";

        public final static String COLUMN_ERDIAGRAM_ORIGINAL_IMAGE ="originalImage";


        public static final String COLUMN_RELATIONAL_SCHEMA_IMAGE ="relationalSchemaImage" ;
    }
}

