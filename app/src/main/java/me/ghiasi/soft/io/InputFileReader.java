package me.ghiasi.soft.io;

import android.graphics.drawable.Drawable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class InputFileReader {

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
    public String readURL(String url)
    {
        String result = "";
        try {
            URL urlWeather = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlWeather.openConnection();

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader, 8192);
                String line ;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
            } else {
                System.out.println("Error in httpURLConnection.getResponseCode()!!!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}