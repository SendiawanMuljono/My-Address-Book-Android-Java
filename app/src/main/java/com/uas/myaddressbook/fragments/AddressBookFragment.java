package com.uas.myaddressbook.fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.uas.myaddressbook.DatabaseHelper;
import com.uas.myaddressbook.R;
import com.uas.myaddressbook.models.AddressBookEmployee;

import java.util.ArrayList;

public class AddressBookFragment extends Fragment {

    RecyclerView rvAddressBookEmployee;
    DatabaseHelper dbhelper;
    ArrayList<AddressBookEmployee> employees = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address_book, container, false);

        dbhelper = new DatabaseHelper(getActivity());
        getAllEmployees();

        rvAddressBookEmployee = view.findViewById(R.id.rvAddressBookEmployee);
        rvAddressBookEmployee.setLayoutManager(new LinearLayoutManager(getActivity()));
        AddressBookAdapter addressBookAdapter = new AddressBookAdapter(getActivity(), employees);
        rvAddressBookEmployee.setAdapter(addressBookAdapter);

        return view;
    }

    private void getAllEmployees(){
        Cursor cursor = dbhelper.getAllEmployees();
        if(!(cursor.moveToFirst()) || cursor.getCount() == 0){
            Toast.makeText(getActivity(), "Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        while(!cursor.isAfterLast()){
            Integer employeeId;
            String name, city, phone, email, picture;
            employeeId = cursor.getInt(0);
            name = cursor.getString(1);
            city = cursor.getString(2);
            phone = cursor.getString(3);
            email = cursor.getString(4);
            picture = cursor.getString(5);

            employees.add(new AddressBookEmployee(employeeId, name, city, phone, email, picture));
            cursor.moveToNext();
        }
    }
}