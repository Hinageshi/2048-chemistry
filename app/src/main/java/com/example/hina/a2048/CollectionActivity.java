package com.example.hina.a2048;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.Map;

public class CollectionActivity extends AppCompatActivity {

    private ImageButton imageButtonH, imageButtonHe, imageButtonLi, imageButtonBe, imageButtonB, imageButtonC, imageButtonN, imageButtonO, imageButtonF, imageButtonNe, imageButtonNa, imageButtonMg, imageButtonAl, imageButtonSi, imageButtonP, imageButtonS, imageButtonCl;
    private String hashMapValues;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        hashMapValues = preferences.getString("values", getString(R.string.defValuesHashMap)); //on récupère les éléments découverts ou non

        imageButtonH = (ImageButton) findViewById(R.id.imageButtonH);
        imageButtonH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo("Hydrogen", getString(R.string.HDescription));
            }
        });

        imageButtonHe = (ImageButton) findViewById(R.id.imageButtonHe);
        imageButtonHe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo("Helium", getString(R.string.HeDescription));
            }
        });

        imageButtonLi = (ImageButton) findViewById(R.id.imageButtonLi);
        imageButtonLi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo("Lithium", "Some trivia");
            }
        });

        imageButtonBe = (ImageButton) findViewById(R.id.imageButtonBe);
        imageButtonBe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo("Beryllium", "Some trivia");
            }
        });

        imageButtonB = (ImageButton) findViewById(R.id.imageButtonB);
        imageButtonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo("Boron", "Some trivia");
            }
        });

        imageButtonC = (ImageButton) findViewById(R.id.imageButtonC);
        imageButtonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo("Carbon", "Some trivia");
            }
        });

        imageButtonN = (ImageButton) findViewById(R.id.imageButtonN);
        imageButtonN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo("Nitrogen", "Some trivia");
            }
        });

        imageButtonO = (ImageButton) findViewById(R.id.imageButtonO);
        imageButtonO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo("Oxygen", "Some trivia");
            }
        });

        imageButtonF = (ImageButton) findViewById(R.id.imageButtonF);
        imageButtonF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo("Fluorine", "Some trivia");
            }
        });

        imageButtonNe = (ImageButton) findViewById(R.id.imageButtonNe);
        imageButtonNe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo("Neon", "Some trivia");
            }
        });

        imageButtonNa = (ImageButton) findViewById(R.id.imageButtonNa);
        imageButtonNa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo("Sodium", "Some trivia");
            }
        });

        imageButtonMg = (ImageButton) findViewById(R.id.imageButtonMg);
        imageButtonMg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo("Magnesium", "Some trivia");
            }
        });

        imageButtonAl = (ImageButton) findViewById(R.id.imageButtonAl);
        imageButtonAl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo("Aluminium", "Some trivia");
            }
        });

        imageButtonSi = (ImageButton) findViewById(R.id.imageButtonSi);
        imageButtonSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo("Silicon", "Some trivia");
            }
        });

        imageButtonP = (ImageButton) findViewById(R.id.imageButtonP);
        imageButtonP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo("Phosphorus", "Some trivia");
            }
        });

        imageButtonS = (ImageButton) findViewById(R.id.imageButtonS);
        imageButtonS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo("Sulfur", "Some trivia");
            }
        });

        imageButtonCl = (ImageButton) findViewById(R.id.imageButtonCl);
        imageButtonCl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo("Chlorine", "Some trivia");
            }
        });

        updateInfos();
    }

    //affiche une alertdialog avec le titre et le texte passé en paramètre
    public void showInfo(String elementName, String description){
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(elementName);
        alertDialog.setMessage(description);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }

    //permet de montrer quels éléments on a découverts ou non. le String contenant les éléments découverts ou non est sous la forme "2/true/4/false/8/false..."
    public void updateInfos(){
        String[] data = hashMapValues.split("/");
        for(int i = 0; i < data.length; i += 2){
            switch(Integer.valueOf(data[i])){
                case 2 : if(Boolean.valueOf(data[i+1])) imageButtonH.setBackground(getResources().getDrawable(R.drawable.h)); break;
                case 4 : if(Boolean.valueOf(data[i+1])) imageButtonHe.setBackground(getResources().getDrawable(R.drawable.he)); break;
                case 8 : if(Boolean.valueOf(data[i+1])) imageButtonLi.setBackground(getResources().getDrawable(R.drawable.li)); break;
                case 16 : if(Boolean.valueOf(data[i+1])) imageButtonBe.setBackground(getResources().getDrawable(R.drawable.be)); break;
                case 32 : if(Boolean.valueOf(data[i+1])) imageButtonB.setBackground(getResources().getDrawable(R.drawable.b)); break;
                case 64 : if(Boolean.valueOf(data[i+1])) imageButtonC.setBackground(getResources().getDrawable(R.drawable.c)); break;
                case 128 : if(Boolean.valueOf(data[i+1])) imageButtonN.setBackground(getResources().getDrawable(R.drawable.n)); break;
                case 256 : if(Boolean.valueOf(data[i+1])) imageButtonO.setBackground(getResources().getDrawable(R.drawable.o)); break;
                case 512 : if(Boolean.valueOf(data[i+1])) imageButtonF.setBackground(getResources().getDrawable(R.drawable.f)); break;
                case 1024 : if(Boolean.valueOf(data[i+1])) imageButtonNe.setBackground(getResources().getDrawable(R.drawable.ne)); break;
                case 2048 : if(Boolean.valueOf(data[i+1])) imageButtonNa.setBackground(getResources().getDrawable(R.drawable.na)); break;
                case 4096 : if(Boolean.valueOf(data[i+1])) imageButtonMg.setBackground(getResources().getDrawable(R.drawable.mg)); break;
                case 8192 : if(Boolean.valueOf(data[i+1])) imageButtonAl.setBackground(getResources().getDrawable(R.drawable.al)); break;
                case 16384 : if(Boolean.valueOf(data[i+1])) imageButtonSi.setBackground(getResources().getDrawable(R.drawable.si)); break;
                case 32768 : if(Boolean.valueOf(data[i+1])) imageButtonP.setBackground(getResources().getDrawable(R.drawable.p)); break;
                case 65536 : if(Boolean.valueOf(data[i+1])) imageButtonS.setBackground(getResources().getDrawable(R.drawable.s)); break;
                case 131072 : if(Boolean.valueOf(data[i+1])) imageButtonCl.setBackground(getResources().getDrawable(R.drawable.cl)); break;
            }
        }
    }

}
