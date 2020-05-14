package local.hal.st31.android.twobuttonsample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ST31 Androidサンプル０３　２コのボタン
 *
 * メインアクテビティクラス
 *
 * @author ohs70394
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btClickText = findViewById(R.id.btClickText);
        btClickText.setOnClickListener(new ButtonClickListener());
        Button btClickToast = findViewById(R.id.btClickToast);
        btClickToast.setOnClickListener(new ButtonClickListener());
    }
    /**
     * ボタンが押されたときの処理が記述されたメンバクラス
     */
    private class ButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            EditText etName = findViewById(R.id.etName);

            String inputStr = etName.getText().toString();
            String outputStr = inputStr + "さん、す・て・き！！";

            TextView tvOutput = findViewById(R.id.tvOutput);

            int id = view.getId();
            switch (id){
                case R.id.btClickText:
                    tvOutput.setText(outputStr);
                    break;
                case R.id.btClickToast:
                    tvOutput.setText("");
                    Context context = getApplicationContext();
                    Toast.makeText(context, outputStr, Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }
}
