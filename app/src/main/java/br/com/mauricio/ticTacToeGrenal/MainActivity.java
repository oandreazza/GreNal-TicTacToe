package br.com.mauricio.ticTacToeGrenal;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.LocalDateTime;

import br.com.mauricio.ticTacToeGrenal.exception.SpotAlreadyFilledException;
import br.com.mauricio.ticTacToeGrenal.model.FinalScore;
import br.com.mauricio.ticTacToeGrenal.model.Match;
import br.com.mauricio.ticTacToeGrenal.model.TicTacToe;
import br.com.mauricio.ticTacToeGrenal.types.Player;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    Player activePlayer;
    Integer interScore = 0;
    Integer gremioScore = 0;
    SharedPreferences userSession;
    Match match;
    TicTacToe ticTacToe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userSession = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        TextView welcome = (TextView) findViewById(R.id.welcome);
        welcome.setText(userSession.getString("email", null));

        startGame();

    }

    private void startGame() {
        match = new Match(new TicTacToe(), Player.GREMIO, Player.INTER);
        match.start();
        ticTacToe = (TicTacToe) match.getGame();
        this.activePlayer = Player.GREMIO;
    }


    public void dropIn(View view) {
        Log.d(TAG, "#### PLAYER ##### "+activePlayer.getPlayerName());

        ImageView counter = (ImageView) view;
        int position = Integer.parseInt(counter.getTag().toString());


        try {
            ticTacToe.play(activePlayer,position);
            counter.setTranslationY(-1000f);
            counter.animate().translationYBy(1000f).setDuration(300);
            counter.setImageResource(activePlayer.getPlayerImage());

            if(ticTacToe.hasWinner()){
                showWinnerOption();
                if (activePlayer.equals(Player.INTER)) {
                     interScore++;
                }else{
                    gremioScore++;
                }
                refreshScore();
                persistWinner();
            }else if(ticTacToe.hasDraw()){
                showDrawOption();
            }else{
                activePlayer = getNextPlayer();
            }
        }catch ( SpotAlreadyFilledException e) {
            Toast.makeText(getApplicationContext(), "Local já ocupado", Toast.LENGTH_SHORT).show();
        }

    }

    private void persistWinner() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ranking = db.getReference("ranking").child("results");

        FinalScore finalScore = new FinalScore();
        finalScore.setWinner(activePlayer.getPlayerName());
        finalScore.setDate(LocalDateTime.now().toString());
        finalScore.setEmail(userSession.getString("email",null));


        ranking.child(userSession.getString("id",null)).push().setValue(finalScore);
    }

    private void showWinnerOption() {
        TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
        winnerMessage.setText(String.format("GOOOOL!!! DO %s", activePlayer.getPlayerName()));

        LinearLayout winnerLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
        winnerLayout.setVisibility(View.VISIBLE);
    }

    private void showDrawOption() {
        TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
        winnerMessage.setText("Ocorreu um empate!");

        LinearLayout winnerLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
        winnerLayout.setVisibility(View.VISIBLE);
    }

    private void refreshScore() {
        TextView gremioScoreField = (TextView) findViewById(R.id.gremioScore);
        TextView interScoreField = (TextView) findViewById(R.id.interScore);

        gremioScoreField.setText(gremioScore.toString());
        interScoreField.setText(interScore.toString());
    }



    private Player getNextPlayer() {
        return this.activePlayer.equals(Player.GREMIO) ? Player.INTER : Player.GREMIO;
    }

    public void playAgain(View view){
        LinearLayout winnerLayout = (LinearLayout)findViewById(R.id.playAgainLayout);
        winnerLayout.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for(int i = 0; i < gridLayout.getChildCount(); i++){
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(android.R.color.transparent);
        }

        startGame();
    }

}
