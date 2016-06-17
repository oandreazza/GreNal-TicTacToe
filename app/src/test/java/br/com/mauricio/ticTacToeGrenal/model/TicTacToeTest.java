package br.com.mauricio.ticTacToeGrenal.model;

import org.junit.Before;
import org.junit.Test;

import br.com.mauricio.ticTacToeGrenal.exception.SpotAlreadyFilledException;
import br.com.mauricio.ticTacToeGrenal.types.Player;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


public class TicTacToeTest {

    private TicTacToe ticTacToe;

    @Before
    public void setUp() throws Exception {
        ticTacToe = new TicTacToe();
        ticTacToe.start();
    }


    @Test
    public void shouldSetPlayerNumberOnTheStageWhenPlay(){
        ticTacToe.play(Player.GREMIO,0);
        assertEquals(Player.GREMIO.getPlayerNumber(),ticTacToe.getPlayerNumberByLocationStage(0));
    }

    @Test
    public void shouldReturnFalseWhenPlayOnSpotThatAlreadyPlayed(){
        ticTacToe.play(Player.GREMIO,0);
        assertFalse(ticTacToe.canPlay(0));
    }

    @Test
    public void shouldReturnTrueWhenPlayOnSpotAvailable(){
        ticTacToe.play(Player.GREMIO,1);
        assertTrue(ticTacToe.canPlay(0));
    }

    @Test
    public void shouldReturnTrueWhenWinnerFirsRow(){
        ticTacToe.playFirstRow();
        assertTrue(ticTacToe.hasWinner());
    }

    @Test
    public void shouldNotHaveWinnerWhenStartGame(){
     assertFalse(ticTacToe.hasWinner());
    }

    @Test(expected = SpotAlreadyFilledException.class)
    public void shouldThrowExceptionWhenPlayInSpotThatAlreadyFilled(){
        ticTacToe.play(Player.GREMIO,0);

        ticTacToe.play(Player.INTER, 0);
    }

    @Test
    public void shouldReturnTrueWhenAreNineAndDoesntHaveWinner(){
        ticTacToe.setMoves(9);

        assertTrue(ticTacToe.hasDraw());
    }

    @Test
    public void shouldReturnFalseWhenMoveAreLowerThanNine(){
        ticTacToe.setMoves(2);

        assertFalse(ticTacToe.hasDraw());
    }


}