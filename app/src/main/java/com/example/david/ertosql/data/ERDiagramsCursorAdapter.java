package com.example.david.ertosql.data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.david.ertosql.R;
import com.example.david.ertosql.data.ERDiagramContract.ERDiagramEntry;

import static com.example.david.ertosql.data.DbBitMapUtility.getImage;

public class ERDiagramsCursorAdapter extends CursorAdapter {

    public ERDiagramsCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.erdiagram_item, parent, false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if(cursor != null && cursor.getCount()>0) {
            TextView nameTextView = view.findViewById(R.id.text_view_diagram_name);
            ImageView imageView = view.findViewById(R.id.image_view_diagram_image);
            // Find the columns of pet attributes that we're interested in
            int nameColumnIndex = cursor.getColumnIndex(ERDiagramEntry.COLUMN_ERDIAGRAM_NAME);
            int originalImageColumnIndex = cursor.getColumnIndex(ERDiagramEntry.COLUMN_ERDIAGRAM_ORIGINAL_IMAGE);
            // Read the pet attributes from the Cursor for the current pet
            String diagramTitle = cursor.getString(nameColumnIndex);
            byte[] originalImage = cursor.getBlob(originalImageColumnIndex);
            imageView.setImageBitmap(getImage(originalImage));
           nameTextView.setText(diagramTitle);
        }
    }
}
