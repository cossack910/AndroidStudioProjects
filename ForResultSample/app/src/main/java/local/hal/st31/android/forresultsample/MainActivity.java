package local.hal.st31.android.forresultsample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /**
     * リクエストコード
     * 星で評価する画面への遷移。
     */
    private static final int RATING_EVALUATE = 1;
    /**
     * リクエストコード１
     * スライダーで評価する画面への遷移
     */
    private static final int SEEK_EVALUATE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btNext1 = findViewById(R.id.btNext1);
        btNext1.setOnClickListener(new ButtonClickListener());
        Button btNext2 = findViewById(R.id.btNext2);
        btNext2.setOnClickListener(new ButtonClickListener());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        EditText etName = findViewById(R.id.etName);
        String name = etName.getText().toString();

        TextView output = findViewById(R.id.tvOutput);
        if(resultCode == RESULT_OK){
            int rate = data.getIntExtra("rate",-1);
            String msg = "正しく評価されていません。もう一度お願いします。";
            if (rate != -1) {
                if(requestCode == RATING_EVALUATE){
                   msg = name + "さんの評価は☆" + rate + "コです。";
                }else if(requestCode == SEEK_EVALUATE){
                    msg = name + "さんの評価は" + rate + "点です。";
                }
            }
            output.setText(msg);
        }
    }
    /**
     * ボタンが押されたときの処理が記述されたメンバクラス。
     */
    private class ButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            EditText etName = findViewById(R.id.etName);
            String name = etName.getText().toString();
            if(name.equals("")){
                Toast.makeText(getApplicationContext(),"名前を入力してください。",Toast.LENGTH_SHORT).show();
            }
            else
            {
                int id = view.getId();
                switch(id){
                    case R.id.btNext1:
                        Intent intent1 = new Intent(getApplicationContext(),RatingEvaluateActivity.class);
                        intent1.putExtra("name",name);
                        startActivityForResult(intent1,RATING_EVALUATE);
                        break;
                    case R.id.btNext2:
                        Intent intent2 = new Intent(getApplicationContext(),SeekEvaluateActivity.class);
                        intent2.putExtra("name",name);
                        startActivityForResult(intent2,SEEK_EVALUATE);
                        break;
                }
            }

        }
    }
}
