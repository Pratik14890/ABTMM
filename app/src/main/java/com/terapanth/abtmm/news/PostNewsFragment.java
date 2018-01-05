package com.terapanth.abtmm.news;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.terapanth.abtmm.MainActivity;
import com.terapanth.abtmm.R;
import com.terapanth.abtmm.home.HomeFragment;
import com.terapanth.abtmm.services.OnExecuteComplete;
import com.terapanth.abtmm.services.WebServiceHandler;
import com.terapanth.abtmm.services.model.news.NewsSummary;
import com.terapanth.abtmm.services.model.news.PostNews;
import com.terapanth.abtmm.services.model.response.WS_NewsSummaryResponse;
import com.terapanth.abtmm.services.model.response.WS_PostNewsResponse;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by MindstixSoftware on 05/01/18.
 */

public class PostNewsFragment extends Fragment implements View.OnClickListener {

    private static MainActivity mainActivity;
    private Context context;
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private ImageButton btnBack;
    private TextView txtBack;
    private ImageButton btnShare;
    private EditText etName, etContactNumber, etEmail, etDesc, etTitle;
    private EditText etImage1, etImage2, etImage3, etImage4, etImage5;
    private TextView btnUpload1, btnUpload2, btnUpload3, btnUpload4, btnUpload5;
    private Bitmap bitmap;
    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] byteArray;
    private String convertImage, imagePath;
    private ProgressDialog progressDialog;
    private EditText currentEdittext;
    private TextView postDataView;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private List<PostNews> postNewsResponse;

    private final static String WS_POST_NEWS_TO_SERVER = "InsertUserAnnouncement";

    public PostNewsFragment() {
        postNewsResponse = new ArrayList<>();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TestPostNewsFragment", "onCreate");
        mainActivity = (MainActivity) getActivity();
        context = mainActivity.getApplicationContext();

        toolbar = (Toolbar) mainActivity.findViewById(R.id.tool_bar);
        toolbar.setVisibility(View.VISIBLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {

        Log.d("TestPostNewsFragment", "onCreateView");

        View view = inflater.inflate(R.layout.fragment_news_post_form, container, false);

        toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setVisibility(View.VISIBLE);

        btnBack = (ImageButton) toolbar.findViewById(R.id.toolbar_back_button);
        btnBack.setVisibility(View.VISIBLE);

        txtBack = (TextView) toolbar.findViewById(R.id.toolbar_back_text);
        txtBack.setVisibility(View.VISIBLE);

        btnShare = (ImageButton) toolbar.findViewById(R.id.toolbar_share_button);
        btnShare.setVisibility(View.INVISIBLE);

        retriveReferencesFromXml(view);
        setOnClickListeners();

        setPageDetails();

        return view;
    }

    private void retriveReferencesFromXml(View view) {
        etName = (EditText)view.findViewById(R.id.et_name);
        etContactNumber = (EditText)view.findViewById(R.id.et_contact_number);
        etEmail = (EditText)view.findViewById(R.id.et_email_id);
        etDesc = (EditText)view.findViewById(R.id.et_news_desc);
        etTitle = (EditText)view.findViewById(R.id.et_news_title);

        etImage1 = (EditText)view.findViewById(R.id.et_image1);
        etImage2 = (EditText)view.findViewById(R.id.et_image2);
        etImage3 = (EditText)view.findViewById(R.id.et_image3);
        etImage4 = (EditText)view.findViewById(R.id.et_image4);
        etImage5 = (EditText)view.findViewById(R.id.et_image5);

        btnUpload1 = (TextView) view.findViewById(R.id.btnUpload1);
        btnUpload2 = (TextView) view.findViewById(R.id.btnUpload2);
        btnUpload3 = (TextView) view.findViewById(R.id.btnUpload3);
        btnUpload4 = (TextView) view.findViewById(R.id.btnUpload4);
        btnUpload5 = (TextView) view.findViewById(R.id.btnUpload5);

        postDataView = (TextView) view.findViewById(R.id.post_data_view);
    }

    private void setPageDetails() {

        toolbarTitle.setText("Post Your News");
    }

    private void setOnClickListeners() {
        btnBack.setOnClickListener(this);
        txtBack.setOnClickListener(this);
        btnUpload1.setOnClickListener(this);
        btnUpload2.setOnClickListener(this);
        btnUpload3.setOnClickListener(this);
        btnUpload4.setOnClickListener(this);
        btnUpload5.setOnClickListener(this);
        postDataView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back_button:
                mainActivity.onBackPressed();
                break;

            case R.id.toolbar_back_text:
                mainActivity.onBackPressed();
                break;

            case R.id.btnUpload1:
                currentEdittext = etImage1;
                openGalleryForImageUploading();
                break;

            case R.id.btnUpload2:
                currentEdittext = etImage2;
                openGalleryForImageUploading();
                break;

            case R.id.btnUpload3:
                currentEdittext = etImage3;
                openGalleryForImageUploading();
                break;

            case R.id.btnUpload4:
                currentEdittext = etImage4;
                openGalleryForImageUploading();
                break;

            case R.id.btnUpload5:
                currentEdittext = etImage5;
                openGalleryForImageUploading();
                break;

            case R.id.post_data_view:
                boolean val = validateForm();
                if(val) {
                    sendFormDataToServer();
                } else {
                    Toast.makeText(context, "Invalid data", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("TestPostNewsFragment", "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("TestPostNewsFragment", "onResume");
    }

    private void openGalleryForImageUploading() {
        byteArrayOutputStream = new ByteArrayOutputStream();
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        progressDialog = ProgressDialog.show(context,"Image is Uploading","Please Wait...",false,false);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            imagePath = uri.getPath();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                getBase64Path();
            } catch (IOException e) {
                e.printStackTrace();
            }
            currentEdittext.setText(String.valueOf(convertImage));
//            progressDialog.dismiss();
        }
    }

    private void getBase64Path() {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byteArray = byteArrayOutputStream.toByteArray();
        convertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public boolean validateForm() {
        if(TextUtils.isEmpty(etName.getText().toString().trim())) {
            return false;
        }

        if(TextUtils.isEmpty(etContactNumber.getText().toString().trim())) {
            return false;
        }

        if(etContactNumber.getText().toString().trim().length() < 10) {
            return false;
        }

        if(TextUtils.isEmpty(etEmail.getText().toString().trim())) {
            return false;
        }

        if(TextUtils.isEmpty(etDesc.getText().toString().trim())) {
            return false;
        }

        if(TextUtils.isEmpty(etTitle.getText().toString().trim())) {
            return false;
        }

        return true;
    }

    public void sendFormDataToServer() {
        WebServiceHandler ws = new WebServiceHandler();
        ws.setMethodName(WS_POST_NEWS_TO_SERVER);
        ws.addAuth();
        ws.addParameter("Title", etTitle.getText().toString().trim(), String.class);
        ws.addParameter("NewsContent", etDesc.getText().toString().trim(), String.class);
        ws.addParameter("Img1Base64", etImage1.getText().toString().trim(), String.class);
        ws.addParameter("Img2Base64", etImage2.getText().toString().trim(), String.class);
        ws.addParameter("Img3Base64", etImage3.getText().toString().trim(), String.class);
        ws.addParameter("Img4Base64", etImage4.getText().toString().trim(), String.class);
        ws.addParameter("Img5Base64", etImage5.getText().toString().trim(), String.class);
        ws.addParameter("Name", etName.getText().toString().trim(), String.class);
        ws.addParameter("MobileNo", etContactNumber.getText().toString().trim(), String.class);
        ws.addParameter("EmailId", etEmail.getText().toString().trim(), String.class);
        ws.setOnExecuteComplete(new OnExecuteComplete() {
            @Override
            public void onComplete(Object obj) {
                List<WS_PostNewsResponse> responseList = Arrays.asList(WS_PostNewsResponse[].class.cast(obj));
                for(WS_PostNewsResponse ws_postNewsResponse : responseList)
                    PostNewsFragment.this.postNewsResponse.add(ws_postNewsResponse.getPostResult());

                if(postNewsResponse.get(0).getStatus().equalsIgnoreCase("Success")) {
                    fragmentManager = getFragmentManager();
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    gotoDashboard();

                } else {
                    Toast.makeText(context, "Failed to update server", Toast.LENGTH_LONG).show();
                }
            }
        });
        ws.execute(WS_PostNewsResponse[].class);
    }

    private void gotoDashboard() {
        fragmentManager = getFragmentManager();
        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, homeFragment);
        fragmentTransaction.addToBackStack(HomeFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }
}
