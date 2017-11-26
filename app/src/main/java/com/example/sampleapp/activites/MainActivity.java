package com.example.sampleapp.activites;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.sampleapp.R;
import com.example.sampleapp.fragments.CustomListFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CustomListFragment.OnFragmentInteractionListener {

    Button image_btn;
    CustomListFragment customlistfrag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image_btn= findViewById(R.id.load_btn);
        image_btn.setOnClickListener(this);
        customlistfrag=new CustomListFragment();
    }

    @Override
    public void onClick(View view) {

        if(R.id.load_btn==view.getId()){

            //to hide the button on click of it...
             image_btn.setVisibility(View.GONE);

            // ADD FRAGMENT ON MAINACTIVITY
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container_ambiant, customlistfrag, "LISTFRAG");
            fragmentTransaction.commit();
        }


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
