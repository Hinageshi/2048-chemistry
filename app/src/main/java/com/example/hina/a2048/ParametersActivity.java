package com.example.hina.a2048;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ParametersActivity extends AppCompatActivity {

    Button buttonResetHighscore, buttonCredits;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameters);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        //reset les Preferences du highscore et des éléments découverts ou non
        buttonResetHighscore = (Button) findViewById(R.id.buttonResetHighscore);
        buttonResetHighscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.edit().putInt("highscore", 0).apply();
                preferences.edit().putString("values", getString(R.string.defValuesHashMap)).apply();
                showResetConfirmation();
            }
        });
        buttonCredits = (Button) findViewById(R.id.buttonCredits);
        buttonCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(CreditsActivity.class);
            }
        });
    }

    //permet de passer à l'activité passée en paramètre
    public void changeActivity(Class newActivity){
        Intent intent = new Intent(this, newActivity);
        startActivity(intent);
    }

    //affiche une alertdialog confirmant le reset
    public void showResetConfirmation(){
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Reset done");
        alertDialog.setMessage("Data has sucessfully been reset.");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }
}
