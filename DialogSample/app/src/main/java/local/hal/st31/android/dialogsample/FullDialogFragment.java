package local.hal.st31.android.dialogsample;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

public class FullDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Activity activity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("通常ダイアログ");
        builder.setMessage("よろしいですか？");
        builder.setPositiveButton("OK", new DialogButtonClickListener());
        builder.setNeutralButton("??", new DialogButtonClickListener());
        builder.setNegativeButton("NG", new DialogButtonClickListener());
        AlertDialog dialog = builder.create();
        return dialog;
    }

    private class DialogButtonClickListener implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which){
            Activity parent = getActivity();
            String msg = "";
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    msg = "通常ダイアログ：OKが選択されました。";
                    break;
                case DialogInterface.BUTTON_NEUTRAL:
                    msg = "通常ダイアログ：??が選択されました。";
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    msg = "通常ダイアログ：NGが選択されました。";
                    break;
            }
            Toast.makeText(parent, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
