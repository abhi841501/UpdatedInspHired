package com.example.insphiredapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.insphiredapp.Api_Model.AllEmployeeDataList;
import com.example.insphiredapp.EmployerActivity.EmpDetailsActivity;
import com.example.insphiredapp.EmployerActivity.SelectSlotsActivity;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api_Client;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.ViewHolder> implements Filterable {
    Context context;
    List<AllEmployeeDataList> allEmployeeDataLists;
    List<AllEmployeeDataList> allEmployeeDataListsSearch;
    String strFirstName;
    String statusCheck="";


    public EmployeeListAdapter(Context context, List<AllEmployeeDataList> allEmployeeDataLists, List<AllEmployeeDataList> allEmployeeDataListsSearch) {
        this.context = context;
        this.allEmployeeDataLists = allEmployeeDataLists;
        this.allEmployeeDataListsSearch = new ArrayList<>(allEmployeeDataLists);
    }
    @NonNull
    @Override
    public EmployeeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.employeelistrecycler, viewGroup, Boolean.parseBoolean("false"));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(context).load(Api_Client.BASE_URL_IMAGES + allEmployeeDataLists.get(position).getEmpImage()).into(holder.circleImageAllEmpList);
        Log.e("images", " " + allEmployeeDataLists.get(position).getEmpImage());
        strFirstName = allEmployeeDataLists.get(position).getFirstName();
        holder.nameEmployeeee.setText(strFirstName);
        holder.desigNameEmployeeee.setText(allEmployeeDataLists.get(position).getCatName());
        holder.startDateAllEmployee.setText(allEmployeeDataLists.get(position).getStartDate());
        holder.endDateAllEmployee.setText(allEmployeeDataLists.get(position).getEndDate());
        holder.amountAllEmployee.setText(allEmployeeDataLists.get(position).getDailyRate());
        holder.addressAllEmployee.setText(allEmployeeDataLists.get(position).getAddress());


        holder.linearItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EmpDId = String.valueOf(allEmployeeDataLists.get(position).getId());
                Intent intent = new Intent(context, EmpDetailsActivity.class);
                String img = allEmployeeDataLists.get(position).getEmpImage();
                String strFirstName = allEmployeeDataLists.get(position).getFirstName();
                float myFloat = allEmployeeDataLists.get(position).getRating();
                int rating = Math.round(myFloat);
              String Rating1 = String.valueOf(allEmployeeDataLists.get(position).getRating());
                intent.putExtra("EmpId", EmpDId);
                intent.putExtra("imgg", img);
                intent.putExtra("FullName", strFirstName);
                intent.putExtra("Designation", allEmployeeDataLists.get(position).getCatName());
                intent.putExtra("Rating", rating);
                intent.putExtra("Rating1", Rating1);
                context.startActivity(intent);
            }
        });

        holder.BookBtnAllemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EmpId = String.valueOf(allEmployeeDataLists.get(position).getId());
                Intent intent = new Intent(context, SelectSlotsActivity.class);
                intent.putExtra("EmpId",EmpId);
                Log.e("TAG", "onClick: "+EmpId );
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return allEmployeeDataLists.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {
            List<AllEmployeeDataList> filtereddata = new ArrayList<>();
            if (keyword.toString().isEmpty())
            {
                filtereddata.addAll(allEmployeeDataListsSearch);
            }
            else
            {
                for (AllEmployeeDataList obj : allEmployeeDataListsSearch)
                {
                    if (obj.getFirstName().toString().toLowerCase().contains(keyword.toString().toLowerCase()))
                        filtereddata.add(obj);
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values=filtereddata;
            return  filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            allEmployeeDataLists.clear();
            allEmployeeDataLists.addAll((List<AllEmployeeDataList>)filterResults.values);

            notifyDataSetChanged();

        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView circleImageAllEmpList;
        TextView nameEmployeeee, desigNameEmployeeee, startDateAllEmployee, endDateAllEmployee, amountAllEmployee, addressAllEmployee;
        LinearLayout linearItemView;
        AppCompatButton BookBtnAllemp;

        public ViewHolder(View itemView) {
            super(itemView);

            circleImageAllEmpList = itemView.findViewById(R.id.circleImageAllEmpList);
            nameEmployeeee = itemView.findViewById(R.id.nameEmployeeee);
            desigNameEmployeeee = itemView.findViewById(R.id.desigNameEmployeeee);
            startDateAllEmployee = itemView.findViewById(R.id.startDateAllEmployee);
            endDateAllEmployee = itemView.findViewById(R.id.endDateAllEmployee);
            amountAllEmployee = itemView.findViewById(R.id.amountAllEmployee);
            addressAllEmployee = itemView.findViewById(R.id.addressAllEmployee);
            linearItemView = itemView.findViewById(R.id.linearItemView);
            BookBtnAllemp = itemView.findViewById(R.id.BookBtnAllemp);


        }
    }


}
