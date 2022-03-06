package com.uas.myaddressbook;

import com.uas.myaddressbook.models.User;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyAddressBookApi {
    @GET("?nim=2301862733&nama=Sendiawan")
    Call<User> getData();
}
