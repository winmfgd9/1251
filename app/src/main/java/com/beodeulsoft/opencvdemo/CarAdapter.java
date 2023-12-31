package com.beodeulsoft.opencvdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CarAdapter extends ArrayAdapter<Car> { //생성자
    public CarAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.car_item, null);

            viewHolder = new ViewHolder();
            viewHolder.imgDot = (ImageView) convertView.findViewById(R.id.imgDot);
            viewHolder.txtNum = (TextView) convertView.findViewById(R.id.txtNum);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        Car car = getItem(position);
        viewHolder.txtNum.setText(""+car.getSlot());
        viewHolder.imgDot.setImageResource(car.getCarEmpty() ? R.drawable.dot_g : R.drawable.dot_r);

        return convertView;
    }
    private class ViewHolder{
        TextView txtNum;
        ImageView imgDot;
    }
}