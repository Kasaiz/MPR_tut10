package tut10.myteam;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ProfileFragment extends Fragment {

    TextView tvName ;
    TextView tvEmail ;
    ImageView ivAvatar ;
    private int id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        tvName = v.findViewById(R.id.tvName);
        tvEmail = v.findViewById(R.id.tvEmail);
        ivAvatar = v.findViewById(R.id.ivAvatar);
        id = getArguments().getInt("ID");

        int a = id+1;
        String url = "https://jsonplaceholder.typicode.com/users/"+a;
        RestLoader loader = new RestLoader();
        loader.execute(url);

        String url2 = "https://robohash.org/"+id+"?set=set2";
        ImageLoader imageLoader = new ImageLoader();
        imageLoader.execute(url2);
        return v;
    }

    class RestLoader extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection urlConnection;
            try{
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream is = urlConnection.getInputStream();
                Scanner sc = new Scanner(is);
                StringBuilder result = new StringBuilder();
                String line;
                while (sc.hasNextLine()){
                    line = sc.nextLine();
                    result.append(line);
                }
                return result.toString();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s == null){
                return;
            }

            try{
                JSONObject root = new JSONObject(s);

//                JSONArray users = root.getJSONArray("users");
//                JSONObject user0 = users.getJSONObject(0);
                String name = root.getString("name");
                tvName.setText(name);
                String email = root.getString("email");
                tvEmail.setText(email);

            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    class ImageLoader extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) {
            URL url;
            HttpURLConnection urlConnection;
            try{
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream is = urlConnection.getInputStream();
                Bitmap image = BitmapFactory.decodeStream(is);
                return image;
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap b) {
            super.onPostExecute(b);
            ivAvatar.setImageBitmap(b);
        }
    }
}