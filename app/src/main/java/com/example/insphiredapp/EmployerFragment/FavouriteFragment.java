package com.example.insphiredapp.EmployerFragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insphiredapp.Adapter.FavouriteEmployerAdapter;
import com.example.insphiredapp.Api_Model.GetFavouriteModel;
import com.example.insphiredapp.Api_Model.GetFavouriteModelList;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api;
import com.example.insphiredapp.retrofit.Api_Client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FavouriteFragment extends Fragment {
    RecyclerView recyclerFavourite;
    List<GetFavouriteModelList>getFavouriteModelLists = new ArrayList<>();
    FavouriteEmployerAdapter favouriteEmployerAdapterAdapter;
    private String UserId,UserType;
    private LinearLayout fav_employee_Linearrr;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  =  inflater.inflate(R.layout.fragment_favourite, container, false);

        recyclerFavourite = view.findViewById(R.id.recyclerFavourite);
        fav_employee_Linearrr = view.findViewById(R.id.fav_employee_Linearrr);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerFavourite.setLayoutManager(layoutManager);

        SharedPreferences getUserIdData = getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        UserId= getUserIdData.getString("Id", "");
        UserType= getUserIdData.getString("userType", "");

        Log.e("feedback", "change" + UserId);

        GetAllEmployeeFavList();


        return  view;
    }

    private void  GetAllEmployeeFavList() {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<GetFavouriteModel> call = service.GET_FAVOURITE_MODEL_CALL("fav_list?+user_id="+UserId+"&user_type="+UserType);

        call.enqueue(new Callback<GetFavouriteModel>() {
            @Override
            public void onResponse(Call<GetFavouriteModel> call, Response<GetFavouriteModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        GetFavouriteModel getFavouriteModel = response.body();
                        String success = getFavouriteModel.getSuccess();
                        String msg = getFavouriteModel.getMessage();
                        Log.e("hello", "success: " +success );

                        if (success.equals("true")|| (success.equals("True"))) {
                            getFavouriteModelLists = getFavouriteModel.getData();

                            if (getFavouriteModelLists.isEmpty())
                            {
                                fav_employee_Linearrr.setVisibility(View.VISIBLE);
                                recyclerFavourite.setVisibility(View.GONE);

                            }

                            favouriteEmployerAdapterAdapter = new FavouriteEmployerAdapter(getActivity(), getFavouriteModelLists);
                            recyclerFavourite.setAdapter(favouriteEmployerAdapterAdapter);

                            Log.e("hello", "getData: " );
                            // Id  = profileGetData.getId();


                           // Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();


                            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            // Calling another activity

                        } else {
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                            pd.dismiss();
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
            public void onFailure(Call<GetFavouriteModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getActivity(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }




}