package local.hal.st31.android.dialogsample;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

public class MsgDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Activity activity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("入力メッセージを表示します。");
        builder.setPositiveButton("OK", new MsgDialogButtonClickListener());
        AlertDialog dialog = builder.create();
        return dialog;
    }

    private class MsgDialogButtonClickListener implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which){
            Bundle extras = getArguments();
            String msg = "";
            if(extras != null){
                msg = extras.getString("msg");
            }
            Activity parent = getActivity();
            Toast.makeText(parent, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
