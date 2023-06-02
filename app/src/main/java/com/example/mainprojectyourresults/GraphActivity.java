package com.example.mainprojectyourresults;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class GraphActivity extends AppCompatActivity {
    private Button btnInsert;
    private Spinner variantsOfDistances;
    private GraphView graph;
    private TextView changeDistance;

    private DatabaseReference databaseReference;
    private DatabaseReference eventDatabaseReference;
    private DatabaseReference listOfDistancesDatabaseReference;
    private String valVariantsOfDistances;
    int allDay;
    private FirebaseAuth auth;
    double timeInSecondsPlusMillisecondsInt;

    private FirebaseUser user;
    LineGraphSeries<DataPoint> series;

    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        init();
        getOnItemSelectedListenerForCategoryEditText();
        setOnClickListenerForBtnInsert();

    }
    private void init() {
        btnInsert = findViewById(R.id.btnInsert);
        variantsOfDistances = findViewById(R.id.variantsOfDistances);
        graph = findViewById(R.id.graph);
        changeDistance = findViewById(R.id.changeDistance);
        ArrayAdapter<CharSequence> adapterVariantsOfDistances = ArrayAdapter.createFromResource(this,
                R.array.distances_array, android.R.layout.simple_spinner_item);
        adapterVariantsOfDistances.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        variantsOfDistances.setAdapter(adapterVariantsOfDistances);


        databaseReference = FirebaseDatabase.getInstance("https://mainprojectyourresults-77941-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference();
        listOfDistancesDatabaseReference = databaseReference.child("ListOfDistances");
        eventDatabaseReference = databaseReference.child("Calendar");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        email = getEmailWithoutPoint();
    }
    private void setOnClickListenerForBtnInsert(){
        btnInsert.setOnClickListener(v ->{
            getData();

        });
    }

    private String getEmailWithoutPoint(){
        String email = user.getEmail();
        assert email != null;
        int index = email.indexOf(".");
        return email.substring(0, index);

    }
    private void getOnItemSelectedListenerForCategoryEditText(){

        variantsOfDistances.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String[] choose;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int selectedItemPosition, long l) {
                choose = getResources().getStringArray(R.array.distances_array);
                valVariantsOfDistances = String.valueOf(choose[selectedItemPosition]);

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }

        });


    }
    private void getData(){

        eventDatabaseReference.child(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<DataSnapshot> matchingChildren = new ArrayList<>();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    if (childSnapshot.child("distance").getValue(String.class).equals(valVariantsOfDistances)) {
                        matchingChildren.add(childSnapshot);
                        List<String> childValues = new ArrayList<>();
                        for (DataSnapshot valueSnapshot : childSnapshot.getChildren()) {
                            childValues.add(valueSnapshot.getValue(String.class));
                        }


                        //день
                        String date = childValues.get(2);
                        String[] partsOfDate = date.split(":");
                        String day = partsOfDate[0];
                        String month = partsOfDate[1];
                        String year = partsOfDate[2];
                        allDay = Integer.parseInt(day + month + year);


                        //результат
                        String result = childValues.get(4);
                        String[] parts = result.split(":");
                        int minutes = Integer.parseInt(parts[0]);
                        int seconds = Integer.parseInt(parts[1]);
                        int milliseconds = Integer.parseInt(parts[2]);
                        int totalSeconds = minutes * 60 + seconds;
                        String timeInSecondsPlusMilliseconds = totalSeconds + "." + milliseconds;
                        timeInSecondsPlusMillisecondsInt = Double.parseDouble(timeInSecondsPlusMilliseconds);
                        series = new LineGraphSeries<>(new DataPoint[] {
                                new DataPoint(timeInSecondsPlusMillisecondsInt, allDay)
                        });





                    }
                }

                graph.addSeries(series);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }


}