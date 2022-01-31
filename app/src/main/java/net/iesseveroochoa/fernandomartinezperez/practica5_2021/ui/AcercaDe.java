package net.iesseveroochoa.fernandomartinezperez.practica5_2021.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import net.iesseveroochoa.fernandomartinezperez.practica5_2021.R;

public class AcercaDe extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        builder.setMessage("Practica 5 \nV1.0 \nFernando Martinez Perez \nLicencia cc \n(Creative Commons)")
                .setTitle(R.string.acercade)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }
}