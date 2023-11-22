package com.example.insphiredapp.EmployerFragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insphiredapp.Api_Model.JobListModel;
import com.example.insphiredapp.Api_Model.JobsList;
import com.example.insphiredapp.EmployerActivity.CreateJobActivity;
import com.example.insphiredapp.JobProfileAdapter.JobProfileAdapter;
import com.example.insphiredapp.databinding.FragmentJobsProfileBinding;
import com.example.insphiredapp.retrofit.Api;
import com.example.insphiredapp.retrofit.Api_Client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobsProfileFragment extends Fragment {
    private FragmentJobsProfileBinding binding;
    JobProfileAdapter jobProfileAdapter;
    private String user_id,userType;
    List<JobsList> jobsListList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentJobsProfileBinding.inflate(inflater, container, false);
        binding.createJobsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateJobActivity.class);
                startActivity(intent);
            }
        });


        SharedPreferences getUserIdData = getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        user_id= getUserIdData.getString("Id", "");
        userType= getUserIdData.getString("userType", "");
        Log.e("feedback", "change" + user_id);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerJobsList.setLayoutManager(layoutManager);
        showJobsApi();


        return binding.getRoot();
       // return inflater.inflate(R.layout.fragment_jobs_profile, container, false);
    }
    private void  showJobsApi() {

      /*  final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("please...");
        pd.show();*/
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<JobListModel> call = service.JOB_LIST_MODEL_CALL("employer_job_list?"+"user_id="+user_id+"&user_type="+userType);

        call.enqueue(new Callback<JobListModel>() {
            @Override
            public void onResponse(Call<JobListModel> call, Response<JobListModel> response) {
               // pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String success = (response.body().getSuccess());
                        String msg = (response.body().getMessage());

                        if (success.equals("true")|| (success.equals("True"))) {
                            jobsListList = response.body().getMyJobList();
                            if (jobsListList.isEmpty())
                            {
                                binding.recyclerJobsList.setVisibility(View.GONE);
                                binding.myJonbsListtLinear.setVisibility(View.VISIBLE);
                            }
                            jobProfileAdapter = new JobProfileAdapter(getActivity(),jobsListList);
                            binding.recyclerJobsList.setAdapter(jobProfileAdapter);



                            Log.e("hello", "getData: " );
                            // Id  = profileGetData.getId();


                        } else {
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                          //  pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(getActivity(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(getActivity(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(getActivity(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(getActivity(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(getActivity(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(getActivity(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(getActivity(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getActivity(), "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JobListModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    //pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getActivity(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                 //   pd.dismiss();
                }
            }
        });


    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            onResume();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }
        showJobsApi();
    }
}