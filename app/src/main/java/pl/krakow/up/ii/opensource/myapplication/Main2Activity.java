package pl.krakow.up.ii.opensource.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main2Activity extends AppCompatActivity {

    final String page_url = "https://wu.up.krakow.pl/WU/";
    String[] items = new String[];

    class GetWyniki2Task extends AsyncTask<Void, Integer, String> {

        @Override
        protected String doInBackground(Void... voids) {
            Request request = new Request.Builder()
                    .url(page_url + "Wynik2.aspx")
                    .get()
                    .build();

            try (Response response = AppConfiguration.okClient.newCall(request).execute()) {
                Log.e("tag", response.message().toString());
                try {
                    return response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String html) {
            Document doc = Jsoup.parse(html);
            ListView v = findViewById(R.id.listView1);
            v.setAdapter();
            Element e = doc.getElementById("ctl00_ctl00_ContentPlaceHolder_RightContentPlaceHolder_tab");
            if (e != null) {
                for (Element td : e.getElementsByTag("td")) {
                    Log.e("--td -- ", td.text());
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        GetWyniki2Task task = new GetWyniki2Task();

        task.execute();

        Toast.makeText(Main2Activity.this, "asdf", Toast.LENGTH_LONG)
                .show();

    }
}
