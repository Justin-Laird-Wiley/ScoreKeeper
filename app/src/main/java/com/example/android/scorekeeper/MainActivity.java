package com.example.android.scorekeeper;

import android.app.ActionBar;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Initialize global variables
    int scoreTeamA = 0;
    int scoreTeamB = 0;
    int pointsInEnd = 0;
    int currentEnd = 1;
    String blankEnds = "";
    Boolean gameOver = false;
    Boolean firstBlankEnd = true;
    Boolean extraEnd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (pointsInEnd == 8) {
            return;
        }
        pointsInEnd++;
        displayPointsInEnd(pointsInEnd);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (pointsInEnd == 0) {
            return;
        }
        pointsInEnd--;
        displayPointsInEnd(pointsInEnd);
    }

    /**
     * This method displays the points won in the end; displayed between the
     * increment and decrement buttons.
     */
    private void displayPointsInEnd(int pointsToDisplay) {
        TextView quantityTextView = (TextView) findViewById(R.id.points_in_end_view);
        String pointsInEndText = "" + pointsToDisplay;
        quantityTextView.setText(pointsInEndText);
    }

    /**
     * This method is called when Team A wins the end.
     */
    public void teamAWinsEnd(View view) {
        if (!gameOver) {            // If the game isn't over, allow points for Team A to be tallied
            scoreTeamA = scoreTeamA + pointsInEnd;
            String scoreBoxIDString = "score_team_a_" + scoreTeamA;
            findScoreBoxByID(scoreBoxIDString);
            incrementEnd();
            displayCurrentEnd();
        }
    }

    /**
     * This method is called when Team B wins the end.
     */
    public void teamBWinsEnd(View view) {
        if (!gameOver) {            // If the game isn't over, allow points for Team B to be tallied
            scoreTeamB = scoreTeamB + pointsInEnd;
            String scoreBoxIDString = "score_team_b_" + scoreTeamB;
            findScoreBoxByID(scoreBoxIDString);
            incrementEnd();
            displayCurrentEnd();
        }
    }

    /**
     * This method uses a reverse lookup to locate the correct score box for
     * the winner of the end.
     */
    private void findScoreBoxByID(String idToFind) {
        int idOfScoreBox = getResources().getIdentifier(idToFind,"id", getPackageName());   // Reverse lookup
        TextView scoreBox = (TextView)findViewById(idOfScoreBox);
        String currentEndScoreText = "" + currentEnd;
        scoreBox.setText(currentEndScoreText);          // Set correct scorebox with currentEnd
    }

    /**
     * This method displays the blank ends as needed.
     */
    public void blankEnd(View view) {
        if (!gameOver) {        // Don't display blank ends after game is over
            concatBlankEnds();
            displayBlankEnds();
            incrementEnd();
            displayCurrentEnd();
        }
    }

    /**
     * This method increments the ends.
     */
    private void incrementEnd() {
        if ((currentEnd < 10) || (extraEnd))    // Increment currentEnd until End 10 is reached;
            currentEnd++;                       // or if Extra End is needed
        else
            gameOver = true;        // After 10 ends, regulation game is over
    }

    /**
     * This method allows for one extra end to be entered on the scoreboard in case a tie.
     * gameOver is set to false, and
     */
    public void addExtraEnd(View view) {
        if ((scoreTeamA == scoreTeamB) && (gameOver)) {
            gameOver = false;
            extraEnd = true;
            incrementEnd();
            displayCurrentEnd();
            extraEnd = false;
        }
    }

    /**
     * This method concatenates new blank ends to the blankEnds string
     */
    private void concatBlankEnds() {
        if (firstBlankEnd) {        // Remove dashes before concatenating first blank end
            blankEnds = "";
            firstBlankEnd = false;
        }
        blankEnds = blankEnds + " " + currentEnd;
    }

    /**
     * This method displays the String blankEnds
     */
    private void displayBlankEnds() {
        TextView quantityTextView = findViewById(R.id.blank_ends_display);
        String blankEndsText = "" + blankEnds;
        quantityTextView.setText(blankEndsText);
    }

    /**
     * This method displays the variable currentEnd
     */
    private void displayCurrentEnd() {
        TextView quantityTextView = (TextView) findViewById(R.id.current_end_display_view);
        String currentEndText = "" + currentEnd;
        quantityTextView.setText(currentEndText);
    }

    /**
     * This method is called when the Reset button is pressed; it clears the
     * scoreboard and resets all global variables.
     */
    public void resetScoreBoard(View view) {
        String teamAReset;
        String teamBReset;

        for (int i = 1; i < 16; i++) {          // Clear out all the scoreboxes
            teamAReset = "score_team_a_" + i;
            teamBReset = "score_team_b_" + i;
            displayResetScore(teamAReset);
            displayResetScore(teamBReset);
        }

        // Reset variables and call display methods as needed
        currentEnd = 1;
        scoreTeamA = 0;
        scoreTeamB = 0;
        pointsInEnd = 0;
        blankEnds = "---";
        gameOver = false;
        firstBlankEnd = true;
        displayCurrentEnd();
        displayBlankEnds();
        displayPointsInEnd(pointsInEnd);
    }

    /**
     * This method uses reverse lookup to find and clear score box passed in
     */
    private void displayResetScore(String scoreBoxToReset) {
        int idOfScoreBox = getResources().getIdentifier(scoreBoxToReset,"id", getPackageName());    // Reverse lookup
        TextView scoreBox = (TextView) findViewById(idOfScoreBox);
        scoreBox.setText("");               // Set the text in the scorebox to blank
    }
}
