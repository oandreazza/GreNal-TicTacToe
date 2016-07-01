package br.com.mauricio.ticTacToeGrenal.strategy;


import org.junit.Test;

import br.com.mauricio.ticTacToeGrenal.exception.DrawException;
import br.com.mauricio.ticTacToeGrenal.exception.WinnerException;
import br.com.mauricio.ticTacToeGrenal.fixture.TicTacToeFixture;
import br.com.mauricio.ticTacToeGrenal.model.TicTacToe;


public class TicTacToeStrategyTest {

    private TicTacToeStrategy strategy;


    @Test(expected = WinnerException.class)
    public void shouldThrowWinnerExceptionWhenWinnerFirstRow() {
        TicTacToe ticTacToe = TicTacToeFixture.get().withWinnerFirstRow().build();
        strategy = new TicTacToeStrategy(ticTacToe);

        strategy.getSituation();
    }

    @Test(expected = DrawException.class)
    public void shouldTrhowDrawExceptionWhenHasDraw(){
        TicTacToe ticTacToe = TicTacToeFixture.get().withDraw().build();
        strategy = new TicTacToeStrategy(ticTacToe);

        strategy.getSituation();
    }

    @Test
    public void shouldReturnNothinWhenHasNoWinnerOrDraw(){
        TicTacToe ticTacToe = TicTacToeFixture.get().build();
        strategy = new TicTacToeStrategy(ticTacToe);

        strategy.getSituation();
    }
}
