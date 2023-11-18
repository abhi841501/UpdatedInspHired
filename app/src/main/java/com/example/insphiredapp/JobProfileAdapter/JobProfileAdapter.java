package com.example.insphiredapp.JobProfileAdapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.insphiredapp.Api_Model.DeleteJobmodel;
import com.example.insphiredapp.Api_Model.JobsList;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api;
import com.example.insphiredapp.retrofit.Api_Client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobProfileAdapter extends RecyclerView.Adapter<JobProfileAdapter.ViewHolder> {
    Context context;
    List<JobsList> jobsListList;

    public JobProfileAdapter(Context context, List<JobsList> jobsListList) {
        this.context = context;
        this.jobsListList = jobsListList;
    }

    @Override
    public JobProfileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.recycler_jobs_profile,parent,Boolean.parseBoolean("false"));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JobProfileAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
     holder.jobsTitleName.setText(jobsListList.get(position).getJobTitle());
     holder.DesignationsName.setText(jobsListList.get(position).getCatName());
     holder.startDateJobsList.setText(jobsListList.get(position).getStartDate());
     holder.endDateJobsList.setText(jobsListList.get(position).getEndDate());
     holder.DailyAmountJlist.setText(jobsListList.get(position).getDailyRate());
     holder.SerialNumberId.setText(jobsListList.get(position).getSerailNo());
     holder.totalHourss.setText(jobsListList.get(position).getStartTime());
     holder.jlistAddress.setText(jobsListList.get(position).getEndTime());

     holder.deleteIconFListJobb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = String.valueOf(jobsListList.get(position).getId());
                String table = jobsListList.get(position).getTable();
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
                        deleteJobApi(id,position,table);
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

    }

    private void deleteJobApi(String idd, int position, String table) {

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<DeleteJobmodel> call = service.DELETE_JOBMODEL_CALL("delete?id="+idd+"&table="+table);

        call.enqueue(new Callback<DeleteJobmodel>() {
            @Override
            public void onResponse(Call<DeleteJobmodel> call, Response<DeleteJobmodel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        DeleteJobmodel deleteJobmodel = response.body();
                        String success = deleteJobmodel.getSuccess();
                        String msg = deleteJobmodel.getMessage();
                        Log.e("hello", "success: " +success );

                        if (success.equals("true")|| (success.equals("True"))) {
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
            public void onFailure(Call<DeleteJobmodel> call, Throwable t) {
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
            jobsListList.remove(position);
            notifyDataSetChanged();     // Update data in adapter.... Notify adapter for change data
        } catch (IndexOutOfBoundsException index) {
            index.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return jobsListList.size();
    }

  public class ViewHolder extends RecyclerView.ViewHolder {
        TextView jobsTitleName,DesignationsName,startDateJobsList,endDateJobsList,DailyAmountJlist,SerialNumberId,totalHourss,jlistAddress;
        ImageView deleteIconFListJobb;
      public ViewHolder(View itemView) {
          super(itemView);
          jobsTitleName = itemView.findViewById(R.id.jobsTitleName);
          DesignationsName = itemView.findViewById(R.id.DesignationsName);
          startDateJobsList = itemView.findViewById(R.id.startDateJobsList);
          endDateJobsList = itemView.findViewById(R.id.endDateJobsList);
          DailyAmountJlist = itemView.findViewById(R.id.DailyAmountJlist);
          SerialNumberId = itemView.findViewById(R.id.SerialNumberId);
          totalHourss = itemView.findViewById(R.id.totalHourss);
          jlistAddress = itemView.findViewById(R.id.jlistAddress);
          deleteIconFListJobb = itemView.findViewById(R.id.deleteIconFListJobb);
      }
  }
}
