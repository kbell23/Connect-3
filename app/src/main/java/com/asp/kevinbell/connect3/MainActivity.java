package com.asp.kevinbell.connect3;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 0 = yellow, 1 = red.
    protected int activePlayer = 0;

    // 1 means the spot on the board is open.
    protected int gameBoard[] = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    // bool to check if the game is still going on
    boolean gameActive = true;

    // possible winning conditions
    protected int winningPositions[][] = {{0,1, 2}, {3,4,5}, {6,7,8}, {0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    // method for dropping a red or yellow coin
    public void dropIn(View view){

        // makes the image view based off what position was clicked
        ImageView piece = (ImageView) view;

        // integer corresponding to the position in the grid
        int piecePosition = Integer.parseInt(view.getTag().toString());

        // checks if the space isn't filled
        if(gameBoard[piecePosition] == 2 && gameActive) {
            // fills spot and proceeds to place a piece based off the active player
            gameBoard[piecePosition] = activePlayer;
            piece.setTranslationY(-1000f);

            switch (activePlayer) {
                case 0:
                    piece.setImageResource(R.drawable.yellow);
                    activePlayer = 1;
                    break;
                case 1:
                    piece.setImageResource(R.drawable.red);
                    activePlayer = 0;
                    break;
            }

            piece.animate().translationYBy(1000f).setDuration(300);

            // loops through the possible winning conditions for a winner
            for(int winningPosition[] : winningPositions){
                if(gameBoard[winningPosition[0]] == gameBoard[winningPosition[1]] &&
                        gameBoard[winningPosition[1]] == gameBoard[winningPosition[2]] &&
                        gameBoard[winningPosition[0]] != 2) {

                        // we have a winner so we have to make out linear layout (contains our win button
                        // to show and this is done by making the layout visible again
                        gameActive = false;
                        TextView winnerMessage = (TextView)findViewById(R.id.winnerMessage);

                        String playerString = "Red";
                        if(gameBoard[winningPosition[0]] == 0){ playerString = "Yellow"; }
                        winnerMessage.setText(playerString + " has won!");

                        LinearLayout playAgainLayout = (LinearLayout)findViewById(R.id.playAgainLayout);
                        playAgainLayout.setVisibility(View.VISIBLE);
                    }
                    // checks for a tie
                    else{
                        boolean gameOver = true;
                        for(int i = 0; i < gameBoard.length; i++) {
                            if (gameBoard[i] == 2) {
                                gameOver = false;
                            }
                        }
                        if(gameOver){
                            TextView winnerMessage = (TextView)findViewById(R.id.winnerMessage);
                            winnerMessage.setText("It's a tie!");
                            LinearLayout playAgainLayout = (LinearLayout)findViewById(R.id.playAgainLayout);
                            playAgainLayout.setVisibility(View.VISIBLE);
                        }

                    }

            }
        }
    }

    // method to play the game after a win or tie
    public void playAgain(View view){

        LinearLayout playAgainLayout = (LinearLayout)findViewById(R.id.playAgainLayout);
        playAgainLayout.setVisibility(View.INVISIBLE);

        GridLayout grid = (GridLayout)findViewById(R.id.gridLayout);

        activePlayer = 0;

        gameActive = true;

        for(int i = 0; i < gameBoard.length; i++){ gameBoard[i] = 2;}


        for(int j = 0; j < grid.getChildCount(); j++){
            ((ImageView) grid.getChildAt(j)).setImageResource(0);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
