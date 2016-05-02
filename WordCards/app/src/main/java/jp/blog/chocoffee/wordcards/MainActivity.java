package jp.blog.chocoffee.wordcards;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void onStartClickAction(View v){
        CheckBox cbx = (CheckBox)findViewById(R.id.cbx);
        RadioButton radioLan = (RadioButton)findViewById(R.id.japEng);
        Intent intent = new Intent(MainActivity.this, CardActivity.class);
        if(cbx.isChecked()){
            intent.putExtra("mode", 123);
        }else {
            intent.putExtra("mode", 456);
        }
        if(radioLan.isChecked()){
            intent.putExtra("Language", 111);
        }else {
            intent.putExtra("Language", 555);
        }

        startActivity(intent);
    }

    public void onEditClickAction(View v){
        Intent intent = new Intent(MainActivity.this, EditActivity.class);  //  とりあえず画面遷移だけ投げる
        intent.putExtra("mode", "add");
        startActivity(intent);
    }

    public void onListClickAction(View v){
        Intent intent = new Intent(MainActivity.this, CardListActivity.class);
        startActivity(intent);
    }

    public void onExitClickAction(View v){
        finish();
    }
}
