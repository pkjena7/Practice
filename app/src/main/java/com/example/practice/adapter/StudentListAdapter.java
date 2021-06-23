package com.example.practice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practice.R;
import com.example.practice.model.Student;

import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.ViewHolder> {

    private List<Student> mStudentList;
    Context context;

    public StudentListAdapter( Context context,List<Student> mStudentList) {
        this.mStudentList = mStudentList;
        this.context = context;
    }

    public void updateList(List<Student> lstItem) {
        mStudentList.clear();
        mStudentList.addAll(lstItem);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentListAdapter.ViewHolder holder, int position) {
        holder.textView1.setText(mStudentList.get(position).getName());
        holder.textView2.setText(mStudentList.get(position).getAge());
        holder.textView3.setText(mStudentList.get(position).getAddress());

    }

    @Override
    public int getItemCount() {
        if (mStudentList !=null){
            return this.mStudentList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView1,textView2,textView3;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1=itemView.findViewById(R.id.name);
            textView2=itemView.findViewById(R.id.age);
            textView3=itemView.findViewById(R.id.address);
        }
    }
}
