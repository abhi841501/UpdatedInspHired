package com.example.insphiredapp.EmployeeFragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insphiredapp.Adapter.CreditAmontAdapter;
import com.example.insphiredapp.Adapter.DevitTransactionAdapter;
import com.example.insphiredapp.Api_Model.GetCreditModel;
import com.example.insphiredapp.Api_Model.GetCreditModelData;
import com.example.insphiredapp.Api_Model.GetWithDrawDebitData;
import com.example.insphiredapp.Api_Model.GetWithDrawModel;
import com.example.insphiredapp.Api_Model.WithdrawModel;
import com.example.insphiredapp.Api_Model.WithdrawModelData;
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
public class WalletsFragmentEmployee extends Fragment {

    AppCompatButton  withDrawBtn;
    RecyclerView recyclerDebitAmount;
    List<GetWithDrawDebitData>getWithDrawDebitDataList = new ArrayList<>();
    DevitTransactionAdapter devitTransactionAdapter;
    CreditAmontAdapter creditAmontAdapter;
    List<GetCreditModelData> getCreditModelDataList = new ArrayList<>();
    TextView DebitText,creditTxt,accountBalance;
     private String amount;
    private String UserId,stramountWithdrawEdit;
    WithdrawModelData withdrawModelData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_payments_employee, container, false);

        withDrawBtn = view.findViewById(R.id.withDrawBtn);
        DebitText = view.findViewById(R.id.DebitText);
        creditTxt = view.findViewById(R.id.creditTxt);
        accountBalance = view.findViewById(R.id.accountBalance);
        recyclerDebitAmount = view.findViewById(R.id.recyclerDebitAmount);

        SharedPreferences getUserIdData = getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        UserId= getUserIdData.getString("Id", "");
        Log.e("feedback", "change" + UserId);
        getWalletDebitApi();

        creditTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creditTxt.setBackground(getActivity().getResources().getDrawable(R.drawable.app));
                DebitText.setBackground(getActivity().getResources().getDrawable(R.drawable.creditback));
                creditTxt.setTextColor(Color.parseColor("#FFFFFF"));
                DebitText.setTextColor(Color.parseColor("#000000"));
                getCreditApi();

            }
        });

        DebitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creditTxt.setBackground(getActivity().getResources().getDrawable(R.drawable.creditback));
                DebitText.setBackground(getActivity().getResources().getDrawable(R.drawable.app));
                DebitText.setTextColor(Color.parseColor("#FFFFFF"));
                creditTxt.setTextColor(Color.parseColor("#000000"));
                getWalletDebitApi();
            }
        });
        withDrawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog withDrawDialog  = new Dialog(getActivity());
                withDrawDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                withDrawDialog.setContentView(R.layout.withdrawpopup);
                withDrawDialog.show();
                Window window = withDrawDialog.getWindow();
                if (window == null) return;
                WindowManager.LayoutParams params = window.getAttributes();
                params.width = 1000;
                params.height = 600;
                window.setAttributes(params);
                //    dialog.getWindow().setLayout(100, 100);
                withDrawDialog.getWindow().setBackgroundDrawableResource(R.drawable.withdrawpopupcard);
                ImageView waCrossImage = withDrawDialog.findViewById(R.id.waCrossImage);
                AppCompatButton submit_WithDrawBtn = withDrawDialog.findViewById(R.id.submit_WithDrawBtn);
                EditText amountWithdrawEdit = withDrawDialog.findViewById(R.id.amountWithdrawEdit);
                waCrossImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        withDrawDialog.dismiss();
                    }
                });

                submit_WithDrawBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        stramountWithdrawEdit = amountWithdrawEdit.getText().toString();
                        if (amountWithdrawEdit.getText().toString().equals(""))
                        {
                            Toast.makeText(getActivity(), "Please enter  amount", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            PostWithdrawAmountApi();
                            withDrawDialog.dismiss();
                        }

                    }
                });

            }

        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerDebitAmount.setLayoutManager(layoutManager);





        return view;
    }

    private void  getCreditApi() {
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<GetCreditModel> call = service.GET_CREDIT_MODEL_CALL("my_wallet?+user_id="+UserId+"&switch_id="+2);


        call.enqueue(new Callback<GetCreditModel>() {
            @Override
            public void onResponse(Call<GetCreditModel> call, Response<GetCreditModel> response) {
                //pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        GetCreditModel GetCreditModel = response.body();
                        String success = GetCreditModel.getSuccess();
                        String msg =GetCreditModel.getMessage();
                        Log.e("hello", "success: " +success );


                        if (success.equals("true")|| (success.equals("True"))) {
                             amount = String.valueOf(GetCreditModel.getTotalAmount());
                            accountBalance.setText(amount);
                            getCreditModelDataList = GetCreditModel.getCredit();
                            creditAmontAdapter = new CreditAmontAdapter(getActivity(),getCreditModelDataList);
                            recyclerDebitAmount.setAdapter(creditAmontAdapter);


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
                                    Toast.makeText(getActivity() ,"Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(getActivity(),"The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(getActivity(),"Internal Server Error..", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<GetCreditModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                   // pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getActivity(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                  //  pd.dismiss();
                }
            }
        });


    }

    private void  getWalletDebitApi() {
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<GetWithDrawModel> call = service.GET_WITH_DRAW_MODEL_CALL("my_wallet?+user_id="+UserId+"&switch_id="+1);
        call.enqueue(new Callback<GetWithDrawModel>() {
            @Override
            public void onResponse(Call<GetWithDrawModel> call, Response<GetWithDrawModel> response) {
               // pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        GetWithDrawModel getWithDrawModel = response.body();
                        String success = getWithDrawModel.getSuccess();
                        String msg =getWithDrawModel.getMessage();
                        Log.e("hello", "success: " +success );


                        if (success.equals("true")|| (success.equals("True"))) {
                             amount = String.valueOf(getWithDrawModel.getTotalAmount());
                            accountBalance.setText(amount);
                            getWithDrawDebitDataList = getWithDrawModel.getDebit();
                            devitTransactionAdapter = new DevitTransactionAdapter(getActivity(),getWithDrawDebitDataList);
                            recyclerDebitAmount.setAdapter(devitTransactionAdapter);

                          //  Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();


                            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            // Calling another activity

                        } else {
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                           // pd.dismiss();
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
                                    Toast.makeText(getActivity() ,"Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(getActivity(),"The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(getActivity(),"Internal Server Error..", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<GetWithDrawModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                   // pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                   // Toast.makeText(getActivity(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                   // pd.dismiss();
                }
            }
        });


    }

    private void  PostWithdrawAmountApi() {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<WithdrawModel> call = service.WITHDRAW_MODEL_CALL(UserId,stramountWithdrawEdit,amount);

        call.enqueue(new Callback<WithdrawModel>() {
            @Override
            public void onResponse(Call<WithdrawModel> call, Response<WithdrawModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        WithdrawModel withdrawModel = response.body();
                        String success = withdrawModel.getSuccess();
                        String msg = withdrawModel.getMessage();
                        Log.e("hello", "success: " +success );

                        if (success.equals("true") || success.equals("True")) {
                            withdrawModelData = withdrawModel.getData();

                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                            Log.e("hello", "getData: " );

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
            public void onFailure(Call<WithdrawModel> call, Throwable t) {
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