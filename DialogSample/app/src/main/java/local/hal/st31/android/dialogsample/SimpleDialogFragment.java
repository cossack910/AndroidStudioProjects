package local.hal.st31.android.dialogsample;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
//import android.support.v7.app.AlertDialog;
import android.widget.Toast;

public class SimpleDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Activity activity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("ダメでしょ！");
        builder.setPositiveButton("OK",new DialogButtonClickListener());
        AlertDialog dialog = builder.create();
        return dialog;
    }

    private class DialogButtonClickListener implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which){
            Activity parent = getActivity();
            Toast.makeText(parent, "シンプルなダイアログ：OKが選択されました。",Toast.LENGTH_SHORT).show();
        }
    }
}
