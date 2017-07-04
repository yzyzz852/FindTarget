package com.motyvacfdhtor.yzyzz.findpokemonv3;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;

    private TextView timeTXT,targetTXT,targetLeftTXT;
    private ImageView targetsBC,targetIcon,realTarget, plusSec;

    private int count = 10;
    private int forNextStage = 10;
    int clicks,clicksForShore, preveousCount;

    int x , y,r,g,b;
    float x1,y1;

    private CountDownTimer PREtimeris, GAMEtimeris, BONUStimeris;

    public long startTime = 31000;
    public long bonusTimeValue = 1000;
    public long BONUSTime;
    public final long PREinterval = 10 * 1000;

    private boolean isGAMEtimer,isBONUStimer = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        init();
        setLisiners();
        setFactorys();
        timers();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

    private void init(){
        targetTXT = (TextView) findViewById(R.id.targetTXT);
        targetLeftTXT=(TextView) findViewById(R.id.targLeftTXT);
        timeTXT = (TextView) findViewById(R.id.timeTXT);

        targetIcon = (ImageView) findViewById(R.id.targetIcon);
        targetsBC = (ImageView) findViewById(R.id.targetBC);
        realTarget = (ImageView) findViewById(R.id.mainTarget);
        plusSec = (ImageView) findViewById(R.id.plusTEN);

        targetLeftTXT.setText("Targets left: " + count);
    }

    private void setLisiners(){
        realTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               randomTargetPosition();
               randomTargetTXTColor();
               animations();
               isGameWin();

            }
        });
    }

    private void isGameWin(){
        clicks++;
        clicksForShore = clicks;
        count--;
        targetLeftTXT.setText("Targets left: " + count);
        if (count == 3){
            targetLeftTXT.setTextColor(Color.rgb(165, 255, 183));
        }if (count == 2){
            targetLeftTXT.setTextColor(Color.rgb(79, 255, 114));
        }if (count == 1){
            targetLeftTXT.setTextColor(Color.rgb(0, 255, 50));
        }

        if (0 >= count){
            if(isGAMEtimer == true){
                GAMEtimeris.cancel();
            }else{
                BONUStimeris.cancel();
            }

            targetLeftTXT.setText("You WIN");
            targetIcon.setVisibility(View.INVISIBLE);
            targetTXT.setVisibility(View.INVISIBLE);

            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
            alertBuilder.setMessage("You WIN! \n You found: " + clicks + " emojis");
            alertBuilder.setCancelable(false);
            alertBuilder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    targetLeftTXT.setVisibility(View.VISIBLE);
                    targetIcon.setVisibility(View.VISIBLE);
                    targetTXT.setVisibility(View.VISIBLE);
                    plusSec.setVisibility(View.VISIBLE);
                    realTarget.setVisibility(View.VISIBLE);
                    init();
                    setLisiners();
                    setFactorys();
                    timers();
                    preveousCount = preveousCount + 5;
                    count = forNextStage + preveousCount;

                }
            }) .setNegativeButton("To menu", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    Intent toMenu = new Intent(MainActivity.this,MainActivity.class);
                    startActivity(toMenu);
                }
            });
            AlertDialog alertDialog = alertBuilder.create();
            alertDialog.show();
            TextView alertTXT = (TextView) alertDialog.findViewById(android.R.id.message);
            Typeface forAlertTYP = Typeface.DEFAULT.createFromAsset(getAssets(),"3Dventure.ttf");
            alertTXT.setTextSize(30);
            alertTXT.setTypeface(forAlertTYP);

        }
    }

    private void randomTargetPosition(){
        Random randomPos = new Random();

        int XminValue = targetsBC.getLeft();
        int XmaxValue = targetsBC.getRight();
        x = randomPos.nextInt(XmaxValue - XminValue - 250) + XminValue;
        x1 = x * 1.0f;
        realTarget.setX(x1);

        int YminValue = targetsBC.getTop();
        int YmaxValue = targetsBC.getBottom();
        y = randomPos.nextInt((YmaxValue - YminValue) - 300 ) + (YminValue + 30 );
        y1= y * 1.0f;
        realTarget.setY(y1);
    }

    private void setFactorys(){
        Typeface forTXT = Typeface.DEFAULT.createFromAsset(getAssets(),"32bit.TTF");
        timeTXT.setTypeface(forTXT);
        targetLeftTXT.setTypeface(forTXT);
        targetTXT.setTypeface(forTXT);
    }

    private void randomTargetTXTColor(){
        Random randomColor = new Random();

        r = randomColor.nextInt(254)+1;
        g = randomColor.nextInt(254)+1;
        b = randomColor.nextInt(254)+1;
        targetTXT.setTextColor(Color.rgb(r,g,b));
    }

    private void animations(){
        AlphaAnimation appearAnimMain = new AlphaAnimation(0.0f,1.0f);
        appearAnimMain.setDuration(100);
        appearAnimMain.setStartOffset(100);
        appearAnimMain.setFillAfter(true);

        AlphaAnimation appearAnimSec = new AlphaAnimation(0.0f,1.0f);
        appearAnimSec.setDuration(150);
        appearAnimSec.setStartOffset(150);
        appearAnimSec.setFillAfter(true);

        targetsBC.startAnimation(appearAnimMain);
        realTarget.startAnimation(appearAnimMain);
        targetTXT.startAnimation(appearAnimSec);
        targetIcon.startAnimation(appearAnimSec);
    }

    private void timers(){
        targetLeftTXT.setVisibility(View.INVISIBLE);
        targetIcon.setVisibility(View.INVISIBLE);
        targetTXT.setVisibility(View.INVISIBLE);
        plusSec.setVisibility(View.INVISIBLE);
        realTarget.setVisibility(View.INVISIBLE);

        targetLeftTXT.setText("Targets left: " + count);

        PREtimeris = new CountDownTimer(6000,100) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeTXT.setText("Game start at: " + millisUntilFinished / 1000 + " s");
            }

            @Override
            public void onFinish() {
                targetLeftTXT.setVisibility(View.VISIBLE);
                targetIcon.setVisibility(View.VISIBLE);
                targetTXT.setVisibility(View.VISIBLE);
                plusSec.setVisibility(View.VISIBLE);
                realTarget.setVisibility(View.VISIBLE);
                PREtimeris.cancel();

                GAMEtimeris = new CountDownTimer(startTime,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                        isGAMEtimer = true;
                        timeTXT.setText("Time left: " + millisUntilFinished / 1000 + " s");
                        BONUSTime = millisUntilFinished;

                        plusSec.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                isGAMEtimer = false;
                                isBONUStimer = true;

                                GAMEtimeris.cancel();

                                BONUStimeris = new CountDownTimer(BONUSTime + bonusTimeValue,1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        plusSec.setVisibility(View.INVISIBLE);
                                        timeTXT.setText("Time left: " + millisUntilFinished / 1000 + " s");
                                    }

                                    @Override
                                    public void onFinish() {
                                        BONUStimeris.cancel();
                                        PREtimeris.cancel();
                                        GAMEtimeris.cancel();

                                        if(count > 0){
                                            targetLeftTXT.setVisibility(View.INVISIBLE);
                                            targetIcon.setVisibility(View.INVISIBLE);
                                            targetTXT.setVisibility(View.INVISIBLE);
                                            plusSec.setVisibility(View.INVISIBLE);
                                            realTarget.setVisibility(View.INVISIBLE);

                                            timeTXT.setText("You lose");

                                            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                                            alertBuilder.setMessage("You LOSE \n You found: " + clicks + " emojis");
                                            alertBuilder.setCancelable(false);

                                            alertBuilder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    finish();
                                                    Intent resetINT = new Intent(MainActivity.this,MainActivity.class);
                                                    startActivity(resetINT);
                                                }
                                            }) .setNegativeButton("To menu", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    finish();
                                                    Intent toMenu = new Intent(MainActivity.this,MainActivity.class);
                                                    startActivity(toMenu);
                                                }
                                            });
                                            AlertDialog alertDialog = alertBuilder.create();
                                            alertDialog.show();
                                            TextView alertTXT = (TextView) alertDialog.findViewById(android.R.id.message);
                                            Typeface forAlertTYP = Typeface.DEFAULT.createFromAsset(getAssets(),"3Dventure.ttf");
                                            alertTXT.setTextSize(20);
                                            alertTXT.setTypeface(forAlertTYP);


                                        }

                                    }
                                }.start();
                            }
                        });
                    }

                    @Override
                    public void onFinish() {
                        PREtimeris.cancel();
                        GAMEtimeris.cancel();

                        if(count > 0) {
                            targetLeftTXT.setVisibility(View.INVISIBLE);
                            targetIcon.setVisibility(View.INVISIBLE);
                            targetTXT.setVisibility(View.INVISIBLE);
                            plusSec.setVisibility(View.INVISIBLE);
                            realTarget.setVisibility(View.INVISIBLE);

                            timeTXT.setText("You lose");

                            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                            alertBuilder.setMessage("You LOSE \n You found: " + clicks + " emojis");
                            alertBuilder.setCancelable(false);

                            alertBuilder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                    Intent resetINT = new Intent(MainActivity.this, MainActivity.class);
                                    startActivity(resetINT);
                                }
                            }).setNegativeButton("To menu", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                    Intent toMenu = new Intent(MainActivity.this, MainActivity.class);
                                    startActivity(toMenu);
                                }
                            });
                            AlertDialog alertDialog = alertBuilder.create();
                            alertDialog.show();
                            TextView alertTXT = (TextView) alertDialog.findViewById(android.R.id.message);
                            Typeface forAlertTYP = Typeface.DEFAULT.createFromAsset(getAssets(), "3Dventure.ttf");
                            alertTXT.setTextSize(20);
                            alertTXT.setTypeface(forAlertTYP);
                        }
                    }
                }.start();

            }
        }.start();
    }
}
