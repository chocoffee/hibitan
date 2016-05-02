package jp.blog.chocoffee.wordcards;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CardListActivity extends ListActivity {    //  ListActivityを親クラスにする
    private RowItemAdapter adapter;

    class RowItemAdapter extends ArrayAdapter<Card>{
        RowItemAdapter(Context con) {
            super(con, R.layout.row_item);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            Card item = getItem(position);
            if(convertView == null){
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.row_item, null);
            }
            if(item != null){
                TextView txteng = (TextView)convertView.findViewById(R.id.txtEng);
                if(txteng != null){
                    txteng.setText(item.getEnglish());
                    Log.d("miyu", txteng.getText().toString());
                }
                TextView txtjap = (TextView)convertView.findViewById(R.id.txtJap);
                if(txtjap != null){
                    txtjap.setText(item.getJapanese());
                }
            }
            return convertView;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);
        CardSQLiteOpenHelper helper = new CardSQLiteOpenHelper(this);
        adapter = new RowItemAdapter(this);
        adapter.addAll(helper.getAllCard());
        setListAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CardSQLiteOpenHelper helper = new CardSQLiteOpenHelper(this);
        adapter.clear();
        adapter.addAll(helper.getAllCard());
        ((RowItemAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
        Card tmp = adapter.getItem(position);
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("mode", "update");
        intent.putExtra("id", tmp.getId());
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card_list, menu);
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

}
