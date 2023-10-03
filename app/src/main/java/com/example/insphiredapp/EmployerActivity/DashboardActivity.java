package com.example.insphiredapp.EmployerActivity;

import static com.example.insphiredapp.R.color.skyBlue;
import static com.example.insphiredapp.R.color.white;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.insphiredapp.EmployerFragment.FavouriteFragment;
import com.example.insphiredapp.EmployerFragment.HomeFragment;
import com.example.insphiredapp.EmployerFragment.PaymentsFragment;
import com.example.insphiredapp.EmployerFragment.ProfileFragment;
import com.example.insphiredapp.R;
import com.example.insphiredapp.databinding.ActivityDashboardBinding;

public class DashboardActivity extends AppCompatActivity {

    ActivityDashboardBinding binding;
    TextView heading,homeIconText,favouriteIconText,walletIconText,profileIconText;
    ImageView homeIcon,favouriteIcon,walletIcon,profileIcon;
    LinearLayoutCompat home_layout,favourite_layout,wallet_layout,profile_layout;
    ConstraintLayout container;
    String Default,EmployrrID;
    private int term_condition=1;
    private boolean shouldLoadHomeFragOnBackPress = true;
    private int navItemIndex = 1;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        View view =binding.getRoot();
        setContentView(view);
        inits();
        default_Load();
        homeIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.home));
        homeIcon.setColorFilter(getApplication().getResources().getColor(skyBlue));

        wallet_Fragment();
        profile_Fragment();
        favourite_Fragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

        Default = getIntent().getStringExtra("strDefault");

        Log.e( "Default: ", ""+ Default  );

        binding.homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navItemIndex = 1;
                HomeFragment homeFragment = new HomeFragment();
                replace_fragment(homeFragment);
                homeIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.home));
                homeIcon.setColorFilter(getApplication().getResources().getColor(skyBlue));
                favouriteIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.appoinment1));
                favouriteIcon.setColorFilter(getApplication().getResources().getColor(white));
                walletIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.wallet1));
                walletIcon.setColorFilter(getApplication().getResources().getColor(white));
                profileIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.profile1));
                profileIcon.setColorFilter(getApplication().getResources().getColor(white));



            }
        });

        binding.favouriteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navItemIndex = 2;
                FavouriteFragment favouriteFragment = new FavouriteFragment();
                replace_fragment(favouriteFragment);
                favouriteIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.appoinment));
                favouriteIcon.setColorFilter(getApplication().getResources().getColor(skyBlue));
                homeIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.house1));
                homeIcon.setColorFilter(getApplication().getResources().getColor(white));
                walletIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.wallet1));
                walletIcon.setColorFilter(getApplication().getResources().getColor(white));
                profileIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.profile1));
                profileIcon.setColorFilter(getApplication().getResources().getColor(white));

            }
        });


        binding.walletLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navItemIndex = 3;
                PaymentsFragment paymentsFragment = new PaymentsFragment();
                replace_fragment(paymentsFragment);

                walletIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.wallet));
                walletIcon.setColorFilter(getApplication().getResources().getColor(skyBlue));;
                homeIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.house1));
                homeIcon.setColorFilter(getApplication().getResources().getColor(white));
                favouriteIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.appoinment1));
                favouriteIcon.setColorFilter(getApplication().getResources().getColor(white));
                profileIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.profile1));
                profileIcon.setColorFilter(getApplication().getResources().getColor(white));;


            }
        });




        binding.profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navItemIndex = 4;
                ProfileFragment profileFragment = new ProfileFragment();
                replace_fragment(profileFragment);

                profileIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.profile));
                profileIcon.setColorFilter(getApplication().getResources().getColor(skyBlue));;
                homeIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.house1));
                homeIcon.setColorFilter(getApplication().getResources().getColor(white));;
                favouriteIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.appoinment1));
                favouriteIcon.setColorFilter(getApplication().getResources().getColor(white));
                walletIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.wallet1));
                walletIcon.setColorFilter(getApplication().getResources().getColor(white));;


            }
        });

    }

    private void profile_Fragment() {
        ProfileFragment profileFragment =new ProfileFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container,profileFragment);
        ft.commit();

    }

    private void wallet_Fragment() {
        PaymentsFragment paymentsFragment =new PaymentsFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, paymentsFragment);
        ft.commit();


    }

    private void favourite_Fragment() {
        FavouriteFragment favouriteFragment = new FavouriteFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container,favouriteFragment);
    }

    private void default_Load() {
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, homeFragment);
        ft.commit();

    }

    private void replace_fragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();


    }
    @Override
    public void onBackPressed() {
        if (shouldLoadHomeFragOnBackPress) {
            if (navItemIndex == 4) {
                navItemIndex = 1;
                // Load the default fragment (dashboard) here.
                // For example, you can call a method like default_Load().
                HomeFragment homeFragment = new HomeFragment();
                replace_fragment(homeFragment);
                homeIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.home));
                homeIcon.setColorFilter(getApplication().getResources().getColor(skyBlue));
                favouriteIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.appoinment1));
                favouriteIcon.setColorFilter(getApplication().getResources().getColor(white));
                walletIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.wallet1));
                walletIcon.setColorFilter(getApplication().getResources().getColor(white));
                profileIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.profile1));
                profileIcon.setColorFilter(getApplication().getResources().getColor(white));
            }

            else if (navItemIndex == 3)
            {
                navItemIndex = 1;
                HomeFragment homeFragment = new HomeFragment();
                replace_fragment(homeFragment);
                homeIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.home));
                homeIcon.setColorFilter(getApplication().getResources().getColor(skyBlue));
                favouriteIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.appoinment1));
                favouriteIcon.setColorFilter(getApplication().getResources().getColor(white));
                walletIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.wallet1));
                walletIcon.setColorFilter(getApplication().getResources().getColor(white));
                profileIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.profile1));
                profileIcon.setColorFilter(getApplication().getResources().getColor(white));

            }
            else if (navItemIndex == 2)
            {
                navItemIndex = 1;
                HomeFragment homeFragment = new HomeFragment();
                replace_fragment(homeFragment);
                homeIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.home));
                homeIcon.setColorFilter(getApplication().getResources().getColor(skyBlue));
                favouriteIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.appoinment1));
                favouriteIcon.setColorFilter(getApplication().getResources().getColor(white));
                walletIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.wallet1));
                walletIcon.setColorFilter(getApplication().getResources().getColor(white));
                profileIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.profile1));
                profileIcon.setColorFilter(getApplication().getResources().getColor(white));

            }
            else {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    return;
                }
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000); // Delay for 2 seconds to reset the flag
            }
        } /*else {
            super.onBackPressed();
        }*/
    }

    private void inits() {
        container = findViewById(R.id.container);
        home_layout = findViewById(R.id.home_layout);
        favourite_layout = findViewById(R.id.favourite_layout);
        wallet_layout = findViewById(R.id.wallet_layout);
        profile_layout = findViewById(R.id.profile_layout);
        homeIcon = findViewById(R.id.homeIcon);
        favouriteIcon = findViewById(R.id.favouriteIcon);
        walletIcon = findViewById(R.id.walletIcon);
        profileIcon = findViewById(R.id.profileIcon);
        homeIconText = findViewById(R.id.homeIconText);
        favouriteIconText = findViewById(R.id.favouriteIconText);
        walletIconText = findViewById(R.id.walletIconText);
        profileIconText = findViewById(R.id.profileIconText);
    }
}