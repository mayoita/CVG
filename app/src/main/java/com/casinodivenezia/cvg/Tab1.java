package com.casinodivenezia.cvg;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Tab1 extends Fragment{
    private static final String TAG = "Tab1Fragment";
    String[] Names = {"Massimo", "Moro","Marco","Luca"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1, container, false);
        ListView listView = (ListView)getActivity().findViewById(R.id.eventiList);

        return rootView;
    }

     class CustomAdapter extends BaseAdapter {

         @Override
         public int getCount() {
             return Names.length;
         }

         @Override
         public Object getItem(int i) {
             return null;
         }

         @Override
         public long getItemId(int i) {
             return 0;
         }

         @Override
         public View getView(int i, View view, ViewGroup viewGroup) {
             return null;
         }
     }
}
