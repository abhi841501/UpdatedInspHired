package com.example.insphiredapp.EmployeeActivity;

import static com.example.insphiredapp.R.color.skyBlue;
import static com.example.insphiredapp.R.color.white;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.insphiredapp.EmployeeFragment.HomeFragmentEmployee;
import com.example.insphiredapp.EmployeeFragment.MyJobsFragment;
import com.example.insphiredapp.EmployeeFragment.ProfileFragmentEmployee;
import com.example.insphiredapp.EmployeeFragment.WalletsFragmentEmployee;
import com.example.insphiredapp.R;
import com.example.insphiredapp.databinding.ActivityDashboardEmployeeBinding;

public class DashboardActivityEmployee extends AppCompatActivity {
    ActivityDashboardEmployeeBinding binding;
    LinearLayoutCompat home_layoutEmployee,appointments_layout,wallet_layoutEmployee,profile_layoutEmployee;
    ConstraintLayout container1;
    ImageView home_layoutEmployeeIcon,appointments_Icon,walletIconEmployee,profileIconEmployee;
    TextView home_layoutEmployee_text,appointmentsIconText,walletIconTextEmployee,profileIconTextEmployee,headingEmployee;
    private boolean shouldLoadHomeFragOnBackPress = true;
    private int navItemIndex = 1;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardEmployeeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        inits();
        default_Load();
        home_layoutEmployeeIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.home));
        home_layoutEmployeeIcon.setColorFilter(getApplication().getResources().getColor(skyBlue));
        my_appointment_fragment();
        wall_fragment();
        prof_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container1, new HomeFragmentEmployee()).commit();

        binding.homeLayoutEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navItemIndex = 1;
                HomeFragmentEmployee homeFragmentEmployee = new HomeFragmentEmployee();
                replace_fragment(homeFragmentEmployee);

                home_layoutEmployeeIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.home));
                home_layoutEmployeeIcon.setColorFilter(getApplication().getResources().getColor(skyBlue));
                appointments_Icon.setBackground(getApplication().getResources().getDrawable(R.drawable.appoinment1));
                appointments_Icon.setColorFilter(getApplication().getResources().getColor(white));
                walletIconEmployee.setBackground(getApplication().getResources().getDrawable(R.drawable.wallet1));
                walletIconEmployee.setColorFilter(getApplication().getResources().getColor(white));
                profileIconEmployee.setBackground(getApplication().getResources().getDrawable(R.drawable.profile1));
                profileIconEmployee.setColorFilter(getApplication().getResources().getColor(white));


            }
        });

        binding.appointmentsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navItemIndex = 2;
                MyJobsFragment myJobsFragment = new MyJobsFragment();
                replace_fragment(myJobsFragment);
                home_layoutEmployeeIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.house1));
                home_layoutEmployeeIcon.setColorFilter(getApplication().getResources().getColor(white));
                appointments_Icon.setBackground(getApplication().getResources().getDrawable(R.drawable.appoinment));
                appointments_Icon.setColorFilter(getApplication().getResources().getColor(skyBlue));
                walletIconEmployee.setBackground(getApplication().getResources().getDrawable(R.drawable.wallet1));
                walletIconEmployee.setColorFilter(getApplication().getResources().getColor(white));
                profileIconEmployee.setBackground(getApplication().getResources().getDrawable(R.drawable.profile1));
                profileIconEmployee.setColorFilter(getApplication().getResources().getColor(white));

            }
        });

        binding.walletLayoutEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navItemIndex = 3;
                WalletsFragmentEmployee walletsFragmentEmployee = new WalletsFragmentEmployee();
                replace_fragment(walletsFragmentEmployee);
                home_layoutEmployeeIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.house1));
                home_layoutEmployeeIcon.setColorFilter(getApplication().getResources().getColor(white));
                appointments_Icon.setBackground(getApplication().getResources().getDrawable(R.drawable.appoinment1));
                appointments_Icon.setColorFilter(getApplication().getResources().getColor(white));
                walletIconEmployee.setBackground(getApplication().getResources().getDrawable(R.drawable.wallet));
                walletIconEmployee.setColorFilter(getApplication().getResources().getColor(skyBlue));
                profileIconEmployee.setBackground(getApplication().getResources().getDrawable(R.drawable.profile1));
                profileIconEmployee.setColorFilter(getApplication().getResources().getColor(white));


            }
        });

        binding.profileLayoutEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navItemIndex = 4;
                ProfileFragmentEmployee profileFragmentEmployee = new ProfileFragmentEmployee();
                replace_fragment(profileFragmentEmployee);

                home_layoutEmployeeIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.house1));
                home_layoutEmployeeIcon.setColorFilter(getApplication().getResources().getColor(white));
                appointments_Icon.setBackground(getApplication().getResources().getDrawable(R.drawable.appoinment1));
                appointments_Icon.setColorFilter(getApplication().getResources().getColor(white));
                walletIconEmployee.setBackground(getApplication().getResources().getDrawable(R.drawable.wallet1));
                walletIconEmployee.setColorFilter(getApplication().getResources().getColor(white));
                profileIconEmployee.setBackground(getApplication().getResources().getDrawable(R.drawable.profile));
                profileIconEmployee.setColorFilter(getApplication().getResources().getColor(skyBlue));


            }
        });

    }
    private void prof_Fragment() {
        ProfileFragmentEmployee profileFragmentEmployee = new ProfileFragmentEmployee();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container1,profileFragmentEmployee);
        fragmentTransaction.commit();

    }

    private void wall_fragment() {
        WalletsFragmentEmployee walletsFragmentEmployee = new WalletsFragmentEmployee();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container1, walletsFragmentEmployee);
        fragmentTransaction.commit();
    }

    private void my_appointment_fragment() {

        MyJobsFragment myJobsFragment = new MyJobsFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container1, myJobsFragment);
        fragmentTransaction.commit();
    }

    private void default_Load() {
        HomeFragmentEmployee homeFragmentEmployee = new HomeFragmentEmployee();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container1,homeFragmentEmployee);
        fragmentTransaction.commit();
    }


    private void replace_fragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container1, fragment);
        ft.commit();


    }
    @Override
    public void onBackPressed() {
        if (shouldLoadHomeFragOnBackPress) {
            if (navItemIndex == 4) {
                navItemIndex = 1;
                // Load the default fragment (dashboard) here.
                // For example, you can call a method like default_Load().
                HomeFragmentEmployee homeFragmentEmployee = new HomeFragmentEmployee();
                replace_fragment(homeFragmentEmployee);

                home_layoutEmployeeIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.home));
                home_layoutEmployeeIcon.setColorFilter(getApplication().getResources().getColor(skyBlue));
                appointments_Icon.setBackground(getApplication().getResources().getDrawable(R.drawable.appoinment1));
                appointments_Icon.setColorFilter(getApplication().getResources().getColor(white));
                walletIconEmployee.setBackground(getApplication().getResources().getDrawable(R.drawable.wallet1));
                walletIconEmployee.setColorFilter(getApplication().getResources().getColor(white));
                profileIconEmployee.setBackground(getApplication().getResources().getDrawable(R.drawable.profile1));
                profileIconEmployee.setColorFilter(getApplication().getResources().getColor(white));

            }

            else if (navItemIndex == 3)
            {
                navItemIndex = 1;
                HomeFragmentEmployee homeFragmentEmployee = new HomeFragmentEmployee();
                replace_fragment(homeFragmentEmployee);

                home_layoutEmployeeIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.home));
                home_layoutEmployeeIcon.setColorFilter(getApplication().getResources().getColor(skyBlue));
                appointments_Icon.setBackground(getApplication().getResources().getDrawable(R.drawable.appoinment1));
                appointments_Icon.setColorFilter(getApplication().getResources().getColor(white));
                walletIconEmployee.setBackground(getApplication().getResources().getDrawable(R.drawable.wallet1));
                walletIconEmployee.setColorFilter(getApplication().getResources().getColor(white));
                profileIconEmployee.setBackground(getApplication().getResources().getDrawable(R.drawable.profile1));
                profileIconEmployee.setColorFilter(getApplication().getResources().getColor(white));

            }
            else if (navItemIndex == 2)
            {
                navItemIndex = 1;
                HomeFragmentEmployee homeFragmentEmployee = new HomeFragmentEmployee();
                replace_fragment(homeFragmentEmployee);

                home_layoutEmployeeIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.home));
                home_layoutEmployeeIcon.setColorFilter(getApplication().getResources().getColor(skyBlue));
                appointments_Icon.setBackground(getApplication().getResources().getDrawable(R.drawable.appoinment1));
                appointments_Icon.setColorFilter(getApplication().getResources().getColor(white));
                walletIconEmployee.setBackground(getApplication().getResources().getDrawable(R.drawable.wallet1));
                walletIconEmployee.setColorFilter(getApplication().getResources().getColor(white));
                profileIconEmployee.setBackground(getApplication().getResources().getDrawable(R.drawable.profile1));
                profileIconEmployee.setColorFilter(getApplication().getResources().getColor(white));

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
        container1 = findViewById(R.id.container1);
        home_layoutEmployee = findViewById(R.id.home_layoutEmployee);
        appointments_layout = findViewById(R.id.appointments_layout);
        wallet_layoutEmployee = findViewById(R.id.wallet_layoutEmployee);
        profile_layoutEmployee = findViewById(R.id.profile_layoutEmployee);
        home_layoutEmployeeIcon = findViewById(R.id.home_layoutEmployeeIcon);
        appointments_Icon = findViewById(R.id.appointments_Icon);
        walletIconEmployee = findViewById(R.id.walletIconEmployee);
        profileIconEmployee = findViewById(R.id.profileIconEmployee);
        home_layoutEmployee_text = findViewById(R.id.home_layoutEmployee_text);
        appointmentsIconText = findViewById(R.id.appointmentsIconText);
        walletIconTextEmployee = findViewById(R.id.walletIconTextEmployee);
        profileIconTextEmployee = findViewById(R.id.profileIconTextEmployee);


    }
}