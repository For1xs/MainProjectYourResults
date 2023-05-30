package com.example.mainprojectyourresults;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private CalendarView mainCalendar;

    private DatabaseReference databaseReference;
    private String stringDateSelected;
    private DatabaseReference eventDatabaseReference;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private ImageButton signOutButton;
    private Button saveEventButton;
    private EditText editTextNameOfTheCompetition;
    private EditText editTextDistance;
    private EditText editTextСategory;
    private EditText editTextResult;
    private ArrayList<Event> membersOfEvent = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    private List<String> listData;
    private ImageButton goToThirdActivity;
    private ImageButton goToFourthActivity;
    private String email;
    private LoginActivity loginActivity = new LoginActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        goToAnotherActivities();
        init();
        initializeMainCalendarSelectedDayChange();
        test();
        setOnClickListenerForSignOutButton();





    }

    private void setOnClickListenerForSignOutButton(){
        signOutButton.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();

        });
    }
    private void test(){
        if (user == null){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            return;
        }

    }
    private void init(){
        mainCalendar = findViewById(R.id.mainCalendar);
        listData = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this,R.layout.dialog_set_event, listData);
        databaseReference = FirebaseDatabase.getInstance("https://mainprojectyourresults-77941-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference();
        eventDatabaseReference = databaseReference.child("Calendar");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        signOutButton = findViewById(R.id.signOutButton);
        email = getEmailWithoutPoint();

    }
    private String getEmailWithoutPoint(){
        String email = user.getEmail();
        assert email != null;
        return email.replaceAll("\\.", "");

    }
    private void goToAnotherActivities(){
        goToThirdActivity = findViewById(R.id.goToThirdActivity);
        goToFourthActivity = findViewById(R.id.goToFourthActivity);






        goToThirdActivity.setOnClickListener(v ->{
            Intent intent = new Intent(this, YourCategoryPage3.class);
            startActivity(intent);
        });
        goToFourthActivity.setOnClickListener(v ->{
            Intent intent = new Intent(this, CalculateTimePage4.class);
            startActivity(intent);
        });
    }

    private void initializeMainCalendarSelectedDayChange(){
        mainCalendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            stringDateSelected = dayOfMonth + ":" + (month + 1) + ":" + year;
            calendarClicked();
        });

    }


    private void calendarClicked(){
        setEventDialog();

        eventDatabaseReference.child(email).child(stringDateSelected).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    Event event = snapshot.getValue(Event.class);
                    editTextNameOfTheCompetition.setText(event.nameOfTheCompetition);
                    editTextDistance.setText(event.distance);
                    editTextСategory.setText(event.category);
                    editTextResult.setText(event.result);

                }
                else {
                    editTextNameOfTheCompetition.setText(null);
                    editTextDistance.setText(null);
                    editTextСategory.setText(null);
                    editTextResult.setText(null);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });


    }


    private void setEventDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater()
                .inflate(R.layout.dialog_set_event, null);
//        constraintLayout.setBackgroundResource(R.drawable.rounded_background);
        editTextNameOfTheCompetition = constraintLayout.findViewById(R.id.editTextNameOfTheCompetition);
        editTextDistance = constraintLayout.findViewById(R.id.editTextDistance);
        editTextСategory = constraintLayout.findViewById(R.id.editTextСategory);
        editTextResult = constraintLayout.findViewById(R.id.editTextResult);
        saveEventButton = constraintLayout.findViewById(R.id.saveEventButton);



        saveEventButton.setOnClickListener( v-> {




            String id = stringDateSelected;
            String nameOfTheCompetition = editTextNameOfTheCompetition.getText().toString();
            String distance = editTextDistance.getText().toString();
            String category = editTextСategory.getText().toString();
            String result = editTextResult.getText().toString();

            Event newEvent = new Event(id, nameOfTheCompetition, distance, category, result);
            eventDatabaseReference.child(email).child(stringDateSelected).setValue(newEvent);


        });

        builder.setView(constraintLayout);
        AlertDialog processingAlertDialog = builder.create();
        processingAlertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        processingAlertDialog.show();


    }


}