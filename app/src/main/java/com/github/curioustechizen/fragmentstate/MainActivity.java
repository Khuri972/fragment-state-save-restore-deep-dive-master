package com.github.curioustechizen.fragmentstate;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import hugo.weaving.DebugLog;


public class MainActivity extends Activity {

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, ThirdFragment.newInstance())
                    .commit();
        }
    }

    public void onNextBtnClick(View v) {
        //This method is called when "Next" button in CategoryFragment is clicked
        //In production code, you would handle this click in the CategoryFragment itself and use Callback Interfaces
        // to ask the host Activity to take appropriate action
        showSecondFragment();
    }

    public void onNextBtnClick1(View v) {
        //This method is called when "Next" button in CategoryFragment is clicked
        //In production code, you would handle this click in the CategoryFragment itself and use Callback Interfaces
        // to ask the host Activity to take appropriate action
        showFirstFragment();
    }

    private void showFirstFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, CategoryFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void showSecondFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, SecondFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @DebugLog
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @DebugLog
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
