package br.com.mauricio.tictactoe.types;


import android.widget.ImageView;

import br.com.mauricio.tictactoe.R;

public enum Player {
    GREMIO(0,"GRÊMIO", R.drawable.gremiologo),
    INTER(1,"INTER", R.drawable.interlogo);

    private int playerNumber;
    private String playerName;
    private int playerImage;

    private Player(int playerNumber, String playerName, int playerImage){
        this.playerNumber = playerNumber;
        this.playerName = playerName;
        this.playerImage = playerImage;
    }

    public Integer getPlayerNumber(){
        return this.playerNumber;
    }

    public String getPlayerName(){
        return this.playerName;
    }

    public int getPlayerImage(){
        return this.playerImage;
    }
}
