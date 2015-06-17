package sk.upjs.ics.refwallet;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class StatistikyActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private TextView lblCestovne,lblStravne,lblSpolu,lblPausaly,lblPocetZap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistiky);

        lblCestovne =(TextView) findViewById(R.id.lblCestovne);
        lblStravne =(TextView) findViewById(R.id.lblStravne);
        lblSpolu =(TextView) findViewById(R.id.lblSpolu);
        lblPausaly=(TextView) findViewById(R.id.lblPausaly);
        lblPocetZap=(TextView) findViewById(R.id.lblPocetZap);

        getLoaderManager().initLoader(0,Bundle.EMPTY,this);
    }

    public void zobrazGraf(View view){
        Intent intent = new Intent(this, GrafActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statistiky, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/
        if (id == R.id.action_Graf) {
            Intent intent = new Intent(this, GrafActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(this);
        loader.setUri(Uri.parse(ZapasContentProvider.CONTENT_URI.toString() + "/stats"));
        Log.w("URI:",ZapasContentProvider.CONTENT_URI.toString() + "/stats");

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            while (cursor.moveToNext()){
                String  pausal=  cursor.getString(0);
                String  stravne=  cursor.getString(1);
                String  cestovne=  cursor.getString(2);
                String  spolu=  cursor.getString(3);
                String  pocetZap = cursor.getString(4);
                lblPausaly.setText(pausal);
                lblStravne.setText(stravne);
                lblCestovne.setText(cestovne);
                lblSpolu.setText(spolu);
                lblPocetZap.setText(pocetZap);
            }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
/*
    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(this);
        loader.setUri(ZapasContentProvider.CONTENT_URI);
        return loader;
    }*/

}
