package com.example.mainprojectyourresults;

import androidx.annotation.Nullable;
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

public class TimeToCategoryActivityPage2 extends AppCompatActivity {

    EditText distanceEditText;
    Spinner categoryRunEditText;
    Button findTimeButton;
    TextView yourCategory;
    RankTableManAutoData rankTableManAutoData = new RankTableManAutoData();
    RankTableWomanAutoData rankTableWomanAutoData = new RankTableWomanAutoData();
    String valCategoryRunEditText;
    String valCategoryEditText;

    String valDistanceEditText;
    private final TableOfDistances tableOfDistances = new TableOfDistances();
    private Spinner categoryEditText;
    private ImageButton goToFirstActivity;
    private ImageButton goToThirdActivity;
    private ImageButton goToFourthActivity;
    private ImageButton signOutButton;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_time_to_category_page2);
        init();
        getOnItemSelectedListenerForRunCategoryEditTextPage2();
        getOnItemSelectedListenerForCategoryEditText();
        goToAnotherActivities();
        onClick();
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
            Intent intent1 = new Intent(this, MainActivity.class);
            startActivity(intent1);
            finish();
        });
        goToThirdActivity.setOnClickListener(v ->{
            Intent intent2 = new Intent(this, YourCategoryPage3.class);
            startActivity(intent2);
            finish();
        });
        goToFourthActivity.setOnClickListener(v ->{
            Intent intent3 = new Intent(this, CalculateTimePage4.class);
            startActivity(intent3);
            finish();
        });
    }
    private void init(){
        distanceEditText = findViewById(R.id.editTextDistance);
        categoryRunEditText = findViewById(R.id.categoryRunEditText);
        categoryEditText = findViewById(R.id.categoryEditTextPage4);
        findTimeButton = findViewById(R.id.findTimeButton);
        yourCategory = findViewById(R.id.yourCategory);
        signOutButton = findViewById(R.id.signOutButton2);


        ArrayAdapter<CharSequence> adapterForCategoryEditTextPage2 = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapterForCategoryEditTextPage2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryEditText.setAdapter(adapterForCategoryEditTextPage2);

        ArrayAdapter<CharSequence> adapterForRunCategoryEditTextPage2 = ArrayAdapter.createFromResource(this,
                R.array.run_category_array, android.R.layout.simple_spinner_item);
        adapterForRunCategoryEditTextPage2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryRunEditText.setAdapter(adapterForRunCategoryEditTextPage2);
    }
    private void getOnItemSelectedListenerForCategoryEditText(){

        categoryEditText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String[] choose;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int selectedItemPosition, long l) {
                choose = getResources().getStringArray(R.array.categories_array);
                valCategoryEditText = String.valueOf(choose[selectedItemPosition]);

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }

        });


    }
    private void getOnItemSelectedListenerForRunCategoryEditTextPage2(){

        categoryRunEditText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String[] choose;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int selectedItemPosition, long l) {
                choose = getResources().getStringArray(R.array.run_category_array);
                valCategoryRunEditText = String.valueOf(choose[selectedItemPosition]);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }

        });


    }
    private void onClick(){


        findTimeButton.setOnClickListener( v-> {

            valDistanceEditText = distanceEditText.getText().toString();
            if(isTheElementInTheList()){
                if(!((getSecondIndex() == 0 ) && (Objects.equals(valCategoryEditText,"MSMK"))) || !((getFirstIndex() == 0) && (Objects.equals(valDistanceEditText,"60")))){
                    if((Objects.equals(valCategoryRunEditText, "Woman auto")) || (valCategoryRunEditText.equals("Man auto"))) {
                        if(!(getFirstIndex() == 100) || !(getSecondIndex() == 100 )){

                            if(Objects.equals(valCategoryRunEditText, "Woman auto")){
                                String time = String.valueOf(rankTableWomanAutoData.getRankTableWomanAuto()[getFirstIndex()][getSecondIndex()]);
                                yourCategory.setText(time);
                            }
                            else if(Objects.equals(valCategoryRunEditText, "Man auto")){
                                String time = String.valueOf(rankTableManAutoData.getRankTableManAuto()[getFirstIndex()][getSecondIndex()]);
                                yourCategory.setText(time);
                            }
                        }
                        else{
                            yourCategory.setText("Неправильные данные");
                        }
                    }
                    else{
                        yourCategory.setText("Неправильные данные");

                    }
                }
                else{
                    yourCategory.setText("Неправильные данные");
                }
            }
            else{
                yourCategory.setText("Неправильные данные");
            }





        });
    }

    private boolean isTheElementInTheList(){
        boolean valIsTheElementInTheList = false;
        for (String element : tableOfDistances.getTableOfDistances()) {
            if (Objects.equals(valDistanceEditText, element)) {
                valIsTheElementInTheList = true;
            }

        }
        return valIsTheElementInTheList;
    }
    private int getFirstIndex(){
        int index1 = 0;
        if(isTheElementInTheList()){
            if(Objects.equals(valDistanceEditText, "60")){
                index1 = 0;

            }
            else if(Objects.equals(valDistanceEditText, "100")){
                index1 = 1;
            }
            else if(Objects.equals(valDistanceEditText, "200")){
                index1 = 2;
            }
            else if(Objects.equals(valDistanceEditText, "200 200")){
                index1 = 3;
            }
            else if(Objects.equals(valDistanceEditText, "300")){
                index1 = 4;
            }
            else if(Objects.equals(valDistanceEditText, "400 400")){
                index1 = 5;
            }
            else if(Objects.equals(valDistanceEditText, "400 200")){
                index1 = 6;
            }
            else if(Objects.equals(valDistanceEditText, "600 400")){
                index1 = 7;
            }
            else if(Objects.equals(valDistanceEditText, "600 200")){
                index1 = 8;
            }
            else if(Objects.equals(valDistanceEditText, "800 400")){
                index1 = 9;
            }
            else if(Objects.equals(valDistanceEditText, "800 200")){
                index1 = 10;
            }
            else if(Objects.equals(valDistanceEditText, "1000 400")){
                index1 = 11;
            }
            else if(Objects.equals(valDistanceEditText, "1000 200")){
                index1 = 12;
            }
            else if(Objects.equals(valDistanceEditText, "1500 400")){
                index1 = 13;
            }
            else if(Objects.equals(valDistanceEditText, "1500 200")){
                index1 = 14;
            }
            else if(Objects.equals(valDistanceEditText, "1 мил")){
                index1 = 15;
            }
            else if(Objects.equals(valDistanceEditText, "3000 400")){
                index1 = 16;
            }
            else if(Objects.equals(valDistanceEditText, "3000 200")){
                index1 = 17;
            }
            else if(Objects.equals(valDistanceEditText, "5000")){
                index1 = 18;
            }
            else if(Objects.equals(valDistanceEditText, "10000")){
                index1 = 19;
            }
            else if(index1 == 0 && !Objects.equals(valDistanceEditText, "60")){
                index1 =  100;
            }
        }
        else{
            return index1;
        }
        return index1;


    }
    private int getSecondIndex(){
        int index2 = 0;
        if(Objects.equals(valCategoryEditText, "MSMK")){
            index2 = 0;
        }
        else if(Objects.equals(valCategoryEditText, "MS")){
            index2 = 1;
        }
        else if(Objects.equals(valCategoryEditText, "KMS")){
            index2 = 2;
        }
        else if(Objects.equals(valCategoryEditText, "I")){
            index2 = 3;
        }
        else if(Objects.equals(valCategoryEditText, "II")){
            index2 = 4;
        }
        else if(Objects.equals(valCategoryEditText, "III")){
            index2 = 5;
        }
        else if(Objects.equals(valCategoryEditText, "I(y)")){
            index2 = 6;
        }
        else if(Objects.equals(valCategoryEditText, "II(y)")){
            index2 = 7;
        }
        else if(Objects.equals(valCategoryEditText, "III(y)")){
            index2 = 8;
        }
        else if(index2 == 0 && !Objects.equals(valCategoryEditText, "MSMK")){
            index2 = 100;
        }
        else{
            return index2;
        }
        return index2;
    }
}