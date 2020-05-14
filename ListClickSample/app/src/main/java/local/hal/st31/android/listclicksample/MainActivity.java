package local.hal.st31.android.listclicksample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvMenu = findViewById(R.id.lvMenu);
        lvMenu.setOnItemClickListener(new ListItemClickListener());
    }
    /**
     * リストが選択されたときの処理が記述されたメンバクラス
     */
    private class ListItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view,int position, long id){
            String item = (String) parent.getItemAtPosition(position);

//            TextView tvText = (TextView) view;
//            String item = tvText.getText().toString();

            String show = "あなたが選んだ定食" + item;
            Toast.makeText(getApplicationContext(),show,Toast.LENGTH_SHORT).show();
        }
    }
}
