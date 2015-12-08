package com.sharonhau.connect3;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean _isYellow = true;
    int [][] _boardYellow = new int[3][3];
    int [][] _boardRed = new int[3][3];
    int _playCount = 0;

    public void onClick(View view) {
        ImageView currentView = (ImageView) view;

        if(currentView.getAlpha() == 1) {
            return;
        }

        int[][] currentBoard;
        if (_isYellow) {
            currentBoard = _boardYellow;
            currentView.setImageResource(R.drawable.yellow);
        } else {
            currentView.setImageResource(R.drawable.red);
            currentBoard = _boardRed;
        }

        // Move the counter into the board
        view.setTranslationY(-1000f);
        view.setAlpha(1f);
        view.animate().translationYBy(1000f).setDuration(500);

        String[] positions = currentView.getTag().toString().split("/");
        int col = Integer.parseInt(positions[0]);
        int row = Integer.parseInt(positions[1]);

        currentBoard[col][row] = 1;

        if (checkWinning(currentBoard, col, row)) {
            String winner;
            int backgroundColor;
            if (_isYellow) {
                winner = "Yellow";
                backgroundColor = Color.YELLOW;
            } else {
                winner = "Red";
                backgroundColor = Color.RED;
            }
            displayMessage(winner + " WON!", backgroundColor);
        } else if (_playCount == 8) {
            displayMessage("Tie game", Color.LTGRAY);
        } else {
            _playCount++;
            _isYellow = !_isYellow;
        }
    }

    private void displayMessage(String text, int backgroundColour) {
        TextView winnerMessage = (TextView) findViewById(R.id.gameOver);
        winnerMessage.setText(text);
        LinearLayout layout = (LinearLayout)findViewById(R.id.gameOverLayout);
        layout.setBackgroundColor(backgroundColour);
        layout.setVisibility(View.VISIBLE);
    }

    private boolean checkWinning(int [][] board, int col, int row) {
        // Check if sum of row is 3
        int rowSum = 0;
        for(int i = 0; i < 3; i++) {
            rowSum += board[col][i];
        }
        if (rowSum == 3) {
           return true;
        }

        // Check if sum of column is 3
        int colSum = 0;
        for(int i = 0; i < 3; i++) {
            colSum += board[i][row];
        }
        if (colSum == 3) {
            return true;
        }

        // Check diagonals
        if (board[0][0] + board[1][1] + board[2][2] == 3) {
            return true;
        } else if (board[0][2] + board[2][2] + board[2][0] == 3) {
            return true;
        }

        return false;
    }

    public void resetGame(View view) {
        _isYellow = true;
        _boardYellow = new int[3][3];
        _boardRed = new int[3][3];
        _playCount = 0;

        LinearLayout layout = (LinearLayout)findViewById(R.id.gameOverLayout);
        layout.setVisibility(View.INVISIBLE);

        // Hide all the counters
        GridLayout board = (GridLayout)findViewById(R.id.boardLayout);
        for (int i = 0; i< board.getChildCount(); i++) {
            board.getChildAt(i).setAlpha(0f);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
