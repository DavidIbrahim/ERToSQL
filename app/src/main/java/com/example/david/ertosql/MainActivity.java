package com.example.david.ertosql;

import android.Manifest;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.david.ertosql.data.ERDiagramContract.ERDiagramEntry;
import com.example.david.ertosql.data.ERDiagramsCursorAdapter;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.opencv.android.OpenCVLoader;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>{
    /**
     *  True for testing ImageProcessing class only
     */
    private static final boolean  TESTING = false;
    private static final String TAG= MainActivity.class.getSimpleName();
    private static final int DIAGRAMS_LOADER = 0;
    private   ERDiagramsCursorAdapter mCursorAdapter;
    static {
        if(!OpenCVLoader.initDebug()){
            Log.d(TAG, "opencv not loaded");
        }else{
            Log.d(TAG, "opencv loaded");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(TESTING) {
            setContentView(R.layout.activity_main_test);
            final ImageView imageView = findViewById(R.id.test_image_view);
            Button buttonStartTesting = findViewById(R.id.button_start_testing);
            buttonStartTesting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageProcessing.testImageProcessing(view.getContext(),imageView);
                }
            });


        }
        else {
            setContentView(R.layout.activity_main);
            Dexter.withActivity(this)
                    .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                    .withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if(!report.areAllPermissionsGranted()){
                        Toast.makeText(MainActivity.this, "You need to grant all permission to use this app features", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                }
            }).check();
            FloatingActionButton startButton = findViewById(R.id.start_button);
            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent cvIntent = new Intent(MainActivity.this, TakeDiagramPicActivity.class);
                    startActivity(cvIntent);
                }
            });

            GridView gridView = (GridView)findViewById(R.id.gridview);
             mCursorAdapter = new ERDiagramsCursorAdapter(this, null);
            gridView.setAdapter(mCursorAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, EditorActivity.class);


                    Uri currentPetUri = ContentUris.withAppendedId(ERDiagramEntry.CONTENT_URI, id);

                    // Set the URI on the data field of the intent
                    intent.setData(currentPetUri);

                    // Launch the {@link EditorActivity} to display the data for the current diagram.
                    startActivity(intent);
                }
            });
            getLoaderManager().initLoader(DIAGRAMS_LOADER, null, this);





        }



    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Define a projection that specifies the columns from the table we care about.
        String[] projection = {
                ERDiagramEntry._ID,
                ERDiagramEntry.COLUMN_ERDIAGRAM_NAME,
                ERDiagramEntry.COLUMN_ERDIAGRAM_ORIGINAL_IMAGE };

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                ERDiagramEntry.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
        }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);

    }
}
