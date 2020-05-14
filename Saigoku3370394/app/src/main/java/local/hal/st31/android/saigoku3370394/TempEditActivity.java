package local.hal.st31.android.saigoku3370394;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class TempEditActivity extends AppCompatActivity {

    private int _selectedTempleNo = 0;

    private String _selectedTempleName = "";

    private DatabaseHelper _helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_edit);

        Intent intent = getIntent();
        _selectedTempleNo = intent.getIntExtra("selectedTempleNo",0);
        _selectedTempleName = intent.getStringExtra("selectedTempleName");

        _helper = new DatabaseHelper(getApplicationContext());

        TextView tvTemp = findViewById(R.id.tvTemp);
        tvTemp.setText(_selectedTempleName);

        SQLiteDatabase db = _helper.getWritableDatabase();
        String[] content = new String[5];
        content = DataAccess.findContentByPK(db, _selectedTempleNo);
        EditText etHonzon = findViewById(R.id.etHonzon);
        EditText etShushi = findViewById(R.id.etShushi);
        EditText etAddress = findViewById(R.id.etAddress);
        EditText etUrl = findViewById(R.id.etUrl);
        EditText etNote = findViewById(R.id.etNote);
        etHonzon.setText(content[0]);
        etShushi.setText(content[1]);
        etAddress.setText(content[2]);
        etUrl.setText(content[3]);
        etNote.setText(content[4]);
    }

    @Override
    protected void onDestroy(){
        _helper.close();
        super.onDestroy();
    }

    public void onSaveButtonClick(View view){
        EditText etHonzon = findViewById(R.id.etHonzon);
        EditText etShushi = findViewById(R.id.etShushi);
        EditText etAddress = findViewById(R.id.etAddress);
        EditText etUrl = findViewById(R.id.etUrl);
        EditText etNote = findViewById(R.id.etNote);
        String honzon = etHonzon.getText().toString();
        String shushi = etShushi.getText().toString();
        String address = etAddress.getText().toString();
        String url = etUrl.getText().toString();
        String note = etNote.getText().toString();
        SQLiteDatabase db = _helper.getWritableDatabase();
        boolean exist = DataAccess.findRowByPK(db, _selectedTempleNo);
        if(exist){
            DataAccess.update(db, _selectedTempleNo, _selectedTempleName, honzon, shushi, address, url, note);
        }else{
            DataAccess.insert(db, _selectedTempleNo, _selectedTempleName, honzon, shushi, address, url, note);
        }
        finish();
    }


}
