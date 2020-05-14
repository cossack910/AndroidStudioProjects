package local.hal.st31.android.itarticlecollection70394;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

public class FalseDispDialogFragment extends androidx.fragment.app.DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Activity activity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("通信に失敗しました。");
        builder.setPositiveButton("OK",new DialogButtonClickListener());
        AlertDialog dialog = builder.create();
        return dialog;
    }

    private class DialogButtonClickListener implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which){
            Activity parent = getActivity();
        }
    }
}
