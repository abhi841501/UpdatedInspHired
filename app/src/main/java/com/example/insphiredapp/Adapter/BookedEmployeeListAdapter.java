package com.example.insphiredapp.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.insphiredapp.Api_Model.CancelModel;
import com.example.insphiredapp.Api_Model.CancelModelData;
import com.example.insphiredapp.Api_Model.GetEmployeeBookedListModelData;
import com.example.insphiredapp.EmployeeActivity.ChatCompanyActivity;
import com.example.insphiredapp.EmployeeActivity.EmployeeMessageActivity;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api;
import com.example.insphiredapp.retrofit.Api_Client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.annotations.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookedEmployeeListAdapter extends RecyclerView.Adapter<BookedEmployeeListAdapter.ViewHolder>implements Filterable {

    Context context;
    List<GetEmployeeBookedListModelData> getEmployeeBookedListModelDataList;
    List<GetEmployeeBookedListModelData> backup;
    CancelModelData cancelModelData;
    private  String strFirstName;

    public BookedEmployeeListAdapter(Context context, List<GetEmployeeBookedListModelData> getEmployeeBookedListModelDataList, List<GetEmployeeBookedListModelData> backup) {
        this.context = context;
        this.getEmployeeBookedListModelDataList = getEmployeeBookedListModelDataList;
        this.backup = new ArrayList<>(getEmployeeBookedListModelDataList);
    }

    @NonNull
    @Override
    public BookedEmployeeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.recycelerhiredcandidate,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookedEmployeeListAdapter.ViewHolder holder,@SuppressLint("RecyclerView") int position) {

        Glide.with(context).load(Api_Client.BASE_URL_IMAGES + getEmployeeBookedListModelDataList.get(position).getEmpImage()).into(holder.onGoingEmployeeImg);
        Log.e("images", " " + getEmployeeBookedListModelDataList.get(position).getEmpImage());

        strFirstName = getEmployeeBookedListModelDataList.get(position).getFirstName();
        holder.onGoingEmployeeName.setText(strFirstName);

        holder.onGoingEmployeeDesignation.setText(getEmployeeBookedListModelDataList.get(position).getCatName());
        holder.startDateOngoing.setText(getEmployeeBookedListModelDataList.get(position).getStartDate());
        holder.endDateOngoing.setText(getEmployeeBookedListModelDataList.get(position).getEndDate());
        holder.priceOngoing.setText(getEmployeeBookedListModelDataList.get(position).getDailyRate());
        holder.locationOngoing.setText(getEmployeeBookedListModelDataList.get(position).getAddress());
        holder.priceBookingOngoing.setText(getEmployeeBookedListModelDataList.get(position).getAmount());

        holder.ImgCalling.setOnClickListener(new View.OnClickListener() {
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
                        String Phone = getEmployeeBookedListModelDataList.get(position).getMobile();
                        callIntent.setData(Uri.parse("tel:"+Phone));
                        context.startActivity(callIntent);
                    }
                    else {
                        // String number=calltext.getText().toString();
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        String Phone = getEmployeeBookedListModelDataList.get(position).getMobile();
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

        holder.ImgMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatCompanyActivity.class);
                context.startActivity(intent);
            }

        });



        holder.CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String  Id = String.valueOf(getEmployeeBookedListModelDataList.get(position).getId());
                CancelApi(Id,position);

            }
        });

    }

    private void  CancelApi(String ids, int position) {

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<CancelModel> call = service.CANCEL_MODEL_CALL(ids);

        call.enqueue(new Callback<CancelModel>() {
            @Override
            public void onResponse(Call<CancelModel> call, Response<CancelModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        CancelModel cancelModel = response.body();
                        String success = cancelModel.getSuccess();
                        String msg = cancelModel.getMessage();
                        Log.e("hello", "success: " +success );

                        if (success.equals("true")|| (success.equals("True"))) {
                            cancelModelData = cancelModel.getData();
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
            public void onFailure(Call<CancelModel> call, Throwable t) {
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
            getEmployeeBookedListModelDataList.remove(position);
            notifyDataSetChanged();     // Update data in adapter.... Notify adapter for change data
        } catch (IndexOutOfBoundsException index) {
            index.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return getEmployeeBookedListModelDataList.size();

    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {
            List<GetEmployeeBookedListModelData> filtereddata = new ArrayList<>();
            if (keyword.toString().isEmpty())
            {
                filtereddata.addAll(backup);
            }
            else
            {
              for (GetEmployeeBookedListModelData obj : backup)
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
           getEmployeeBookedListModelDataList.clear();
            getEmployeeBookedListModelDataList.addAll((List<GetEmployeeBookedListModelData>)filterResults.values);

             notifyDataSetChanged();

        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ImgMessage,ImgCalling;
        CircleImageView onGoingEmployeeImg;
        TextView onGoingEmployeeName,onGoingEmployeeDesignation,startDateOngoing,endDateOngoing,
                priceOngoing,locationOngoing,priceBookingOngoing;

        AppCompatButton CancelBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            ImgMessage = itemView.findViewById(R.id.ImgMessage);
            onGoingEmployeeImg = itemView.findViewById(R.id.onGoingEmployeeImg);
            onGoingEmployeeName = itemView.findViewById(R.id.onGoingEmployeeName);
            onGoingEmployeeDesignation = itemView.findViewById(R.id.onGoingEmployeeDesignation);
            startDateOngoing = itemView.findViewById(R.id.startDateOngoing);
            endDateOngoing = itemView.findViewById(R.id.endDateOngoing);
            priceOngoing = itemView.findViewById(R.id.priceOngoing);
            priceBookingOngoing = itemView.findViewById(R.id.priceBookingOngoing);
            locationOngoing = itemView.findViewById(R.id.locationOngoing);
            CancelBtn = itemView.findViewById(R.id.CancelBtn);
            ImgCalling = itemView.findViewById(R.id.ImgCalling);




        }
    }
}
