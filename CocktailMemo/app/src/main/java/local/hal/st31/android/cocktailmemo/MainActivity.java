package local.hal.st31.android.cocktailmemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int _cocktailId = -1;

    private String _cocktailName = "";


    private DatabaseHelper _helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new SaveButtonClickListener());

        ListView lvCocktail = findViewById(R.id.lvCocktail);
        lvCocktail.setOnItemClickListener(new ListItemClickListener());

        _helper = new DatabaseHelper(getApplicationContext());
    }
    @Override
    protected void onDestroy(){
        _helper.close();
        super.onDestroy();
    }
    private class SaveButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            EditText etNote = findViewById(R.id.etNote);
            String note = etNote.getText().toString();

            SQLiteDatabase db =_helper.getWritableDatabase();

            String sqlDelete = "DELETE FROM cocktailmemos WHERE _id = ?";
            SQLiteStatement stmt = db.compileStatement(sqlDelete);
            stmt.bindLong(1,_cocktailId);
            stmt.executeUpdateDelete();

            String sqlInsert = "INSERT INTO cocktailmemos (_id,name,note) VALUES (?,?,?)";

            stmt = db.compileStatement(sqlInsert);
            stmt.bindLong(1,_cocktailId);
            stmt.bindString(2,_cocktailName);
            stmt.bindString(3,note);
            stmt.executeInsert();

            TextView tvCocktailName = findViewById(R.id.tvCocktailName);
            Button btnSave = findViewById(R.id.btnSave);
            tvCocktailName.setText(getString(R.string.tv_name));
            etNote.setText("");
            btnSave.setEnabled(false);
        }

    }
    private class ListItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent,View view,int position,long id){
            _cocktailId =position;
            _cocktailName =(String) parent.getItemAtPosition(position);
            TextView tvCocktailName = findViewById(R.id.tvCocktailName);
            Button btnSave = findViewById(R.id.btnSave);
            tvCocktailName.setText(_cocktailName);
            btnSave.setEnabled(true);

            SQLiteDatabase db = _helper.getWritableDatabase();
            String sql = "SELECT * FROM cocktailmemos WHERE _id = " + _cocktailId;
            Cursor cursor = db.rawQuery(sql,null);
            String note ="";
            while (cursor.moveToNext()){
                int idxNote = cursor.getColumnIndex("note");
                note = cursor.getString(idxNote);
            }
            EditText etNote = findViewById(R.id.etNote);
            etNote.setText(note);
        }
    }
}
