package jp.blog.chocoffee.wordcards;

/**
 * Created by guest on 16/01/28.
 */
public class RowItem {
    private String eng;
    private String jap;

    public RowItem(String eng, String jap) {
        this.eng = eng;
        this.jap = jap;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public String getJap() {
        return jap;
    }

    public void setJap(String jap) {
        this.jap = jap;
    }
}

