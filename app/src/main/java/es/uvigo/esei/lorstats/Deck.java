package es.uvigo.esei.lorstats;

import java.util.ArrayList;

public class Deck {

    private String nameD;
    private ArrayList<String> cards;

    public Deck (String name, ArrayList<String> cards){
        this.nameD = name;
        this.cards=cards;
    }

    public String getNameD() {
        return nameD;
    }

    public ArrayList<String> getCards() {
        return cards;
    }
}
