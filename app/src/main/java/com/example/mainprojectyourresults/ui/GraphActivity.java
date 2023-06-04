package com.example.mainprojectyourresults.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mainprojectyourresults.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
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
    private ImageButton goToThirdActivity;
    private ImageButton goToFourthActivity;
    private ImageButton goToFirstActivity;
    double timeInSecondsPlusMillisecondsInt;

    private FirebaseUser user;
    LineGraphSeries<DataPoint> series;

    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_graph);
        init();
        getOnItemSelectedListenerForCategoryEditText();
        setOnClickListenerForBtnInsert();
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        goToAnotherActivities();
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
    private void goToAnotherActivities(){
        goToFirstActivity = findViewById(R.id.goToFirstActivity);
        goToThirdActivity = findViewById(R.id.goToThirdActivity);
        goToFourthActivity = findViewById(R.id.goToFourthActivity);





        goToFirstActivity.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, MainActivity.class);
            startActivity(intent1);
            finish();
        });
        goToThirdActivity.setOnClickListener(v ->{
            Intent intent2 = new Intent(this, YourCategoryPage.class);
            startActivity(intent2);
            finish();
        });
        goToFourthActivity.setOnClickListener(v ->{
            Intent intent3 = new Intent(this, CalculateTimePage.class);
            startActivity(intent3);
            finish();
        });
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
                DataPoint[] dp = new DataPoint[0];
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    int point = 0;
                    if (childSnapshot.child("distance").getValue(String.class).equals(valVariantsOfDistances)) {
                        matchingChildren.add(childSnapshot);
                        List<String> childValues = new ArrayList<>();
                        for (DataSnapshot valueSnapshot : childSnapshot.getChildren()) {
                            childValues.add(valueSnapshot.getValue(String.class));
                            point++;
                        }


                        //день
                        String date = childValues.get(2);
                        String[] partsOfDate = date.split(":");
                        int day = Integer.parseInt(partsOfDate[0]);
                        int month = Integer.parseInt(partsOfDate[1]);
                        int year = Integer.parseInt(partsOfDate[2]);
//                        allDay = Double.parseDouble(day + "." + month);
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month - 1, day); // month is zero-indexed
                        Date allDay = calendar.getTime();


                        //результат
                        String result = childValues.get(4);
                        String[] parts = result.split(":");
//                        int minutes = Integer.parseInt(parts[0]);
//                        int seconds = Integer.parseInt(parts[1]);
//                        int milliseconds = Integer.parseInt(parts[2]);
//                        int totalSeconds = minutes * 60 + seconds;
//                        String timeInSecondsPlusMilliseconds = totalSeconds + "." + milliseconds;
                        int minutes = Integer.parseInt(parts[0]);
                        int seconds = Integer.parseInt(parts[1]);
                        String timeInSecondsPlusMilliseconds = minutes + "." + seconds;
                        timeInSecondsPlusMillisecondsInt = Double.parseDouble(timeInSecondsPlusMilliseconds);


                        series = new LineGraphSeries<>(new DataPoint[]{
                                new DataPoint(allDay, timeInSecondsPlusMillisecondsInt)
                        });
                        series.setColor(Color.argb(200, 162, 201, 255));
                        series.setDrawAsPath(true);
                        series.setDataPointsRadius(20);
                        series.setThickness(8);
                        series.setDrawDataPoints(true);
//                        graph.getGridLabelRenderer().setHumanRounding(false);
                        graph.getGridLabelRenderer().setNumHorizontalLabels(4); // only 4 because of the space
                        graph.addSeries(series);

                    } else if (point == 0) {
                    }



                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }




}
