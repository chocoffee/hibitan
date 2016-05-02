package jp.blog.chocoffee.wordcards;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class EditActivity extends Activity {
    private int id = -1;

    private boolean isExistWord(String english){
        boolean b = false;
        CardSQLiteOpenHelper sqLiteOpenHelper = new CardSQLiteOpenHelper(this);
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("CARD", new String[]{"english"}, "english = ?", new String[]{english}, null, null, null);
        if(cursor.getCount() >= 1){
            b = true;
        }
        sqLiteDatabase.close();
        sqLiteOpenHelper.close();

        return b;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        String mode = intent.getStringExtra("mode");

        ImageButton btn = (ImageButton)findViewById(R.id.btnAdd);
        ImageButton btnDelete = (ImageButton)findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new DeleteOnClickAction());

        if("update".equals(mode)){
            CardSQLiteOpenHelper helper = new CardSQLiteOpenHelper(this);
            Card tmp = helper.findCardById(id);

            EditText edtE = (EditText)findViewById(R.id.edtEng);
            EditText edtJ = (EditText)findViewById(R.id.edtJap);
            edtE.setText(tmp.getEnglish());
            edtJ.setText(tmp.getJapanese());

            btn.setOnClickListener(new UpdateOnClickAction());
            btn.setImageResource(R.drawable.update);
        }else{
            btn.setOnClickListener(new AddOnClickAction());
            btn.setImageResource(R.drawable.add);
            btnDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
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

    public void onCloseClickAction(View v){
        finish();
    }

    private class AddOnClickAction implements View.OnClickListener {
        @Override
        public void onClick(View v){
            EditText edtE = (EditText)findViewById(R.id.edtEng);
            EditText edtJ = (EditText)findViewById(R.id.edtJap);
            String english = edtE.getText().toString();
            String japanese = edtJ.getText().toString();

            Card newCard = new Card(japanese, english, -1);
            CardSQLiteOpenHelper helper = new CardSQLiteOpenHelper(EditActivity.this);

            if(isExistWord(english)) {
                Toast.makeText(EditActivity.this, "すでに存在する単語です", Toast.LENGTH_SHORT).show();
            }else if(english.length() == 0 || japanese.length() == 0){
                Toast.makeText(EditActivity.this, "文字を入力してください", Toast.LENGTH_SHORT).show();
            }else {
                helper.insertCard(newCard);
                Toast.makeText(EditActivity.this, "追加完了！", Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    }

    private class UpdateOnClickAction implements View.OnClickListener {
        @Override
        public void onClick(View v){
            EditText edtE = (EditText)findViewById(R.id.edtEng);
            EditText edtJ = (EditText)findViewById(R.id.edtJap);
            String english = edtE.getText().toString();
            String japanese = edtJ.getText().toString();

            Card newCard = new Card(japanese, english, id);
            CardSQLiteOpenHelper helper = new CardSQLiteOpenHelper(EditActivity.this);
            boolean ret = helper.updateCard(newCard);
            if (ret) {
                Toast.makeText(EditActivity.this, "編集成功！", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(EditActivity.this, "編集失敗！", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }

    private class DeleteOnClickAction implements View.OnClickListener {
        @Override
        public void onClick(View v){
            AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setTitle("確認");
            builder.setMessage("削除してよろしいですか？");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CardSQLiteOpenHelper helper = new CardSQLiteOpenHelper(EditActivity.this);
                    helper.deleteCardbyId(id);
                    Toast.makeText(EditActivity.this, "削除しました。" , Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

            builder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });

            builder.show();
        }
    }



}
