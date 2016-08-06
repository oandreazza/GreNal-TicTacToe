package br.com.mauricio.ticTacToeGrenal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class GameTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_type);
    }


    public void selectType(View view){
        switch (view.getId()){
            case R.id.singlePlayer :
                Intent singlePlayer = new Intent(this,MainActivity.class);
                startActivity(singlePlayer);
                break;
            case R.id.multiplayer :
                Intent multiplayer = new Intent(this, MultiplayerActivity.class);
                startActivity(multiplayer);

        }

    }
}
