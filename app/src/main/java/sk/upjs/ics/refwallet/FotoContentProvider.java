package sk.upjs.ics.refwallet;


import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.Calendar;

import static sk.upjs.ics.refwallet.ProviderFoto.PhotoUri;
import static sk.upjs.ics.refwallet.Defaults.ALL_COLUMNS;
import static sk.upjs.ics.refwallet.Defaults.NO_CONTENT_OBSERVER;
import static sk.upjs.ics.refwallet.Defaults.NO_GROUP_BY;
import static sk.upjs.ics.refwallet.Defaults.NO_HAVING;
import static sk.upjs.ics.refwallet.Defaults.NO_SELECTION;
import static sk.upjs.ics.refwallet.Defaults.NO_SELECTION_ARGS;
import static sk.upjs.ics.refwallet.Defaults.NO_SORT_ORDER;
import static sk.upjs.ics.refwallet.Defaults.NO_NULL_COLUMN_HACK;

/**
 * Created by Maťo21 on 13. 6. 2015.
 */
public class FotoContentProvider extends ContentProvider {

        public static final String ALL_ROWS = null;

        public static final String AUTHORITY = "sk.upjs.ics.refwallet.FotoContentProvider";

        public static final Uri PHOTO_URI_CONTENT_URI = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_CONTENT)
    .authority(AUTHORITY)
    .appendPath(ProviderFoto.PhotoUri.TABLE_NAME)
    .build();

        private static final String MIME_TYPE_PHOTO_URI = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + AUTHORITY + "." + ProviderFoto.PhotoUri.TABLE_NAME;

        public static final int URI_MATCH_PHOTO = 0;

        public static final int URI_MATCH_PHOTO_BY_ID = 1;

        private UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        public FotoContentProvider() {
        }


        private DatabaseOpenHelper databaseHelper;

        @Override
        public boolean onCreate() {

            uriMatcher.addURI(AUTHORITY, ProviderFoto.PhotoUri.TABLE_NAME, URI_MATCH_PHOTO);
            uriMatcher.addURI(AUTHORITY, ProviderFoto.PhotoUri.TABLE_NAME + "/#", URI_MATCH_PHOTO_BY_ID);

            databaseHelper = new DatabaseOpenHelper(this.getContext());

            return true;
        }

        @Override
        public Cursor query(Uri uri, String[] projection, String selection,
                            String[] selectionArgs, String sortOrder) {

            switch(uriMatcher.match(uri)) {
                case URI_MATCH_PHOTO:
                    Cursor cursor = getPhotoCursor();
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    return cursor;
                default:
                    return null;
            }
        }

        private Cursor getPhotoCursor() {
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            Cursor cursor = db.query(ProviderFoto.PhotoUri.TABLE_NAME,
                    ALL_COLUMNS,
                    NO_SELECTION,
                    NO_SELECTION_ARGS,
                    NO_GROUP_BY,
                    NO_HAVING,
                    NO_SORT_ORDER);
            return cursor;
        }

        @Override
        public int delete(Uri uri, String selection, String[] selectionArgs) {

            switch(uriMatcher.match(uri)){

                case URI_MATCH_PHOTO_BY_ID:
                    //Log.w(getClass().getName(), "MATCHT PHOTO BY ID");
                    long id1 = ContentUris.parseId(uri);
                    int affectedRows3 = databaseHelper.getWritableDatabase()
                            .delete(PhotoUri.TABLE_NAME, PhotoUri._ID + " = " + id1, Defaults.NO_SELECTION_ARGS);
                    getContext().getContentResolver().notifyChange(PHOTO_URI_CONTENT_URI, NO_CONTENT_OBSERVER);
                    return affectedRows3;

                //else delete all items in table
                // http://stackoverflow.com/questions/19183294/what-is-the-best-way-in-android-to-delete-all-rows-from-a-table
                // https://groups.google.com/forum/#!topic/android-developers/wK5gZ-VxcSg
                case URI_MATCH_PHOTO:
                    //Log.w(getClass().getName(), "MATCHT PHOTO");
                    int affectedRows4 = databaseHelper.getWritableDatabase()
                            .delete(PhotoUri.TABLE_NAME, ALL_ROWS, Defaults.NO_SELECTION_ARGS);
                    getContext().getContentResolver().notifyChange(PHOTO_URI_CONTENT_URI, NO_CONTENT_OBSERVER);
                    return affectedRows4;
            }
            //Log.w(getClass().getName(), "NO MATCH!");
            return 0;
        /*long id = ContentUris.parseId(uri);
        if (id != -1l) {
            int affectedRows = databaseHelper.getWritableDatabase()
                .delete(TrainingHistory.TABLE_NAME, TrainingHistory._ID + " = " + id, Defaults.NO_SELECTION_ARGS);
            getContext().getContentResolver().notifyChange(TRAINING_HISTORY_CONTENT_URI, NO_CONTENT_OBSERVER);
            return affectedRows;
        }
        //else delete all items in table
        // http://stackoverflow.com/questions/19183294/what-is-the-best-way-in-android-to-delete-all-rows-from-a-table
        // https://groups.google.com/forum/#!topic/android-developers/wK5gZ-VxcSg
        int affectedRows = databaseHelper.getWritableDatabase()
                .delete(TrainingHistory.TABLE_NAME, ALL_ROWS, Defaults.NO_SELECTION_ARGS);
        getContext().getContentResolver().notifyChange(TRAINING_HISTORY_CONTENT_URI, NO_CONTENT_OBSERVER);
        return affectedRows;*/
        }

        @Override
        public String getType(Uri uri) {
            switch(uriMatcher.match(uri)){
                case URI_MATCH_PHOTO:
                    return MIME_TYPE_PHOTO_URI;
                default:
                    return null;
            }
        }

        @Override
        public Uri insert(Uri uri, ContentValues values) {
            //Log.w(getClass().getName(), "INSERT at: " + uri);
            switch (uriMatcher.match(uri)) {
                case URI_MATCH_PHOTO:
                    System.out.println("UriMatchPhoto!");
                    //Log.e(getClass().getName(), "UriMatchPhoto!");
                    Calendar calendar = Calendar.getInstance();
                    ContentValues contentValues1 = new ContentValues();
                    contentValues1.put(PhotoUri._ID, Defaults.AUTOGENERATED_ID);
                    contentValues1.put(PhotoUri.URI, values.getAsString(PhotoUri.URI));
                    contentValues1.put(PhotoUri.YEAR, calendar.get(Calendar.YEAR));
                    contentValues1.put(PhotoUri.MONTH, calendar.get(Calendar.MONTH)+1);
                    contentValues1.put(PhotoUri.DAY, calendar.get(Calendar.DAY_OF_MONTH));
                    contentValues1.put(PhotoUri.DESCRIPTION, values.getAsString(PhotoUri.DESCRIPTION));

                    SQLiteDatabase db1 = databaseHelper.getWritableDatabase();
                    long newId1 = db1.insert(PhotoUri.TABLE_NAME, NO_NULL_COLUMN_HACK, contentValues1);
                    getContext().getContentResolver().notifyChange(PHOTO_URI_CONTENT_URI, NO_CONTENT_OBSERVER);
                    //Log.w(getClass().getName(), "vkladam uri: " + values.getAsString(PhotoUri.URI));
                    return ContentUris.withAppendedId(PHOTO_URI_CONTENT_URI, newId1);
            }
            return null;
        }

        @Override
        public int update(Uri uri, ContentValues values, String selection,
                          String[] selectionArgs) {
            throw new UnsupportedOperationException("Not yet implemented");
        }
    }