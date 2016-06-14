package br.com.mauricio.ticTacToeGrenal.model;


import br.com.mauricio.ticTacToeGrenal.types.Player;

public class Match {

    private Player playerOne;

    private Player playerTwo;

    private Game game;


    public Match(Game game, Player playerOne, Player playerTwo) {
        this.game = game;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public Match() {

    }

    public Game getGame() {
        return this.game;
    }

    public boolean hasPlayers() {
        return this.playerOne != null && this.playerTwo != null;
    }
}
