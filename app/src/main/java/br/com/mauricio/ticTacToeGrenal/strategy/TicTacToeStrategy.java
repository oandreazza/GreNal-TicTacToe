package br.com.mauricio.ticTacToeGrenal.strategy;

import br.com.mauricio.ticTacToeGrenal.exception.DrawException;
import br.com.mauricio.ticTacToeGrenal.exception.WinnerException;
import br.com.mauricio.ticTacToeGrenal.model.TicTacToe;

public class TicTacToeStrategy {


    private  TicTacToe ticTacToe;
    private int winnerPositions[][] = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    public TicTacToeStrategy(TicTacToe ticTacToe) {
        this.ticTacToe = ticTacToe;
    }

    private void hasWinner() {
        boolean hasWinner = false;
        for (int[] winnerPosition: winnerPositions) {
            hasWinner = (this.ticTacToe.getStagePosition(winnerPosition[0]) == this.ticTacToe.getStagePosition(winnerPosition[1]))
                    && (this.ticTacToe.getStagePosition(winnerPosition[1]) == this.ticTacToe.getStagePosition(winnerPosition[2]))
                    && this.ticTacToe.getStagePosition(winnerPosition[0]) != 2;

            if(hasWinner)
                throw new WinnerException();
        }

    }

    private void hasDraw() {
        if(this.ticTacToe.getMoves() == 9)
            throw new DrawException();
    }


    public void getSituation() {
        hasWinner();
        hasDraw();
    }


}
