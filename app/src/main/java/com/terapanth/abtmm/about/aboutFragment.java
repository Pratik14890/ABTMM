package com.terapanth.abtmm.about;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.terapanth.abtmm.R;
import com.terapanth.abtmm.services.ExecutionCompleteListener;
import com.terapanth.abtmm.services.WebServiceHandler;

/**
 * Created by MindstixSoftware on 01/01/18.
 */

public class AboutFragment extends Fragment {

    Button btn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        btn = (Button)view.findViewById(R.id.testButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                WebServiceHandler client = new WebServiceHandler(getActivity());
                client.setMethodName("GetBWNMemberList");
                client.addParameter("AuthKey","Adnkmdl230$fksJ#");
                client.addParameter("Name","ABC");
                client.addParameter("Profession","Student");
                client.addParameter("City","Pune");
                client.setOnExecutionComplete(new ExecutionCompleteListener() {
                    @Override
                    public void onComplete(String str) {
                        String str1 = str;
                    }
                });
                client.execute();
            }
        });

        return view;
    }
}
