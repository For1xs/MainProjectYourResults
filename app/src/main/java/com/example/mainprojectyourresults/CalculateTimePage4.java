package com.example.mainprojectyourresults;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import com.example.mainprojectyourresults.data.RankTableManAutoData;
import com.example.mainprojectyourresults.data.RankTableWomanAutoData;
import com.example.mainprojectyourresults.data.TableOfDistances;
import com.google.firebase.auth.FirebaseAuth;

import android.widget.Spinner;

import java.util.Objects;

public class CalculateTimePage4 extends AppCompatActivity {
    //    private TextView test;
    private EditText distanceEditTextPage4;
    private Spinner categoryEditTextPage4;
    private Spinner categoryRunEditTextPage4;
    private Button calculateButtonPage4;
    private TextView timeToRoundTextViewPage4;
    private String valDistanceEditTextPage4;
    private String valCategoryEditTextPage4;
    private String valCategoryRunEditTextPage4;
    private final RankTableManAutoData rankTableManAutoData = new RankTableManAutoData();
    private final RankTableWomanAutoData rankTableWomanAutoData = new RankTableWomanAutoData();
    private final TableOfDistances tableOfDistances = new TableOfDistances();
    private ImageButton goToFirstActivity;
    private ImageButton goToThirdActivity;
    private ImageButton goToFourthActivity;
    private ImageButton signOutButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_time_page4);
        Objects.requireNonNull(getSupportActionBar()).hide();
        init();
        onClick();
        getOnItemSelectedListenerForCategoryEditTextPage4();
        getOnItemSelectedListenerForRunCategoryEditTextPage4();
        goToAnotherActivities();
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
    private void goToAnotherActivities(){
        goToFirstActivity = findViewById(R.id.goToFirstActivity);
        goToThirdActivity = findViewById(R.id.goToThirdActivity);
        goToFourthActivity = findViewById(R.id.goToFourthActivity);

        goToFirstActivity.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        goToThirdActivity.setOnClickListener(v ->{
            Intent intent = new Intent(this, YourCategoryPage3.class);
            startActivity(intent);
        });
        goToFourthActivity.setOnClickListener(v ->{
            Intent intent = new Intent(this, CalculateTimePage4.class);
            startActivity(intent);
        });
    }
    private void init(){
        categoryEditTextPage4 = findViewById(R.id.categoryEditTextPage4);
        distanceEditTextPage4 = findViewById(R.id.editTextDistance);
        categoryRunEditTextPage4 = findViewById(R.id.categoryRunEditTextPage4);
        calculateButtonPage4 = findViewById(R.id.calculateButtonPage4);
        timeToRoundTextViewPage4 = findViewById(R.id.timeToRoundTextViewPage4);
        signOutButton = findViewById(R.id.signOutButton4);

        ArrayAdapter<CharSequence> adapterForCategoryEditTextPage4 = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapterForCategoryEditTextPage4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryEditTextPage4.setAdapter(adapterForCategoryEditTextPage4);

        ArrayAdapter<CharSequence> adapterForRunCategoryEditTextPage4 = ArrayAdapter.createFromResource(this,
                R.array.run_category_array, android.R.layout.simple_spinner_item);
        adapterForRunCategoryEditTextPage4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryRunEditTextPage4.setAdapter(adapterForRunCategoryEditTextPage4);





    }
    private void getOnItemSelectedListenerForCategoryEditTextPage4(){

        categoryEditTextPage4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String[] choose;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int selectedItemPosition, long l) {
                choose = getResources().getStringArray(R.array.categories_array);
                valCategoryEditTextPage4 = String.valueOf(choose[selectedItemPosition]);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }

        });


    }

    private void getOnItemSelectedListenerForRunCategoryEditTextPage4(){

        categoryRunEditTextPage4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String[] choose;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int selectedItemPosition, long l) {
                choose = getResources().getStringArray(R.array.run_category_array);
                valCategoryRunEditTextPage4 = String.valueOf(choose[selectedItemPosition]);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }

        });


    }


    private void onClick(){
        calculateButtonPage4.setOnClickListener( v->{
            valDistanceEditTextPage4 = distanceEditTextPage4.getText().toString();

            String[] arrayOfDouble = String.valueOf(calculateTimeToRound()).split("[.]");
            if(!isTheElementInTheList()){
                timeToRoundTextViewPage4.setText("Неправильные данные");
            }
            else{
                timeToRoundTextViewPage4.setText(arrayOfDouble[0]+ "." + arrayOfDouble[1]);
            }
//            String a = String.valueOf(isTheElementInTheList());
//            test.setText(a);
        });
    }

    private double calculateTimeToRound(){
        double valCalculateTimeToRound = 0;


        if(isTheElementInTheList()) {
            String[] arrayOfStrings = valDistanceEditTextPage4.split(" ");
            double numberOfRounds = Double.parseDouble(arrayOfStrings[0])/Double.parseDouble(arrayOfStrings[1]);


            String[] arrayOfDouble = String.valueOf(getTime()).split("[.]");
            double seconds = Double.parseDouble(arrayOfDouble[0]);
            valCalculateTimeToRound = seconds / numberOfRounds;
        } else{
            valCalculateTimeToRound = 0;
        }
        return valCalculateTimeToRound;

    }

    private double getTime(){
        double time = 0;
        if(isTheElementInTheList() && (getFirstIndex() != 100) && (getSecondIndex() != 100)){
            if(Objects.equals(valCategoryRunEditTextPage4, "Woman auto")){
                time = rankTableWomanAutoData.getRankTableWomanAuto()[getFirstIndex()][getSecondIndex()];

            }
            else if(Objects.equals(valCategoryRunEditTextPage4, "Man auto")){
                time = rankTableManAutoData.getRankTableManAuto()[getFirstIndex()][getSecondIndex()];

            }
        } else{
            return time;
        }

        return time;
    }
    private boolean isTheElementInTheList(){
        boolean valIsTheElementInTheList = false;
        for (String element : tableOfDistances.getTableOfDistances()) {
            if (Objects.equals(valDistanceEditTextPage4, element)) {
                valIsTheElementInTheList = true;
            }
//
        }
        return valIsTheElementInTheList;
    }
    private int getFirstIndex(){
        int index1 = 0;
        if(isTheElementInTheList()) {
            if (Objects.equals(valDistanceEditTextPage4, "60")) {
                index1 = 0;

            } else if (Objects.equals(valDistanceEditTextPage4, "100")) {
                index1 = 1;
            } else if (Objects.equals(valDistanceEditTextPage4, "200")) {
                index1 = 2;
            } else if (Objects.equals(valDistanceEditTextPage4, "200 200")) {
                index1 = 3;
            } else if (Objects.equals(valDistanceEditTextPage4, "300")) {
                index1 = 4;
            } else if (Objects.equals(valDistanceEditTextPage4, "400 400")) {
                index1 = 5;
            } else if (Objects.equals(valDistanceEditTextPage4, "400 200")) {
                index1 = 6;
            } else if (Objects.equals(valDistanceEditTextPage4, "600 400")) {
                index1 = 7;
            } else if (Objects.equals(valDistanceEditTextPage4, "600 200")) {
                index1 = 8;
            } else if (Objects.equals(valDistanceEditTextPage4, "800 400")) {
                index1 = 9;
            } else if (Objects.equals(valDistanceEditTextPage4, "800 200")) {
                index1 = 10;
            } else if (Objects.equals(valDistanceEditTextPage4, "1000 400")) {
                index1 = 11;
            } else if (Objects.equals(valDistanceEditTextPage4, "1000 200")) {
                index1 = 12;
            } else if (Objects.equals(valDistanceEditTextPage4, "1500 400")) {
                index1 = 13;
            } else if (Objects.equals(valDistanceEditTextPage4, "1500 200")) {
                index1 = 14;
            } else if (Objects.equals(valDistanceEditTextPage4, "1 мил")) {
                index1 = 15;
            } else if (Objects.equals(valDistanceEditTextPage4, "3000 400")) {
                index1 = 16;
            } else if (Objects.equals(valDistanceEditTextPage4, "3000 200")) {
                index1 = 17;
            } else if (Objects.equals(valDistanceEditTextPage4, "5000")) {
                index1 = 18;
            } else if (Objects.equals(valDistanceEditTextPage4, "10000")) {
                index1 = 19;
            }
        } else {
            index1 = 100;
        }

        return index1;

    }
    private int getSecondIndex(){
        int index2 = 0;
        if(isTheElementInTheList()){
            if(Objects.equals(valCategoryEditTextPage4, "MSMK")){
                index2 = 0;
            }
            else if(Objects.equals(valCategoryEditTextPage4, "MS")){
                index2 = 1;
            }
            else if(Objects.equals(valCategoryEditTextPage4, "KMS")){
                index2 = 2;
            }
            else if(Objects.equals(valCategoryEditTextPage4, "I")){
                index2 = 3;
            }
            else if(Objects.equals(valCategoryEditTextPage4, "II")){
                index2 = 4;
            }
            else if(Objects.equals(valCategoryEditTextPage4, "III")){
                index2 = 5;
            }
            else if(Objects.equals(valCategoryEditTextPage4, "I(y)")){
                index2 = 6;
            }
            else if(Objects.equals(valCategoryEditTextPage4, "II(y)")){
                index2 = 7;
            }
            else if(Objects.equals(valCategoryEditTextPage4, "III(y)")){
                index2 = 8;
            }
            if(index2 == 0 && !Objects.equals(valCategoryEditTextPage4, "MSMK")){
                return 100;
            }
        } else {
            index2 = 100;
        }

        return index2;

    }

}