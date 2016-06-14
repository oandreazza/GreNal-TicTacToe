package br.com.mauricio.ticTacToeGrenal.model;


import br.com.mauricio.ticTacToeGrenal.types.Player;

public class TicTacToe implements Game {

    private int stage[] = {2,2,2,2,2,2,2,2,2};

    public void play(Player player, int position) {
        this.stage[position] = player.getPlayerNumber();
    }

    public Integer getPlayerNumberByLocationStage(int position) {
        return this.stage[position];
    }

}
