package com.example.insphiredapp.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.insphiredapp.Api_Model.GetEmployeeHistoryModelData;
import com.example.insphiredapp.Api_Model.RequestTimeSlotModel;
import com.example.insphiredapp.EmployeeActivity.ChatCompanyActivity;
import com.example.insphiredapp.EmployerActivity.FeedbackActivity;
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

public class EmployerHistoryAdapter extends RecyclerView.Adapter<EmployerHistoryAdapter.ViewHolder> implements Filterable {


    Context context;
    List<GetEmployeeHistoryModelData> getEmployeeHistoryModelData;
    List<GetEmployeeHistoryModelData> employerHistory;
    private  String strFirstName,UserIdDD,UserTypeee,StartDateeee,EndDateeee;
    private int year,month,day;


    public EmployerHistoryAdapter(Context context, List<GetEmployeeHistoryModelData> getEmployeeHistoryModelData, List<GetEmployeeHistoryModelData> employerHistory) {
        this.context = context;
        this.getEmployeeHistoryModelData = getEmployeeHistoryModelData;
        this.employerHistory = new ArrayList<>(getEmployeeHistoryModelData);
    }


    @Override
    public EmployerHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycleremployerhistory,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EmployerHistoryAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        Glide.with(context).load(Api_Client.BASE_URL_IMAGES + getEmployeeHistoryModelData.get(position).getEmpImage()).into(holder.EmployerHistoryImg);
        Log.e("images", " " + getEmployeeHistoryModelData.get(position).getEmpImage());
        strFirstName = getEmployeeHistoryModelData.get(position).getFirstName();
        holder.EmployerNameHistory.setText(strFirstName);

        holder.EmployerDgNameHistory.setText(getEmployeeHistoryModelData.get(position).getCatName());
        holder.startDateHistory.setText(getEmployeeHistoryModelData.get(position).getStartDate());
        holder.endDateHistory.setText(getEmployeeHistoryModelData.get(position).getEndDate());
        holder.totalAmountHistory.setText(getEmployeeHistoryModelData.get(position).getDailyRate());
        holder.locationHistory.setText(getEmployeeHistoryModelData.get(position).getAddress());

        holder.feedbackBtnHistoryyy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id = getEmployeeHistoryModelData.get(position).getEmployeeId();
                String name = getEmployeeHistoryModelData.get(position).getFirstName();
                String catName = getEmployeeHistoryModelData.get(position).getCatName();
                String img = getEmployeeHistoryModelData.get(position).getEmpImage();

                Intent intent = new Intent(context, FeedbackActivity.class);
                intent.putExtra("EmployeeIdFeedback",Id);
                intent.putExtra("FirstName",name);
                intent.putExtra("CTName",catName);
                intent.putExtra("img",img);

                context.startActivity(intent);
            }
        });


        holder.ImgCallingHisss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   callPhoneNumber();
                try
                {
                    if(Build.VERSION.SDK_INT > 22)
                    {
                        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling

                            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.CALL_PHONE}, 101);
                            return;
                        }
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        String Phone = getEmployeeHistoryModelData.get(position).getMobile();
                        callIntent.setData(Uri.parse("tel:"+Phone));
                        context.startActivity(callIntent);
                    }
                    else {
                        // String number=calltext.getText().toString();
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        String Phone = getEmployeeHistoryModelData.get(position).getMobile();
                        callIntent.setData(Uri.parse("tel:"+Phone));
                        context.startActivity(callIntent);
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });


        holder.ImgMessageEmpHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatCompanyActivity.class);
                context.startActivity(intent);
            }
        });

        holder.ImgEmployerHreshe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeSlotIdd = String.valueOf(getEmployeeHistoryModelData.get(position).getTimeslotId());
                String  EmployeeIDD =  String.valueOf(getEmployeeHistoryModelData.get(position).getEmployeeId());
                CalenderDialogg(timeSlotIdd,EmployeeIDD);
            }
        });


    }

    private void CalenderDialogg(String timeSlotIdd, String employeeIDD) {
        Dialog openDialog = new Dialog(context);
        openDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        openDialog.setContentView(R.layout.rescheduled_pop_up);
        AppCompatButton RequestBtnBooking = openDialog.findViewById(R.id.RequestBtnBooking);
        ImageView crossRequestPOpUp = openDialog.findViewById(R.id.crossRequestPOpUp);
        TextView selectSDateReqTxt = openDialog.findViewById(R.id.selectSDateReqTxt);
        TextView selectEndTxtReq = openDialog.findViewById(R.id.selectEndTxtReq);
        LinearLayout linearselectSDateReqTxt = openDialog.findViewById(R.id.linearselectSDateReqTxt);
        LinearLayout linearEndTxtReq = openDialog.findViewById(R.id.linearEndTxtReq);
        openDialog.show();
        Window window = openDialog.getWindow();
        if (window == null) return;
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT; // Match the width to the parent
        params.height = WindowManager.LayoutParams.WRAP_CONTENT; // Match the height to the parent
        window.setAttributes(params);
       /* params.width = 900;
        params.height = 640;
        window.setAttributes(params);
        //    dialog.getWindow().setLayout(100, 100);
        emailVerifyDialog.getWindow().setBackgroundDrawableResource(R.drawable.popup_background);*/

        crossRequestPOpUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog.dismiss();
            }
        });

        linearselectSDateReqTxt.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();

                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                Log.e("month","kkk.." +month);

                //calendar.add(Calendar.MONTH, 1);

                linearselectSDateReqTxt.setOnClickListener(view -> {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(context,R.style.MyDatePicker, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            month = month+ 1;

                            String formattedMonth = String.format("%02d", month);
                            String formatDay = String.format("%02d",day);
                            String date = year + "-" + formattedMonth + "-" + formatDay;

                            selectSDateReqTxt.setText(date);
                        }
                    }, year, month, day);
                    datePickerDialog.show();
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                    datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
                    datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                });

            }
        });
        linearEndTxtReq.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();

                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                Log.e("month","kkk.." +month);

                //calendar.add(Calendar.MONTH, 1);

                linearEndTxtReq.setOnClickListener(view -> {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(context,R.style.MyDatePicker, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            month = month+ 1;

                            String formattedMonth = String.format("%02d", month);
                            String formatDay = String.format("%02d",day);
                            String date = year + "-" + formattedMonth + "-" + formatDay;

                            selectEndTxtReq.setText(date);
                        }
                    }, year, month, day);
                    datePickerDialog.show();
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                    datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
                    datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                });

            }
        });

        SharedPreferences getUserIdData = context.getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        UserIdDD= getUserIdData.getString("Id", "");
        UserTypeee= getUserIdData.getString("userType", "");
        Log.e("feedback", "change" + UserIdDD);

        RequestBtnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartDateeee = selectSDateReqTxt.getText().toString();
                EndDateeee = selectEndTxtReq.getText().toString();
                RequestTimeApi(timeSlotIdd,employeeIDD);
                openDialog.dismiss();
            }
        });

    }

    private void  RequestTimeApi(String timeSlotIdd, String employeeIDD) {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<RequestTimeSlotModel> call = service.REQUEST_TIME_SLOT_MODEL_CALL(timeSlotIdd,UserIdDD,employeeIDD,StartDateeee,EndDateeee,UserTypeee);
        call.enqueue(new Callback<RequestTimeSlotModel>() {
            @Override
            public void onResponse(Call<RequestTimeSlotModel> call, Response<RequestTimeSlotModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        RequestTimeSlotModel requestTimeSlotModel = response.body();
                        String success = requestTimeSlotModel.getSuccess();
                        String msg = requestTimeSlotModel.getMessage();
                        Log.e("hello", "success: " +success );

                        if (success.equals("true")|| (success.equals("True"))) {
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
            public void onFailure(Call<RequestTimeSlotModel> call, Throwable t) {
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

    @Override
    public int getItemCount() {
        return getEmployeeHistoryModelData.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {
            List<GetEmployeeHistoryModelData> filtereddata = new ArrayList<>();
            if (keyword.toString().isEmpty())
            {
                filtereddata.addAll(employerHistory);
            }
            else
            {
                for (GetEmployeeHistoryModelData obj1 : employerHistory)
                {
                    if (obj1.getFirstName().toString().toLowerCase().contains(keyword.toString().toLowerCase()))
                        filtereddata.add(obj1);
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values=filtereddata;
            return  filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            getEmployeeHistoryModelData.clear();
            getEmployeeHistoryModelData.addAll((List<GetEmployeeHistoryModelData>)filterResults.values);

            notifyDataSetChanged();

        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatButton feedbackBtnHistoryyy;
        ImageView ImgMessageEmpHistory,ImgCallingHisss;
        CircleImageView EmployerHistoryImg;
        TextView EmployerNameHistory,EmployerDgNameHistory,startDateHistory,endDateHistory,totalAmountHistory,locationHistory;
        LinearLayout linearHistoryy;
        AppCompatImageView ImgEmployerHreshe;
        public ViewHolder(View itemView) {
            super(itemView);

            feedbackBtnHistoryyy = itemView.findViewById(R.id.feedbackBtnHistoryyy);
            ImgMessageEmpHistory = itemView.findViewById(R.id.ImgMessageEmpHistory);
            linearHistoryy = itemView.findViewById(R.id.linearHistoryy);
            EmployerHistoryImg = itemView.findViewById(R.id.EmployerHistoryImg);
            EmployerNameHistory = itemView.findViewById(R.id.EmployerNameHistory);
            EmployerDgNameHistory = itemView.findViewById(R.id.EmployerDgNameHistory);
            startDateHistory = itemView.findViewById(R.id.startDateHistory);
            endDateHistory = itemView.findViewById(R.id.endDateHistory);
            totalAmountHistory = itemView.findViewById(R.id.totalAmountHistory);
            locationHistory = itemView.findViewById(R.id.locationHistory);
            ImgCallingHisss = itemView.findViewById(R.id.ImgCallingHisss);
            ImgEmployerHreshe = itemView.findViewById(R.id.ImgEmployerHreshe);


        }
    }
}
