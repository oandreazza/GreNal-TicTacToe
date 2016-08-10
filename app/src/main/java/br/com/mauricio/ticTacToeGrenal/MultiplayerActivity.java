package br.com.mauricio.ticTacToeGrenal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.mauricio.ticTacToeGrenal.model.Room;
import br.com.mauricio.ticTacToeGrenal.model.TicTacToe;

public class MultiplayerActivity extends AppCompatActivity {

    private List<String> games = new ArrayList<String>();
    private ListView gameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);
        gameList = (ListView) findViewById(R.id.gameList);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference roomsRef = db.getReference("multiplayer").child("rooms");

        FirebaseListAdapter<Room> adapter = new FirebaseListAdapter<Room>(this, Room.class, android.R.layout.simple_list_item_1, roomsRef) {
            @Override
            protected void populateView(View view, Room room, int position) {
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setText(room.getName());
            }
        };
        gameList.setAdapter(adapter);

        TicTacToe ticTacToe = new TicTacToe();
        ticTacToe.setList();
        DatabaseReference gamesReef = db.getReference("multiplayer").child("games");
        gamesReef.push().setValue(ticTacToe);



    }
}
