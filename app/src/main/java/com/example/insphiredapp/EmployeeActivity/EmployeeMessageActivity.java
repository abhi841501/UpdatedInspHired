package com.example.insphiredapp.EmployeeActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insphiredapp.Adapter.EmployeeMsgAdapter;
import com.example.insphiredapp.Api_Model.MsgEmployeeData;
import com.example.insphiredapp.Api_Model.MsgEmployeeModel;
import com.example.insphiredapp.Api_Model.UserChatModel;
import com.example.insphiredapp.Api_Model.UserChatModelData;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api;
import com.example.insphiredapp.retrofit.Api_Client;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeMessageActivity extends AppCompatActivity {
    private static final int INTERVAL = 5000; // Update every 1 second
    private Handler handler = new Handler();


    private ImageView backArrowME;
    private ImageView sendBtnEmployee;
    private List<MsgEmployeeData> msgEmployeeDataList = new ArrayList<>();
    private UserChatModelData userChatModelData;
    private EmployeeMsgAdapter employeeMsgAdapter;
    private RecyclerView recyclerSendMsg;
    private EditText editSendMsg;
    private String user_id, employeeType, UserType, StrSendMessage,employerType,employee_id,employer_id;
    MessagesList messagesList;
    MessageInput input;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_message);

        backArrowME = findViewById(R.id.backArrowME);
        sendBtnEmployee = findViewById(R.id.sendBtnEmployee);
        recyclerSendMsg = findViewById(R.id.recyclerSendMsg);
        editSendMsg = findViewById(R.id.editSendMsg);

        //initsView();

        //employee_id =getIntent().getStringExtra("EmployeeId");
        Log.e("employee_id", "onCreate: " + employeeType);

        backArrowME.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences getUserIdData = getApplicationContext().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        user_id = getUserIdData.getString("Id", "");
        UserType = getUserIdData.getString("userType", "");


        employeeType = getIntent().getStringExtra("UserTypeEmpppp");
        employerType = getIntent().getStringExtra("UserTypeEmployer");
        employee_id = getIntent().getStringExtra("employeeIdddd");
        employer_id = getIntent().getStringExtra("IdEmployer");

        Log.e( "onCreate ","employee"+employee_id );
        Log.e( "onCreate ","employee type "+employeeType );
        Log.e( "onCreate ","UserId "+user_id );
        Log.e( "onCreate ","User type "+UserType );


        Log.e( "onCreate1 ","employer_id"+employer_id );
        Log.e( "onCreate1 ","employerType "+employerType );
        Log.e( "onCreate1 ","UserId "+user_id );
        Log.e( "onCreate1 ","User type "+UserType );



        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(EmployeeMessageActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerSendMsg.setLayoutManager(layoutManager);
       // recyclerSendMsg.setItemAnimator(new DefaultItemAnimator());
        handler.postDelayed(updateChatRunnable, INTERVAL);


        employeeMsgAdapter = new EmployeeMsgAdapter(EmployeeMessageActivity.this, msgEmployeeDataList, UserType, user_id);
        recyclerSendMsg.setAdapter(employeeMsgAdapter);

        if (UserType.equals("employer")||UserType.equals("corporator"))
        {
            GetMessageList();
        }
        else if (UserType.equals("employee"))
        {
            GetMessageList1();

        }




        sendBtnEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrSendMessage = editSendMsg.getText().toString();

                editSendMsg.setText("");

                if (UserType.equals("employer")||UserType.equals("corporator"))
                {
                    employee_id = getIntent().getStringExtra("employeeIdddd");
                    PostMessageApi();
                }
                else if (UserType.equals("employee"))
                {
                    employer_id = getIntent().getStringExtra("IdEmployer");
                   PostMessageApi1();

                }


            }
        });

    }

    private Runnable updateChatRunnable = new Runnable() {
        @Override
        public void run() {
            if (UserType.equals("employer")||UserType.equals("corporator"))
            {
                GetMessageList();
            }
            else if (UserType.equals("employee"))
            {
                GetMessageList1();

            }
            handler.postDelayed(this, INTERVAL);
        }
    };

/*    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove the updateChatRunnable from the handler to stop periodic updates when the activity is destroyed
        handler.removeCallbacks(updateChatRunnable);
    }*/

    private void PostMessageApi() {
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<UserChatModel> call = service.USER_CHAT_MODEL_CALL(user_id, employee_id, StrSendMessage, UserType);

        call.enqueue(new Callback<UserChatModel>() {
            @Override
            public void onResponse(Call<UserChatModel> call, Response<UserChatModel> response) {
              //  pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {

                        String success = response.body().getSuccess();
                        String msg = response.body().getMessage();
                        Log.e("hello", "getData:... " + success);

                        if (success.equals("true") || (success.equals("True"))) {
                            GetMessageList();
                            Log.e("hello", "getData: ");
                            // Id  = profileGetData.getId();
                        } else {
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                          //  pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getApplicationContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(getApplicationContext(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(getApplicationContext(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(getApplicationContext(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(getApplicationContext(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(getApplicationContext(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(getApplicationContext(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(getApplicationContext(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getApplicationContext(), "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UserChatModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(getApplicationContext(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    //pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getApplicationContext(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                   // pd.dismiss();
                }
            }
        });
    }
    private void PostMessageApi1() {
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<UserChatModel> call = service.USER_CHAT_MODEL_CALL(user_id, employer_id, StrSendMessage, UserType);

        call.enqueue(new Callback<UserChatModel>() {
            @Override
            public void onResponse(Call<UserChatModel> call, Response<UserChatModel> response) {
              //  pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {

                        String success = response.body().getSuccess();
                        String msg = response.body().getMessage();
                        Log.e("hello", "getData:... " + success);

                        if (success.equals("true") || (success.equals("True"))) {
                            GetMessageList1();
                            Log.e("hello", "getData: ");
                            // Id  = profileGetData.getId();
                        } else {
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                          //  pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getApplicationContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(getApplicationContext(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(getApplicationContext(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(getApplicationContext(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(getApplicationContext(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(getApplicationContext(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(getApplicationContext(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(getApplicationContext(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getApplicationContext(), "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UserChatModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(getApplicationContext(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    //pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getApplicationContext(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                   // pd.dismiss();
                }
            }
        });
    }
    private void GetMessageList() {
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<MsgEmployeeModel> call = service.MSG_EMPLOYEE_CALL("get_chat?to_user="+employee_id+"&user_type="+employeeType+"&from_user="+user_id+"&login_type="+UserType);

        call.enqueue(new Callback<MsgEmployeeModel>() {
            @Override
            public void onResponse(Call<MsgEmployeeModel> call, Response<MsgEmployeeModel> response) {
                //pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        MsgEmployeeModel msgEmployeeModel = response.body();
                        String success = msgEmployeeModel.getSuccess();
                        String msg = msgEmployeeModel.getMessage();
                        if (msgEmployeeModel != null) {
                            List<MsgEmployeeData> chatMessages = msgEmployeeModel.getData();
                            updateChatUI(chatMessages);
                        }

                        Log.e("hello", "success: " + success);

                        if (success.equals("true") || (success.equals("True"))) {
                            msgEmployeeDataList.clear();
                            msgEmployeeDataList = msgEmployeeModel.getData();

                            Log.e("hello", "getData: ");

                            employeeMsgAdapter.setData(msgEmployeeDataList);
                            recyclerSendMsg.smoothScrollToPosition(msgEmployeeDataList.size() - 1);
                            employeeMsgAdapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                           // pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getApplicationContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(getApplicationContext(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(getApplicationContext(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(getApplicationContext(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(getApplicationContext(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(getApplicationContext(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(getApplicationContext(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(getApplicationContext(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getApplicationContext(), "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MsgEmployeeModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(getApplicationContext(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    //pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getApplicationContext(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                  //  pd.dismiss();
                }
            }
        });


    }
    private void GetMessageList1() {
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<MsgEmployeeModel> call = service.MSG_EMPLOYEE_CALL("get_chat?to_user="+employer_id+"&user_type="+employerType+"&from_user="+user_id+"&login_type="+UserType);

        call.enqueue(new Callback<MsgEmployeeModel>() {
            @Override
            public void onResponse(Call<MsgEmployeeModel> call, Response<MsgEmployeeModel> response) {
                //pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        MsgEmployeeModel msgEmployeeModel = response.body();
                        String success = msgEmployeeModel.getSuccess();
                        String msg = msgEmployeeModel.getMessage();
                        if (msgEmployeeModel != null) {
                            List<MsgEmployeeData> chatMessages = msgEmployeeModel.getData();
                            updateChatUI(chatMessages);
                        }

                        Log.e("hello", "success: " + success);

                        if (success.equals("true") || (success.equals("True"))) {
                            msgEmployeeDataList.clear();
                            msgEmployeeDataList = msgEmployeeModel.getData();

                            Log.e("hello", "getData: ");

                            employeeMsgAdapter.setData(msgEmployeeDataList);
                            recyclerSendMsg.smoothScrollToPosition(msgEmployeeDataList.size() - 1);
                            employeeMsgAdapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                           // pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getApplicationContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(getApplicationContext(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(getApplicationContext(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(getApplicationContext(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(getApplicationContext(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(getApplicationContext(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(getApplicationContext(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(getApplicationContext(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getApplicationContext(), "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MsgEmployeeModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(getApplicationContext(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    //pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getApplicationContext(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                  //  pd.dismiss();
                }
            }
        });


    }

    private void updateChatUI(List<MsgEmployeeData> chatMessages) {

        StringBuilder newChatText = new StringBuilder();
        String chatText = " ";
        for (MsgEmployeeData message : chatMessages) {
            newChatText.insert(0, message.getChatMessage() + "\n");
        }

        // Append the existing chat history after the new messages
        newChatText.append(chatText);

        // Update the chatText with the new chat history
        chatText = String.valueOf(newChatText);
    }
}