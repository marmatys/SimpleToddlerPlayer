package com.armatys.std.ui;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.armatys.std.IntentExtraKeys;
import com.armatys.std.Intents;
import com.armatys.std.R;

public class SelectActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = SelectActivity.class.getName();

    private CursorAdapter adapter;

    static final String[] PROJECTION = new String[]{MediaStore.Video.Media._ID, MediaStore.Video.Media.DATA, MediaStore.Video.Media.TITLE};

    static final String SELECTION = MediaStore.Video.Media.DATA + " NOT LIKE '%Recorder%'";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new SelectCursorAdapter(this, null, 0);
        setListAdapter(adapter);

        getLoaderManager().initLoader(0, null, this);
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, PROJECTION, SELECTION, null, MediaStore.Video.Media.TITLE);
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        CursorWrapper item = (CursorWrapper) adapter.getItem(position);
        int dataIndex = item.getColumnIndex(MediaStore.Video.Media.DATA);
        String path = item.getString(dataIndex);
        Intent intent = Intents.SHOW_MOVIE.getIntent();
        intent.putExtra(IntentExtraKeys.MOVIE_PATH.getKey(), path);
        startActivity(intent);
    }

    class SelectCursorAdapter extends CursorAdapter {
        private LayoutInflater inflater;

        public SelectCursorAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return inflater.inflate(R.layout.view_select_list_item, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
//            CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            TextView titleTextView = (TextView) view.findViewById(R.id.textView);

            int idIndex = cursor.getColumnIndex(MediaStore.Video.Media._ID);
            int titleIndex = cursor.getColumnIndex(MediaStore.Video.Media.TITLE);

            int id = cursor.getInt(idIndex);
            String title = cursor.getString(titleIndex);
            Bitmap thumbnail = MediaStore.Video.Thumbnails.getThumbnail(getContentResolver(), id, MediaStore.Video.Thumbnails.MICRO_KIND, null);

            imageView.setImageBitmap(thumbnail);
            titleTextView.setText(title);
        }
    }
}