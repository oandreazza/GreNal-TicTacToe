package br.com.mauricio.tictactoe;


public enum Player {
    GREMIO(0,"GRÊMIO"),
    INTER(1,"INTER");

    private int playerNumber;
    private String playerName;

    private Player(int playerNumber, String playerName){
        this.playerNumber = playerNumber;
        this.playerName = playerName;
    }

    public Integer getPlayerNumber(){
        return this.playerNumber;
    }

    public String getPlayerName(){
        return this.playerName;
    }
}
