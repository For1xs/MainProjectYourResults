package com.example.mainprojectyourresults;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mainprojectyourresults.data.RankTableManAutoData;
import com.example.mainprojectyourresults.data.RankTableWomanAutoData;
import com.example.mainprojectyourresults.data.TableOfDistances;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class YourCategoryPage3 extends AppCompatActivity {
    private EditText distanceEditTextPage3;
    private Spinner categoryRunEditTextPage3;
    private EditText resultEditTextPage3;
    private Button findResultButton;
    private Button goToSecondActivity;

    private TextView categoryTextView;
    private String valDistanceEditTextPage3;
    private String valCategoryRunEditTextPage3;
    private final RankTableManAutoData rankTableManAutoData = new RankTableManAutoData();
    private final RankTableWomanAutoData rankTableWomanAutoData = new RankTableWomanAutoData();
    private String valResultEditTextPage3;
    private final TableOfDistances tableOfDistances = new TableOfDistances();
    private ImageButton goToFirstActivity;
    private ImageButton goToThirdActivity;
    private ImageButton goToFourthActivity;
    private ImageButton signOutButton;
    private ImageButton goToFifthActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_your_category_page3);
        init();
        onClick();
        getOnItemSelectedListenerForCategoryEditTextPage3();
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
        goToFifthActivity = findViewById(R.id.goToFifthActivity);



        goToFifthActivity.setOnClickListener(v ->{
            Intent intent = new Intent(this, GraphActivity.class);
            startActivity(intent);
        });
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
        goToSecondActivity.setOnClickListener(v -> {
            Intent intent = new Intent(this, TimeToCategoryActivityPage2.class);
            startActivity(intent);
        });
    }
    private void init(){
        distanceEditTextPage3 = findViewById(R.id.editTextDistance);
        categoryRunEditTextPage3 = findViewById(R.id.categoryRunEditTextPage3);
        resultEditTextPage3 = findViewById(R.id.resultEditTextPage3);
        findResultButton = findViewById(R.id.findResultButton);
        categoryTextView = findViewById(R.id.categoryTextView);
        signOutButton = findViewById(R.id.signOutButton3);
        goToSecondActivity = findViewById(R.id.goToSecondActivity);
        ArrayAdapter<CharSequence> adapterForRunCategoryEditTextPage4 = ArrayAdapter.createFromResource(this,
                R.array.run_category_array, android.R.layout.simple_spinner_item);
        adapterForRunCategoryEditTextPage4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryRunEditTextPage3.setAdapter(adapterForRunCategoryEditTextPage4);
    }

    private void getOnItemSelectedListenerForCategoryEditTextPage3(){

        categoryRunEditTextPage3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String[] choose;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int selectedItemPosition, long l) {
                choose = getResources().getStringArray(R.array.run_category_array);
                valCategoryRunEditTextPage3 = String.valueOf(choose[selectedItemPosition]);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }

        });


    }

    public void onClick(){
        findResultButton.setOnClickListener(v -> {
            valDistanceEditTextPage3 = distanceEditTextPage3.getText().toString();
            valResultEditTextPage3 = resultEditTextPage3.getText().toString();
            if(getCategory() == null){
                categoryTextView.setText("неправильные данные");
            }
            else{
                categoryTextView.setText(getCategory());
            }
        });
    }
    private boolean isTheElementInTheList(){
        boolean valIsЕheElementInTheList = false;
        for (String element : tableOfDistances.getTableOfDistances()) {
            if (Objects.equals(valDistanceEditTextPage3, element)) {
                valIsЕheElementInTheList = true;
            }
//
        }
        return valIsЕheElementInTheList;
    }
    private int getFirstIndex() {
        int index1 = 0;
        if (isTheElementInTheList()) {
            if (Objects.equals(valDistanceEditTextPage3, "60")) {
                index1 = 0;

            } else if (Objects.equals(valDistanceEditTextPage3, "100")) {
                index1 = 1;
            } else if (Objects.equals(valDistanceEditTextPage3, "200")) {
                index1 = 2;
            } else if (Objects.equals(valDistanceEditTextPage3, "200 200")) {
                index1 = 3;
            } else if (Objects.equals(valDistanceEditTextPage3, "300")) {
                index1 = 4;
            } else if (Objects.equals(valDistanceEditTextPage3, "400 400")) {
                index1 = 5;
            } else if (Objects.equals(valDistanceEditTextPage3, "400 200")) {
                index1 = 6;
            } else if (Objects.equals(valDistanceEditTextPage3, "600 400")) {
                index1 = 7;
            } else if (Objects.equals(valDistanceEditTextPage3, "600 200")) {
                index1 = 8;
            } else if (Objects.equals(valDistanceEditTextPage3, "800 400")) {
                index1 = 9;
            } else if (Objects.equals(valDistanceEditTextPage3, "800 200")) {
                index1 = 10;
            } else if (Objects.equals(valDistanceEditTextPage3, "1000 400")) {
                index1 = 11;
            } else if (Objects.equals(valDistanceEditTextPage3, "1000 200")) {
                index1 = 12;
            } else if (Objects.equals(valDistanceEditTextPage3, "1500 400")) {
                index1 = 13;
            } else if (Objects.equals(valDistanceEditTextPage3, "1500 200")) {
                index1 = 14;
            } else if (Objects.equals(valDistanceEditTextPage3, "1 мил")) {
                index1 = 15;
            } else if (Objects.equals(valDistanceEditTextPage3, "3000 400")) {
                index1 = 16;
            } else if (Objects.equals(valDistanceEditTextPage3, "3000 200")) {
                index1 = 17;
            } else if (Objects.equals(valDistanceEditTextPage3, "5000")) {
                index1 = 18;
            } else if (Objects.equals(valDistanceEditTextPage3, "10000")) {
                index1 = 19;
            }
            if (index1 == 0 && !Objects.equals(valDistanceEditTextPage3, "60")) {
                return 69;
            } else {
                return index1;
            }
        } else categoryTextView.setText("неправильные данные");
        return index1;
    }
    private String getTimeInSecondsPlusMilliseconds(){

        String[] parts = valResultEditTextPage3.split(":");
        int minutes = Integer.parseInt(parts[0]);
        int seconds = Integer.parseInt(parts[1]);
        int milliseconds = Integer.parseInt(parts[2]);

        int totalSeconds = minutes * 60 + seconds;
        String timeInSecondsPlusMilliseconds = totalSeconds + "." + milliseconds;
        return timeInSecondsPlusMilliseconds;

    }
    private int getCategoryNum(){
        int i = 0;
        int j = getFirstIndex();

        if(Objects.equals(valCategoryRunEditTextPage3, "Man auto")){
            for(i = 0; i < rankTableManAutoData.getRankTableManAuto().length; ){
                if(Double.parseDouble(getTimeInSecondsPlusMilliseconds()) > rankTableManAutoData.getRankTableManAuto()[j][i]){
                    i++;
                }
                else{
                    break;
                }
            }
        }
        else if(Objects.equals(valCategoryRunEditTextPage3, "Woman auto")){
            for(i = 0; i < rankTableWomanAutoData.getRankTableWomanAuto().length; ){
                if(Double.parseDouble(getTimeInSecondsPlusMilliseconds()) > rankTableWomanAutoData.getRankTableWomanAuto()[j][i]){
                    i++;
                }
                else{
                    break;
                }
            }
        }else{
            i = 68;
        }
        return i+1;
    }
    private String getCategory(){
        String valCategory = null;
        if(isTheElementInTheList()) {
            if (getCategoryNum() == 1) {
                valCategory = "MSMK";
            } else if (getCategoryNum() == 2) {
                valCategory = "MS";
            } else if (getCategoryNum() == 3) {
                valCategory = "KMS";
            } else if (getCategoryNum() == 4) {
                valCategory = "I";
            } else if (getCategoryNum() == 5) {
                valCategory = "II";
            } else if (getCategoryNum() == 6) {
                valCategory = "III";
            } else if (getCategoryNum() == 7) {
                valCategory = "I(y)";
            } else if (getCategoryNum() == 8) {
                valCategory = "II(y)";
            } else if (getCategoryNum() == 9) {
                valCategory = "III(y)";
            } else if (getCategoryNum() == 69) {
                valCategory = "Неправильные данные";
            }

        }else{
            return valCategory;
        }
        return valCategory;

    }
}