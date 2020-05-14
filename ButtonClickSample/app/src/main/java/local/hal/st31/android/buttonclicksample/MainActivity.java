package local.hal.st31.android.buttonclicksample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * ST31 Androidサンプル０２　ボタンクリック
 *
 * メインアクテヴィティクラス
 *
 * @author ohs70394
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btClick = findViewById(R.id.btClick);
        ButtonClickListener listener = new ButtonClickListener();
        btClick.setOnClickListener(listener);
    }
    /**
     * ボタンが押されたときの処理が記述されたメンバクラス
     */
    private class ButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            EditText input = findViewById(R.id.etName);
            String inputStr = input.getText().toString();

            TextView output = findViewById(R.id.tvOutput);
            output.setText(inputStr + "さん、す。て。き！！");
        }
    }
}
