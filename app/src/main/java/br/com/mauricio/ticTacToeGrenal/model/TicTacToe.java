package br.com.mauricio.ticTacToeGrenal.model;


import br.com.mauricio.ticTacToeGrenal.exception.SpotAlreadyFilledException;
import br.com.mauricio.ticTacToeGrenal.types.Player;

public class TicTacToe implements Game {

    private int[] stage;
    private int moves;

    public TicTacToe() {
        setInitialStageStatus();
    }

    public void play(Player player, int position) {
        if(!canPlay(position))
            throw new SpotAlreadyFilledException();

        moves++;
        this.stage[position] = player.getPlayerNumber();
    }


    public boolean canPlay(int position) {
        return stage[position] == 2;
    }


    public void playFirstRow(){
        this.stage[0] = Player.GREMIO.getPlayerNumber();
        this.stage[1] = Player.GREMIO.getPlayerNumber();
        this.stage[2] = Player.GREMIO.getPlayerNumber();

    }

    public void playDraw(){
        this.moves = 9;
    }

    @Override
    public void start() {
       //setInitialStageStatus();
    }

    private void setInitialStageStatus() {
        this.stage = new int[] {2,2,2,2,2,2,2,2,2};
        this.moves = 0;
    }


    public void setMoves(int moves) {
        this.moves = moves;
    }

    public int getMoves() {
        return this.moves;
    }

    public int getStagePosition(int position) {
        return this.stage[position];
    }
}
