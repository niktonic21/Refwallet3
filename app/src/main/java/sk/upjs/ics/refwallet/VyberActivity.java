package sk.upjs.ics.refwallet;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class VyberActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vyber);
    }

    public void vyberZoznam(View view) {
        Toast.makeText(this, "Zoznam zápasov", Toast.LENGTH_SHORT)
                .show();
        Intent intent = new Intent(this, ZoznamActivity.class);
       // intent.putExtra("meal", "breakfast");
        startActivity(intent);
    }
    public void vyberStadion(View view) {
        Toast.makeText(this, "Nájdi štadión", Toast.LENGTH_SHORT)
                .show();
        Intent intent = new Intent(this, StadionActivity.class);
        // intent.putExtra("meal", "breakfast");
        startActivity(intent);
    }
    public void vyberStatistiky(View view) {
        Toast.makeText(this, "Štatistiky", Toast.LENGTH_SHORT)
                .show();
        Intent intent = new Intent(this, StatistikyActivity.class);
        // intent.putExtra("meal", "breakfast");
        startActivity(intent);
    }
    public void vyberFoto(View view) {
        Toast.makeText(this, "Pridaj foto", Toast.LENGTH_SHORT)
                .show();
        Intent intent = new Intent(this, FotoActivity.class);
        // intent.putExtra("meal", "breakfast");
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vyber, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
