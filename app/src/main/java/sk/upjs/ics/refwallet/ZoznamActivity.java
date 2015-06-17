package sk.upjs.ics.refwallet;

import android.app.LoaderManager;
import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;

import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import static sk.upjs.ics.refwallet.Defaults.NO_COOKIE;
import static sk.upjs.ics.refwallet.Defaults.NO_CURSOR;

import static sk.upjs.ics.refwallet.DetailZapasActivity.ZAPAS_ID_EXTRA;

public class ZoznamActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener{
    private static final int NOTES_LOADER_ID = 0;
    private static final int INSERT_NOTE_TOKEN = 0;
    private static final int DELETE_NOTE_TOKEN = 0;
   // private ListView listZapasov;
   // ZapasyDao zapasyDao = ZapasyDao.INSTANCE;
    private GridView zapasGridView;
    private android.widget.SimpleCursorAdapter adapter;
    private EditText EditDatum;
    private EditText EditCZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoznam);

        //listZapasov = (ListView) findViewById(R.id.listZapasov);


        getLoaderManager().initLoader(NOTES_LOADER_ID, Bundle.EMPTY, this);

        zapasGridView = (GridView) findViewById(R.id.zapasGridView);
        zapasGridView.setAdapter(initializeAdapter());
        zapasGridView.setOnItemClickListener(this);


       /* listZapasov.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Zapas zapas = zapasyDao.list().get(position);

                Intent intent = new Intent(ZoznamActivity.this, DetailZapasActivity.class);
                intent.putExtra(ZAPAS_ID_EXTRA, zapas.getId());

                startActivity(intent);
            }
        });*/
    }

    private void insertIntoContentProvider(String zapasCZ,String zapasDatum) {
        Uri uri = ZapasContentProvider.CONTENT_URI;
        ContentValues values = new ContentValues();
        values.put(Provider.Zapas.CISLO_ZAP, zapasCZ);
        values.put(Provider.Zapas.TIMESTAMP, zapasDatum);

        AsyncQueryHandler insertHandler = new AsyncQueryHandler(getContentResolver()) {
            @Override
            protected void onInsertComplete(int token, Object cookie, Uri uri) {
                Toast.makeText(ZoznamActivity.this, "Note was saved", Toast.LENGTH_SHORT)
                        .show();
            }
        };

        insertHandler.startInsert(INSERT_NOTE_TOKEN, NO_COOKIE, uri, values);
    }

    private ListAdapter initializeAdapter() {
        String z =" C.Z: "+Provider.Zapas.CISLO_ZAP.toString();
        System.out.println("Z: " + z);
        System.out.println("provider: "+Provider.Zapas.CISLO_ZAP);
        String[] from = {Provider.Zapas.TIMESTAMP,Provider.Zapas.CISLO_ZAP};
        int[] to = {R.id.datumGridViewItem,R.id.czGridViewItem};
        this.adapter = new SimpleCursorAdapter(this, R.layout.zapas_grid, Defaults.NO_CURSOR, from, to, Defaults.NO_FLAGS);
        return this.adapter;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(this);
        loader.setUri(ZapasContentProvider.CONTENT_URI);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        this.adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        this.adapter.swapCursor(NO_CURSOR);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
        Cursor selectedZapasCursor = (Cursor) parent.getItemAtPosition(position);
        int descriptionColumnIndex = selectedZapasCursor.getColumnIndex(Provider.Zapas.CISLO_ZAP);
        String noteDescription = "Číslo zápasu: "+selectedZapasCursor.getString(descriptionColumnIndex);


            Intent intent = new Intent(ZoznamActivity.this, DetailZapasActivity.class);
            intent.putExtra(ZAPAS_ID_EXTRA,id);

            startActivity(intent);
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_zoznam, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_new) {
            Intent intent = new Intent(this, PridajZapasActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


