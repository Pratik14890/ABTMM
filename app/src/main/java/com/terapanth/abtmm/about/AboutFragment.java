package com.terapanth.abtmm.about;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.terapanth.abtmm.R;
import com.terapanth.abtmm.services.OnExecuteComplete;
import com.terapanth.abtmm.services.WebServiceHandler;
import com.terapanth.abtmm.services.model.response.WS_MagazineResponse;

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
                WebServiceHandler client = new WebServiceHandler();
                client.setMethodName("GetBWNMemberList");
                client.addAuth();
                client.addParameter("Name","ABC", String.class);
                client.addParameter("Profession","Student", String.class);
                client.addParameter("City","Pune", String.class);
                client.setOnExecuteComplete(new OnExecuteComplete() {
                    @Override
                    public void onComplete(Object str) {
                        String str1 = String.valueOf(str);
                    }
                });
                client.execute(WS_MagazineResponse[].class);
            }
        });

        return view;
    }
}
