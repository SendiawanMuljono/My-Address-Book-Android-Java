package com.uas.myaddressbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.uas.myaddressbook.fragments.ListEmployeeFragment;
import com.uas.myaddressbook.fragments.SearchFragment;

public class EmployeeSearchActivity extends AppCompatActivity{

    private SearchFragment searchFragment;
    private ListEmployeeFragment listEmployeeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_search);

//        deleteDatabase("employee");

        searchFragment = new SearchFragment();
        listEmployeeFragment = new ListEmployeeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerSearch, searchFragment)
                .replace(R.id.containerListEmployee, listEmployeeFragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.nav_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(EmployeeSearchActivity.this, AddressBookActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}