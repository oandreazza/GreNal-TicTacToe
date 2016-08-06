package br.com.mauricio.ticTacToeGrenal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class MultiplayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);

        ListView gameList = (ListView) findViewById(R.id.gameList);

        List<String> games = new ArrayList<String>(asList("Grenal 5 x 1", "Vamooo Colorador", "Gre x Nal"));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,games);
        gameList.setAdapter(arrayAdapter);


    }
}
