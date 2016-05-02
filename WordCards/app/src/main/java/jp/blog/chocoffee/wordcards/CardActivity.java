package jp.blog.chocoffee.wordcards;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class CardActivity extends Activity {
    private ArrayList<Card> ary = new ArrayList<>();
    private ArrayList<Card> ranAry = new ArrayList<>();
    private int pos;
    private int visible = 0;
    private void dispOneCard() {
        Card tmp = ary.get(pos);
        TextView txt1 = (TextView)findViewById(R.id.txtEng);
        txt1.setText(tmp.getEnglish());
        TextView txt2 = (TextView)findViewById(R.id.txtJap);
        txt2.setText(tmp.getJapanese());
        txt2.setVisibility(View.INVISIBLE);
    }
    private void dispOneCardReverse() {
        Card tmp = ary.get(pos);
        TextView txt1 = (TextView)findViewById(R.id.txtEng);
        txt1.setText(tmp.getJapanese());
        TextView txt2 = (TextView)findViewById(R.id.txtJap);
        txt2.setText(tmp.getEnglish());
        txt2.setVisibility(View.INVISIBLE);
    }

    private int mode = 0;
    private int lan = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        Intent intent = getIntent();
        mode = intent.getIntExtra("mode", 0);
        lan = intent.getIntExtra("Language", 0);

        pos = 0;

        CardSQLiteOpenHelper helper = new CardSQLiteOpenHelper(this);
        ary = helper.getAllCard();
        if(ary.size() <= 1){
            ImageButton btnNext = (ImageButton)findViewById(R.id.btnNext);
            btnNext.setEnabled(false);
        }
        if(mode == 123) {
            modeRandom();
        }
        ImageButton btnPrev = (ImageButton)findViewById(R.id.btnPrev);
        btnPrev.setEnabled(false);

        if(lan == 111) {
            dispOneCard();
        }else {
            dispOneCardReverse();
        }
        nowState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card, menu);
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

    public void onPrevClickAction(View v){
        pos--;
        visible = 0;
        if(lan == 111) {
            dispOneCard();
        }else {
            dispOneCardReverse();
        }
        ImageButton btnPrev = (ImageButton)findViewById(R.id.btnPrev);
        ImageButton btnNext = (ImageButton)findViewById(R.id.btnNext);
        ImageButton btnShowAns = (ImageButton)findViewById(R.id.btnAns);
        TextView txt2 = (TextView)findViewById(R.id.txtJap);
        txt2.setVisibility(View.INVISIBLE);
//        btnShowAns.setText("SHOW ANSWER");
        btnShowAns.setImageResource(R.drawable.show_answer);
        if(pos == 0){
            btnPrev.setEnabled(false);
        }else {
            btnPrev.setEnabled(true);
        }
        if(pos == ary.size() - 1){
            btnNext.setEnabled(false);
        }else {
            btnNext.setEnabled(true);
        }
        nowState();
    }

    public void onNextClickAction(View v){
        pos++;
        visible = 0;
        if(lan == 111) {
            dispOneCard();
        }else {
            dispOneCardReverse();
        }
        ImageButton btnNext = (ImageButton)findViewById(R.id.btnNext);
        ImageButton btnPrev = (ImageButton)findViewById(R.id.btnPrev);
        ImageButton btnShowAns = (ImageButton)findViewById(R.id.btnAns);
        TextView txt2 = (TextView)findViewById(R.id.txtJap);
        txt2.setVisibility(View.INVISIBLE);
//        btnShowAns.setText("SHOW ANSWER");
        btnShowAns.setImageResource(R.drawable.show_answer);
        if(pos == ary.size() - 1){
            btnNext.setEnabled(false);
        }else {
            btnNext.setEnabled(true);
        }
        if(pos == 0){
            btnPrev.setEnabled(false);
        }else {
            btnPrev.setEnabled(true);
        }
        nowState();
    }

    public void onShowAnsClickAction(View v){
        TextView txt2 = (TextView)findViewById(R.id.txtJap);
        ImageButton btnShowAns = (ImageButton)findViewById(R.id.btnAns);
        visible++;
        if(visible % 2 == 1){
            txt2.setVisibility(View.VISIBLE);
//            btnShowAns.setText("HIDE ANSWER");
            btnShowAns.setImageResource(R.drawable.hide_answer);
        }else {
            txt2.setVisibility(View.INVISIBLE);
//            btnShowAns.setText("SHOW ANSWER");
            btnShowAns.setImageResource(R.drawable.show_answer);
        }
    }

    public void onFinishClickAction(View v){
        finish();
        Toast.makeText(CardActivity.this, "お疲れ様でした", Toast.LENGTH_SHORT).show();
    }

    public void nowState() {
        TextView txtNowState = (TextView)findViewById(R.id.txtNowState);
        txtNowState.setText((pos + 1) + "ページ / 全" + ary.size() + "ページ中");
    }

    public void modeRandom() {
        Random r = new Random();
        for (int i = 0; i < ary.size(); i++){
            int ran = r.nextInt(ary.size());
            ranAry.add(ary.get(ran));
            ary.remove(ran);
        }
        for(int j = 0; j < ranAry.size();j++){
            ary.add(ranAry.get(j));
        }
    }
}
