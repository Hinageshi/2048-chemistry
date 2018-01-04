package com.example.hina.a2048;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class HomeActivity extends AppCompatActivity {

    ImageButton imageButtonParameters;
    Button buttonNewGame, buttonHowToPlay, buttonCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imageButtonParameters = (ImageButton) findViewById(R.id.imageButtonParameters);
        imageButtonParameters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(ParametersActivity.class);
            }
        });

        buttonNewGame = (Button) findViewById(R.id.buttonNewGame);
        buttonNewGame.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                changeActivity(MainActivity.class);
            }
        });

        buttonHowToPlay = (Button) findViewById(R.id.buttonHowToPlay);
        buttonHowToPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(HowToPlayActivity.class);
            }
        });

        buttonCollection = (Button) findViewById(R.id.buttonCollection);
        buttonCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(CollectionActivity.class);
            }
        });
    }

    //Permet de passer à l'activité passée en paramètres
    public void changeActivity(Class newActivity){
        Intent intent = new Intent(this, newActivity);
        startActivity(intent);
    }
}
