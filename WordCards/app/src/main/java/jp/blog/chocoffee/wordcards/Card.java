package jp.blog.chocoffee.wordcards;

/**
 * Created by guest on 16/01/14.
 */
public class Card {
    private String japanese;
    private String english;
    private int id;

    public Card(String japanese, String english, int id){
        this.japanese = japanese;
        this.english = english;
        this.id = id;
    }

    public String getJapanese() {
        return japanese;
    }

    public String getEnglish() {
        return english;
    }
    public int getId() {
        return id;
    }
}

