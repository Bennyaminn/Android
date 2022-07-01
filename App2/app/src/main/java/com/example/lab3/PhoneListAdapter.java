package com.example.lab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PhoneListAdapter extends RecyclerView.Adapter<PhoneListAdapter.PhoneViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<Phone> mPhoneList;
    private OnNoteListener mOnNoteListener;

    public PhoneListAdapter(Context context, OnNoteListener onNoteListener){
        mLayoutInflater = LayoutInflater.from(context);
        this.mOnNoteListener = onNoteListener;
        this.mPhoneList = null;
    }

    @NonNull
    @Override
    public PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View rootView = mLayoutInflater.inflate(R.layout.recyclerview_item, parent, false);
        PhoneViewHolder phoneViewHolder = new PhoneViewHolder(rootView, mOnNoteListener);
        return phoneViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneViewHolder holder, int position) {
        holder.bindToPhoneViewHolder(position);
        holder.mPhone= mPhoneList.get(position);

    }

    @Override
    public int getItemCount() {
        if(mPhoneList != null)
            return mPhoneList.size();
        return 0;
    }

    public class PhoneViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView markaTextView;
        TextView modelTextView;
        OnNoteListener onNoteListener;
        Phone mPhone;



        public PhoneViewHolder(@NonNull View itemView, OnNoteListener onNoteListener){
            super(itemView);
            markaTextView = itemView.findViewById(R.id.markaTextView);
            modelTextView = itemView.findViewById(R.id.modelTextView);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        public void bindToPhoneViewHolder(int position){
            markaTextView.setText(mPhoneList.get(position).getProducent());
            modelTextView.setText(mPhoneList.get(position).getModel());
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(mPhoneList.get(getAdapterPosition()));
        }

        public Phone getPhone(){
            return mPhone;
        };

    }

    public void setPhoneList(List<Phone> phoneList){
        this.mPhoneList = phoneList;
        notifyDataSetChanged();
    }

    public interface OnNoteListener{
        void onNoteClick(Phone phone);
    }
}
