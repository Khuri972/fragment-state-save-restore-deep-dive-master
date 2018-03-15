package com.github.curioustechizen.fragmentstate;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import hugo.weaving.DebugLog;

/**
 * Created by CRAFT BOX on 3/7/2018.
 */

public class ThirdFragment extends Fragment {

    private static final String KEY_ADAPTER_STATE = "com.github.curioustechizen.fragmentstate.KEY_ADAPTER_STATE";

    private FlagshipDeviceAdapter mAdapter;
    private ArrayList<ThirdModel> mAdapterSavedState = new ArrayList<ThirdModel>();

    public ThirdFragment(){}

    public static ThirdFragment newInstance(){
        return new ThirdFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null && savedInstanceState.containsKey(KEY_ADAPTER_STATE)){
            mAdapterSavedState = (ArrayList<ThirdModel>) savedInstanceState.getSerializable(KEY_ADAPTER_STATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_category, container, false);
        ListView lv = (ListView) rootView.findViewById(android.R.id.list);

        //When we go to next fragment and return back here, the adapter is already present and populated.
        //Don't create it again in such cases. Hence the null check.
        if(mAdapter == null){
            mAdapter = new FlagshipDeviceAdapter(getActivity());
        }

        //Use the state retrieved in onCreate and set it on your views etc in onCreateView
        //This method is not called if the device is rotated when your fragment is on the back stack.
        //That's OK since the next time the device is rotated, we save the state we had retrieved in onCreate
        //instead of saving current state. See onSaveInstanceState for more details.
        if(mAdapterSavedState != null){
            mAdapter.onRestoreInstanceState(mAdapterSavedState);
        }
        lv.setAdapter(mAdapter);
        ((Button)rootView.findViewById(R.id.btn_populate)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThirdModel da=new ThirdModel();
                da.setName("Nexus 4");
                da.setFormFactor("Phone");
                mAdapterSavedState.add(da);
                da=new ThirdModel();
                da.setName("jay");
                da.setFormFactor("achariya");
                mAdapterSavedState.add(da);

                da=new ThirdModel();
                da.setName("hardip");
                da.setFormFactor("gol");
                mAdapterSavedState.add(da);

                da=new ThirdModel();
                da.setName("Ravi");
                da.setFormFactor("Shiroya");
                mAdapterSavedState.add(da);
                populateList(mAdapterSavedState);
            }
        });
        return rootView;
    }

    @DebugLog
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(mAdapter != null){
            //This case is for when the fragment is at the top of the stack. onCreateView was called and hence there is state to save
            mAdapterSavedState = mAdapter.onSaveInstanceState();
        }

        //However, remember that this method is called when the device is rotated even if your fragment is on the back stack.
        //In such cases, the onCreateView was not called, hence there is nothing to save.
        //Hence, we just re-save the state that we had retrieved in onCreate. We sort of relay the state from onCreate to onSaveInstanceState.
        outState.putSerializable(KEY_ADAPTER_STATE, mAdapterSavedState);
    }

    private void populateList(ArrayList<ThirdModel> items) {
        mAdapter.clear();
        mAdapter.addAll(items);
    }

    @DebugLog
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @DebugLog
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    static class FlagshipDeviceAdapter extends ArrayAdapter<ThirdModel> {
        FlagshipDeviceAdapter(Context context){
            super(context, android.R.layout.simple_list_item_multiple_choice);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_multiple_choice, parent, false);
            TextView tv = (TextView) v.findViewById(android.R.id.text1);
            ThirdModel item = getItem(position);
            tv.setText(item.name + " ("+item.formFactor+")");
            return v;
        }

        ArrayList<ThirdModel> onSaveInstanceState(){
            int size = getCount();
            ArrayList<ThirdModel> items = new ArrayList<ThirdModel>(size);
            for(int i=0;i<size;i++){
                items.add(getItem(i));
            }
            return items;
        }

        void onRestoreInstanceState(ArrayList<ThirdModel> items){
            clear();
            addAll(items);
        }
    }

}
