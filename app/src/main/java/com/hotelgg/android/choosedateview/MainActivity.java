package com.hotelgg.android.choosedateview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void show_date_view(View view){
        ChooseDateDialogFragment dialogFragment = new ChooseDateDialogFragment();

        dialogFragment.show(getSupportFragmentManager(), "date");
    }
}
