package com.uas.myaddressbook.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.uas.myaddressbook.MyAddressBookApi;
import com.uas.myaddressbook.R;
import com.uas.myaddressbook.models.Employee;
import com.uas.myaddressbook.models.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListEmployeeFragment extends Fragment {

    RecyclerView rvListEmployee;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_employee, container, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://u73olh7vwg.execute-api.ap-northeast-2.amazonaws.com/stage2/people/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyAddressBookApi myAddressBookApi = retrofit.create(MyAddressBookApi.class);
        Call<User> list = myAddressBookApi.getData();

        list.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getActivity(), "RESPONSE FAIL", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = response.body();
                getActivity().setTitle(user.getNim() + " - " + user.getNama());
                ArrayList<Employee> employees = user.getEmployees();

                rvListEmployee = view.findViewById(R.id.rvListEmployee);
                rvListEmployee.setLayoutManager(new LinearLayoutManager(getActivity()));
                ListEmployeeAdapter listEmployeeAdapter = null;
                String search = "";
                try {
                    search = getArguments().getString("search");
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                ArrayList<Employee> searchedEmployees = new ArrayList<>();
                if(!search.equals("")){
                    for (int i = 0; i < employees.size(); i++){
                        String empName = employees.get(i).getName().getFirst() + " " + employees.get(i).getName().getLast();
                        if(empName.toLowerCase().contains(search.toLowerCase())){
                            searchedEmployees.add(employees.get(i));
                        }
                    }
                    if(searchedEmployees.isEmpty()){
                        Toast.makeText(getActivity(), "Search not found", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        listEmployeeAdapter = new ListEmployeeAdapter(getActivity(), searchedEmployees);
                    }
                }
                else{
                    listEmployeeAdapter = new ListEmployeeAdapter(getActivity(), employees);
                }

                rvListEmployee.setAdapter(listEmployeeAdapter);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(), "FAIL", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}