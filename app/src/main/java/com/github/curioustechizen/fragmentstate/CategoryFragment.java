package com.github.curioustechizen.fragmentstate;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import hugo.weaving.DebugLog;

public class CategoryFragment extends Fragment {

    private static final FlagshipDevice[] ITEMS = {
            new FlagshipDevice("1", "Php"),
            new FlagshipDevice("2", "Android"),
            new FlagshipDevice("3", "Phython"),
            new FlagshipDevice("4", "Nod"),
            new FlagshipDevice("5", ".Net")
    };
    private static final String KEY_ADAPTER_STATE = "com.github.curioustechizen.fragmentstate.KEY_ADAPTER_STATE";

    private FlagshipDeviceAdapter mAdapter;
    private ArrayList<FlagshipDevice> mAdapterSavedState;

    public CategoryFragment() {
    }

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @DebugLog
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Retrieve saved state in onCreate. This method is called even when this fragment is on the back stack
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_ADAPTER_STATE)) {
            mAdapterSavedState = savedInstanceState.getParcelableArrayList(KEY_ADAPTER_STATE);
        }
    }

    @DebugLog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_category, container, false);
        ListView lv = (ListView) rootView.findViewById(android.R.id.list);
        if (mAdapter == null) {
            mAdapter = new FlagshipDeviceAdapter(getActivity());
        }

        if (mAdapterSavedState != null) {
            mAdapter.onRestoreInstanceState(mAdapterSavedState);
        }
        lv.setAdapter(mAdapter);
        ((Button) rootView.findViewById(R.id.btn_populate)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateList(ITEMS);
            }
        });
        return rootView;
    }

    @DebugLog
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mAdapter != null) {
            mAdapterSavedState = mAdapter.onSaveInstanceState();
        }

        //However, remember that this method is called when the device is rotated even if your fragment is on the back stack.
        //In such cases, the onCreateView was not called, hence there is nothing to save.
        //Hence, we just re-save the state that we had retrieved in onCreate. We sort of relay the state from onCreate to onSaveInstanceState.
        outState.putParcelableArrayList(KEY_ADAPTER_STATE, mAdapterSavedState);
    }

    private void populateList(FlagshipDevice... items) {
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


    static class FlagshipDeviceAdapter extends ArrayAdapter<FlagshipDevice> {
        FlagshipDeviceAdapter(Context context) {
            super(context, android.R.layout.simple_list_item_multiple_choice);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_multiple_choice, parent, false);
            TextView tv = (TextView) v.findViewById(android.R.id.text1);
            FlagshipDevice item = getItem(position);
            tv.setText(item.name + " (" + item.formFactor + ")");
            return v;
        }

        ArrayList<FlagshipDevice> onSaveInstanceState() {
            int size = getCount();
            ArrayList<FlagshipDevice> items = new ArrayList<FlagshipDevice>(size);
            for (int i = 0; i < size; i++) {
                items.add(getItem(i));
            }
            return items;
        }

        void onRestoreInstanceState(ArrayList<FlagshipDevice> items) {
            clear();
            addAll(items);
        }

    }

    static class FlagshipDevice implements Parcelable {
        String name;
        String formFactor;

        FlagshipDevice(String name, String formFactor) {
            this.name = name;
            this.formFactor = formFactor;
        }

        protected FlagshipDevice(Parcel in) {
            name = in.readString();
            formFactor = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(formFactor);
        }

        @SuppressWarnings("unused")
        public static final Parcelable.Creator<FlagshipDevice> CREATOR = new Parcelable.Creator<FlagshipDevice>() {
            @Override
            public FlagshipDevice createFromParcel(Parcel in) {
                return new FlagshipDevice(in);
            }

            @Override
            public FlagshipDevice[] newArray(int size) {
                return new FlagshipDevice[size];
            }
        };
    }
}
