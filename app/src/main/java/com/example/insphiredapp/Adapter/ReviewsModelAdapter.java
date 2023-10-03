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
import com.example.insphiredapp.Api_Model.EmployeeReviewModel;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api_Client;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewsModelAdapter extends RecyclerView.Adapter<ReviewsModelAdapter.ViewHolder> {
    Context context;
    List<EmployeeReviewModel>employeeReviewModelList;

    public ReviewsModelAdapter(Context context, List<EmployeeReviewModel> employeeReviewModelList) {
        this.context = context;
        this.employeeReviewModelList = employeeReviewModelList;
    }

    @NonNull
    @Override
    public ReviewsModelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycyclerreviews1,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsModelAdapter.ViewHolder holder, int position) {


        Glide.with(context).load(Api_Client.BASE_URL_IMAGES1 + employeeReviewModelList.get(position).getImage()).into(holder.companyImagesReview);
        Log.e("images", " " + employeeReviewModelList.get(position).getImage());
        holder.ratingIdRwww.setRating(Float.parseFloat(employeeReviewModelList.get(position).getRating()));
        holder.CompanyNameReviewM.setText((CharSequence) employeeReviewModelList.get(position).getCompanyName());
        holder.reviewMessageget.setText(employeeReviewModelList.get(position).getComment());



    }

    @Override
    public int getItemCount() {
        return employeeReviewModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView companyImagesReview;
        TextView CompanyNameReviewM,reviewMessageget;
        AppCompatRatingBar ratingIdRwww;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            companyImagesReview = itemView.findViewById(R.id.companyImagesReview);
            CompanyNameReviewM = itemView.findViewById(R.id.CompanyNameReviewM);
            reviewMessageget = itemView.findViewById(R.id.reviewMessageget);
            ratingIdRwww = itemView.findViewById(R.id.ratingIdRwww);
        }



    }
}
