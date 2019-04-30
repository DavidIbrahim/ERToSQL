package com.example.david.ertosql.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.EditText;

import static com.example.david.ertosql.data.ERDiagramContract.*;

public class ERDiagramProvider extends ContentProvider {

    private static final String TAG = ERDiagramProvider.class.getSimpleName();
    /**
     * Database helper object
     */
    private ERDiagramDbHelper mDbHelper;

    /**
     * URI matcher code for the content URI for the diagrams table
     */
    private static final int DIAGRAMS = 100;

    /**
     * URI matcher code for the content URI for a single diagrams in the diagrams table
     */
    private static final int DIAGRAMS_ID = 101;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        // The content URI of the form "content://com.example.android.diagramss/diagramss" will map to the
        // integer code {@link #diagrams}. This URI is used to provide access to MULTIPLE rows
        // of the diagrams table.
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_DIAGRAMS, DIAGRAMS);

        // The content URI of the form "content://com.example.android.diagrams/diagrams/#" will map to the
        // integer code {@link #DIAGRAMS_ID}. This URI is used to provide access to ONE single row
        // of the diagrams table.
        //
        // In this case, the "#" wildcard is used where "#" can be substituted for an integer.
        // For example, "content://com.example.android.diagrams/diagrams/3" matches, but
        // "content://com.example.android.diagrams/diagrams" (without a number at the end) doesn't match.
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_DIAGRAMS + "/#", DIAGRAMS_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new ERDiagramDbHelper(getContext());

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case DIAGRAMS:
                // For the DIAGRAMSS code, query the diagrams table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the diagrams table.
                cursor = database.query(ERDiagramEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case DIAGRAMS_ID:
                // For the DIAGRAMS_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.diagrams/diagrams/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = ERDiagramEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                // This will perform a query on the diagrams table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(ERDiagramEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        // Set notification URI on the Cursor,
        // so we know what content URI the Cursor was created for.
        // If the data at this URI changes, then we know we need to update the Cursor.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case DIAGRAMS:
                return ERDiagramEntry.CONTENT_LIST_TYPE;
            case DIAGRAMS_ID:
                return ERDiagramEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case DIAGRAMS:
                return insertDiagram(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertDiagram(Uri uri, ContentValues values) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Insert the new diagram with the given values
        long id = database.insert(ERDiagramEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(TAG, "Failed to insert row for " + uri);
            return null;
        }
        // Notify all listeners that the data has changed for the pet content URI
        getContext().getContentResolver().notifyChange(uri, null);
        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rowsDeleted;

        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case DIAGRAMS:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(ERDiagramEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case DIAGRAMS_ID:
                // Delete a single row given by the ID in the URI
                selection = ERDiagramEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(ERDiagramEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case DIAGRAMS:
                return updateDiagram(uri, values, selection, selectionArgs);
            case DIAGRAMS_ID:
                // For the ERDIAGRAM_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = ERDiagramEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateDiagram(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }
    /**
     * Update diagrams in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more diagrams).
     * Return the number of rows that were successfully updated.
     */
    private int updateDiagram(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsUpdated = database.update(ERDiagramEntry.TABLE_NAME, values, selection, selectionArgs);
        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        // Returns the number of database rows affected by the update statement
        return rowsUpdated;
    }
}
