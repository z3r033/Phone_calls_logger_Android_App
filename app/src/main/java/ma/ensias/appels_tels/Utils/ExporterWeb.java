package ma.ensias.appels_tels.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ma.ensias.appels_tels.AppelActivity;
import ma.ensias.appels_tels.models.AppelModel;

public class ExporterWeb extends AsyncTask<Void, Void , String> {

    Context c;
    String url;
    AppelModel appelmodel;
    ProgressDialog pd;

    public ExporterWeb(Context c,AppelModel model) {

        this.c = c;
        this.url = Urls.urlwebservice;

        appelmodel = new AppelModel();
        appelmodel.setNom(model.getNom());
        appelmodel.setNumero(model.getNumero());
        appelmodel.setDate(model.getDate());
        appelmodel.setTime(model.getTime());
        appelmodel.setType(model.getType());
        appelmodel.setLocation(model.getLocation());
        appelmodel.setDuration(model.getDuration());
        appelmodel.setTemps_appelle(model.getTemps_appelle());



    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(c);
        pd.setMessage("loading");
        pd.show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.envoyer();
    }

    private String envoyer() {
        Object mconnect = connection_backendjson.connect(url);
        if (mconnect.toString().startsWith("Error")) {
            return mconnect.toString();
        }
        try {
            HttpURLConnection connection = (HttpURLConnection) mconnect;
            //   OutputStream os = new BufferedOutputStream(connection.getOutputStream());
            // BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));


            String jsonInputString = "{\"nom\":\""+appelmodel.getNom()+"\",\"numero\":\""+appelmodel.getNumero()+"\",\"date\":\""+appelmodel.getDate()+"\",\"time\":\""+appelmodel.getTime()+"\",\"type\":\""+appelmodel.getType()+"\",\"duration\":"+appelmodel.getDuration()+",\"temps_appelle\":\""+appelmodel.getTemps_appelle()+"\",\"location\":\""+appelmodel.getLocation()+"\"}";
            try(OutputStream os2 = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os2.write(input, 0, input.length);
            }

            //ici get la reponse sans decoder
            int responsecode = connection.getResponseCode();
            if (responsecode == connection.HTTP_OK) {



             //   String response = headerToken + " userid " + headerUser;
                //      SharedPref.saveSharedSetting(c, "userconnect", "True");


              //  return response;

               InputStream is = new BufferedInputStream(connection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = br.readLine()) != null) {
                    response.append(line + "\n");
                }
                br.close();
                is.close();
                return response.toString();




            } else {
                return "Erreur" + String.valueOf(responsecode);
            }


        } catch (IOException e) {
            e.printStackTrace();
            return "dd";
        }

        //     return "dd";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (pd != null)
        {
            pd.dismiss();
            Toast.makeText(c,"bien sync :)",Toast.LENGTH_LONG).show();
        }
        //  display(s);



    }




}
