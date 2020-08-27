package indiqueeganhe2019.com.indiqueeganhemobileag.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import indiqueeganhe2019.com.indiqueeganhemobileag.R;

public class SingleChoiceDialogFragment extends DialogFragment {
    int position = 0; //default select position

    public interface SingleChoiceListener{
        void onPositiveButtonClicked(String [] list,int position);
        void onNevativeButtonClicked();
    }

    SingleChoiceListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener= (SingleChoiceListener) context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString()+ "teste");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final String [] list= getActivity().getResources().getStringArray(R.array.listretorno);
        builder.setTitle("selecione o retorno")
                .setSingleChoiceItems(list, position, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        position=i;

                    }
                })
                .setPositiveButton("salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        mListener.onPositiveButtonClicked(list,position);

                    }
                })
                .setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        mListener.onNevativeButtonClicked();

                    }
                });


                return builder.create();

    }
}
