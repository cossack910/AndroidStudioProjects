package local.hal.st31.android.lifecyclesample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SubActivity extends AppCompatActivity{
        @Override
        protected void onCreate(Bundle savedInstanceState){
            Log.i("LifeCycleSample","Sub onCreate() called.");
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sub);

            Button btPrevious = findViewById(R.id.btPrevious);
            btPrevious.setOnClickListener(new ButtonClickListener());
        }

        @Override
        protected void onStart(){
            Log.i("LifeCycleSample","Sub onStart() called.");
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
                finish();
            }
        }

}

