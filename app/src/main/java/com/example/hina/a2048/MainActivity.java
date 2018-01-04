package com.example.hina.a2048;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{

    public int matrix[][] = new int[4][4];
    public ImageView tiles[][] = new ImageView[4][4];
    public HashMap<Integer, Boolean> hashMap = new HashMap<Integer, Boolean>();
    public String hashMapValues;

    public int score;
    public boolean hasWon;
    SharedPreferences preferences;

    public Button buttonMenu;
    public GestureDetectorCompat gestureDetector;
    public TextView textViewScore;
    public TextView textViewBest;
    public GridLayout gridLayout;

    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gestureDetector = new GestureDetectorCompat(this, this);

        buttonMenu = (Button) findViewById(R.id.buttonMenu);
        textViewScore = (TextView) findViewById(R.id.textViewScore);
        textViewBest = (TextView) findViewById(R.id.textViewBest);
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        buttonMenu.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                showWarning();
            }
        });

        //initialisation du tableau d'ImageView
        tiles[0][0] = (ImageView) findViewById(R.id.imageView00);
        tiles[0][1] = (ImageView) findViewById(R.id.imageView01);
        tiles[0][2] = (ImageView) findViewById(R.id.imageView02);
        tiles[0][3] = (ImageView) findViewById(R.id.imageView03);
        tiles[1][0] = (ImageView) findViewById(R.id.imageView10);
        tiles[1][1] = (ImageView) findViewById(R.id.imageView11);
        tiles[1][2] = (ImageView) findViewById(R.id.imageView12);
        tiles[1][3] = (ImageView) findViewById(R.id.imageView13);
        tiles[2][0] = (ImageView) findViewById(R.id.imageView20);
        tiles[2][1] = (ImageView) findViewById(R.id.imageView21);
        tiles[2][2] = (ImageView) findViewById(R.id.imageView22);
        tiles[2][3] = (ImageView) findViewById(R.id.imageView23);
        tiles[3][0] = (ImageView) findViewById(R.id.imageView30);
        tiles[3][1] = (ImageView) findViewById(R.id.imageView31);
        tiles[3][2] = (ImageView) findViewById(R.id.imageView32);
        tiles[3][3] = (ImageView) findViewById(R.id.imageView33);

        score = 0; //au début le score est à zéro
        hasWon = false; //le joueur n'a pas encore gagné
        textViewScore.setText(String.valueOf(score));

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int highscore = preferences.getInt("highscore", 0); //on récupère le highscore
        hashMapValues = preferences.getString("values", getString(R.string.defValuesHashMap)); //on récupère les éléments découverts ou non
        textViewBest.setText(String.valueOf(highscore));

        instantiateHashMap(); //on remplit la hashmap avec les éléments découverts ou non
        System.out.println(showHashMap()); //debug

        addRandomTile();
        addRandomTile();
        afficheMatrice(); //debug

    }

    //affiche le contenu de la matrice dans le Logcat (debug)
    public void afficheMatrice(){
        for (int i = 0; i < 4; i++){
            String print = "";
            for (int j = 0; j < 4; j++){
                print += matrix[i][j];
            }
            Log.e(String.valueOf(i), print);
        }
    }

    //passe à l'activité passée en paramètre
    public void changeActivity(Class newActivity){
        Intent intent = new Intent(this, newActivity);
        startActivity(intent);
    }

    //génénère un nombre aléatoire
    public int randomNumber(int min, int max){
        Random r = new Random();
        return r.nextInt(max - min) + min;
    }

    //ajoute une tuile à une position aléatoire de la grille
    public void addRandomTile(){
        int x = randomNumber(0,4);
        int y = randomNumber(0,4);
        if (isTileEmpty(x, y)){
            matrix[x][y] = 2;
            tiles[x][y].setBackgroundResource(R.drawable.h);
        } else {
            addRandomTile();
        }
        updateGrid();
    }

    //check si la tuile passée en paramètre est vide
    public boolean isTileEmpty(int x, int y){
        if (matrix[x][y] == 0){
            return true;
        } else {
            return false;
        }
    }

    //check si il y a des tuiles vides
    public boolean isThereEmptyTiles(){
        for (int x = 0; x < 4; x++){
            for (int y = 0; y < 4; y++){
                if (matrix[x][y] == 0){
                    Log.e("Is There Empty Tiles ?", "Yes");
                    return true;
                }
            }
        }
        Log.e("Is There Empty Tiles ?", "No");
        return false;
    }

    //update l'affichaque de la grille pour chaque case de la matrice : si 2 on met l'image H, si 4 on met l'image He, ect...
    public void updateGrid(){
        for(int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (matrix[i][j] == 0){
                    tiles[i][j].setBackgroundResource(R.drawable.basic);
                } else if (matrix[i][j] == 2){
                    tiles[i][j].setBackgroundResource(R.drawable.h);
                } else if (matrix[i][j] == 4){
                    tiles[i][j].setBackgroundResource(R.drawable.he);
                } else if (matrix[i][j] == 8){
                    tiles[i][j].setBackgroundResource(R.drawable.li);
                } else if (matrix[i][j] == 16){
                    tiles[i][j].setBackgroundResource(R.drawable.be);
                } else if (matrix[i][j] == 32){
                    tiles[i][j].setBackgroundResource(R.drawable.b);
                } else if (matrix[i][j] == 64){
                    tiles[i][j].setBackgroundResource(R.drawable.c);
                } else if (matrix[i][j] == 128){
                    tiles[i][j].setBackgroundResource(R.drawable.n);
                } else if (matrix[i][j] == 256){
                    tiles[i][j].setBackgroundResource(R.drawable.o);
                } else if (matrix[i][j] == 512){
                    tiles[i][j].setBackgroundResource(R.drawable.f);
                } else if (matrix[i][j] == 1024){
                    tiles[i][j].setBackgroundResource(R.drawable.ne);
                } else if (matrix[i][j] == 2048){
                    tiles[i][j].setBackgroundResource(R.drawable.na);
                } else if (matrix[i][j] == 4096){
                    tiles[i][j].setBackgroundResource(R.drawable.mg);
                } else if (matrix[i][j] == 8192){
                    tiles[i][j].setBackgroundResource(R.drawable.al);
                } else if (matrix[i][j] == 16384){
                    tiles[i][j].setBackgroundResource(R.drawable.si);
                } else if (matrix[i][j] == 32768){
                    tiles[i][j].setBackgroundResource(R.drawable.p);
                } else if (matrix[i][j] == 65536){
                    tiles[i][j].setBackgroundResource(R.drawable.s);
                } else if (matrix[i][j] == 131072){
                    tiles[i][j].setBackgroundResource(R.drawable.cl);
                }
                /*
                switch(matrix[i][j]){
                    case 0 : tiles[i][j].setBackgroundResource(R.drawable.basic);
                    case 2 : tiles[i][j].setBackgroundResource(R.drawable.h);
                    case 4 : tiles[i][j].setBackgroundResource(R.drawable.he);
                    case 8 : tiles[i][j].setBackgroundResource(R.drawable.li);
                }
                */
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    //lorsque l'utilisateur swipe sur l'écran, vérifie dans quel sens va le swipe (gauche, droite, haut, bas)
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
        boolean result = false;
        try {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onRightSwipe();
                    } else {
                        onLeftSwipe();
                    }
                    result = true;
                }
            }
            else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    onDownSwipe();
                } else {
                    onUpSwipe();
                }
                result = true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    //gère le swipe vers la gauche, puis affiche une tuile 2 (H) aléatoirement sur la grille si il y a des tuiles vides, puis update l'affichaque de la grille et le meilleur score, et vérifie si le joueur a gagné ou non (si il n'a pas déjà gagné)
    public void onLeftSwipe(){
        Log.e("Swipe", "Left!");
        for (int x = 0; x < 4 ; x++){
            for (int y = 3; y > 0; y--){
                if (isTileEmpty(x, y-1)){
                    matrix[x][y-1] = matrix[x][y];
                    matrix[x][y] = 0;
                } else if (matrix[x][y] == matrix[x][y-1]){
                    matrix[x][y-1] += matrix[x][y];
                    updateScore(matrix[x][y-1]);
                    matrix[x][y] = 0;
                    if(!isInHashMap(matrix[x][y-1])){
                        showNewElement();
                        changeValue(matrix[x][y-1]);
                    }
                }
            }
        }
        afficheMatrice();
        if (isThereEmptyTiles()){
            addRandomTile();
        }
        updateGrid();
        updateHighscore(score);
        checkIfLose();
        if (!hasWon){
            checkIfWon();
        }
    }

    //gère le swipe vers la droite, puis affiche une tuile 2 (H) aléatoirement sur la grille si il y a des tuiles vides, puis update l'affichaque de la grille et le meilleur score, et vérifie si le joueur a gagné ou non (si il n'a pas déjà gagné)
    public void onRightSwipe(){
        Log.e("Swipe", "Right!");
        for (int x = 0; x < 4; x++){
            for (int y = 0; y < 3; y++){
                if (isTileEmpty(x, y+1)){
                    matrix[x][y+1] = matrix[x][y];
                    matrix[x][y] = 0;
                } else if (matrix[x][y] == matrix[x][y+1]){
                    matrix[x][y+1] += matrix[x][y];
                    updateScore(matrix[x][y+1]);
                    matrix[x][y] = 0;
                    if(!isInHashMap(matrix[x][y+1])){
                        showNewElement();
                        changeValue(matrix[x][y+1]);
                    }
                }
            }
        }
        afficheMatrice();
        if (isThereEmptyTiles()){
            addRandomTile();
        }
        updateGrid();
        updateHighscore(score);
        checkIfLose();
        if (!hasWon){
            checkIfWon();
        }
    }

    //gère le swipe vers le haut, puis affiche une tuile 2 (H) aléatoirement sur la grille si il y a des tuiles vides, puis update l'affichaque de la grille et le meilleur score, et vérifie si le joueur a gagné ou non (si il n'a pas déjà gagné)
    public void onUpSwipe(){
        Log.e("Swipe", "Up!");
        for (int y = 0; y < 4; y++){
            for (int x = 3; x > 0; x--){
                if (isTileEmpty(x-1, y)){
                    matrix[x-1][y] = matrix[x][y];
                    matrix[x][y] = 0;
                } else if (matrix[x][y] == matrix[x-1][y]){
                    matrix[x-1][y] += matrix[x][y];
                    updateScore(matrix[x-1][y]);
                    matrix[x][y] = 0;
                    if(!isInHashMap(matrix[x-1][y])){
                        showNewElement();
                        changeValue(matrix[x-1][y]);
                    }
                }
            }
        }
        afficheMatrice();
        if (isThereEmptyTiles()){
            addRandomTile();
        }
        updateGrid();
        updateHighscore(score);
        checkIfLose();
        if (!hasWon){
            checkIfWon();
        }
    }

    //gère le swipe vers le bas, puis affiche une tuile 2 (H) aléatoirement sur la grille si il y a des tuiles vides, puis update l'affichaque de la grille et le meilleur score, et vérifie si le joueur a gagné ou non (si il n'a pas déjà gagné)
    public void onDownSwipe(){
        Log.e("Swipe", "Down!");
        for (int y = 0; y < 4; y++){
            for (int x = 0; x < 3; x++){
                if (isTileEmpty(x+1, y)){
                    matrix[x+1][y] = matrix[x][y];
                    matrix[x][y] = 0;
                } else if (matrix[x][y] == matrix[x+1][y]){
                    matrix[x+1][y] += matrix[x][y];
                    updateScore(matrix[x+1][y]);
                    matrix[x][y] = 0;
                    if(!isInHashMap(matrix[x+1][y])){
                        showNewElement();
                        changeValue(matrix[x+1][y]);
                    }
                }
            }
        }
        afficheMatrice();
        if (isThereEmptyTiles()){
            addRandomTile();
        }
        updateGrid();
        updateHighscore(score);
        checkIfLose();
        if (!hasWon){
            checkIfWon();
        }
    }

    //ajoute des points lorsque deux tuiles fusionnent et update le score affiché
    public void updateScore(int tile){
        if (tile == 2){
            score += 0;
        } else if (tile == 4){
            score += 4;
        } else if (tile == 8){
            score += 16;
        } else if (tile == 16){
            score += 48;
        } else if (tile == 32){
            score += 128;
        } else if (tile == 64){
            score += 320;
        } else if (tile == 128){
            score += 768;
        } else if (tile == 256){
            score += 1792;
        } else if (tile == 512){
            score += 4096;
        } else if (tile == 1024){
            score += 9216;
        } else if (tile == 2048){
            score += 20480;
        } else if (tile == 4096){
            score += 45056;
        } else if (tile == 8192){
            score += 98304;
        } else if (tile == 16384){
            score += 212992;
        } else if (tile == 32768){
            score += 458752;
        } else if (tile == 65536){
            score += 983040;
        } else if (tile == 131072){
            score += 2097125;
        }
        textViewScore.setText(String.valueOf(score));
    }

    //vérifie que le joueur a gagné, c'est à dire si la tuile 2048 (Na) se trouve sur la grille
    public void checkIfWon(){
        for (int x = 0; x < 4; x++){
            for (int y = 0; y < 4; y++){
                if (matrix[x][y] == 2048 && !hasWon){
                    Log.e("A", "gagné");
                    hasWon = true;
                    gameWon();
                }
            }
        }
    }

    //vérifie si le joueur a perdu, c'est à dire si plus aucune tuile ne peut être fusionnée avec une autre
    public void checkIfLose(){
        boolean check = false;
        if (!isThereEmptyTiles()){
            check = true;
            loop:
            for (int y = 0; y < 4; y++){
                for (int x = 0; x < 4; x++){
                    if (y != 3) {
                        if (x != 3) {
                            if (matrix[x][y] == matrix[x + 1][y]) {
                                check = false;
                                break loop;
                            }
                        }
                        if (matrix[x][y] == matrix[x][y + 1]) {
                            check = false;
                            break loop;
                        }
                    } else {
                        if (x != 3){
                            if (matrix[x][y] == matrix[x + 1][y]){
                                check = false;
                                break loop;
                            }
                        }
                    }
                }
            }
        }
        if (check){
            gameLose();
        }
    }

    //affiche une alertdialog annonçant la victoire et sauvegarde les éléments découverts
    public void gameWon(){
        saveHashMap();
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Victory !");
        alertDialog.setMessage("You won ! Do you want to continue or restart a game ?");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.cancel();
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "New Game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeActivity(MainActivity.class);
            }
        });
        alertDialog.show();
    }

    //affiche une alertdialog annonçant la défaite et sauvegarde les éléments découverts
    public void gameLose(){
        saveHashMap();
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Defeat...");
        alertDialog.setMessage("You lose ! Do you want to restart a game ?");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeActivity(MainActivity.class);
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeActivity(HomeActivity.class);
            }
        });
        alertDialog.show();
    }

    //met à jour le highscore dans SharedPreferences
    public void updateHighscore(int score){
        if (Integer.parseInt(textViewBest.getText().toString()) < score){
            textViewBest.setText(String.valueOf(score));
            preferences.edit().putInt("highscore", score).apply();
        }
    }

    //instancie la hashmap selon le String rangé dans SharedPreferences contenant les éléments découverts ou non
    public void instantiateHashMap(){
        String[] data = hashMapValues.split("/");
        int i = 0;
        while (i < data.length){
            hashMap.put(Integer.parseInt(data[i]), Boolean.valueOf(data[i+1]));
            i += 2;
        }
    }

    //Affiche le contenu de la hashmap sous forme de String
    public String showHashMap(){
        String s = "";
        for(Map.Entry<Integer, Boolean> entry : hashMap.entrySet()){
            Integer key = entry.getKey();
            Boolean value = entry.getValue();
            s += String.valueOf(key) + "/" + String.valueOf(value) + "/";
        }
        return s;
    }

    //vérifie qu'un élément a été découvert ou non
    public boolean isInHashMap(int value){
        return Boolean.valueOf(hashMap.get(value));
    }

    //affiche une alertdialog lorsqu'un élément est découvert, et passe cet élément à true dans la hashmap
    public void showNewElement(){
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("New element discovered !");
        alertDialog.setMessage("You discovered a new element ! Check the collection to learn more about it.");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }

    //passe à true un élément découvert
    public void changeValue(int key){
        hashMap.put(key, true);
    }

    //sauvegarde les éléments découverts
    public void saveHashMap(){
        hashMapValues = showHashMap();
        preferences.edit().putString("values", hashMapValues).apply();
    }

    //affiche une alertDialog prévenant que l'on perd la progression si l'on retourne au menu
    public void showWarning(){
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Warning !");
        alertDialog.setMessage("If you return to the menu, you'll lose your current game. Do you want to return to the menu anyway ?");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeActivity(HomeActivity.class);
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }

    //on sauvegarde les éléments découverts
    @Override
    protected void onStop(){
        super.onStop();
        saveHashMap();
    }

    //fonctions inutilisées mais à implémenter
    @Override
    public boolean onDown(MotionEvent e){
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e){}

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY){
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e){

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e){
        return false;
    }



}
