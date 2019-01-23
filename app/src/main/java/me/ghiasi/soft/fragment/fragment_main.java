package me.ghiasi.soft.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.ghiasi.soft.MainActivity;
import me.ghiasi.soft.R;


public class fragment_main extends Fragment {
    View view;
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main,
                container, false);

//        listenerInit();
        return view;
    }
//    private void listenerInit(){
//        (view.findViewById(R.id.charity)).setOnClickListener(
//                new menuItemListener(1,"Charity"));
//        (view.findViewById(R.id.shops)).setOnClickListener(
//                new menuItemListener(2,"shops"));
//        (view.findViewById(R.id.homeService)).setOnClickListener(
//                new menuItemListener(3,"home Service"));
//        (view.findViewById(R.id.health)).setOnClickListener(
//                new menuItemListener(4,"health Service"));
//        (view.findViewById(R.id.carService)).setOnClickListener(
//                new menuItemListener(5,"car Service"));
//    }

    private class menuItemListener implements View.OnClickListener{
        int mode;
        String modeStr;
        public menuItemListener(int mode,String modeStr) {
            this.mode = mode;
            this.modeStr = modeStr;
        }

        @Override
        public void onClick(View v) {
            ((MainActivity)getActivity()).showProfiles(mode,modeStr);
        }
    }
}
