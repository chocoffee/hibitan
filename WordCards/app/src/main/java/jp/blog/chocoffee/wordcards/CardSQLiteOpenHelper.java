package jp.blog.chocoffee.wordcards;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by guest on 16/01/14.
 */
public class CardSQLiteOpenHelper extends SQLiteOpenHelper {

    private ArrayList<Card> ary = new ArrayList<>();

    public CardSQLiteOpenHelper(Context context){
        super(context, "CARD_DB", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE CARD(_id INTEGER PRIMARY KEY AUTOINCREMENT, english TEXT, japanese TEXT)");
        db.execSQL("INSERT INTO CARD VALUES(1, 'apple', 'りんご')");
        db.execSQL("INSERT INTO CARD VALUES(2, 'banana', 'バナナ')");
        db.execSQL("INSERT INTO CARD VALUES(3, 'lemon', 'レモン')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2){

    }

    public ArrayList<Card> getAllCard() {
        ary.clear();
        SQLiteDatabase database = getReadableDatabase();
        if (database == null) return null;

        String[] column = new String[]{"japanese", "english", "_id"};
        Cursor cur = database.query("CARD", column, null, null, null, null, null);
        while(cur.moveToNext()){
            Card tmp = new Card(cur.getString(0), cur.getString(1), cur.getInt(2));
            ary.add(tmp);
        }
        cur.close();
        return ary;
    }

    public boolean insertCard(Card src) {
        long ret = -1;

        SQLiteDatabase db = getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("japanese", src.getJapanese());
            values.put("english", src.getEnglish());

            ret = db.insert("CARD", null, values);

        }finally {
            db.close();
        }
        return ret != -1;
    }

    public void deleteCardbyId(int id){
        SQLiteDatabase db = getWritableDatabase();
        try{
            String sid = String.valueOf(id);
            db.delete("CARD", "_id=?", new String[]{sid});
        }finally {
            db.close();
        }
    }

    public boolean updateCard(Card newCard) {
        long ret;
        SQLiteDatabase db = getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("japanese", newCard.getJapanese());
            values.put("english", newCard.getEnglish());

            String sid = String.valueOf(newCard.getId());

            ret = db.update("CARD", values, "_id=?", new String[]{sid});
        } finally {
            db.close();
        }
        return ret != -1;
    }

    public Card findCardById(int id){
        Card tmp = null;
        SQLiteDatabase db = getReadableDatabase();
        try{
            String sid = String.valueOf(id);
            String[] column = new String[]{"japanese", "english", "_id"};
            Cursor cur = db.query("CARD", column, "_id=?", new String[]{sid}, null, null, null);
            if(cur.moveToNext()){
                tmp = new Card(cur.getString(0), cur.getString(1), cur.getInt(2));
            }
            cur.close();
        }finally {
            db.close();
        }
        return tmp;
    }
}
