package br.com.mauricio.ticTacToeGrenal.fixture;

import br.com.mauricio.ticTacToeGrenal.model.TicTacToe;


public class TicTacToeFixture {
    private TicTacToe ticTacToe = new TicTacToe();

    public static TicTacToeFixture get() {
        return new TicTacToeFixture();
    }

    public TicTacToe build() {
        return this.ticTacToe;
    }

    public TicTacToeFixture withWinnerFirstRow() {
        this.ticTacToe.playFirstRow();
        return this;
    }

    public TicTacToeFixture withDraw() {
        this.ticTacToe.playDraw();
        return this;
    }
}
