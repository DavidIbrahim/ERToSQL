package com.example.david.ertosql.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.david.ertosql.data.ERDiagramContract.*;

public class ERDiagramDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = ERDiagramDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "diagrams.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    public ERDiagramDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
// Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_PETS_TABLE =  "CREATE TABLE " + ERDiagramEntry.TABLE_NAME + " ("
                + ERDiagramEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ERDiagramEntry.COLUMN_ERDIAGRAM_NAME + " TEXT NOT NULL, "
                + ERDiagramEntry.COLUMN_ERDIAGRAM_SQL_CODE + " TEXT, "
                + ERDiagramEntry.COLUMN_ERDIAGRAM_ORIGINAL_IMAGE + " BLOB, "
                + ERDiagramEntry.COLUMN_RELATIONAL_SCHEMA_IMAGE + " BLOB);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + ERDiagramEntry.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }
}
