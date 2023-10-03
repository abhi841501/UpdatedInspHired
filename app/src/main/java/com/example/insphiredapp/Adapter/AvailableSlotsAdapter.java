package com.example.insphiredapp.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insphiredapp.Api_Model.DeleteTimeSlotModel;
import com.example.insphiredapp.Api_Model.GetCreateSlotsModelData;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api;
import com.example.insphiredapp.retrofit.Api_Client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvailableSlotsAdapter extends RecyclerView.Adapter<AvailableSlotsAdapter.AvailHolder> {
    Context context;
    List<GetCreateSlotsModelData> getCreateSlotsModelDataList;
    String TableName,TimeSlotId;
    private  String Data;

    public AvailableSlotsAdapter(Context context, List<GetCreateSlotsModelData> getCreateSlotsModelDataList,String tableName, String timeSlotId) {
        this.context = context;
        this.getCreateSlotsModelDataList = getCreateSlotsModelDataList;
        TableName = tableName;
        TimeSlotId = timeSlotId;
    }

    @Override
    public AvailableSlotsAdapter.AvailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.recycleremployeeslots,parent,Boolean.parseBoolean("false"));
        return new  AvailHolder(view);
    }

    @Override
    public void onBindViewHolder(AvailableSlotsAdapter.AvailHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.StartDateAvailSlots.setText(getCreateSlotsModelDataList.get(position).getStartDate());
        holder.EndDateAvailSlots.setText(getCreateSlotsModelDataList.get(position).getEndDate());
        holder.startTimeAvailSlot.setText(getCreateSlotsModelDataList.get(position).getStartTime());
        holder.endTimeAvailSlot.setText(getCreateSlotsModelDataList.get(position).getEndTime());

        holder.deleteAvailSlots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteApi(TableName,TimeSlotId,position);
            }
        });
    }

    private void  deleteApi(String TableNamee,String TimeSlotIdd,int position) {

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<DeleteTimeSlotModel> call = service.DELETE_TIME_SLOT_MODEL_CALL("delete?id="+TableNamee+"&table="+TimeSlotIdd);

        call.enqueue(new Callback<DeleteTimeSlotModel>() {
            @Override
            public void onResponse(Call<DeleteTimeSlotModel> call, Response<DeleteTimeSlotModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        DeleteTimeSlotModel deleteTimeSlotModel = response.body();
                        String success = deleteTimeSlotModel.getSuccess();
                        String message = deleteTimeSlotModel.getMessage();
                        Log.e("hello", "success: " +success );

                        if (success.equals("true")|| (success.equals("True"))) {
                             Data = deleteTimeSlotModel.getData();
                            removeItem(position);
                            notifyDataSetChanged();
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                            Log.e("hello", "getData: " );
                            // Id  = profileGetData.getId();

                            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            // Calling another activity

                        } else {
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<DeleteTimeSlotModel> call, Throwable t) {
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
            getCreateSlotsModelDataList.remove(position);
            notifyDataSetChanged();     // Update data in adapter.... Notify adapter for change data
        } catch (IndexOutOfBoundsException index) {
            index.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return getCreateSlotsModelDataList.size();
    }

    public class AvailHolder extends RecyclerView.ViewHolder {
        TextView StartDateAvailSlots,EndDateAvailSlots,startTimeAvailSlot,endTimeAvailSlot;
        ImageView deleteAvailSlots;
        AppCompatButton BookBtnSlotsAvail;
        public AvailHolder(View itemView) {
            super(itemView);

            StartDateAvailSlots = itemView.findViewById(R.id.StartDateAvailSlots);
            EndDateAvailSlots = itemView.findViewById(R.id.EndDateAvailSlots);
            startTimeAvailSlot = itemView.findViewById(R.id.startTimeAvailSlot);
            endTimeAvailSlot = itemView.findViewById(R.id.endTimeAvailSlot);
            deleteAvailSlots = itemView.findViewById(R.id.deleteAvailSlots);


        }
    }


}
