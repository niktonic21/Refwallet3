package sk.upjs.ics.refwallet;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static sk.upjs.ics.refwallet.Provider.Zapas.CISLO_ZAP;
import static sk.upjs.ics.refwallet.Provider.Zapas.TABLE_NAME;
import static sk.upjs.ics.refwallet.Provider.Zapas.TIMESTAMP;
import static sk.upjs.ics.refwallet.Provider.Zapas.STADION;
import static sk.upjs.ics.refwallet.Provider.Zapas.LIGA;
import static sk.upjs.ics.refwallet.Provider.Zapas.DOMACI;
import static sk.upjs.ics.refwallet.Provider.Zapas.HOSTIA;
import static sk.upjs.ics.refwallet.Provider.Zapas.HR1;
import static sk.upjs.ics.refwallet.Provider.Zapas.HR2;
import static sk.upjs.ics.refwallet.Provider.Zapas.CR1;
import static sk.upjs.ics.refwallet.Provider.Zapas.CR2;
import static sk.upjs.ics.refwallet.Provider.Zapas.INSTRUKTOR;
import static sk.upjs.ics.refwallet.Provider.Zapas.VIDEO;
import static sk.upjs.ics.refwallet.Provider.Zapas.ODCHOD;
import static sk.upjs.ics.refwallet.Provider.Zapas.PRICHOD;
import static sk.upjs.ics.refwallet.Provider.Zapas.Z_MESTA;
import static sk.upjs.ics.refwallet.Provider.Zapas.DO_MESTA;
import static sk.upjs.ics.refwallet.Provider.Zapas.PAUSAL;
import static sk.upjs.ics.refwallet.Provider.Zapas.STRAVNE;
import static sk.upjs.ics.refwallet.Provider.Zapas.CESTOVNE;
import static sk.upjs.ics.refwallet.Provider.Zapas.SPOLU;





import static sk.upjs.ics.refwallet.Defaults.DEFAULT_CURSOR_FACTORY;


public class DatabaseOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "daRefere";
    public static final int DATABASE_VERSION = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, DEFAULT_CURSOR_FACTORY, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlTemplate = "CREATE TABLE %s ("
                + "%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "%s TEXT,"
                + "%s TEXT,"
                + "%s DATETIME,"
                + "%s TEXT,"
                + "%s TEXT,"
                + "%s TEXT,"
                + "%s TEXT,"
                + "%s TEXT,"
                + "%s TEXT,"
                + "%s TEXT,"
                + "%s TEXT,"
                + "%s TEXT,"
                + "%s DATETIME,"
                + "%s DATETIME,"
                + "%s TEXT,"
                + "%s TEXT,"
                + "%s INTEGER,"
                + "%s REAL,"
                + "%s REAL,"
                + "%s REAL"
                + ")";
        String sql =  String.format(sqlTemplate, TABLE_NAME, _ID, CISLO_ZAP, LIGA ,TIMESTAMP,STADION,
                DOMACI,HOSTIA,HR1,HR2,CR1,CR2,INSTRUKTOR,VIDEO,PRICHOD,ODCHOD,Z_MESTA,DO_MESTA,
                PAUSAL,STRAVNE,CESTOVNE,SPOLU);
        db.execSQL(sql);

        String sqlTemplate1 = "CREATE TABLE %s ("
                + "%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "%s TEXT,"
                + "%s INTEGER,"
                + "%s INTEGER,"
                + "%s INTEGER,"
                + "%s TEXT"
                + ")";

        String sql2 = String.format(sqlTemplate1,ProviderFoto.PhotoUri.TABLE_NAME,
                ProviderFoto.PhotoUri._ID, ProviderFoto.PhotoUri.URI,
                ProviderFoto.PhotoUri.YEAR,ProviderFoto.PhotoUri.MONTH,
                ProviderFoto.PhotoUri.DAY,ProviderFoto.PhotoUri.DESCRIPTION);
        db.execSQL(sql2);

        insertSampleEntry(db,"23","EXS","2015-01-12","Steelka","Kosice","Poprad","Jura","Jonak",
                "Jobbagy","Bogdan","Orsi","Konco","13:30","19:30","Kosice","Poprad",84,6.3,100,190);
        insertSampleEntry(db,"56","EXS","2015-01-13","Arena","Presov","Poprad","Lauff","Jonak",
                "Jobbagy","Bogdan","Orsi","Konco","11:30","21:30","Presov","Poprad",55,4.5,15.5,75);
        insertSampleEntry(db, "33","EXS","2015-02-15","Steelka","Kosice","Poprad","Jura","Jonak",
                "Jobbagy","Bogdan","Orsi","Konco","13:30","19:30","Zdana","Kosice",23,6.3,32,61.3);
    }

    private void insertSampleEntry(SQLiteDatabase db, String cislozap,String liga,String timestamp,String stadion,
                                   String domaci,String hostia,String hr1,String hr2,String cr1,String cr2,
                                   String instruktor,String video,String odchod,String prichod,String zmesta,String domesta,
                                   int pausal,double stravne,double cestovne,double spolu) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Provider.Zapas.CISLO_ZAP, cislozap);
        contentValues.put(Provider.Zapas.LIGA,liga );
        contentValues.put(Provider.Zapas.TIMESTAMP, timestamp);
        contentValues.put(Provider.Zapas.STADION,stadion );
        contentValues.put(Provider.Zapas.DOMACI,domaci );
        contentValues.put(Provider.Zapas.HOSTIA,hostia );
        contentValues.put(Provider.Zapas.HR1, hr1);
        contentValues.put(Provider.Zapas.HR2, hr2 );
        contentValues.put(Provider.Zapas.CR1, cr1);
        contentValues.put(Provider.Zapas.CR2, cr2);
        contentValues.put(Provider.Zapas.INSTRUKTOR,instruktor );
        contentValues.put(Provider.Zapas.VIDEO, video);
        contentValues.put(Provider.Zapas.ODCHOD, odchod);
        contentValues.put(Provider.Zapas.PRICHOD,prichod );
        contentValues.put(Provider.Zapas.Z_MESTA, zmesta);
        contentValues.put(Provider.Zapas.DO_MESTA, domesta);
        contentValues.put(Provider.Zapas.PAUSAL, pausal);
        contentValues.put(Provider.Zapas.STRAVNE, stravne);
        contentValues.put(Provider.Zapas.CESTOVNE, cestovne);
        contentValues.put(Provider.Zapas.SPOLU, spolu);
        db.insert(Provider.Zapas.TABLE_NAME, Defaults.NO_NULL_COLUMN_HACK, contentValues);
    }
/*    private String createTableSql() {
        String sqlTemplate = "CREATE TABLE %s ("
                + "%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "%s TEXT,"
                + "%s TEXT,"
                + "%s DATE,"
                + "%s TEXT,"
                + "%s TEXT,"
                + "%s TEXT,"
                + "%s TEXT,"
                + "%s TEXT,"
                + "%s TEXT,"
                + "%s TEXT,"
                + "%s TEXT,"
                + "%s TEXT,"
                + "%s DATETIME,"
                + "%s DATETIME,"
                + "%s TEXT,"
                + "%s TEXT,"
                + "%s INTEGER,"
                + "%s REAL,"
                + "%s REAL,"
                + "%s REAL"
                + ")";
        return String.format(sqlTemplate, TABLE_NAME, _ID, CISLO_ZAP, LIGA ,TIMESTAMP,STADION,
                DOMACI,HOSTIA,HR1,HR2,CR1,CR2,INSTRUKTOR,VIDEO,PRICHOD,ODCHOD,Z_MESTA,DO_MESTA,
                PAUSAL,STRAVNE,CESTOVNE,SPOLU);
    }
    private String createTableSql2(){
        String sqlTemplate1 = "CREATE TABLE %s ("
                + "%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "%s TEXT,"
                + "%s INTEGER,"
                + "%s INTEGER,"
                + "%s INTEGER,"
                + "%s TEXT"
                + ")";

        return String.format(sqlTemplate1,TABLE_NAME,_ID, URI,YEAR,MONTH,DAY,DESCRIPTION);

    }*/

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // do nothing
    }
}

