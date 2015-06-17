package sk.upjs.ics.refwallet;

import android.app.LoaderManager;
import android.content.AsyncQueryHandler;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static sk.upjs.ics.refwallet.Defaults.NO_COOKIE;
import static sk.upjs.ics.refwallet.Defaults.NO_SELECTION;
import static sk.upjs.ics.refwallet.Defaults.NO_SELECTION_ARGS;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DetailZapasActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {
    public static final String ZAPAS_BUNDLE_KEY = "zapas";

    public static final String ZAPAS_ID_EXTRA = "zapasId";
    private static final int NOTES_LOADER_ID = 0;
    private static final int INSERT_NOTE_TOKEN = 0;
    private static final int DELETE_NOTE_TOKEN = 0;


    private EditText editCZ,editLiga,editDatum,editMiesto,editDomaci,editHostia,
            editHR1,editHR2,editCR1,editCR2,editInstruktor,editVideo,editPrichod,
            editOdchod,editZmesta,editDoMesta,editPausal;

    private TextView lblCestovne,lblStravne,lblSumaSpolu;

    private ZapasyDao zapasyDao = ZapasyDao.INSTANCE;

    private Zapas zapas;
    //loader colbak na aktivitu aplomovma simple cursor adapter,loader
    private boolean ignoreSaveOnFinish;
    private Long zapasId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_zapas);

        editCZ =(EditText) findViewById(R.id.editCZ);
        editLiga = (EditText) findViewById(R.id.editLiga);
        editDatum=(EditText) findViewById(R.id.editDatum);
        editMiesto = (EditText) findViewById(R.id.editMiesto);
        editDomaci = (EditText) findViewById(R.id.editDomaci);
        editHostia = (EditText) findViewById(R.id.editHostia);
        editHR1 =(EditText) findViewById(R.id.editHR1);
        editHR2 =(EditText) findViewById(R.id.editHR2);
        editCR1 =(EditText) findViewById(R.id.editCR1);
        editCR2 =(EditText) findViewById(R.id.editCR2);
        editInstruktor =(EditText) findViewById(R.id.editInstruktor);
        editVideo =(EditText) findViewById(R.id.editVideo);
        editPrichod =(EditText) findViewById(R.id.editPrichod);
        editOdchod =(EditText) findViewById(R.id.editOdchod);
        editZmesta =(EditText) findViewById(R.id.editZmesta);
        editDoMesta =(EditText) findViewById(R.id.editDoMesta);
        editPausal =(EditText) findViewById(R.id.editPausal);
        lblCestovne =(TextView) findViewById(R.id.lblPocetKM);
        lblStravne =(TextView) findViewById(R.id.lblStravne);
        lblSumaSpolu =(TextView) findViewById(R.id.lblSpolu);

        if (savedInstanceState != null) {
            zapas = (Zapas) savedInstanceState.get(ZAPAS_BUNDLE_KEY);
        } else {
             zapasId = (Long) getIntent().getSerializableExtra(ZAPAS_ID_EXTRA);
            /*if (zapasId != null) {
                zapas = zapasyDao.getZapas(zapasId);
            } else {
                zapas = new Zapas();
            }*/
        }
        getLoaderManager().initLoader(0,Bundle.EMPTY,this);


       // editDoMesta.addTextChangedListener(new CestovneTextWatcher());*/
    }/*
          editOdchod.addTextChangedListener(new StravneTextWatcher());

          lblCestovne.addTextChangedListener(new SpoluTextWatcher());

      private class StravneTextWatcher implements TextWatcher {

          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {
          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {
          }

          @Override
          public void afterTextChanged(Editable editable) {
              updateStravne();
          }
      }
   */

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        CursorLoader loader = new CursorLoader(this);
        loader.setUri(Uri.parse(ZapasContentProvider.CONTENT_URI.toString()+"/"+zapasId));
        return loader;
    }

    private void vymazZapas() {
        Uri selectedZapasUri = ContentUris.withAppendedId(ZapasContentProvider.CONTENT_URI,zapasId);
        AsyncQueryHandler deleteHandler = new AsyncQueryHandler(getContentResolver()) {
            @Override
            protected void onDeleteComplete(int token, Object cookie, int result) {
                Toast.makeText(DetailZapasActivity.this, "Zápas bol vymazaný", Toast.LENGTH_SHORT)
                        .show();
            }
        };
        deleteHandler.startDelete(DELETE_NOTE_TOKEN, NO_COOKIE, selectedZapasUri,
                NO_SELECTION, NO_SELECTION_ARGS);
    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        while(cursor.moveToNext()){
            String cisloZap = cursor.getString(cursor.getColumnIndex(Provider.Zapas.CISLO_ZAP));
            String timestamp =  cursor.getString(cursor.getColumnIndex(Provider.Zapas.TIMESTAMP));
            String liga =  cursor.getString(cursor.getColumnIndex(Provider.Zapas.LIGA));
            String  domaci =  cursor.getString(cursor.getColumnIndex(Provider.Zapas.DOMACI));
            String  hostia=  cursor.getString(cursor.getColumnIndex(Provider.Zapas.HOSTIA));
            String  stadion=  cursor.getString(cursor.getColumnIndex(Provider.Zapas.STADION));
            String  hr1=  cursor.getString(cursor.getColumnIndex(Provider.Zapas.HR1));
            String  hr2=  cursor.getString(cursor.getColumnIndex(Provider.Zapas.HR2));
            String  cr1=  cursor.getString(cursor.getColumnIndex(Provider.Zapas.CR1));
            String  cr2=  cursor.getString(cursor.getColumnIndex(Provider.Zapas.CR2));
            String  instruktor=  cursor.getString(cursor.getColumnIndex(Provider.Zapas.INSTRUKTOR));
            String  video=  cursor.getString(cursor.getColumnIndex(Provider.Zapas.VIDEO));
            String  odchod=  cursor.getString(cursor.getColumnIndex(Provider.Zapas.ODCHOD));
            String  prichod=  cursor.getString(cursor.getColumnIndex(Provider.Zapas.PRICHOD));
            String  zmesta=  cursor.getString(cursor.getColumnIndex(Provider.Zapas.Z_MESTA));
            String  domesta=  cursor.getString(cursor.getColumnIndex(Provider.Zapas.DO_MESTA));
            String  pausal=  cursor.getString(cursor.getColumnIndex(Provider.Zapas.PAUSAL));
            String  stravne=  cursor.getString(cursor.getColumnIndex(Provider.Zapas.STRAVNE));
            String  cestovne=  cursor.getString(cursor.getColumnIndex(Provider.Zapas.CESTOVNE));
            String  spolu=  cursor.getString(cursor.getColumnIndex(Provider.Zapas.SPOLU));
            editCZ.setText(cisloZap);
            editLiga.setText(liga);
            editDatum.setText(timestamp);
            editDomaci.setText(domaci);
            editHostia.setText(hostia);
            editMiesto.setText(stadion);
            editHR1.setText(hr1);
            editHR2.setText(hr2);
            editCR1.setText(cr1);
            editCR2.setText(cr2);
            editInstruktor.setText(instruktor);
            editVideo.setText(video);
            editOdchod.setText(odchod);
            editPrichod.setText(prichod);
            editZmesta.setText(zmesta);
            editDoMesta.setText(domesta);
            editPausal.setText(pausal);
            lblStravne.setText(stravne);
            lblCestovne.setText(cestovne);
            lblSumaSpolu.setText(spolu);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    private class CestovneTextWatcher implements TextWatcher {

          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {
          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {
          }

          @Override
          public void afterTextChanged(Editable editable) {
              updateCestovne();
      }    }

  /*    private class SpoluTextWatcher implements TextWatcher {

          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {
          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {
          }

          @Override
          public void afterTextChanged(Editable editable) {
              updateSpolu();
          }
      }
      private void updateStravne() {
          try {
              int odchod =Integer.parseInt(editOdchod.getText().toString());
              int prichod= Integer.parseInt(editPrichod.getText().toString());
              int stravne = odchod-prichod;
              double cena;
              if(stravne<=5000){
                  cena=4.2;
                  lblStravne.setText(new DecimalFormat("#.##").format(cena));
              }
              if(stravne<=12000){
                  cena=6.3;
                  lblStravne.setText(new DecimalFormat("#.##").format(cena));
              }
              if(stravne>12000){
                  cena=9.8;
                  lblStravne.setText(new DecimalFormat("#.##").format(cena));
              }

              //lblFahrenheit.setText(new DecimalFormat("#.##").format(fahrenheit));
          } catch (NumberFormatException e) {
              Toast
                      .makeText(this, "Please enter a number", Toast.LENGTH_LONG)
                      .show();
          }
      }
*/
      public static class Vzdialen{
          String zMesta;
          String doMesta;
          int vzdialenost;

      }
      private int dajVzdialenost(String zMes,String doMes){
          int vysledok;
          List<Vzdialen> vzd = new ArrayList<>();
          Vzdialen v =new Vzdialen();
          v.zMesta="Kosice";
          v.doMesta="Poprad";
          v.vzdialenost=102*2;
          vzd.add(v);
          Vzdialen v2 =new Vzdialen();
          v2.zMesta="Trencin";
          v2.doMesta="Kosice";
          v2.vzdialenost=323*2;
          vzd.add(v2);
          for (int i = 0; i <vzd.size() ; i++) {
              System.out.println("Vzdialenost "+vzd.get(i).vzdialenost);
              if(zMes.equals(vzd.get(i).zMesta)&& doMes.equals(vzd.get(i).doMesta)){
                  System.out.println("som dnu "+vzd.get(i).vzdialenost);
                  vysledok=vzd.get(i).vzdialenost;
                  System.out.println("vysledok "+vysledok);
                  return vysledok;
              }
              if(zMes.equals(vzd.get(i).doMesta)&& doMes.equals(vzd.get(i).zMesta)){
                  vysledok=vzd.get(i).vzdialenost;
                  return vysledok;
              }

          }
          return -1;

      }
    private Double cenaCestovne;
      private void updateCestovne() {
          try {
              String zMesta = editZmesta.getText().toString();
              String doMesta= editDoMesta.getText().toString();
              System.out.println("doMesta a z mesta " + doMesta+" "+ zMesta);
              double cestovne = dajVzdialenost(zMesta,doMesta)*0.2;
              System.out.println("Cestovne" + cestovne);
              lblCestovne.setText(new DecimalFormat("#.##").format(cestovne)+"");
              cenaCestovne=(cestovne);
              //lblFahrenheit.setText(new DecimalFormat("#.##").format(fahrenheit));
          } catch (NumberFormatException e) {
              Toast
                      .makeText(this, "Please enter a number", Toast.LENGTH_LONG)
                      .show();
          }
      }

/*
      private void updateSpolu() {
          try {
              int pausal =Integer.parseInt(editPausal.getText().toString());
              int cestovne= Integer.parseInt(lblCestovne.getText().toString());
              double stravne= Integer.parseInt(lblStravne.getText().toString());
              double suma = pausal+stravne+cestovne;

              lblSumaSpolu.setText(new DecimalFormat("#.##").format(suma));
          } catch (NumberFormatException e) {
              Toast
                      .makeText(this, "Please enter a number", Toast.LENGTH_LONG)
                      .show();
          }
      }
    */

/*    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        saveZapas();
        outState.putSerializable(ZAPAS_BUNDLE_KEY, zapas);
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_zapas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.deleteTaskMenu) {
            vymazZapas();
           // zapasyDao.delete(zapas);
           // ignoreSaveOnFinish = true;
            finish();
            return true;
        }
        if (id == android.R.id.home) {
            /* osetrime sipku */
            //ignoreSaveOnFinish = true;
            //finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
