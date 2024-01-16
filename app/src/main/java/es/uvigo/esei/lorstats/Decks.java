package es.uvigo.esei.lorstats;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Decks {
    private ArrayList<Deck> decks;
    private File savePath;
    private Context c;
    public Decks(Context c){
        decks = new ArrayList<>();
        savePath = c.getFilesDir();
        this.c=c;
    }

    public void addDeck(Deck d){
        decks.add(d);
        try{
            FileOutputStream fileout= this.c.openFileOutput("decks.txt", Context.MODE_APPEND | MODE_PRIVATE);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fileout));
            bw.write(d.getNameD());
            bw.write(" ");
            for (int i=0;i<d.getCards().size();i++){
                bw.write(d.getCards().get(i));
                if(i!=d.getCards().size()-1){
                    bw.write(",");
                }
            }
            bw.newLine();
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadDecks(){
        FileInputStream file = null;
        try {
            file = c.openFileInput( "decks.txt" );
            BufferedReader reader = new BufferedReader( new InputStreamReader( file ) );
            String line;
            String name;
            ArrayList<String> cards;
            Deck d;
            while((line = reader.readLine())!=null){
                Log.i("deck",line);
                name=getNameDeck(line);
                cards=getCardsDeck(line);
                d= new Deck(name,cards);
                decks.add(d);
            }
            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText( c, "File not found", Toast.LENGTH_LONG ).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteDeck(String dName){

        try {
            FileInputStream file = c.openFileInput( "decks.txt" );
            FileOutputStream fileO =c.openFileOutput("myTempFile.txt",Context.MODE_PRIVATE);
            BufferedReader reader = new BufferedReader( new InputStreamReader( file ) );
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fileO));
            String line;
            String name;
            ArrayList<String> cards;
            Deck d;
            while((line = reader.readLine())!=null){
                writer.write(line);
            }
            reader.close();
            writer.close();
            FileInputStream file2 = c.openFileInput( "myTempFile.txt" );
            FileOutputStream fileO2 =c.openFileOutput("decks.txt",Context.MODE_PRIVATE);
            BufferedReader reader2 = new BufferedReader( new InputStreamReader( file2 ) );
            BufferedWriter writer2 = new BufferedWriter(new OutputStreamWriter(fileO2));

            while((line = reader2.readLine())!=null){
                name=getNameDeck(line);
                if(!name.equals(dName)){
                    writer2.write(line);
                    writer2.newLine();
                }
                else{
                    cards=getCardsDeck(line);
                    d= new Deck(name,cards);
                    decks.remove(d);
                }

            }
            reader2.close();
            writer2.close();
            c.deleteFile("myTempFile.txt");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText( c, "File not found", Toast.LENGTH_LONG ).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public String getNameDeck(String line){
        String[] aux = line.split(" ");
        return aux[0];
    }

    public ArrayList<String> getCardsDeck(String line){
            String[] aux = line.split(" ");
            String cards = aux[1];
            ArrayList<String> toret = new ArrayList<>();
            String[] aux2 = cards.split(",");
            for (int i = 0 ; i<aux2.length;i++){
                toret.add(aux2[i]);
            }
            return toret;

    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }
}
