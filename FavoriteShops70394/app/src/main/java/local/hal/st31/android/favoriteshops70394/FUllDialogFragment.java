package local.hal.st31.android.favoriteshops70394;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * ST31 評価課題
 *
 * 通常ダイアログクラス
 *
 * @author 70394
 */
public class FUllDialogFragment extends DialogFragment{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity activity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("確認！");
        builder.setMessage("このお店情報を削除してもよろしいですか？");
        builder.setPositiveButton("削除",new DialogButtonClickListener());
        builder.setNegativeButton("キャンセル",new DialogButtonClickListener());
        AlertDialog dialog = builder.create();
        return dialog;
    }

    /**
     * ダイアログのボタンが押されたときの処理が記述されたメンバクラス
     */
    public class DialogButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Activity parent = getActivity();
            String msg = "";
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    msg = "お店情報を削除しました。";
                    ShopEditActivity shopeditActivity = (ShopEditActivity) getActivity();
                    shopeditActivity.listDelete();
                    Toast.makeText(parent, msg, Toast.LENGTH_SHORT).show();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    }
}
