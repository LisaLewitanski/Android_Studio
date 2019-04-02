package com.lewitanski.lisa.assignment2;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * <b>MainActivity</b>
 * <p>
 * This class creates a new game and display the player's score.
 * It will also creates a new game if the user clicks on the button 'new game'.
 * <p>
 *
 * @author Lisa Lewitanski
 * @version 1.0
 */

public class MainActivity extends AppCompatActivity {
    /**
     * The GameBoard allows to create a new game.
     * @see MainActivity#canvas
     *
     * The Arraylist of integer is the score of each player.
     * @see MainActivity#
     *
     * The Button allows to create a new game.
     * @see MainActivity#new_game
     *
     */
    private GameBoard canvas = null;
    private Button new_game;
    private TextView score_p1;
    private TextView score_p2;
    private TextView player_turn;
    /**
     * The onCreate method allows to create a new game.
     * It also allows to create an arraylist to save the score of the player.
     * In a second time, the method will displays the score, the player's turn and the winner.
     * If the user clicks on the button 'new game', an new game will begin.
     *
     * @see MainActivity#onCreate(Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        canvas = (GameBoard) findViewById(R.id.the_canvas);
        new_game = (Button) findViewById(R.id.new_game);
        score_p1 = (TextView) findViewById(R.id.score_player1);
        score_p2 = (TextView) findViewById(R.id.score_player2);
        player_turn = (TextView) findViewById(R.id.turn);
    }

    /**
     * This method allows to display a new game if the player clicks on the button 'New Game'.
     * First it creates a new game, set the two scores at 0 and display the whom's turn.
     * Furthermore, the score size is set to 20.
     *
     * @param v : The view
     * @see MainActivity#onNewGameClick(View)
     */
    public void onNewGameClick(View v) {
        canvas.newGame();
        canvas.invalidate();
        score_p1.setText("0");
        score_p2.setText("0");
        score_p1.setTextSize(20);
        score_p2.setTextSize(20);
        player_turn.setText("Turn of player 1");
    }

    /**
     * This method allows to display the player's turn, the score and the winner.
     *
     * @see MainActivity#onDisplayChanged()
     */
    public void onDisplayChanged() {
        // Get the player's score
        int turn = canvas.getTurn();
        ArrayList<Integer> scores = canvas.getScore();
        // Verify if it's the end of the game
        if (canvas.end_game) {
            // Display the winner of the game based on the score
            if (scores.get(0) > scores.get(1))
                player_turn.setText("Player 1 is the winner!");
            else if (scores.get(0) < scores.get(1))
                player_turn.setText("Player 2 is the winner!");
            else
                player_turn.setText("Equality between the two players !");
        }
        // Display the player's turn
        else if (turn == 1)
            player_turn.setText("Turn of player 1");
        else if (turn == 2)
            player_turn.setText("Turn of player 2");
        // Display the score
        score_p1.setText(Integer.toString(scores.get(0)));
        score_p2.setText(Integer.toString(scores.get(1)));
        // Set the score's size at 20
        score_p1.setTextSize(20);
        score_p2.setTextSize(20);
    }
}
