package com.example.david.ertosql;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.david.ertosql.data.DbBitMapUtility;

import static com.example.david.ertosql.data.ERDiagramContract.*;

public class EditorActivity extends AppCompatActivity  implements
        LoaderManager.LoaderCallbacks<Cursor>  {
    /** Identifier for the Diagram data loader */
    private static final int EXISTING_DIAGRAM_LOADER = 0;
    private String mParentActivity;

    private Uri mCurrentPetUri;

    private ImageView imageView;
    private Button buttonSave;
    private Button buttonCancel;
    private EditText editTextSql;
    private EditText editTextTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Intent intent = getIntent();
        mCurrentPetUri = intent.getData();
        mParentActivity = intent.getStringExtra("ParentActivity");
        imageView = findViewById(R.id.image_view_editor);
        buttonSave = findViewById(R.id.button_save);
        buttonCancel = findViewById(R.id.button_cancel);
        editTextSql = findViewById(R.id.editText_sql_code);
        editTextTitle = findViewById(R.id.editText_diagram_name);
        getLoaderManager().initLoader(EXISTING_DIAGRAM_LOADER, null, this);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDiagram();
                finish();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mParentActivity != null &&mParentActivity.equals(TakeDiagramPicActivity.class.getSimpleName())){
                    int rowsDeleted = getContentResolver().delete(mCurrentPetUri, null, null);
                }
                finish();
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                ERDiagramEntry._ID,
                ERDiagramEntry.COLUMN_ERDIAGRAM_NAME,
                ERDiagramEntry.COLUMN_ERDIAGRAM_ORIGINAL_IMAGE, ERDiagramEntry.COLUMN_ERDIAGRAM_SQL_CODE};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
               mCurrentPetUri,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex(ERDiagramEntry.COLUMN_ERDIAGRAM_NAME);
            int originalImageColumnIndex = cursor.getColumnIndex(ERDiagramEntry.COLUMN_ERDIAGRAM_ORIGINAL_IMAGE);
            int sqlCodeColumnIndex = cursor.getColumnIndex(ERDiagramEntry.COLUMN_ERDIAGRAM_SQL_CODE);

            // Read the pet attributes from the Cursor for the current pet
            String titleName = cursor.getString(nameColumnIndex);
            byte[] originalImage = cursor.getBlob(originalImageColumnIndex);
            String sqlcode = cursor.getString(sqlCodeColumnIndex);
            editTextTitle.setText(titleName);
            editTextSql.setText(sqlcode);
            imageView.setImageBitmap(DbBitMapUtility.getImage(originalImage));
        }

    }

    private void saveDiagram()
    {
        ContentValues values = new ContentValues();
        values.put(ERDiagramEntry.COLUMN_ERDIAGRAM_NAME,editTextTitle.getText().toString());
        values.put(ERDiagramEntry.COLUMN_ERDIAGRAM_SQL_CODE,editTextSql.getText().toString());

        int rowsAffected = getContentResolver().update(mCurrentPetUri, values, null, null);

        // Show a toast message depending on whether or not the update was successful.
        if (rowsAffected == 0) {
            // If no rows were affected, then there was an error with the update.
            Toast.makeText(this, "Save Failed",
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the update was successful and we can display a toast.
            Toast.makeText(this, "Saved Successfully",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
