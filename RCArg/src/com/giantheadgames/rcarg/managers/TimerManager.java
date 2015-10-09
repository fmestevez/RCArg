package com.giantheadgames.rcarg.managers;

import java.util.ArrayList;
import java.util.Collections;

import com.giantheadgames.rcarg.entities.InGameState;
import com.giantheadgames.rcarg.screens.InGameScreen;
import com.giantheadgames.rcarg.util.ObjectSerializer;


public class TimerManager {
    
    private static double timer;
    private static ArrayList<Double> highScores;
    private static ObjectSerializer<ArrayList<Double>> os;
    private static String scoresPath = "highscores.ser";
    
    public static void init(){
        os = new ObjectSerializer<ArrayList<Double>>(scoresPath);
        highScores = os.read();
        if(highScores == null){
            System.out.println("Null");
            highScores = new ArrayList<Double>();
        }
    }
    
    public static void update(float delta){
        if(InGameScreen.getGameState() == InGameState.RACING){
            timer = timer + delta;
        }
    }
    
    public static double getMillis() {
        return timer;
    }

    public static void setMillis(double millis) {
        TimerManager.timer = millis;
    }
    
    public static String getFormattedTime(){
        return formatIntoMMSSCC(timer);
    }
    
    public static String formatIntoMMSSCC(double delta)
    {
        
        int time_mins = (int)(delta / 60.0f);
        delta -= time_mins * 60;
        int time_secs = (int)(delta);
        delta -= time_secs;
        int time_centis = (int)(delta * 100.0f);
        
        return
          ((time_mins < 10)?"0":"") + time_mins + ":" 
        + ((time_secs < 10)?"0":"") + time_secs + ":" 
        + ((time_centis < 10)?"0":"") + time_centis;

    }
    
    public static void addScore(){
        highScores.add(timer);
        Collections.sort(highScores);
    }
    
    public static String[] getHighScores(){
        String [] podium = new String[3];
        if(highScores == null) {
            podium[0] = "99:99:99";
            podium[1] = "99:99:99";
            podium[2] = "99:99:99";
            return podium;
        }
        switch(highScores.size()){
        case 0:
            podium[0] = "99:99:99";
            podium[1] = "99:99:99";
            podium[2] = "99:99:99";
            break;
        case 1:
            podium[0] = formatIntoMMSSCC(highScores.get(0));
            podium[1] = "99:99:99";
            podium[2] = "99:99:99";
            break;
        case 2:
            podium[0] = formatIntoMMSSCC(highScores.get(0));
            podium[1] = formatIntoMMSSCC(highScores.get(1));
            podium[2] = "99:99:99";
            break;
        case 3:
            podium[0] = formatIntoMMSSCC(highScores.get(0));
            podium[1] = formatIntoMMSSCC(highScores.get(1));
            podium[2] = formatIntoMMSSCC(highScores.get(2));
            break;
        default:
            podium[0] = formatIntoMMSSCC(highScores.get(0));
            podium[1] = formatIntoMMSSCC(highScores.get(1));
            podium[2] = formatIntoMMSSCC(highScores.get(2));
        }
        
        return podium;
    }
    
    public static void dispose(){
        
        os.write(highScores);
        os = null;
        timer = 0;
    }

}
