package com.assignment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TripSurvey#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TripSurvey extends Fragment {
    ListView listContainer;
    ArrayList<String> trips_name;
    ArrayList<Trips> trips_instance;
    Button btn;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TripSurvey() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TripSurvey.
     */
    // TODO: Rename and change types and number of parameters
    public static TripSurvey newInstance(String param1, String param2) {
        TripSurvey fragment = new TripSurvey();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trip_survey, container, false);
    }

    @Override
    @SuppressLint("Range")
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        trips_name = new ArrayList<String>();
        trips_name.add("No trips found! Create new trips.");

        DatabaseHelper db = new DatabaseHelper(getContext().getApplicationContext());
        Cursor cursor = db.ReadRecord(DatabaseHelper.table_trip, "*", "", "");
        if (cursor.moveToFirst()) {
            trips_name.clear();
            trips_instance = new ArrayList<>();
            do {
                Trips t = new Trips(
                        cursor.getString(cursor.getColumnIndex("name")).toString(),
                        cursor.getString(cursor.getColumnIndex("email")).toString(),
                        cursor.getString(cursor.getColumnIndex("place_type")).toString(),
                        cursor.getString(cursor.getColumnIndex("date_from")).toString(),
                        cursor.getString(cursor.getColumnIndex("date_to")).toString(),
                        cursor.getString(cursor.getColumnIndex("travel_via")).toString(),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex("no_of_person")).toString()),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex("no_of_days")).toString()),
                        Float.parseFloat(cursor.getString(cursor.getColumnIndex("price_from")).toString()),
                        Float.parseFloat(cursor.getString(cursor.getColumnIndex("price_to")).toString()),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")).toString())
                );
                trips_instance.add(t);
                trips_name.add(t.getName());
            } while (cursor.moveToNext());
        } else
            Toast.makeText(getContext().getApplicationContext(), "No trips available.", Toast.LENGTH_SHORT).show();


        listContainer = view.findViewById(R.id.parent_container);
        ArrayAdapter ad = new ArrayAdapter(getContext().getApplicationContext(), R.layout.triplist_layout, trips_name);
        listContainer.setAdapter(ad);
        btn = view.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext().getApplicationContext(), TripActivity.class));
            }
        });
        registerForContextMenu(listContainer);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.trip_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.update:
                Intent i = new Intent(getContext().getApplicationContext(), TripActivity.class);
                i.putExtra("trip", trips_instance.get((int) info.id));
                startActivity(i);
                break;
            case R.id.delete:
                System.out.println(trips_instance);
                String[] where = {"" + trips_instance.get((int) info.id).getTrip_id()};
                new DatabaseHelper(getContext().getApplicationContext()).deleteRecord(DatabaseHelper.table_trip, "id=?", where, getContext().getApplicationContext());
                trips_name.remove((int) info.id);
                trips_instance.remove((int) info.id);
                this.onResume();
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.onViewCreated(getView(), null);
    }
}