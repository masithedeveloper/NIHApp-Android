package za.co.android.nihapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import za.co.android.nihapp.Interfaces.IParentSpinner;
import za.co.android.nihapp.R;

public class ParentsSpinnerAdapter extends ArrayAdapter<IParentSpinner> {
    private Context mContext;
    private int resource;
    private ArrayList<IParentSpinner> mData;

    public ParentsSpinnerAdapter(Context context, int resource, ArrayList<IParentSpinner> data) {
        super(context, resource, data);
        this.mContext = context;
        this.mData = data;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View mySpinner = inflater.inflate(resource, parent, false);
        TextView text = mySpinner.findViewById(R.id.layout_parent_spinner_text);
        text.setTextColor(Color.BLACK);
        IParentSpinner currentItem = mData.get(position);
        text.setText(currentItem.GetDisplay());
        return mySpinner;
    }
}