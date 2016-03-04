package t14.com.GameRentals;

<<<<<<< HEAD
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
=======
>>>>>>> origin/Austin
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
=======
>>>>>>> origin/Austin

/**
 * Created by yourui on 3/2/16.
 */
public class MyItemsFragment extends Fragment {
<<<<<<< HEAD
    private GameList gameList;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gameList = new GameList();
        View v = inflater.inflate(R.layout.my_items, container,false);
        Button addButton = (Button)v.findViewById(R.id.AddButton);
        ListView myItems = (ListView)v.findViewById(R.id.myItems);
        addButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddGameActivity.class);
                startActivity(intent);
            }
        });
        myItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
                adb.setMessage("Do you want to edit it?");
                adb.setCancelable(true);

                adb.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(getActivity(), EditGameActivity.class);
                        startActivity(intent);
                        Toast.makeText(getActivity(), "edit", Toast.LENGTH_SHORT).show();
                    }
                });
                adb.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                adb.show();
            }
        });
        return v;
    }

=======

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        return inflater.inflate(R.layout.my_items, container,false);
    }
>>>>>>> origin/Austin
    
}