package com.sharonhau.connect3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    boolean isYellow = true;
    int [][] boardYellow = new int[3][3];
    int [][] boardRed = new int[3][3];
    int plays = 0;

    public void onClick(View view) {
        ImageView currentView = (ImageView) view;

        if(currentView.getAlpha() == 1) {
            return;
        }

        int[][] currentBoard;
        if (isYellow) {
            currentBoard = boardYellow;
        } else {
            currentView.setImageResource(R.drawable.red);
            currentBoard = boardRed;
        }

        view.setTranslationY(-1000f);
        view.setAlpha(1f);
        view.animate().translationYBy(1000f).setDuration(500);

        String[] positions = currentView.getTag().toString().split("/");
        int col = Integer.parseInt(positions[0]);
        int row = Integer.parseInt(positions[1]);

        currentBoard[col][row] = 1;

        if (checkWinning(currentBoard, col, row)) {
            Toast.makeText(getApplicationContext(), (isYellow ? "Yellow" : "Red") + " WON!", Toast.LENGTH_SHORT).show();
        } else if (plays == 8) {
            Toast.makeText(getApplicationContext(), "Tie game", Toast.LENGTH_SHORT).show();
        } else {
            plays++;
            isYellow = !isYellow;
        }
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
