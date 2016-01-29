package cn.manjuu.chatboard.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.manjuu.chatboard.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<String> mDataList;
    private OnItemClickListener mListener;

    public MyAdapter() {
        mDataList = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            mDataList.add("msg-" + i);
        }
    }

    public void setData(int position, String msg) {
        mDataList.set(position, msg);
    }

    public void addData(String msg, int position) {
        mDataList.add(position, msg);
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        mDataList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup inflate = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder vh = new ViewHolder(inflate, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView childAt = (TextView) holder.mRoot.getChildAt(0);
        childAt.setText(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewGroup mRoot;
        OnItemClickListener mListener;

        public ViewHolder(ViewGroup v, OnItemClickListener listener) {
            super(v);
            mRoot = v;
            mListener = listener;
            mRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position == RecyclerView.NO_POSITION) {
                        return;
                    }
                    mListener.onItemClick(v, position);
                }
            });
        }
    }

    public void setListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
}