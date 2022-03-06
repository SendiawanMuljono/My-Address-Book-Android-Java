package com.uas.myaddressbook.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.uas.myaddressbook.AddressBookActivity;
import com.uas.myaddressbook.DatabaseHelper;
import com.uas.myaddressbook.EmployeeDetailsActivity;
import com.uas.myaddressbook.MyAddressBookApi;
import com.uas.myaddressbook.R;
import com.uas.myaddressbook.models.AddressBookEmployee;
import com.uas.myaddressbook.models.Employee;
import com.uas.myaddressbook.models.User;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmployeeDetailsFragment extends Fragment {

    TextView tvDetailEmpName, tvDetailEmpCity, tvDetailEmpPhone, tvDetailEmpRegDate, tvDetailEmpEmail, tvAddToAddressBookClick;
    DatabaseHelper dbhelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee_details, container, false);

        tvDetailEmpName = view.findViewById(R.id.tvDetailEmpName);
        tvDetailEmpCity = view.findViewById(R.id.tvDetailEmpCity);
        tvDetailEmpPhone = view.findViewById(R.id.tvDetailEmpPhone);
        tvDetailEmpRegDate = view.findViewById(R.id.tvDetailEmpRegDate);
        tvDetailEmpEmail = view.findViewById(R.id.tvDetailEmpEmail);
        tvAddToAddressBookClick = view.findViewById(R.id.tvAddToAddressBookClick);

        String employeeId = getArguments().getString("employeeId");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://u73olh7vwg.execute-api.ap-northeast-2.amazonaws.com/stage2/people/" + employeeId + "/")
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
                Employee employee = employees.get(0);

                String latitude = employee.getLocation().getCoordinates().getLatitude();
                String longitude = employee.getLocation().getCoordinates().getLongitude();
                MapsFragment mapFragment = new MapsFragment(latitude, longitude);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerMap, mapFragment).commit();

                tvDetailEmpName.setText(employee.getName().getFirst() + " " + employee.getName().getLast());
                tvDetailEmpCity.setText("City: " + employee.getLocation().getCity() + ", " + employee.getLocation().getCountry());
                tvDetailEmpPhone.setText("Phone: " + employee.getPhone() + " / " + employee.getCell());
                String temp = employee.getRegistered().getDate();
                String regDate = getMonthYear(temp);
                tvDetailEmpRegDate.setText("Member since: " + regDate);
                tvDetailEmpEmail.setText("Email: " + employee.getEmail());

                tvAddToAddressBookClick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //masukin data employee ke sqlite
                        dbhelper = new DatabaseHelper(getActivity());
                        Integer employeeId = employee.getEmployeeId();
                        String name = employee.getName().getFirst() + " " + employee.getName().getLast();
                        String city = employee.getLocation().getCity() + ", " + employee.getLocation().getCountry();
                        String phone = employee.getPhone();
                        String email = employee.getEmail();
                        String picture = employee.getPicture().getLarge();
                        AddressBookEmployee addressBookEmployee = new AddressBookEmployee(employeeId, name, city, phone, email, picture);

                        boolean inserted = dbhelper.insertEmployee(addressBookEmployee);
                        if(inserted){
                            Toast.makeText(getActivity(), "Employee added to address book", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), AddressBookActivity.class);
                            getActivity().startActivity(intent);
                        }
                        else{
                            Toast.makeText(getActivity(), "Fail to add employee as it may already exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(), "FAIL", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public String getMonthYear(String date){
        String dates = date.substring(0, 10);
        LocalDate parsed = LocalDate.parse(dates);

        Month tempMonth = parsed.getMonth();
        String temp = tempMonth.toString();
        String month = temp.substring(0,1).toUpperCase() + temp.substring(1).toLowerCase();

        Integer year = parsed.getYear();

        return month + " " + year.toString();
    }
}