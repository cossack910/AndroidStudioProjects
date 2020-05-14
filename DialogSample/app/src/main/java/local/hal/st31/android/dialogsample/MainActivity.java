package local.hal.st31.android.dialogsample;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showSimpleDialog(View view){
        SimpleDialogFragment dialog = new SimpleDialogFragment();
        FragmentManager manager = getSupportFragmentManager();
        dialog.show(manager,"SimpleDialogFragment");
    }

    public void showDialog(View view){
        FullDialogFragment dialog = new FullDialogFragment();
        FragmentManager manager = getSupportFragmentManager();
        dialog.show(manager,"FullDialogFragment");
    }

    public void showMsgDialog(View view){
        EditText etMsg = findViewById(R.id.etMsg);
        String msg = etMsg.getText().toString();
        Bundle extras = new Bundle();
        extras.putString("msg",msg);
        MsgDialogFragment dialog = new MsgDialogFragment();
        dialog.setArguments(extras);
        FragmentManager manager = getSupportFragmentManager();
        dialog.show(manager,"MsgDialogFragment");
    }

    public void showDatePickerDialog(View view){
        Calendar cal = Calendar.getInstance();
        int nowYear = cal.get(Calendar.YEAR);
        int nowMonth = cal.get(Calendar.MONTH);
        int nowDayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, new DatePickerDialogDateSetListener(), nowYear, nowMonth, nowDayOfMonth);
        dialog.show();

    }

    public void showTimePickerDialog(View view){
        TimePickerDialog dialog = new TimePickerDialog(MainActivity.this, new TimePickerDialogTimeSetListener(),0,0,true);
        dialog.show();
    }

    private class DatePickerDialogDateSetListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view,int year,int month,int dayOfMonth){
            String msg = "日付選択ダイアログ：" + year + "年" + (month + 1) + "月" + dayOfMonth + "日";
            Toast.makeText(getApplicationContext(), msg,Toast.LENGTH_SHORT).show();
        }
    }

    private class TimePickerDialogTimeSetListener implements TimePickerDialog.OnTimeSetListener{
        @Override
        public void onTimeSet(TimePicker view,int hourOfDay, int minute){
            String msg = "時間選択ダイアログ：" + hourOfDay + "時" + minute + "分";
            Toast.makeText(getApplicationContext(), msg,Toast.LENGTH_SHORT).show();
        }
    }
}
