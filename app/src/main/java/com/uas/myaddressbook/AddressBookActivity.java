package com.uas.myaddressbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.uas.myaddressbook.fragments.AddressBookFragment;
import com.uas.myaddressbook.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddressBookActivity extends AppCompatActivity {

    private AddressBookFragment addressBookFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book);

        addressBookFragment = new AddressBookFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerAddressBook, addressBookFragment).commit();

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
                    Toast.makeText(AddressBookActivity.this, "RESPONSE FAIL", Toast.LENGTH_SHORT).show();
                    return;
                }
                User user = response.body();
                setTitle(user.getNim() + " - " + user.getNama());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(AddressBookActivity.this, "FAIL", Toast.LENGTH_SHORT).show();
            }
        });


    }
}