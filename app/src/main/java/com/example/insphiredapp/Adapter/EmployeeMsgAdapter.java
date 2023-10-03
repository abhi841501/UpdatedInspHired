package com.example.insphiredapp.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.insphiredapp.Api_Model.MsgEmployeeData;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api_Client;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmployeeMsgAdapter extends RecyclerView.Adapter<EmployeeMsgAdapter.ViewHolder> {
    Context context;
    List<MsgEmployeeData> msgEmployeeDataList;
    private  String UserType;
    private  String user_id;
    private String FromImgEmp,FromImgEmployer;

    public EmployeeMsgAdapter(Context context, List<MsgEmployeeData> msgEmployeeDataList, String UserType, String user_id) {
        this.context = context;
        this.msgEmployeeDataList = msgEmployeeDataList;
        this.UserType = UserType;
        this.user_id = user_id;
    }

    @NonNull
    @Override
    public EmployeeMsgAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_send_message,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeMsgAdapter.ViewHolder holder, int position) {
        SharedPreferences getUserIdData = context.getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        FromImgEmp = getUserIdData.getString("getEmpImage", "");
        FromImgEmployer= getUserIdData.getString("getEmployerImage", "");
        String FromUserId = msgEmployeeDataList.get(position).getFromUser();
        String Imgg = msgEmployeeDataList.get(position).getFromImage();
        Log.e( "onBindViewHolder ", Imgg);
        Log.e( "ViewHolder ", UserType);
        if (user_id.equals(FromUserId))
        {
            if (UserType.equals("employer"))
            {
             //   Glide.with(context).load(Api_Client.BASE_URL_IMAGES1 + msgEmployeeDataList.get(position).getFromImage()).into(holder.employeeImggRight);
                Glide.with(context).load(Api_Client.BASE_URL_IMAGES1+FromImgEmployer).into(holder.employeeImggRight);


            }
            else if (UserType.equals("corporator"))
            {
                Glide.with(context).load(Api_Client.BASE_URL_IMAGES2+FromImgEmployer).into(holder.employeeImggRight);
               // Glide.with(context).load(Api_Client.BASE_URL_IMAGES2 + msgEmployeeDataList.get(position).getFromImage()).into(holder.employeeImggRight);
            }
            else if (UserType.equals("employee"))
            {
              //  Glide.with(context).load(Api_Client.BASE_URL_IMAGES+ msgEmployeeDataList.get(position).getFromImage()).into(holder.employeeImggRight);
                Glide.with(context).load(Api_Client.BASE_URL_IMAGES+FromImgEmp).into(holder.employeeImggRight);

            }


          //  holder.employeeNameeRight.setText(msgEmployeeDataList.get(position).getFirstName());
            holder.chatemployeeMsggRight.setText(msgEmployeeDataList.get(position).getChatMessage());
            holder.LinearRight.setVisibility(View.VISIBLE);
            holder.LinearLeft.setVisibility(View.GONE);
        }
        else
        {
          /*  if (UserType.equals("employer"))
            {
                //Glide.with(context).load(Api_Client.BASE_URL_IMAGES1 + msgEmployeeDataList.get(position).getToImage()).into(holder.comapanyImgLeft);
                Glide.with(context).load(Api_Client.BASE_URL_IMAGES1+FromImgEmployer).into(holder.comapanyImgLeft);

            }
            else if (UserType.equals("corporator"))
            {
               // Glide.with(context).load(Api_Client.BASE_URL_IMAGES2 + msgEmployeeDataList.get(position).getToImage()).into(holder.comapanyImgLeft);
                Glide.with(context).load(Api_Client.BASE_URL_IMAGES2+FromImgEmployer).into(holder.comapanyImgLeft);

            }
            else if (UserType.equals("employee"))
            {
              //  Glide.with(context).load(Api_Client.BASE_URL_IMAGES + msgEmployeeDataList.get(position).getToImage()).into(holder.comapanyImgLeft);
                Glide.with(context).load(Api_Client.BASE_URL_IMAGES+FromImgEmp).into(holder.comapanyImgLeft);
            }*/
           /* Glide.with(context).load(Api_Client.BASE_URL_IMAGES1 + msgEmployeeDataList.get(position).getToImage()).into(holder.comapanyImgLeft);
            Glide.with(context).load(Api_Client.BASE_URL_IMAGES2 + msgEmployeeDataList.get(position).getToImage()).into(holder.comapanyImgLeft);
            Glide.with(context).load(Api_Client.BASE_URL_IMAGES + msgEmployeeDataList.get(position).getToImage()).into(holder.comapanyImgLeft);*/
       //     holder.comapanyNameLeft.setText(msgEmployeeDataList.get(position).getFirstName());
            holder.chatCMsgLeft.setText(msgEmployeeDataList.get(position).getChatMessage());
            holder.LinearLeft.setVisibility(View.VISIBLE);
            holder.LinearRight.setVisibility(View.GONE);
        }



    }

    @Override
    public int getItemCount() {
        return msgEmployeeDataList.size();
    }

    public void setData(List<MsgEmployeeData> msgEmployeeDataList1) {
        msgEmployeeDataList.clear();
        msgEmployeeDataList.addAll(msgEmployeeDataList1);
        notifyDataSetChanged();

    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView comapanyImgLLLL,employeeImggRight;
        TextView comapanyNameLeft,chatCMsgLeft,chatemployeeMsggRight,employeeNameeRight;
        LinearLayout LinearLeft,LinearRight;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
          //  comapanyNameLeft = itemView.findViewById(R.id.comapanyNameLeft);
            chatCMsgLeft = itemView.findViewById(R.id.chatCMsgLeft);
          //  comapanyImgLLLL = itemView.findViewById(R.id.comapanyImgLLLL);
          //  employeeNameeRight = itemView.findViewById(R.id.employeeNameeRight);
            chatemployeeMsggRight = itemView.findViewById(R.id.chatemployeeMsggRight);
            employeeImggRight = itemView.findViewById(R.id.employeeImggRight);
            LinearLeft = itemView.findViewById(R.id.LinearLeft);
            LinearRight = itemView.findViewById(R.id.LinearRight);

        }
    }
}
