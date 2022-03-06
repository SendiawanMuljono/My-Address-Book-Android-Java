package com.uas.myaddressbook.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.uas.myaddressbook.DownloadImageTask;
import com.uas.myaddressbook.EmployeeDetailsActivity;
import com.uas.myaddressbook.R;
import com.uas.myaddressbook.models.AddressBookEmployee;
import com.uas.myaddressbook.models.Employee;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class AddressBookAdapter extends RecyclerView.Adapter<AddressBookAdapter.LinearViewHolder> {
    private ArrayList<AddressBookEmployee> employees;
    private Context context;
    private AddressBookAdapter.OnItemClickCallback onItemClickCallback;

    public AddressBookAdapter(Context context, ArrayList<AddressBookEmployee> employees) {
        this.context = context;
        this.employees = employees;
    }

    public void setOnItemClickCallback(AddressBookAdapter.OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public AddressBookAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_address_book_adapter, viewGroup, false);
        return new AddressBookAdapter.LinearViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressBookAdapter.LinearViewHolder holder, int position) {
        AddressBookEmployee employee = employees.get(position);

        DownloadImageTask downloadImageTask = (DownloadImageTask) new DownloadImageTask(holder.ivBookEmpPicture)
                .execute(employee.getPicture());

        holder.tvBookEmpName.setText(employee.getName());
        holder.tvBookEmpCity.setText("City: " + employee.getCity());
        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = employee.getPhone();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                context.startActivity(intent);
            }
        });
        holder.btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address[] = {employee.getEmail()};
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, address);
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder{

        ImageView ivBookEmpPicture;
        TextView tvBookEmpName, tvBookEmpCity;
        Button btnCall, btnEmail;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);

            ivBookEmpPicture = (ImageView) itemView.findViewById(R.id.ivBookEmpPicture);
            tvBookEmpName = (TextView) itemView.findViewById(R.id.tvBookEmpName);
            tvBookEmpCity = (TextView) itemView.findViewById(R.id.tvBookEmpCity);
            btnCall = (Button) itemView.findViewById(R.id.btnCall);
            btnEmail = (Button) itemView.findViewById(R.id.btnEmail);
        }
    }

    public interface OnItemClickCallback{
        void onItemClicked(Employee employee);
    }
}