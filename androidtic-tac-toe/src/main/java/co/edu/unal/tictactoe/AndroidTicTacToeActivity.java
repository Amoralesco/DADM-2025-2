package co.edu.unal.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;

public class AndroidTicTacToeActivity extends AppCompatActivity {


    private TicTacToeGame mGame;
    private Button mBoardButtons[];
    private TextView mInfoTextView;
    private Button mNewGameButton;
    private boolean mGameOver = false;
    // Counters
    private int mHumanWins = 0;
    private int mAndroidWins = 0;
    private int mTies = 0;
    private TextView mScoreYouTextView;
    private TextView mScoreAndroidTextView;
    private TextView mScoreTiesTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_tic_tac_toe);

    mBoardButtons = new Button[TicTacToeGame.BOARD_SIZE];
    // assign buttons by index
    mBoardButtons[0] = findViewById(R.id.one);
    mBoardButtons[1] = findViewById(R.id.two);
    mBoardButtons[2] = findViewById(R.id.three);
    mBoardButtons[3] = findViewById(R.id.four);
    mBoardButtons[4] = findViewById(R.id.five);
    mBoardButtons[5] = findViewById(R.id.six);
    mBoardButtons[6] = findViewById(R.id.seven);
    mBoardButtons[7] = findViewById(R.id.eight);
    mBoardButtons[8] = findViewById(R.id.nine);

        mInfoTextView = findViewById(R.id.information);

        mNewGameButton = findViewById(R.id.new_game_button);
        mNewGameButton.setOnClickListener(v -> startNewGame());

    // Score text views
    mScoreYouTextView = findViewById(R.id.score_you);
    mScoreAndroidTextView = findViewById(R.id.score_android);
    mScoreTiesTextView = findViewById(R.id.score_ties);
    updateScoreDisplay();

        mGame = new TicTacToeGame();
        startNewGame();
    }

    private void startNewGame() {
        mGame.clearBoard();
        mGameOver = false;

        for (int i = 0; i < mBoardButtons.length; i++) {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
        }

        mInfoTextView.setText(getString(R.string.first_human));
        mNewGameButton.setVisibility(View.GONE); // Ocultar el botón al iniciar
    }

    private class ButtonClickListener implements View.OnClickListener {
        int location;

        public ButtonClickListener(int location) {
            this.location = location;
        }

        @Override
        public void onClick(View view) {
            if (!mGameOver && mBoardButtons[location].isEnabled()) {
                setMove(TicTacToeGame.HUMAN_PLAYER, location);

                int winner = mGame.checkForWinner();
                if (winner == 0) {
                    mInfoTextView.setText(getString(R.string.turn_computer));
                    int move = mGame.getComputerMove();
                    setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                    winner = mGame.checkForWinner();
                }

                if (winner == 0) {
                    mInfoTextView.setText(getString(R.string.turn_human));
                } else {
                    finishGame(winner);
                }
            }
        }
    }

    // Helper to update all score TextViews
    private void updateScoreDisplay() {
        mScoreYouTextView.setText(getString(R.string.score_you, mHumanWins));
        mScoreAndroidTextView.setText(getString(R.string.score_android, mAndroidWins));
        mScoreTiesTextView.setText(getString(R.string.score_ties, mTies));
    }

    // Consolidated end-of-game handling
    private void finishGame(int winner) {
        if (winner == 1) {
            mInfoTextView.setText(getString(R.string.result_tie));
            mTies++;
        } else if (winner == 2) {
            mInfoTextView.setText(getString(R.string.result_human_wins));
            mHumanWins++;
        } else {
            mInfoTextView.setText(getString(R.string.result_computer_wins));
            mAndroidWins++;
        }
        mGameOver = true;
        updateScoreDisplay();
        mNewGameButton.setVisibility(View.VISIBLE);
    }

    private void setMove(char player, int location) {
        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        if (player == TicTacToeGame.HUMAN_PLAYER)
            mBoardButtons[location].setTextColor(Color.GREEN);
        else
            mBoardButtons[location].setTextColor(Color.RED);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem item = menu.add(0, 1, 0, getString(R.string.new_game));
        item.setIcon(android.R.drawable.ic_menu_revert);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            startNewGame();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}

