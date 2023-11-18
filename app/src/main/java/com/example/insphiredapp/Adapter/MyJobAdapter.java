package com.example.insphiredapp.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.insphiredapp.Api_Model.JobCancelData;
import com.example.insphiredapp.Api_Model.JobCancelModel;
import com.example.insphiredapp.Api_Model.MyJobModelData;
import com.example.insphiredapp.EmployeeActivity.ShowReviewEmployeeActivity;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api;
import com.example.insphiredapp.retrofit.Api_Client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyJobAdapter extends RecyclerView.Adapter<MyJobAdapter.ViewHolder> implements Filterable {
    Context context;
    List<MyJobModelData> myJobModelDataList;
    List<MyJobModelData> myJobModelDataListBackUp;
    String UserType,is_corporator;
    JobCancelData jobCancelData;

    public MyJobAdapter(Context context, List<MyJobModelData> myJobModelDataList, List<MyJobModelData> myJobModelDataListBackUp, String userType) {
        this.context = context;
        this.myJobModelDataList = myJobModelDataList;
        this.myJobModelDataListBackUp = new ArrayList<>(myJobModelDataList);
        UserType = userType;
    }
    @NonNull
    @Override
    public MyJobAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.recyclermyjob,parent,Boolean.parseBoolean("false"));
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyJobAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {



        is_corporator = String.valueOf(myJobModelDataList.get(position).getIsCorporator());
        if (is_corporator.equals("0"))
        {

            //Glide.with(context).load(Api_Client.BASE_URL_IMAGES2+myJobModelDataList.get(position).getCompanyImage()).into(holder.imgMyjobss);
            Glide.with(context).load(Api_Client.BASE_URL_IMAGES1+myJobModelDataList.get(position).getCompanyImage()).into(holder.imgMyjobss);
        }
        else
        {
            Glide.with(context).load(Api_Client.BASE_URL_IMAGES2+myJobModelDataList.get(position).getCompanyImage()).into(holder.imgMyjobss);
            //Glide.with(context).load(Api_Client.BASE_URL_IMAGES1+myJobModelDataList.get(position).getCompanyImage()).into(holder.imgMyjobss);
        }


        Log.e("images", " " + myJobModelDataList.get(position).getCompanyImage());
        holder.nameMyjobss.setText(myJobModelDataList.get(position).getCatName());
        holder.profileNamejobss.setText(myJobModelDataList.get(position).getCompanyName());
        holder.joiningDateHJobss.setText(myJobModelDataList.get(position).getJoiningDate());
        holder.endDateHJobss.setText(myJobModelDataList.get(position).getEndDate());
        holder.amountMyjobss.setText(myJobModelDataList.get(position).getDailyRate());
        holder.locationJobss.setText(myJobModelDataList.get(position).getCompanyAddress());



        holder.ViewReviewEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ShowReviewEmployeeActivity.class);
                context.startActivity(i);
            }
        });

        holder.CancelBtnMYJobssss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  BookingId = String.valueOf(myJobModelDataList.get(position).getBookingId());
                CancelJobApi(BookingId,position);

            }
        });



    }

    private void  CancelJobApi(String idsss, int position) {

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<JobCancelModel> call = service.JOB_CANCEL_MODEL_CALL(idsss);

        call.enqueue(new Callback<JobCancelModel>() {
            @Override
            public void onResponse(Call<JobCancelModel> call, Response<JobCancelModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        JobCancelModel jobCancelModel = response.body();
                        String success = jobCancelModel.getSuccess();
                        String msg = jobCancelModel.getMessage();
                        Log.e("hello", "success: " +success );

                        if (success.equals("true")|| (success.equals("True"))) {
                            jobCancelData = jobCancelModel.getData();
                            String status = String.valueOf(jobCancelData.getStatus());
                            removeItem(position);
                            notifyDataSetChanged();
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                            Log.e("hello", "getData: " );
                            // Id  = profileGetData.getId();

                            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            // Calling another activity

                        } else {
                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(context, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(context, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(context, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(context, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(context, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(context, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(context, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(context, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(context, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JobCancelModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(context, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(context, "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }
    @SuppressLint("NotifyDataSetChanged")
    public void removeItem(int position) {
        try {
            myJobModelDataList.remove(position);
            notifyDataSetChanged();     // Update data in adapter.... Notify adapter for change data
        } catch (IndexOutOfBoundsException index) {
            index.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getItemCount() {
        return myJobModelDataList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {
            List<MyJobModelData> filtereddata = new ArrayList<>();
            if (keyword.toString().isEmpty())
            {
                filtereddata.addAll(myJobModelDataListBackUp);
            }
            else
            {
                for (MyJobModelData obj : myJobModelDataListBackUp)
                {
                    if (obj.getCatName().toString().toLowerCase().contains(keyword.toString().toLowerCase()))
                        filtereddata.add(obj);
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values=filtereddata;
            return  filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            myJobModelDataList.clear();
            myJobModelDataList.addAll((List<MyJobModelData>)filterResults.values);
            notifyDataSetChanged();

        }
    };

    public  class ViewHolder extends  RecyclerView.ViewHolder {
        LinearLayout linearFindJobs;
        CircleImageView imgMyjobss;
        TextView nameMyjobss,profileNamejobss,joiningDateHJobss,endDateHJobss,amountMyjobss,locationJobss,
                statusMyJobss,paymentMyJobss,ViewReviewEmployee;
        AppCompatButton CancelBtnMYJobssss;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgMyjobss = itemView.findViewById(R.id.imgMyjobss);
            nameMyjobss = itemView.findViewById(R.id.nameMyjobss);
            profileNamejobss = itemView.findViewById(R.id.profileNamejobss);
            joiningDateHJobss = itemView.findViewById(R.id.joiningDateHJobss);
            endDateHJobss = itemView.findViewById(R.id.endDateHJobss);
            amountMyjobss = itemView.findViewById(R.id.amountMyjobss);
            locationJobss = itemView.findViewById(R.id.locationJobss);
            statusMyJobss = itemView.findViewById(R.id.statusMyJobss);
            paymentMyJobss = itemView.findViewById(R.id.paymentMyJobss);
            CancelBtnMYJobssss = itemView.findViewById(R.id.CancelBtnMYJobssss);
            ViewReviewEmployee = itemView.findViewById(R.id.ViewReviewEmployee);

        }
    }
}
