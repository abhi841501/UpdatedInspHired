package com.example.insphiredapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.insphiredapp.Api_Model.GetCreateSlotsModelData;
import com.example.insphiredapp.EmployerActivity.ItemClickListener;
import com.example.insphiredapp.R;

import java.util.List;

public class SelectSlotsAdapter extends RecyclerView.Adapter<SelectSlotsAdapter.ViewHolder> {
    Context context;
    private int selectedPosition = -1;
    ItemClickListener itemClickListener;
    List<GetCreateSlotsModelData> getCreateSlotsModelDataList;

    public SelectSlotsAdapter(Context context, ItemClickListener itemClickListener, List<GetCreateSlotsModelData> getCreateSlotsModelDataList) {
        this.context = context;
        this.itemClickListener = itemClickListener;
        this.getCreateSlotsModelDataList = getCreateSlotsModelDataList;
    }


    @NonNull
    @Override
    public SelectSlotsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.recyclerselectslots,viewGroup,Boolean.parseBoolean("false"));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectSlotsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.StartDateSelectSlots.setText(getCreateSlotsModelDataList.get(position).getStartDate());
        holder.EndDateSelectSlots.setText(getCreateSlotsModelDataList.get(position).getEndDate());
        holder.startTimeSelectSlot.setText(getCreateSlotsModelDataList.get(position).getStartTime());
        holder.endTimeSelectSlot.setText(getCreateSlotsModelDataList.get(position).getEndTime());

        holder.SelectSlotRadioBtn.setChecked(position == selectedPosition);

     /*   holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String  id = String.valueOf(getCreateSlotsModelDataList.get(position).getId());
                itemClickListener.onClick(position,id);
                Log.e("gello", "onClick: "+id );
            }
        });
*/
        holder.radioGroupBtnSlots.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                //Toast.makeText(getApplicationContext(), rb.getText(), Toast.LENGTH_SHORT).show();
                String  id = String.valueOf(getCreateSlotsModelDataList.get(position).getId());
                itemClickListener.onClick(position,id);
            }
        });

     /*  holder.SelectSlotRadioBtn.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener()   {
                    @Override
                    public void onCheckedChanged(
                            CompoundButton compoundButton,
                            boolean b)
                    {
                        // check condition
                        if (b) {

                            // When checked
                            // update selected position
                            selectedPosition = holder.getAdapterPosition();

                            // Call listener

                        }
                    }
                });*/

    }


    @Override public long getItemId(int position)
    {
        // pass position
        return position;
    }
    @Override public int getItemViewType(int position)
    {
        // pass position
        return position;
    }

    @Override
    public int getItemCount() {
        return getCreateSlotsModelDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView StartDateSelectSlots,EndDateSelectSlots,startTimeSelectSlot,endTimeSelectSlot;
        RadioGroup radioGroupBtnSlots;
        RadioButton SelectSlotRadioBtn;
        public ViewHolder(View itemView) {
            super(itemView);

            StartDateSelectSlots = itemView.findViewById(R.id.StartDateSelectSlots);
            EndDateSelectSlots = itemView.findViewById(R.id.EndDateSelectSlots);
            startTimeSelectSlot = itemView.findViewById(R.id.startTimeSelectSlot);
            endTimeSelectSlot = itemView.findViewById(R.id.endTimeSelectSlot);
            radioGroupBtnSlots = itemView.findViewById(R.id.radioGroupBtnSlots);
            SelectSlotRadioBtn = itemView.findViewById(R.id.SelectSlotRadioBtn);

            SelectSlotRadioBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int copyOfLastCheckedPosition = selectedPosition;
                    selectedPosition = getAdapterPosition();
                    notifyItemChanged(copyOfLastCheckedPosition);
                    notifyItemChanged(selectedPosition);

                }
            });


        }
    }
}
