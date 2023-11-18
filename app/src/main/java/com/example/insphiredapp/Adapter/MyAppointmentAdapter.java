package com.example.insphiredapp.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insphiredapp.Api_Model.JobCancelData;
import com.example.insphiredapp.Api_Model.JobCancelModel;
import com.example.insphiredapp.Api_Model.UpcomingJobModelData;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api;
import com.example.insphiredapp.retrofit.Api_Client;

import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAppointmentAdapter extends RecyclerView.Adapter<MyAppointmentAdapter.ViewHolder> {
    Context context;
    List<UpcomingJobModelData> upcomingJobModelDataList;
    JobCancelData jobCancelData;

    public MyAppointmentAdapter(Context context, List<UpcomingJobModelData> upcomingJobModelDataList) {
        this.context = context;
        this.upcomingJobModelDataList = upcomingJobModelDataList;
    }

    @NonNull
    @Override
    public MyAppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.myappointments,parent,Boolean.parseBoolean("false"));

        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyAppointmentAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if(position == 0) {
            holder.CardMYJonbss.setBackgroundColor(ContextCompat.getColor(context, R.color.skyBlue));
            holder.CNameFF.setTextColor(Color.WHITE);
            holder.CatNameFF.setTextColor(Color.WHITE);
            holder.startDateFF.setTextColor(Color.WHITE);
            holder.endDateFF.setTextColor(Color.WHITE);
            holder.startDateStatic.setTextColor(Color.WHITE);
            holder.endDateStatic.setTextColor(Color.WHITE);

        }
        holder.CNameFF.setText(upcomingJobModelDataList.get(position).getCompanyName());
        holder.CatNameFF.setText(upcomingJobModelDataList.get(position).getCatName());
        holder.startDateFF.setText(upcomingJobModelDataList.get(position).getStartDate());
        holder.endDateFF.setText(upcomingJobModelDataList.get(position).getEndDate());

        holder.CancelBtnUpcomingJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = String.valueOf(upcomingJobModelDataList.get(position).getBookingAddressId());
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.alert_jobdelete);
                dialog.getWindow().setLayout(850, 350);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.popup_background);
                // dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);

                TextView okay_text = dialog.findViewById(R.id.okay_text);
                TextView cancel_text = dialog.findViewById(R.id.cancel_text);

                okay_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteUpcomingJobApi(id,position);
                        dialog.dismiss();
                    }
                });

                cancel_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();



            }
        });
        String input_date=upcomingJobModelDataList.get(position).getStartDate();
        SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd");
        Date dt1= null;
        try {
            dt1 = format1.parse(input_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat format2=new SimpleDateFormat("dd");
        DateFormat format3=new SimpleDateFormat("MMM");
        String finalDay=format2.format(dt1);
        String finalMonth=format3.format(dt1);
        holder.upcomingDate.setText(finalDay);
        holder.upcomingMonth.setText(finalMonth);



    }

    private void  deleteUpcomingJobApi(String id, int position) {

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<JobCancelModel> call = service.JOB_CANCEL_MODEL_CALL(id);

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
            upcomingJobModelDataList.remove(position);
            notifyDataSetChanged();     // Update data in adapter.... Notify adapter for change data
        } catch (IndexOutOfBoundsException index) {
            index.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return upcomingJobModelDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView CNameFF,CatNameFF,upcomingMonth,startDateFF,endDateFF,upcomingDate,startDateStatic,endDateStatic;
        LinearLayout CardMYJonbss;
        AppCompatButton CancelBtnUpcomingJobs;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            CNameFF = itemView.findViewById(R.id.CNameFF);
            CatNameFF = itemView.findViewById(R.id.CatNameFF);
            upcomingMonth = itemView.findViewById(R.id.upcomingMonth);
            startDateFF = itemView.findViewById(R.id.startDateFF);
            endDateFF = itemView.findViewById(R.id.endDateFF);
            upcomingDate = itemView.findViewById(R.id.upcomingDate);
            CardMYJonbss = itemView.findViewById(R.id.CardMYJonbss);
            startDateStatic = itemView.findViewById(R.id.startDateStatic);
            endDateStatic = itemView.findViewById(R.id.endDateStatic);
            CancelBtnUpcomingJobs = itemView.findViewById(R.id.CancelBtnUpcomingJobs);
        }
    }
}
