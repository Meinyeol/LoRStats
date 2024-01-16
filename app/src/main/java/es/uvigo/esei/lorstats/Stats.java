package es.uvigo.esei.lorstats;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

public class Stats extends AsyncTask<URL, Void, Boolean> {
    public Activity a;
    public Context mContext;
    public Boolean found = false;
    public String name;
    public String name2;
    public String rank;
    public int lp;
    public Stats(Context mContext, Activity activity,String name){
        this.mContext = mContext;
        this.a=activity;
        this.name2 = name;
    }
    @Override
    protected void onPreExecute() {
    }
    @Override
    protected Boolean doInBackground(URL... urls) {
        boolean toret = false;

            ConnectivityManager connMgr = (ConnectivityManager) this.mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if ((networkInfo != null && networkInfo.isConnected())) {
                try {
                    HttpURLConnection conn = (HttpURLConnection) urls[0].openConnection();
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();
                    int codigoRespuesta = conn.getResponseCode();
                    InputStream is = conn.getInputStream();
                    Log.i("codigo",String.valueOf(codigoRespuesta).toString());
                    StringBuilder s = new StringBuilder();
                    int c;
                    BufferedReader in = new BufferedReader(new InputStreamReader(is));
                    String line;
                    while ((line=in.readLine())!=null) {
                        s.append(line);
                    }


                    JSONObject j = new JSONObject(s.toString());
                    JSONArray  json = j.getJSONArray("entries");

                    Log.i("asd",String.valueOf(json.length()));
                    for (int i=0; i<json.length();i++){
                        if(json.getJSONObject(i).getString("summonerName").equals(this.name2)) {
                            this.name = json.getJSONObject(i).getString("summonerName");
                            this.rank = json.getJSONObject(i).getString("rank");
                            this.lp = json.getJSONObject(i).getInt("leaguePoints");
                            this.found=true;
                        }
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                toret = true;
            } else {

                toret=false;

            }


        return toret;
}
    @Override
    public void onPostExecute(Boolean result)
    {
        if(found){
            TextView nameView = (TextView) this.a.findViewById(R.id.nameView);
            TextView rankView = (TextView) this.a.findViewById(R.id.rankView);
            TextView lpView = (TextView) this.a.findViewById(R.id.lpView);

            nameView.setText("Summoner Name: " + this.name);
            rankView.setGravity(Gravity.LEFT);
            rankView.setText("Rank: " + this.rank);
            lpView.setText("LP: " + String.valueOf(this.lp));
        }
        else{
            TextView nameView = (TextView) this.a.findViewById(R.id.nameView);
            TextView rankView = (TextView) this.a.findViewById(R.id.rankView);
            TextView lpView = (TextView) this.a.findViewById(R.id.lpView);
            rankView.setGravity(Gravity.CENTER);
            nameView.setText("");
            rankView.setText("Player Not Found");
            lpView.setText("");
        }

    }
}


