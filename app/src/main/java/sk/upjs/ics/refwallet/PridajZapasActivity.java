package sk.upjs.ics.refwallet;

import android.app.LoaderManager;
import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static sk.upjs.ics.refwallet.Defaults.NO_COOKIE;


public class PridajZapasActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks{
    public static final String ZAPAS_BUNDLE_KEY = "zapas";

    public static final String ZAPAS_ID_EXTRA = "zapasId";


    private EditText editCZ,editLiga,editDatum,editMiesto,editDomaci,editHostia,
            editHR1,editHR2,editCR1,editCR2,editInstruktor,editVideo,editPrichod,
            editOdchod,editZmesta,editDoMesta,editPausal;

    private TextView lblCestovne,lblStravne,lblSumaSpolu;
    private static final int NOTES_LOADER_ID = 0;
    private static final int INSERT_NOTE_TOKEN = 0;
    private static final int DELETE_NOTE_TOKEN = 0;
    private Zapas zapas;

    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;


    private final SensorEventListener mSensorListener = new SensorEventListener() {


        @Override
        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter
            if (mAccel > 2) {

                editCZ.setText("");
                editLiga.setText("");
                editDatum.setText("");
                editMiesto.setText("");
                editDomaci.setText("");
                editHostia.setText("");
                editHR1.setText("");
                editHR2.setText("");
                editCR1.setText("");
                editCR2.setText("");
                editInstruktor.setText("");
                editVideo.setText("");
                editPrichod.setText("");
                editOdchod.setText("");
                editZmesta.setText("");
                editDoMesta.setText("");
                editPausal.setText("");

            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pridaj_zapas);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

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

        editDoMesta.addTextChangedListener(new CestovneTextWatcher());
        editZmesta.addTextChangedListener(new CestovneTextWatcher());

        editOdchod.addTextChangedListener(new StravneTextWatcher());
        editPrichod.addTextChangedListener(new StravneTextWatcher());

        editPausal.addTextChangedListener(new SpoluTextWatcher());
        editDoMesta.addTextChangedListener(new SpoluTextWatcher());
        editZmesta.addTextChangedListener(new SpoluTextWatcher());
        editOdchod.addTextChangedListener(new SpoluTextWatcher());
        editPrichod.addTextChangedListener(new SpoluTextWatcher());

    }

    public void pridajZapas(View view) {
        String cislozap = editCZ.getText().toString();
        String liga = editLiga.getText().toString();
        String datum = editDatum.getText().toString();
        String timestamp=datum.substring(0,4)+"-"+datum.substring(5,7)+"-"+datum.substring(8,10);
        System.out.print(timestamp);
        String stadion = editMiesto.getText().toString();
        String domaci = editDomaci.getText().toString();
        String hostia = editHostia.getText().toString();
        String hr1 = editHR1.getText().toString();
        String hr2 = editHR2.getText().toString();
        String cr1 = editCR1.getText().toString();
        String cr2 = editCR2.getText().toString();
        String instruktor = editInstruktor.getText().toString();
        String video = editVideo.getText().toString();
        String odchod = editOdchod.getText().toString();
        String prichod = editPrichod.getText().toString();
        String zmesta = editZmesta.getText().toString();
        String domesta = editDoMesta.getText().toString();
        int pausal;
        if(editPausal.getText().toString().equals("")){
             pausal =0;
        }else {
             pausal = Integer.parseInt(editPausal.getText().toString());
        }
        double stravne = stravneCena;
        double cestovne=cenaCestovne;
        double spolu =cenaSpolu;
        insertIntoContentProvider(cislozap,liga,timestamp,stadion,domaci,hostia, hr1, hr2, cr1, cr2,
                instruktor, video, odchod, prichod, zmesta, domesta,pausal+"", stravne+"", cestovne+"", spolu+"");
        finish();
    }

    public void zrusZapas(View view){
        finish();
    }

    private void insertIntoContentProvider(String cislozap,String liga,String timestamp,String stadion,
                                           String domaci,String hostia,String hr1,String hr2,String cr1,String cr2,
                                           String instruktor,String video,String odchod,String prichod,String zmesta,String domesta,
                                           String pausal,String stravne,String cestovne,String spolu) {
        Uri uri = ZapasContentProvider.CONTENT_URI;
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

        AsyncQueryHandler insertHandler = new AsyncQueryHandler(getContentResolver()) {
            @Override
            protected void onInsertComplete(int token, Object cookie, Uri uri) {
                Toast.makeText(PridajZapasActivity.this, "Zápas bol pridaný", Toast.LENGTH_SHORT)
                        .show();
            }
        };

        insertHandler.startInsert(INSERT_NOTE_TOKEN, NO_COOKIE, uri, contentValues);
    }


    private class CestovneTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            updateCestovne();
        }

        @Override
        public void afterTextChanged(Editable editable) {
            updateCestovne();
        }    }

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
        return 0;

    }
    private Double cenaCestovne=0.00;
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
        } catch (Exception e) {
            //Toast.makeText(this, "Please enter a number", Toast.LENGTH_LONG).show();
        }
    }
    private class StravneTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            updateStravne();
        }

        @Override
        public void afterTextChanged(Editable editable) {
            updateStravne();
        }
    }
    private double stravneCena=0.00;
    private void updateStravne() {
        try {
            String odchod =editOdchod.getText().toString();
            String prichod= editPrichod.getText().toString();
            String delims ="[:]";
            String[] odchody=odchod.split(delims);
            String[] prichody=prichod.split(delims);
            int odchodVMIn=0;
            int prichodVMin=0;
            for(int i=0; i < odchody.length; i++){
                if(i==0){
                    odchodVMIn=Integer.parseInt(odchody[i])*60;
                    prichodVMin=Integer.parseInt(prichody[i])*60;
                }else {
                    odchodVMIn=odchodVMIn+Integer.parseInt(odchody[i]);
                    prichodVMin=prichodVMin+Integer.parseInt(prichody[i]);
                }
            }
            int stravne=0;
            if(odchodVMIn>prichodVMin){
                 stravne =1440-odchodVMIn; //1440 je pocet min za 24hodin
            }else {
                 stravne = prichodVMin-odchodVMIn;
            }
            System.out.println("Stravne" + stravne);
            if(stravne<300){
                stravneCena=0.0;
                lblStravne.setText(new DecimalFormat("#.##").format(stravneCena)+"");
                System.out.println("Stravne" +new DecimalFormat("#.##").format(stravneCena)+"");
            }
            if(stravne>=300){ //300 pocet min za 5 hodin
                stravneCena=4.20;
                lblStravne.setText(new DecimalFormat("#.##").format(stravneCena)+"");
                System.out.println("Stravne" +new DecimalFormat("#.##").format(stravneCena)+"");
            }
            if(stravne>=720){ //720 pocet min za 12 hodin
                stravneCena=6.30;
                lblStravne.setText(new DecimalFormat("#.##").format(stravneCena)+"");
                System.out.println("Stravne" +new DecimalFormat("#.##").format(stravneCena)+"");
            }
            if(stravne>=1080){ //1080 pocet min za 18 hodin
                stravneCena=9.80;
                lblStravne.setText(new DecimalFormat("#.##").format(stravneCena)+"");
                System.out.println("Stravne" +new DecimalFormat("#.##").format(stravneCena)+"");
            }
        } catch (Exception e) {
           // Toast.makeText(this, "Please enter a number", Toast.LENGTH_LONG).show();
        }
    }
    private class SpoluTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            updateSpolu();
        }

        @Override
        public void afterTextChanged(Editable editable) {
            updateSpolu();
        }
    }
        private double cenaSpolu = 0.00;

        private void updateSpolu() {
            try {
                cenaSpolu = Double.parseDouble(editPausal.getText().toString()) + cenaCestovne + stravneCena;
                System.out.println("Spolu" +new DecimalFormat("#.##").format(cenaSpolu)+"");
                lblSumaSpolu.setText(new DecimalFormat("#.##").format(cenaSpolu)+"");
            } catch (NumberFormatException e) {
                //Toast.makeText(this, "Please enter a number", Toast.LENGTH_LONG).show();
            }
        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pridaj_zapas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.pridaj_zapas) {
            String cislozap = editCZ.getText().toString();
            String liga = editLiga.getText().toString();
            String datum = editDatum.getText().toString();
            String timestamp=datum.substring(0,4)+"-"+datum.substring(5,7)+"-"+datum.substring(8,10);
            System.out.print(timestamp);
            String stadion = editMiesto.getText().toString();
            String domaci = editDomaci.getText().toString();
            String hostia = editHostia.getText().toString();
            String hr1 = editHR1.getText().toString();
            String hr2 = editHR2.getText().toString();
            String cr1 = editCR1.getText().toString();
            String cr2 = editCR2.getText().toString();
            String instruktor = editInstruktor.getText().toString();
            String video = editVideo.getText().toString();
            String odchod = editOdchod.getText().toString();
            String prichod = editPrichod.getText().toString();
            String zmesta = editZmesta.getText().toString();
            String domesta = editDoMesta.getText().toString();
            int pausal;
            if(editPausal.getText().toString().equals("")){
                pausal =0;
            }else {
                pausal = Integer.parseInt(editPausal.getText().toString());
            }
            double stravne = stravneCena;
            double cestovne=cenaCestovne;
            double spolu =cenaSpolu;
            insertIntoContentProvider(cislozap,liga,timestamp,stadion,domaci,hostia, hr1, hr2, cr1, cr2,
                    instruktor, video, odchod, prichod, zmesta, domesta,pausal+"", stravne+"", cestovne+"", spolu+"");
            finish();
            return true;
        }

        if (id == android.R.id.home) {
            //vytvorNovyNewZapas();

           // finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object o) {

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
