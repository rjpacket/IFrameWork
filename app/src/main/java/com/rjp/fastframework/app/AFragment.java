package com.rjp.fastframework.app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.rjp.fastframework.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AFragment extends Fragment {


    public AFragment() {
        // Required empty public constructor
    }

    public static AFragment newInstance(int count) {
        Bundle args = new Bundle();
        args.putInt("count", count);
        AFragment fragment = new AFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_a, container, false);
        TextView textView = view.findViewById(R.id.text_view);
        Bundle arguments = getArguments();
        if(arguments != null && arguments.containsKey("count")){
            int count = arguments.getInt("count");
            textView.setText(String.valueOf(count));
        }
        return view;
    }

}
