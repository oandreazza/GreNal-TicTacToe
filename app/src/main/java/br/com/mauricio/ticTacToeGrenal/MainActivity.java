package br.com.mauricio.ticTacToeGrenal;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.LocalDateTime;

import br.com.mauricio.ticTacToeGrenal.model.FinalScore;
import br.com.mauricio.ticTacToeGrenal.model.TicTacToe;
import br.com.mauricio.ticTacToeGrenal.types.Player;

public class MainActivity extends AppCompatActivity {

    Player activePlayer = Player.GREMIO;
    private TextView mStatusTextView;
    int gameStage[] = {2,2,2,2,2,2,2,2,2};
    int winningStage[][] = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};
    Integer interScore = 0;
    Integer gremioScore = 0;
    int move = 0;
    SharedPreferences userSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userSession = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        TextView welcome = (TextView) findViewById(R.id.welcome);
        welcome.setText(userSession.getString("email", null));

    }


    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        int position = Integer.parseInt(counter.getTag().toString());

        TicTacToe ticTacToe = new TicTacToe();

        if(ticTacToe.canPlay(position)){
            ticTacToe.play(activePlayer,position);

            if(ticTacToe.hasWinner()){

            }

            activePlayer = getNextPlayer();
        }


        if (theresNoPlay(position)) {
            counter.setTranslationY(-1000f);

            play(activePlayer, counter);

            for (int[] winningPosition : winningStage) {
                if (hasWinner(winningPosition)) {

                    Player winner = Player.GREMIO;

                    if (gameStage[winningPosition[0]] == Player.INTER.getPlayerNumber()) {
                        winner = Player.INTER;
                        interScore++;
                    }else{
                        gremioScore++;
                    }

                    refreshScore();

                    TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                    winnerMessage.setText(String.format("%s venceu!!!", winner.getPlayerName()));

                    LinearLayout winnerLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
                    winnerLayout.setVisibility(View.VISIBLE);

                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference ranking = db.getReference("ranking").child("results");

                    FinalScore finalScore = new FinalScore();
                    finalScore.setWinner(winner.getPlayerName());
                    finalScore.setDate(LocalDateTime.now().toString());
                    finalScore.setEmail(userSession.getString("email",null));


                    ranking.child(userSession.getString("id",null)).push().setValue(finalScore);

                }
            }

            if(hasDraw())
                showDrawOption();

        }
    }

    private void showDrawOption() {
        TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
        winnerMessage.setText("Ocorreu um empate!");

        LinearLayout winnerLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
        winnerLayout.setVisibility(View.VISIBLE);
    }

    private boolean hasDraw() {
        return move == 9;
    }

    private boolean hasWinner(int[] winningPosition) {
        return (gameStage[winningPosition[0]] == gameStage[winningPosition[1]])
                && (gameStage[winningPosition[1]] == gameStage[winningPosition[2]])
                && gameStage[winningPosition[0]] != 2;
    }

    private boolean theresNoPlay(int location) {
        return gameStage[location] == 2;
    }

    private void refreshScore() {
        TextView gremioScoreField = (TextView) findViewById(R.id.gremioScore);
        TextView interScoreField = (TextView) findViewById(R.id.interScore);

        gremioScoreField.setText(gremioScore.toString());
        interScoreField.setText(interScore.toString());
    }


    private void play(Player player, ImageView counter) {
        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        gameStage[tappedCounter] = player.getPlayerNumber();
        counter.setImageResource(player.getPlayerImage());
        counter.animate().translationYBy(1000f).setDuration(300);
        activePlayer = getNextPlayer();
        move++;
    }

    private Player getNextPlayer() {
        return this.activePlayer.equals(Player.GREMIO) ? Player.INTER : Player.GREMIO;
    }

    public void playAgain(View view){
        LinearLayout winnerLayout = (LinearLayout)findViewById(R.id.playAgainLayout);
        winnerLayout.setVisibility(View.INVISIBLE);

        activePlayer = Player.GREMIO;

        for (int i =0; i < gameStage.length; i++){
            gameStage[i] = 2;
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for(int i = 0; i < gridLayout.getChildCount(); i++){
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(android.R.color.transparent);
        }

        move = 0;

    }

}
