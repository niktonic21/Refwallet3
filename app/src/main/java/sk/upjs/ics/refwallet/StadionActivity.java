package sk.upjs.ics.refwallet;

import android.app.ActionBar;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.internal.CameraUpdateParcelable;

import java.util.ArrayList;
import java.util.List;

public class StadionActivity extends ActionBarActivity {

    final List<Stadionik> stadiony = new ArrayList<>();
    GoogleMap mapa;
    Spinner spinner;
    Double markLong;
    Double markLati;
    String markTitle;
    int cislo=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stadion);

        SupportMapFragment smap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapa = smap.getMap();
        mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraPosition cp = mapa.getCameraPosition();
        vytvorStadiony();
        // mapa.setMyLocationEnabled(true);
        //mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(48.73,19.85),6.0f));
        //float zoomik = mapa.getCameraPosition().zoom;
        System.out.println("Pred saveInsa" + stadiony.get(2));

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<Stadionik> dataAdapter = new ArrayAdapter<Stadionik>(this,
                android.R.layout.simple_spinner_item, stadiony);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        if(savedInstanceState!=null) {
            System.out.println("V saveInsa " + stadiony.get(2));
            final double lat =savedInstanceState.getDouble("lat");
           final double lon = savedInstanceState.getDouble("lon");
           final float zoom= savedInstanceState.getFloat("zoom");
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    mapa.clear();
                    if (cislo==0) {
                        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(lon, lat), zoom));
                        cislo=1;
                        System.out.println("poloha " + lon+" lat "+lat+" zoom "+zoom);
                        System.out.println("Vyber ma napisat" + stadiony.get(2)+" cislo "+cislo);
                    } else{
                        System.out.println("V else na vyber "+stadiony.get(2));
                        mapa.addMarker(new MarkerOptions().position(
                                new LatLng(stadiony.get((int) spinner.getSelectedItemId()).xLong,
                                        stadiony.get((int) spinner.getSelectedItemId()).yLati))
                                .title(stadiony.get((int) spinner.getSelectedItemId()).nazov));
                        markLati=stadiony.get((int) spinner.getSelectedItemId()).yLati;
                        markLong=stadiony.get((int) spinner.getSelectedItemId()).xLong;
                        markTitle=stadiony.get((int) spinner.getSelectedItemId()).nazov;
                        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(stadiony.get((int) spinner.getSelectedItemId()).xLong,
                                        stadiony.get((int) spinner.getSelectedItemId()).yLati),
                                12.0f));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    return;
                }
            });

        }else{
            System.out.println("V else "+stadiony.get(2));
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    mapa.clear();
                    if (stadiony.get((int) spinner.getSelectedItemId()).nazov.equals("Vyber")) {
                        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(stadiony.get((int) spinner.getSelectedItemId()).xLong,
                                        stadiony.get((int) spinner.getSelectedItemId()).yLati),
                                6.0f));
                        System.out.println("Vyber ma napisat" +
                                ""+stadiony.get(2));
                    } else{
                        System.out.println("V else na vyber "+stadiony.get(2));
                        mapa.addMarker(new MarkerOptions().position(
                                new LatLng(stadiony.get((int) spinner.getSelectedItemId()).xLong,
                                        stadiony.get((int) spinner.getSelectedItemId()).yLati))
                                .title(stadiony.get((int) spinner.getSelectedItemId()).nazov));
                        markLati=stadiony.get((int) spinner.getSelectedItemId()).yLati;
                        markLong=stadiony.get((int) spinner.getSelectedItemId()).xLong;
                        markTitle=stadiony.get((int) spinner.getSelectedItemId()).nazov;
                        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(stadiony.get((int) spinner.getSelectedItemId()).xLong,
                                        stadiony.get((int) spinner.getSelectedItemId()).yLati),
                                12.0f));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    return;
                }
            });
        }
    }

    private void vytvorStadiony() {
        Stadionik st = new Stadionik();
        st.nazov="Vyber";
        st.xLong=48.73;
        st.yLati=19.85;
        stadiony.add(st);
        Stadionik st1 = new Stadionik();
        st1.nazov="Steel arena KE";
        st1.xLong=48.7153270000;
        st1.yLati=21.2481042000;
        stadiony.add(st1);
        Stadionik st2 = new Stadionik();
        st2.nazov="Ice arena PO";
        st2.xLong=48.9876257000;
        st2.yLati=21.2305301000;
        stadiony.add(st2);
        Stadionik st3 = new Stadionik();
        st3.nazov="Kamzik arena PP";
        st3.xLong=49.0606297000;
        st3.yLati=20.3110768000;
        stadiony.add(st3);
        Stadionik st4 = new Stadionik();
        st4.nazov="Baran arena BB";
        st4.xLong=48.7340064000;
        st4.yLati=19.1540246000;
        stadiony.add(st4);
        Stadionik st5 = new Stadionik();
        st5.nazov="Zvolen arena ZV";
        st5.xLong=48.5662928000;
        st5.yLati=19.1211553000;
        stadiony.add(st5);

        Stadionik st6 = new Stadionik();
        st6.nazov="Trencin arena TN";
        st6.xLong=48.9612331000;
        st6.yLati=18.1591943000;
        stadiony.add(st6);
    }

    public class Stadionik{
        String nazov;
        double xLong;
        double yLati;

        @Override
        public String toString() {
            return nazov;
        }
    }

    public void mojaPoz(View viev) {
        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapa.clear();
        // Getting GoogleMap object from the fragment
        mapa = fm.getMap();

        // Enabling MyLocation Layer of Google Map
       // mapa.setMyLocationEnabled(true);

        // Getting LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Getting Current Location
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            // Getting latitude of the current location
            double latitude = location.getLatitude();

            // Getting longitude of the current location
            double longitude = location.getLongitude();

            // Creating a LatLng object for the current location
            LatLng latLng = new LatLng(latitude, longitude);

            mapa.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mapa.animateCamera(CameraUpdateFactory.zoomTo(12.0f));

            mapa.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title("Vaša poloha"));
            //markLati=latitude;
            //markLong=longitude;
            //markTitle="Vaša poloha";
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stadion, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle, PersistableBundle outPersistentState) {
        bundle.putDouble("lat", mapa.getCameraPosition().target.latitude);
        bundle.putDouble("lon", mapa.getCameraPosition().target.longitude);
        bundle.putFloat("zoom", mapa.getCameraPosition().zoom);
        System.out.print("Bundle"+mapa.getCameraPosition().target.latitude);
       // bundle.putDouble("markLati",markLati);
       // bundle.putDouble("markerLong",markLong);
        //bundle.putString("markTitle",markTitle);
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

        return super.onOptionsItemSelected(item);
    }
}
