package br.com.mauricio.ticTacToeGrenal.model;


import java.util.Arrays;
import java.util.List;

import br.com.mauricio.ticTacToeGrenal.exception.SpotAlreadyFilledException;
import br.com.mauricio.ticTacToeGrenal.types.Player;

public class TicTacToe implements Game {

    private Integer[] stage;
    private int moves;
    private List<Integer> stageList;

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
        this.stage = new Integer[] {2,2,2,2,2,2,2,2,2};
        this.moves = 0;
    }

    public void setList(){
        this.stageList = Arrays.asList(this.stage);
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

    public List<Integer> getStageList() {
        return stageList;
    }

    public void setStageList(List<Integer> stageList) {
        this.stageList = stageList;
    }
}
