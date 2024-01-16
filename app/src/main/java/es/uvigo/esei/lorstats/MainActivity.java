package es.uvigo.esei.lorstats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    public WebView wView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        wView = (WebView) this.findViewById( R.id.wView);
        WebSettings webSettings = wView.getSettings();
        webSettings.setJavaScriptEnabled( true );
        wView.setWebViewClient( new WebViewClient() );
        wView.loadUrl("file:///android_asset/galeria.html");




    }
    @Override
    protected void onPause(){
        super.onPause();
        wView.stopLoading();

    }
    @Override
    protected void onResume(){
        super.onResume();
        wView.loadUrl("file:///android_asset/galeria.html");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem menu) {
        boolean toret = false;
        switch (menu.getItemId()) {
            case R.id.op_cards:
                Intent iCards = new Intent(this, CardsActivity.class);
                this.startActivity(iCards);
                toret = true;
                this.finish();
                break;
            case R.id.op_stats:
                Intent iStats = new Intent(this, StatsActivity.class);
                this.startActivity(iStats);
                toret = true;
                this.finish();
                break;
        }
        return toret;
    }


}


