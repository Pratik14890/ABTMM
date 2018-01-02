package com.terapanth.abtmm.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.terapanth.abtmm.commons.Constants;
import com.terapanth.abtmm.pojo.WebServiceParams;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suraj Prajapati on 1/2/2018.
 */

public class WebServiceHandler {

    public String methodName;
    private List<WebServiceParams> webServiceParamsList;
    private ExecutionCompleteListener executionCompleteListener;
    private Context context;
    private SoapPrimitive resultString;
    private ProgressDialog progressDialog;

    public WebServiceHandler(Context context){
        webServiceParamsList = new ArrayList<>();
        this.context = context;
        progressDialog = new ProgressDialog(context);
    }

    public void showProgressDialog(boolean toShow, String message) {
        if (toShow) {
            progressDialog.setMessage(message);
            progressDialog.setCancelable(false);
            progressDialog.show();
            return;
        }
        progressDialog.dismiss();
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void addParameter(String key, String parameter) {
        WebServiceParams webServiceParams = new WebServiceParams();
        webServiceParams.setKeyname(key);
        webServiceParams.setValue(parameter);
        webServiceParamsList.add(webServiceParams);
    }

    public  void setOnExecutionComplete(ExecutionCompleteListener executionCompleteListener) {
        this.executionCompleteListener = executionCompleteListener;
    }

    public void execute() {
        new AsyncCallWS().execute();
    }

    public class AsyncCallWS extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            showProgressDialog(true, Constants.LOADER_MESSAGE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            getDataFromWebServices();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            executionCompleteListener.onComplete(resultString.toString());
            showProgressDialog(false, "");
        }

    }

    public void getDataFromWebServices() {
        String SOAP_ACTION = Constants.NAMESPACE + methodName;
        try {
            SoapObject Request = new SoapObject(Constants.NAMESPACE, methodName);
            if(webServiceParamsList != null && webServiceParamsList.size() > 0) for (WebServiceParams webServiceParam : webServiceParamsList)
                Request.addProperty(webServiceParam.getKeyname(), webServiceParam.getValue());
            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(Constants.BASE_URL);
            transport.call(SOAP_ACTION, soapEnvelope);
            resultString = (SoapPrimitive) soapEnvelope.getResponse();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
