package io.qthjen_dev.deviceinfo.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.qthjen_dev.deviceinfo.R;

/**
 * Created by q-thjen on 2/2/18.
 */

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {

    List<String> list;

    public InfoAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.info_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.info.setText(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView info;

        public ViewHolder(View itemView) {
            super(itemView);

            info = itemView.findViewById(R.id.tv_info);

        }

    }

}
