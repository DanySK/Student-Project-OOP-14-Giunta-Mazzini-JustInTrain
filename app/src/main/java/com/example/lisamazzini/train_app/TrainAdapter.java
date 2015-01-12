package com.example.lisamazzini.train_app;


import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.TextView;

        import com.jaus.trainapp.model.Train;

        import java.util.LinkedList;
        import java.util.List;

/**
 * Created by albertogiunta on 28/12/14.
 */

public class TrainAdapter extends BaseAdapter {

    private List<Train> list = new LinkedList<>();
    private LayoutInflater inflater;

    public TrainAdapter(Context context, List<Train> list) {
        this.list = list;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.train_adapter_layout, parent, false);
            holder = new ViewHolder();
            holder.trainCategory = (TextView)convertView.findViewById(R.id.trainCat);
            holder.trainNumber = (TextView)convertView.findViewById(R.id.trainNum);
            holder.delay = (TextView)convertView.findViewById(R.id.delay);
            holder.lastSeen = (TextView)convertView.findViewById(R.id.lastseen);
            holder.isMoving = (TextView)convertView.findViewById(R.id.moving);
            holder.time = (TextView)convertView.findViewById(R.id.timelastseen);
            holder.condition = (TextView)convertView.findViewById(R.id.condition);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.trainCategory.setText(list.get(position).getCategory());
        holder.trainNumber.setText(list.get(position).getNumber());
        holder.lastSeen.setText(list.get(position).getLastSeenStation());
        holder.time.setText(list.get(position).getLastSeenTime());
        holder.condition.setText(list.get(position).getCondition());
        holder.delay.setText("" + list.get(position).getDelay());
        holder.isMoving.setText("" + list.get(position).isMoving());

        return convertView;
    }
    static class ViewHolder {
        TextView trainCategory;
        TextView trainNumber;
        TextView delay;
        TextView lastSeen;
        TextView time;
        TextView condition;
        TextView isMoving;
    }

}

