package br.com.mauricio.ticTacToeGrenal.model;

import org.junit.Before;
import org.junit.Test;

import br.com.mauricio.ticTacToeGrenal.types.Player;

import static junit.framework.Assert.assertEquals;


/**
 * Created by mauricio on 6/7/16.
 */
public class TicTacToeTest {

    private TicTacToe ticTacToe;

    @Before
    public void setUp() throws Exception {
        ticTacToe = new TicTacToe();
    }


    @Test
    public void shouldSetPlayerNumberOnTheStageWhenPlay(){
        ticTacToe.play(Player.GREMIO,0);
        assertEquals(Player.GREMIO.getPlayerNumber(),ticTacToe.getPlayerNumberByLocationStage(0));
    }
}