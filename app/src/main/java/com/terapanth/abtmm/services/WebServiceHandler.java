package com.terapanth.abtmm.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by Suraj Prajapati on 1/2/2018.
 */

public class WebServiceHandler {

    private final static String URL = "http://manage.abtmm.in/webservice/abtmm.asmx";
    private final static String AUTH_KEY_VALUE = "Adnkmdl230$fksJ#";
    private final static String AUTH_KEY = "AuthKey";

    public String address, methodName;
    public ArrayList<PropertyInfo> parameters;
    OnExecuteComplete onExecuteComplete;
    private Object param;
    private Class output;
    private boolean showProgress = false;
    private Context context;
    private String message = "Loading...";
    private ProgressDialog dialog;

    public WebServiceHandler(){
        this.address = URL;
        parameters = new ArrayList<>();
    }

    public void showDialog(Context context, String message) {
        this.context = context;
        this.message = message;
        showProgress= true;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
        if(parameters !=null) parameters.clear();
    }


    public void addParameter(String paramName, String paramValue, Class paramType) {
        PropertyInfo pi = new PropertyInfo();
        pi.setName(paramName);
        pi.setValue(paramValue);
        pi.setType(paramType);
        parameters.add(pi);
    }

    public void addAuth() {
        addParameter(AUTH_KEY, AUTH_KEY_VALUE, String.class);
    }

    public void addParameter(PropertyInfo parameter) {
        parameters.add(parameter);
    }

    public  void setOnExecuteComplete(OnExecuteComplete onExecuteComplete) {
        this.onExecuteComplete = onExecuteComplete;
    }

    public void execute(Class outputClass) {
        if (showProgress) {
            dialog = new ProgressDialog(context);
            dialog.setMessage(message);
            dialog.show();
            dialog.setCancelable(false);
        }
        new WebServiceHandler.ServiceAsync().execute();
        this.output = outputClass;
    }

    public class ServiceAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String s;
            try {

                //http://www.c-sharpcorner.com/UploadFile/88b6e5/how-to-call-web-service-in-android-using-soap/

                String OPERATION_NAME = methodName;
                String SOAP_ACTION = "http://tempuri.org/" + OPERATION_NAME;
                String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
                String SOAP_ADDRESS = address;

                SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);
                //request.addProperty(AuthKey, AuthKeyValue);

                for(PropertyInfo pi : parameters)
                    request.addProperty(pi);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
                SoapPrimitive response;

                httpTransport.call(SOAP_ACTION, envelope);
                response = (SoapPrimitive) envelope.getResponse();
                s = response.toString();
            } catch (Exception e) {
                s = e.getMessage();
            }
            Log.i("WService", "doInBackground + " + s);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Object a = null;
            if (dialog != null &&dialog.isShowing())
                dialog.dismiss();
            try {
                a = new Gson().fromJson(s, output);
            } catch (Exception e) {
                a = e.getMessage();
            }
            onExecuteComplete.onComplete(a);
        }
    }
}
