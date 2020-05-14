package local.hal.st31.android.prefmemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvPref = findViewById(R.id.lvPref);
        List<String> prefList = createPrefectureList();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, prefList);
        lvPref.setAdapter(adapter);
        lvPref.setOnItemClickListener(new ListItemClickListener());
    }
    /**
     * 都道府県のリストを生成するメソッド
     */
    private  List<String> createPrefectureList(){
        List<String> prefList = new ArrayList<>();
        prefList.add("北海道");
        prefList.add("青森県");
        prefList.add("岩手県");
        prefList.add("宮城県");
        prefList.add("秋田県");
        prefList.add("山形県");
        prefList.add("福島県");
        prefList.add("茨城県");
        prefList.add("栃木県");
        prefList.add("群馬県");
        prefList.add("埼玉県");
        prefList.add("千葉県");
        prefList.add("東京都");
        prefList.add("神奈川県");
        prefList.add("新潟県");
        prefList.add("富山県");
        prefList.add("石川県");
        prefList.add("福井県");
        prefList.add("山梨県");
        prefList.add("長野県");
        prefList.add("岐阜県");
        prefList.add("静岡県");
        prefList.add("愛知県");
        prefList.add("三重県");
        prefList.add("滋賀県");
        prefList.add("京都府");
        prefList.add("大阪府");
        prefList.add("兵庫県");
        prefList.add("奈良県");
        prefList.add("和歌山県");
        prefList.add("鳥取県");
        prefList.add("島根県");
        prefList.add("岡山県");
        prefList.add("広島県");
        prefList.add("山口県");
        prefList.add("徳島県");
        prefList.add("香川県");
        prefList.add("愛媛県");
        prefList.add("高知県");
        prefList.add("福岡県");
        prefList.add("佐賀県");
        prefList.add("長崎県");
        prefList.add("熊本県");
        prefList.add("大分県");
        prefList.add("宮崎県");
        prefList.add("鹿児島県");
        prefList.add("沖縄県");
        return prefList;
    }
    /**
     * リストが選択されたときの処理が記述されたメンバクラス。
     */
    private class ListItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent,View view,int position,long id){
            String prefName = (String) parent.getItemAtPosition(position);

            Intent intent = new Intent(getApplicationContext(),PrefEditActivity.class);
            intent.putExtra("selectedPrefNo",position);
            intent.putExtra("selectedPrefName",prefName);
            startActivity(intent);
        }
    }

}
