package br.com.mauricio.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Player activePlayer = Player.GREMIO;

    int gameStage[] = {2,2,2,2,2,2,2,2,2};
    int winningStage[][] = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dropIn(View view){
        ImageView counter = (ImageView)view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if(gameStage[tappedCounter] == 2) {
            counter.setTranslationY(-1000f);
            gameStage[tappedCounter] = activePlayer.getPlayerNumber();

            if (activePlayer.equals(Player.GREMIO)) {
                counter.setImageResource(R.drawable.gremioloog);
                activePlayer = Player.INTER;
            } else {
                counter.setImageResource(R.drawable.logo_internacional);
                activePlayer = Player.GREMIO;
            }


            counter.animate().translationYBy(1000f).setDuration(300);

            for(int[] winningPosition : winningStage){
                if((gameStage[winningPosition[0]] == gameStage[winningPosition[1]])
                        && (gameStage[winningPosition[1]] == gameStage[winningPosition[2]])
                        && gameStage[winningPosition[0]]  != 2  ){

                    Player winner = Player.GREMIO;

                    if(gameStage[winningPosition[0]] == Player.INTER.getPlayerNumber()){
                        winner = Player.INTER;
                    }

                    TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                    winnerMessage.setText(String.format("%s has won!!!",winner.getPlayerName()));

                    LinearLayout winnerLayout = (LinearLayout)findViewById(R.id.playAgainLayout);
                    winnerLayout.setVisibility(View.VISIBLE);

                }
            }
        }
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
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }

    }
}
