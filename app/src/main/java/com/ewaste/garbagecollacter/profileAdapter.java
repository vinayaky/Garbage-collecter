package com.ewaste.garbagecollacter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class profileAdapter extends RecyclerView.Adapter<MainViewHolder>{
    private Context context;
    private List<DataClass> dataList;

    public profileAdapter(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getDataImage()).into(holder.image);
        holder.name.setText(dataList.get(position).getDataName());
        holder.work.setText(dataList.get(position).getDataWeight());
        holder.number.setText(dataList.get(position).getDataNumber());
        holder.garbageType.setText(dataList.get(position).getDataClint());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, profileDetail.class);
                intent.putExtra("image",dataList.get(holder.getAdapterPosition()).getDataImage() );
                intent.putExtra("name",dataList.get(holder.getAdapterPosition()).getDataName());
                intent.putExtra("work",dataList.get(holder.getAdapterPosition()).getDataWeight());
                intent.putExtra("add",dataList.get(holder.getAdapterPosition()).getDataAdd());
                intent.putExtra("number",dataList.get(holder.getAdapterPosition()).getDataNumber());
                intent.putExtra("garbage",dataList.get(holder.getAdapterPosition()).getDataClint());
                intent.putExtra("key",dataList.get(holder.getAdapterPosition()).getKey());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
class MainViewHolder extends  RecyclerView.ViewHolder{
    ImageView image;
    TextView name , work,number,garbageType;
    CardView cardView;
    public MainViewHolder(@NonNull View itemView) {
        super(itemView);
        image= itemView.findViewById(R.id.Img);
        name = itemView.findViewById(R.id.Name);
        work = itemView.findViewById(R.id.Weight);
        garbageType = itemView.findViewById(R.id.Address);
        cardView = itemView.findViewById(R.id.cardview);
        number = itemView.findViewById(R.id.Number);
    }
}