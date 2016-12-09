package th.co.omc.memberdemo.adapter.signup;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.model.shopping.Model;

/**
 * Created by teera-s on 11/24/2016 AD.
 */

public class SpinnerDateAdapter extends ArrayAdapter<String> {

    private Context activity;
    private ArrayList data;
    public Resources res;
    private String header;
    LayoutInflater inflater;
    Model model;

    /*************  CustomAdapter Constructor *****************/
    public SpinnerDateAdapter(Context activitySpinner, int textViewResourceId, ArrayList objects, Resources resLocal, String header) {
        super(activitySpinner, textViewResourceId, objects);

        /********** Take passed values **********/
        activity = activitySpinner;
        data     = objects;
        res      = resLocal;
        this.header = header;

        /***********  Layout inflator to call external xml layout () **********************/
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {

        /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
        View row = inflater.inflate(R.layout.spinner_item, parent, false);

        /***** Get each Model object from Arraylist ********/
        model = null;
        model = (Model) data.get(position);

        TextView textView = (TextView) row.findViewById(R.id.row_item);

        if(position == 0) {
            switch (header) {
                case "Date" :
                    textView.setText(activity.getResources().getString(R.string.spinner_date));
                    break;
                case "Month" :
                    textView.setText(activity.getResources().getString(R.string.spinner_month));
                    break;
                case "Year" :
                    textView.setText(activity.getResources().getString(R.string.spinner_year));
                    break;
                case "Prefix" :
                    textView.setText(activity.getResources().getString(R.string.prefix));
                    break;
                case "Nationality" :
                    textView.setText(activity.getResources().getString(R.string.spinner_nationality));
                    break;
                default: break;
            }
        } else {
            textView.setText(model.getItemName());
        }
        return row;
    }
}
