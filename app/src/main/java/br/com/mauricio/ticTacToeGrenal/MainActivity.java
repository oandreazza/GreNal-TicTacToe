package br.com.mauricio.ticTacToeGrenal;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.LocalDateTime;

import java.util.List;

import br.com.mauricio.ticTacToeGrenal.exception.DrawException;
import br.com.mauricio.ticTacToeGrenal.exception.SpotAlreadyFilledException;
import br.com.mauricio.ticTacToeGrenal.exception.WinnerException;
import br.com.mauricio.ticTacToeGrenal.model.FinalScore;
import br.com.mauricio.ticTacToeGrenal.model.Match;
import br.com.mauricio.ticTacToeGrenal.model.TicTacToe;
import br.com.mauricio.ticTacToeGrenal.strategy.TicTacToeStrategy;
import br.com.mauricio.ticTacToeGrenal.types.Player;

public class MainActivity extends AppCompatActivity {

    private Player activePlayer;
    private Integer interScore = 0;
    private Integer gremioScore = 0;
    private SharedPreferences userSession;
    private Match match;
    private TicTacToe ticTacToe;
    private boolean doubleBackToExitPressedOnce = false;
    private TicTacToeStrategy gameStrategy;
    private FirebaseDatabase db;
    private DatabaseReference gameReef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.db = FirebaseDatabase.getInstance();

        userSession = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        TextView welcome = (TextView) findViewById(R.id.welcome);
        welcome.setText(userSession.getString("email", null));

        startGame();

        gameReef = db.getReference("multiplayer").child("games").child("-KOhJ8Kf4oSd2tjIxzrX");
        gameReef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TicTacToe ticTacToeChanged = dataSnapshot.getValue(TicTacToe.class);
                Log.i("Jogo:", ticTacToeChanged.getStageList().toString());
                refresh(ticTacToeChanged);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void startGame() {
        match = new Match(new TicTacToe(), Player.GREMIO, Player.INTER);
        match.start();
        ticTacToe = (TicTacToe) match.getGame();
        this.activePlayer = Player.GREMIO;
        gameStrategy = new TicTacToeStrategy(ticTacToe);

    }


    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        int position = Integer.parseInt(counter.getTag().toString());



        try {
            ticTacToe.play(activePlayer,position);
            //playAnimate(counter);
            ticTacToe.setList();
            gameReef.setValue(ticTacToe);
            gameStrategy.getSituation();
        } catch (WinnerException e){
            showWinnerOption();
            if (activePlayer.equals(Player.INTER)) {
                interScore++;
            }else{
                gremioScore++;
            }
            refreshScore();
            persistWinner();
        } catch (DrawException e){
            showDrawOption();
        } catch ( SpotAlreadyFilledException e) {
            Toast.makeText(getApplicationContext(), "Local já ocupado", Toast.LENGTH_SHORT).show();
        } finally {
            activePlayer = getNextPlayer();
        }

    }

    private void playAnimate(ImageView tappedSpot) {
        tappedSpot.setTranslationY(-1000f);
        tappedSpot.animate().translationYBy(1000f).setDuration(300);
        tappedSpot.setImageResource(activePlayer.getPlayerImage());
    }

    private void persistWinner() {
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

        resetStage();

        startGame();
    }

    private void resetStage() {
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for(int i = 0; i < gridLayout.getChildCount(); i++){
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(android.R.color.transparent);
        }
    }

    private void refresh(TicTacToe ticTacToeParam){
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        List<Integer> listSpot = ticTacToeParam.getStageList();
        Log.i("Atualizando:",listSpot.toString());
        for(int i = 0; i < gridLayout.getChildCount(); i++){
            if(listSpot.get(i) != 2){
                Player playerRefresh = Player.getPlayerById(listSpot.get(i));
                ((ImageView) gridLayout.getChildAt(i)).setImageResource(playerRefresh.getPlayerImage());

            }else {
                ((ImageView) gridLayout.getChildAt(i)).setImageResource(android.R.color.transparent);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            this.finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Clique novamente para sair", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
