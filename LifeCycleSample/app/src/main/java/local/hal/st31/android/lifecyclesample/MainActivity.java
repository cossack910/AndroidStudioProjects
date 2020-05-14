package local.hal.st31.android.lifecyclesample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btNext = findViewById(R.id.btNext);
        btNext.setOnClickListener(new ButtonClickListener());
    }

    @Override
    protected void onStart(){
        Log.i("LifeCycleSample","Main onStart() called.");
        super.onStart();
    }

    @Override
    protected void onRestart(){
        Log.i("LifeCycleSample", "Main onRestart() called");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i("LifeCycleSample", "Main onResume() called.");
        super.onResume();
    }

    @Override
    protected void onPause(){
        Log.i("LifeCycleSample","Main onPause() called.");
        super.onPause();
    }

    @Override
    protected void onStop(){
        Log.i("LifeCycleSample","Main onStop() called.");
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        Log.i("LifeCycleSample","Main onDestroy() called.");
        super.onDestroy();
    }

    /**
     * ボタンが押されたときの処理が記述されたメンバクラス。
     */

    private class ButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            Intent intent = new Intent(getApplicationContext(),SubActivity.class);
            startActivity(intent);
        }
    }

}
