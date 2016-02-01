package cn.manjuu.chatboard.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.manjuu.chatboard.IStatics;
import cn.manjuu.chatboard.Logger;


/**
 * Created by Administrator on 2014/12/29.
 */
public abstract class BaseFragment extends Fragment {

    protected static final boolean DEBUG = IStatics.DEBUG;
    protected static final boolean UI_DEBUG = IStatics.UI_DEBUG;
    protected FragmentManager mFm;

    protected abstract void findView();

    protected abstract void registerListener();

    protected abstract void init();

    @Override
    public void onAttach(Context context) {
        Logger.v(this + "   onAttach");
        super.onAttach(context);

        mFm = getFragmentManager();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.v(this + "   onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.v(this + "   onCreateView ");
        return null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Logger.v(this + "   onActivityCreated ");
        super.onActivityCreated(savedInstanceState);

        findView();
        init();
        registerListener();
    }

    @Override
    public void onStart() {
        Logger.v(this + "   onStart ");
        super.onStart();
    }

    @Override
    public void onResume() {
        Logger.v(this + "   onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Logger.v(this + "   onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Logger.v(this + "   onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Logger.v(this + "   onDestroyView ");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Logger.v(this + "   onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Logger.v(this + "   onDetach ");
        super.onDetach();
    }

}
