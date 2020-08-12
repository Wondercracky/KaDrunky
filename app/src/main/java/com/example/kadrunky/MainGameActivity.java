package com.example.kadrunky;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MainGameActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<Player> playerNamesList = new ArrayList<Player>();
    ArrayList<Question> questionList = new ArrayList<Question>();
    private TextView playerNameField, questionField, prisField, rundeField;
    private Button nextRoundButton, svar1Button, svar2Button, svar3Button, svar4Button;

    int roundCounter = 0;

    Animation anim = new AlphaAnimation(0.0f, 1.0f);
    Animation fadeIn = new AlphaAnimation(0, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        readQuestions();

        anim.setDuration(200);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);

        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(1000);

        playerNameField = findViewById(R.id.playerName_TextField);
        questionField = findViewById(R.id.question_TextField);
        prisField = findViewById(R.id.prisDrekke_TextField);
        rundeField = findViewById(R.id.rundeNummer_TextField);

        nextRoundButton = findViewById(R.id.nextRound_Button);
        nextRoundButton.setOnClickListener(this);
        svar1Button = findViewById(R.id.svar1_Button);
        svar1Button.setOnClickListener(this);
        svar2Button = findViewById(R.id.svar2_Button);
        svar2Button.setOnClickListener(this);
        svar3Button = findViewById(R.id.svar3_Button);
        svar3Button.setOnClickListener(this);
        svar4Button = findViewById(R.id.svar4_Button);
        svar4Button.setOnClickListener(this);

        playerNamesList = (ArrayList<Player>) getIntent().getSerializableExtra("playerList");

        startGame();

    }

    public String randomizePlayer() {
        for(int i = 0; i < playerNamesList.size(); i++) {
            if (!playerNamesList.get(i).taken) {
                playerNamesList.get(i).taken = true;
                return playerNamesList.get(i).name;
            }
        }
        resetPlayers();
        playerNamesList.get(0).taken = true;
        return playerNamesList.get(0).name;
    }
    public void resetPlayers() {
        for(int i = 0; i < playerNamesList.size(); i++) {
             playerNamesList.get(i).taken = false;
        }
        Collections.shuffle(playerNamesList);
    }
    public void startGame() {
        roundCounter = 1;
        Collections.shuffle(playerNamesList);
        playerNameField.setText(randomizePlayer());
        playerNameField.startAnimation(fadeIn);
        questionField.setText(questionList.get(roundCounter-1).question);
        questionField.startAnimation(fadeIn);
        prisField.setText(questionList.get(roundCounter-1).pris);
        checkQuestionKahoot();
    }
    public void readQuestions() {
        try {
            DataInputStream textFileStream = new DataInputStream(getAssets().open(String.format("questions.txt")));
            Scanner leser = new Scanner(textFileStream);
            while (leser.hasNextLine()) {
                String linje = leser.nextLine();
                String[] parts = linje.split(";");
                questionList.add(new Question(parts[0],
                        Boolean.parseBoolean(parts[1]),
                        Boolean.parseBoolean(parts[2]),
                        parts[3],
                        Boolean.parseBoolean(parts[4]),
                        Integer.parseInt(parts[5]),
                        parts[6],
                        parts[7],
                        parts[8],
                        parts[9],
                        Boolean.parseBoolean(parts[10]),
                        Boolean.parseBoolean(parts[11])));
            }
        } catch(Error | FileNotFoundException error) {
            System.out.print(error);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void checkQuestionKahoot() {
        if (questionList.get(roundCounter-1).kahoot) {
            svar1Button.setVisibility(View.VISIBLE);
            svar1Button.startAnimation(fadeIn);
            svar1Button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPurple));
            svar1Button.setText(questionList.get(roundCounter-1).alternativ1);
            svar2Button.setVisibility(View.VISIBLE);
            svar2Button.startAnimation(fadeIn);
            svar2Button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPurple));
            svar2Button.setText(questionList.get(roundCounter-1).alternativ2);
            svar3Button.setVisibility(View.VISIBLE);
            svar3Button.startAnimation(fadeIn);
            svar3Button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPurple));
            svar3Button.setText(questionList.get(roundCounter-1).alternativ3);
            svar4Button.setVisibility(View.VISIBLE);
            svar4Button.startAnimation(fadeIn);
            svar4Button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPurple));
            svar4Button.setText(questionList.get(roundCounter-1).alternativ4);
        } else {
            svar1Button.setVisibility(View.GONE);
            svar2Button.setVisibility(View.GONE);
            svar3Button.setVisibility(View.GONE);
            svar4Button.setVisibility(View.GONE);
        }
    }

    public void checkCorrect() {
        if (questionList.get(roundCounter-1).svarNummer == 1) {
            svar1Button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGreen));
        } else if (questionList.get(roundCounter-1).svarNummer == 2) {
            svar2Button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGreen));
        }else if (questionList.get(roundCounter-1).svarNummer == 3) {
            svar3Button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGreen));
        } else if (questionList.get(roundCounter-1).svarNummer == 4) {
            svar4Button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGreen));
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.nextRound_Button:
                if (questionList.size() == roundCounter) {
                    questionField.setText("Ferdig!");
                    break;
                } else {
                    roundCounter++;
                    if(questionList.get(roundCounter-1).alle) {
                        playerNameField.setText("Alle");
                        playerNameField.startAnimation(fadeIn);
                        questionField.setText(questionList.get(roundCounter-1).question);
                        questionField.startAnimation(fadeIn);
                        questionList.get(roundCounter-1).taken = true;
                        prisField.setText(questionList.get(roundCounter-1).pris);
                        prisField.setAnimation(fadeIn);
                        rundeField.setText("Runde " + roundCounter);
                        rundeField.setAnimation(fadeIn);
                        nextRoundButton.startAnimation(fadeIn);
                        break;
                    } else {
                        checkQuestionKahoot();
                        playerNameField.setText(randomizePlayer());
                        playerNameField.startAnimation(fadeIn);
                        questionField.setText(questionList.get(roundCounter-1).question);
                        questionField.startAnimation(fadeIn);
                        questionList.get(roundCounter-1).taken = true;
                        prisField.setText(questionList.get(roundCounter-1).pris);
                        prisField.setAnimation(fadeIn);
                        rundeField.setText("Runde " + roundCounter);
                        rundeField.setAnimation(fadeIn);
                        nextRoundButton.startAnimation(fadeIn);
                        break;
                    }
                }
            case R.id.svar1_Button:
                if(questionList.get(roundCounter-1).svarNummer == 1) {
                    svar1Button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorOrange));
                    svar1Button.startAnimation(anim);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            svar1Button.clearAnimation();
                            svar1Button.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        }
                    }, 2000); // Millisecond 1000 = 1 sec
                } else {
                    svar1Button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorOrange));
                    svar1Button.startAnimation(anim);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            svar1Button.clearAnimation();
                            svar1Button.setBackgroundColor(getResources().getColor(R.color.colorRed));
                            checkCorrect();
                        }
                    }, 2000); // Millisecond 1000 = 1 sec
                }
                break;

            case R.id.svar2_Button:
                if(questionList.get(roundCounter-1).svarNummer == 2) {
                    svar2Button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorOrange));
                    svar2Button.startAnimation(anim);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            svar2Button.clearAnimation();
                            svar2Button.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        }
                    }, 2000); // Millisecond 1000 = 1 sec
                } else {
                    svar2Button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorOrange));
                    svar2Button.startAnimation(anim);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            svar2Button.clearAnimation();
                            svar2Button.setBackgroundColor(getResources().getColor(R.color.colorRed));
                            checkCorrect();
                        }
                    }, 2000); // Millisecond 1000 = 1 sec
                }
                break;

            case R.id.svar3_Button:
                if(questionList.get(roundCounter-1).svarNummer == 3) {
                    svar3Button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorOrange));
                    svar3Button.startAnimation(anim);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            svar3Button.clearAnimation();
                            svar3Button.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        }
                    }, 2000); // Millisecond 1000 = 1 sec
                } else {
                    svar3Button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorOrange));
                    svar3Button.startAnimation(anim);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            svar3Button.clearAnimation();
                            svar3Button.setBackgroundColor(getResources().getColor(R.color.colorRed));
                            checkCorrect();
                        }
                    }, 2000); // Millisecond 1000 = 1 sec
                }
                break;

            case R.id.svar4_Button:
                if(questionList.get(roundCounter-1).svarNummer == 4) {
                    svar4Button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorOrange));
                    svar4Button.startAnimation(anim);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            svar4Button.clearAnimation();
                            svar4Button.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        }
                    }, 2000); // Millisecond 1000 = 1 sec
                } else {
                    svar4Button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorOrange));
                    svar4Button.startAnimation(anim);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            svar4Button.clearAnimation();
                            svar4Button.setBackgroundColor(getResources().getColor(R.color.colorRed));
                            checkCorrect();
                        }
                    }, 2000); // Millisecond 1000 = 1 sec
                }
                break;

            default:
                break;
        }
    }
    @Override
    public void onBackPressed() {

    }
}