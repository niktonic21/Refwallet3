package sk.upjs.ics.refwallet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import static sk.upjs.ics.refwallet.Defaults.DEFAULT_CURSOR_FACTORY;
/**
 * Created by Maťo21 on 13. 6. 2015.
 */
public interface ProviderFoto {

    public static class PhotoUri implements BaseColumns {
        public static final String TABLE_NAME = "photoUri";

        public static final String URI = "uri";

        public static final String YEAR = "year";

        public static final String MONTH = "month";

        public static final String DAY = "day";

        public static final String DESCRIPTION = "description";
    }
}