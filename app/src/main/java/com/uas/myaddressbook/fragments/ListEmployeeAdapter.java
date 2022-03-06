package com.uas.myaddressbook.fragments;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.uas.myaddressbook.DownloadImageTask;
import com.uas.myaddressbook.EmployeeDetailsActivity;
import com.uas.myaddressbook.R;
import com.uas.myaddressbook.models.Employee;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class ListEmployeeAdapter extends RecyclerView.Adapter<ListEmployeeAdapter.LinearViewHolder> {
    private ArrayList<Employee> employees;
    private Context context;
    private OnItemClickCallback onItemClickCallback;

    public ListEmployeeAdapter(Context context, ArrayList<Employee> employees) {
        this.context = context;
        this.employees = employees;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_list_employee_adapter, viewGroup, false);
        return new LinearViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {
        Employee employee = employees.get(position);

        DownloadImageTask downloadImageTask = (DownloadImageTask) new DownloadImageTask(holder.ivEmpPicture)
                .execute(employee.getPicture().getLarge());
        holder.tvEmpName.setText(employee.getName().getFirst() + " " + employee.getName().getLast());
        holder.tvEmpCity.setText("City: " + employee.getLocation().getCity() + ", " + employee.getLocation().getCountry());
        holder.tvEmpPhone.setText("Phone: " + employee.getPhone() + " / " + employee.getCell());

        String temp = employee.getRegistered().getDate();
        String regDate = getMonthYear(temp);
        holder.tvEmpRegDate.setText("Member since: " + regDate);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EmployeeDetailsActivity.class);
                intent.putExtra("employeeId", employee.getEmployeeId().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder{

        ImageView ivEmpPicture;
        TextView tvEmpName, tvEmpCity, tvEmpPhone, tvEmpRegDate;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);

            ivEmpPicture = (ImageView) itemView.findViewById(R.id.ivEmpPicture);
            tvEmpName = (TextView) itemView.findViewById(R.id.tvEmpName);
            tvEmpCity = (TextView) itemView.findViewById(R.id.tvEmpCity);
            tvEmpPhone = (TextView) itemView.findViewById(R.id.tvEmpPhone);
            tvEmpRegDate = (TextView) itemView.findViewById(R.id.tvEmpRegDate);
        }
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

    public interface OnItemClickCallback{
        void onItemClicked(Employee employee);
    }
}

