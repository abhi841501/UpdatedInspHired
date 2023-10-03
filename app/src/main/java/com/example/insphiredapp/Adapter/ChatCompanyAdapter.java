package com.example.insphiredapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.insphiredapp.Api_Model.ChatCompanyModelData;
import com.example.insphiredapp.EmployeeActivity.EmployeeMessageActivity;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api_Client;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatCompanyAdapter extends RecyclerView.Adapter<ChatCompanyAdapter.ViewHolder> {

    Context context;
    List<ChatCompanyModelData> chatCompanyModelList;
    private String UserType;
    private String  User = "employee";
    private  String employeeImg;

    public ChatCompanyAdapter(Context context, List<ChatCompanyModelData> chatCompanyModelList, String userType) {
        this.context = context;
        this.chatCompanyModelList = chatCompanyModelList;
        UserType = userType;
    }

    @Override
    public ChatCompanyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.relative_chat_employee, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatCompanyAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        employeeImg = chatCompanyModelList.get(position).getImage();

           Glide.with(context).load(Api_Client.BASE_URL_IMAGES1 + chatCompanyModelList.get(position).getImage()).into(holder.companyImgcc);
            //Glide.with(context).load(Api_Client.BASE_URL_IMAGES2 + chatCompanyModelList.get(position).getImage()).into(holder.companyImgcc);
        /*     if (UserType.equals("employee") || UserType.equals("corporator"))
            {
                Glide.with(context).load(Api_Client.BASE_URL_IMAGES1 + chatCompanyModelList.get(position).getImage()).into(holder.companyImgcc);
                Glide.with(context).load(Api_Client.BASE_URL_IMAGES2 + chatCompanyModelList.get(position).getImage()).into(holder.companyImgcc);

            }
*/
        holder.cnameee.setText(chatCompanyModelList.get(position).getCompanyName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String IdEmployer = String.valueOf(chatCompanyModelList.get(position).getEmployerId());
                String UserTypeEmployer = String.valueOf(chatCompanyModelList.get(position).getUserType());
                Intent i = new Intent(context, EmployeeMessageActivity.class);
                i.putExtra("UserTypeEmployer", UserTypeEmployer);
                i.putExtra("IdEmployer", IdEmployer);
                context.startActivity(i);
                Log.e("onClick1","GetID"+IdEmployer);
            }
        });
        SharedPreferences getUserIdData = context.getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = getUserIdData.edit();
        editor.putString("getEmployerImage",String.valueOf(employeeImg));
        editor.apply();

    }

    @Override
    public int getItemCount() {
        return chatCompanyModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView companyImgcc;
        TextView cnameee;

        public ViewHolder(View itemView) {
            super(itemView);
            companyImgcc = itemView.findViewById(R.id.companyImgcc);
            cnameee = itemView.findViewById(R.id.cnameee);

        }
    }
}
