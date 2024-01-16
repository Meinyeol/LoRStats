package es.uvigo.esei.lorstats;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class CardsActivity extends AppCompatActivity {
    private ArrayList<String> decks;
    private ArrayAdapter<String> decksAdapter;
    private Decks decksC;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        ListView v = this.findViewById(R.id.deckList);
        decksC = new Decks(this);
        this.decks=new ArrayList<String>();
        this.decksAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item,this.decks);
        v.setAdapter(this.decksAdapter);
        Context c = this;
        this.registerForContextMenu(v);
        decksC.loadDecks();
        Iterator<Deck> i= decksC.getDecks().iterator();
        while(i.hasNext()){
            decksAdapter.add(i.next().getNameD());
        }
        Button button = (Button) findViewById(R.id.opAdd);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final EditText inputName = new EditText(c);
                final TextView tv1 = new TextView(c);
                final EditText inputCards = new EditText(c);
                final TextView tv2 = new TextView(c);
                tv1.setText("Deck Name");
                tv2.setText("Cards (Example: jinx,zed,yasuo...)");
                LinearLayout ll=new LinearLayout(c);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.addView(tv1);
                ll.addView(inputName);
                ll.addView(tv2);
                ll.addView(inputCards);
                AlertDialog.Builder builder = new AlertDialog.Builder( c );
                builder.setTitle( "Add Deck" );
                builder.setView(ll);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Deck deck = new Deck(inputName.getText().toString(),recorrerIn(inputCards.getText().toString()));
                        if(compareD(deck)){
                            decksC.addDeck(deck);
                            decksAdapter.add(inputName.getText().toString());
                        }else{
                            Toast t = new Toast(c);
                            t.setText("You can't add a deck with the same name of other deck");
                            t.show();
                        }

                    }
                });
                builder.show();
            }
        });
    }

    public ArrayList<String> recorrerIn(String cards){
        ArrayList<String> toret = new ArrayList<>();
        String[] aux = cards.split(",");
        for (int i = 0 ; i<aux.length;i++){
            toret.add(aux[i]);
        }
        return toret;
    }
    @Override
    public void onCreateContextMenu(ContextMenu context, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.deckList) {
            this.getMenuInflater().inflate( R.menu.context_menu, context );
            context.setHeaderTitle( R.string.app_name );

        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.getMenuInflater().inflate(R.menu.menu_cards, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menu)
    {
        boolean toret = false;
        switch( menu.getItemId() ) {
            case R.id.opinicio:
                Intent iHome = new Intent(this, MainActivity.class);
                this.startActivity(iHome);
                toret = true;
                this.finish();
                break;
            case R.id.opstats:
                Intent iStats = new Intent(this, StatsActivity.class);
                this.startActivity(iStats);
                toret = true;
                this.finish();
                break;
        }
        return toret;
    }

    public boolean onContextItemSelected(MenuItem item)
    {
        switch( item.getItemId() ) {
            case R.id.opRemoveM:
                int pos = ( (AdapterView.AdapterContextMenuInfo)
                        item.getMenuInfo() ).position;
                decksC.deleteDeck(decks.get(pos));
                decksAdapter.remove(decks.get(pos));
                break;

        }
        return true;
    }

    public boolean compareD(Deck d){
        Iterator<String> i = decks.iterator();

        while(i.hasNext()){
            if(d.getNameD().equals(i.next())){
                return false;
            }
        }
        return true;
    }
}
