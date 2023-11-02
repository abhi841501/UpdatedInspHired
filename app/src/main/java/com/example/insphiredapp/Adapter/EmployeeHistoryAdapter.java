package com.example.insphiredapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.insphiredapp.Api_Model.EmployeeHistoryCompleteList;
import com.example.insphiredapp.EmployeeActivity.EmployeeGiveRatingActivity;
import com.example.insphiredapp.EmployerActivity.FeedbackActivity;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api_Client;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.annotations.NonNull;

public class EmployeeHistoryAdapter extends RecyclerView.Adapter<EmployeeHistoryAdapter.ViewHolder> {
    Context context;
    List<EmployeeHistoryCompleteList> employeeHistoryCompleteLists;
    private  String UserType;

    public

    EmployeeHistoryAdapter(Context context, List<EmployeeHistoryCompleteList> employeeHistoryCompleteLists, String userType) {
        this.context = context;
        this.employeeHistoryCompleteLists = employeeHistoryCompleteLists;
        UserType = userType;
    }

    @NonNull
    @Override
    public EmployeeHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_completed_history,parent,Boolean.parseBoolean("false"));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeHistoryAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(Api_Client.BASE_URL_IMAGES1 + employeeHistoryCompleteLists.get(position).getImage()).into(holder.imgEEH);
        holder.companyNameEEH.setText(employeeHistoryCompleteLists.get(position).getCompanyName());
        holder.catNameEEH.setText(employeeHistoryCompleteLists.get(position).getCatName());
        holder.startDateEEH.setText(employeeHistoryCompleteLists.get(position).getStartDate());
        holder.endDateEEH.setText(employeeHistoryCompleteLists.get(position).getEndDate());
        holder.DailyAmountEEH.setText(employeeHistoryCompleteLists.get(position).getAmount());
        holder.AddressEEH.setText(employeeHistoryCompleteLists.get(position).getAddress());
      //  holder.StatusEEH.setText(employeeHistoryCompleteLists.get(position).getStatus());
        holder.feedbackBtnCompleteH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id = employeeHistoryCompleteLists.get(position).getEmployerId();
                String name = employeeHistoryCompleteLists.get(position).getCompanyName();
                String catName = employeeHistoryCompleteLists.get(position).getCatName();
                String img = employeeHistoryCompleteLists.get(position).getImage();

                Intent intent = new Intent(context, EmployeeGiveRatingActivity.class);
                intent.putExtra("EmployerIdFeedback",Id);
                intent.putExtra("CompanyName",name);
                intent.putExtra("catNamee",catName);
                intent.putExtra("imgg",img);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return employeeHistoryCompleteLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgEEH;
        TextView companyNameEEH, catNameEEH,startDateEEH,endDateEEH,DailyAmountEEH,AddressEEH,StatusEEH,paymentEEH;
        AppCompatButton feedbackBtnCompleteH;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgEEH = itemView.findViewById(R.id.imgEEH);
            companyNameEEH = itemView.findViewById(R.id.companyNameEEH);
            catNameEEH = itemView.findViewById(R.id.catNameEEH);
            startDateEEH = itemView.findViewById(R.id.startDateEEH);
            endDateEEH = itemView.findViewById(R.id.endDateEEH);
            DailyAmountEEH = itemView.findViewById(R.id.DailyAmountEEH);
            AddressEEH = itemView.findViewById(R.id.AddressEEH);
            StatusEEH = itemView.findViewById(R.id.StatusEEH);
            paymentEEH = itemView.findViewById(R.id.paymentEEH);
            feedbackBtnCompleteH = itemView.findViewById(R.id.feedbackBtnCompleteH);
        }
    }
}
