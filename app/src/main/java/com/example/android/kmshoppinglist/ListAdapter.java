package com.example.android.kmshoppinglist;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Lydia on 06-Oct-18.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private static Context mContext;
    private Cursor mCursor;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    //constructor for ListAdapter class
    public ListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    //inner class (ViewHolder)
    public static class ListViewHolder extends RecyclerView.ViewHolder {


        public TextView tv_list_name;
        public ImageView iv_image;

        //constructor for ViewHolder class
        public ListViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            tv_list_name = itemView.findViewById(R.id.tv_list_name);
            iv_image = itemView.findViewById(R.id.imageView1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);

                            Intent DocIntent = new Intent(mContext, HomeActivity.class);
                            DocIntent.putExtra("pos", position);
                            mContext.startActivity(DocIntent);
                        }
                    }
                }
            });
        }
    }


    @Override
    public ListAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_element_for_rv, parent, false);
        return new ListViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(ListAdapter.ListViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(ListContract.ItemsList.COLUMN_ITEM_NAME));
        String spec = mCursor.getString(mCursor.getColumnIndex(ListContract.ItemsList.COLUMN_ITEM_TOTAL_PRICE));

        int id = mCursor.getInt(mCursor.getColumnIndex(ListContract.ItemsList._ID)); //to get the id (primary key) of that row in the database

        holder.tv_list_name.setText(name);
        holder.iv_image.setImageResource(R.drawable.ic_item_24dp);
        holder.itemView.setTag(id); //for the RecyclerView to hold onto the id

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    private void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }

        mCursor = newCursor;

        if (newCursor != null) {
            // Force the RecyclerView to refresh
            notifyDataSetChanged();
        }
    }

}
