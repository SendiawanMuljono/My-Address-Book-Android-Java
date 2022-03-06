package com.uas.myaddressbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.uas.myaddressbook.fragments.EmployeeDetailsFragment;

public class EmployeeDetailsActivity extends AppCompatActivity {

    private EmployeeDetailsFragment employeeDetailsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);

        String employeeId = getIntent().getStringExtra("employeeId");

        Bundle bundle = new Bundle();
        bundle.putString("employeeId", employeeId);
        employeeDetailsFragment = new EmployeeDetailsFragment();
        employeeDetailsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerDetailEmployee, employeeDetailsFragment).commit();

    }
}