package es.uvigo.esei.lorstats;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class StatsActivity extends AppCompatActivity {
    public Stats stats;
    public EditText tx;
    public String save;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        Button bt = this.findViewById(R.id.opSearch);
        tx = (EditText) this.findViewById(R.id.nameShow);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchPlayer();
            }
        });
    }

    public void searchPlayer(){
        try {
            this.stats=new Stats(this,this,this.tx.getText().toString());
            URL url = url = new URL("https://euw1.api.riotgames.com/lol/league/v4/challengerleagues/by-queue/RANKED_SOLO_5x5?api_key=RGAPI-81b7aefd-6098-4a23-b16d-a180c90b6c90");
            this.stats.execute(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onPause(){
        super.onPause();
        save = tx.getText().toString();
        tx.setText("");
    }
    @Override
    protected void onResume(){
        super.onResume();
        tx.setText(save);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.getMenuInflater().inflate(R.menu.menu_stats, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menu) {
        boolean toret = false;
        switch (menu.getItemId()) {
            case R.id.opInicio:
                Intent iHome = new Intent(this, MainActivity.class);
                this.startActivity(iHome);
                toret = true;
                this.finish();
                break;
            case R.id.opCards:
                Intent iCards = new Intent(this, CardsActivity.class);
                this.startActivity(iCards);
                toret = true;
                this.finish();
                break;
        }
        return toret;
    }
}