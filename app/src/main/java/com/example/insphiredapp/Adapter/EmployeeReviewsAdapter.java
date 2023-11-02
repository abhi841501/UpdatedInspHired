package com.example.insphiredapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.insphiredapp.Api_Model.EmployeeReviewDetail;
import com.example.insphiredapp.Api_Model.EmployeeReviewModel;
import com.example.insphiredapp.R;
import com.example.insphiredapp.ShowReviewApiModel.EmpDetailData;
import com.example.insphiredapp.ShowReviewApiModel.EmpReviewDataList;
import com.example.insphiredapp.retrofit.Api_Client;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmployeeReviewsAdapter extends RecyclerView.Adapter<EmployeeReviewsAdapter.ViewHolder> {
    Context context;
    List<EmpReviewDataList> employeeReviewModelList;

    public EmployeeReviewsAdapter(Context context, List<EmpReviewDataList> employeeReviewModelList) {
        this.context = context;
        this.employeeReviewModelList = employeeReviewModelList;
    }

    @Override
    public EmployeeReviewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_employee_review,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EmployeeReviewsAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(Api_Client.BASE_URL_IMAGES + employeeReviewModelList.get(position).getEmpImage()).into(holder.companyImagesReviewEmployee);
        Log.e("images", " " + employeeReviewModelList.get(position).getEmpImage());
        holder.ratingIdRwwwEmployee.setRating(Float.parseFloat(employeeReviewModelList.get(position).getRating()));
        holder.CompanyNameReviewMEmployee.setText((CharSequence) employeeReviewModelList.get(position).getFirstName());
        holder.reviewMessagegetEmployee.setText(employeeReviewModelList.get(position).getComment());


    }

    @Override
    public int getItemCount() {
        return employeeReviewModelList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView companyImagesReviewEmployee;
        TextView CompanyNameReviewMEmployee,reviewMessagegetEmployee;
        AppCompatRatingBar ratingIdRwwwEmployee;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            companyImagesReviewEmployee = itemView.findViewById(R.id.companyImagesReviewEmployee);
            CompanyNameReviewMEmployee = itemView.findViewById(R.id.CompanyNameReviewMEmployee);
            reviewMessagegetEmployee = itemView.findViewById(R.id.reviewMessagegetEmployee);
            ratingIdRwwwEmployee = itemView.findViewById(R.id.ratingIdRwwwEmployee);
        }



    }
}
