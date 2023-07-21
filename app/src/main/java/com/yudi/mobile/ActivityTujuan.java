package com.yudi.mobile;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yudi.mobile.R;

import java.io.InputStream;
import java.net.URL;

public class ActivityTujuan extends AppCompatActivity {

    private TextView textViewGreeting;
    private TextView textViewName;
    private EditText textEditNim;
    private SharedPreferences sharedPreferences;
    private Button saveButton;
    private static final String SHARED_PREF_NAME = "my_shared_pref";
    private static final String KEY_NAME = "name";
    private static final String KEY_NIM = "nim";

    private ImageView imageView;
    private Button downloadButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tujuan);

        textViewGreeting = findViewById(R.id.textViewGreeting);
        textViewName = findViewById(R.id.textViewName);
        textEditNim = findViewById(R.id.textEditNim);
        saveButton = findViewById(R.id.saveButton);
        imageView = findViewById(R.id.imageView);
        downloadButton = findViewById(R.id.downloadButton);
        progressBar = findViewById(R.id.progressBar);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String name = sharedPreferences.getString(KEY_NAME, "");

        String greeting = "Selamat datang, " + name + "!";
        textViewGreeting.setText(greeting);
        textViewName.setText("Nama: " + name);
//        String savedNim = sharedPreferences.getString(KEY_NIM, "");
//        textEditNim.setText(savedNim);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedNim();
            }
        });


        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imageUrl = "https://gallery.poskota.co.id/storage/Foto/santai-2.jpg"; // Ganti dengan URL gambar yang ingin diunduh
                DownloadImageTask task = new DownloadImageTask();
                task.execute(imageUrl);
            }
        });
    }
    private void savedNim() {
        String nim = textEditNim.getText().toString().trim();

        if (nim.isEmpty()) {
            Toast.makeText(this, "Silakan masukkan nim", Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_NAME, nim);
            editor.apply();
            Toast.makeText(this, "Nim berhasil disimpan", Toast.LENGTH_SHORT).show();
        }
    }
    private class DownloadImageTask extends AsyncTask<String, Integer, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
            downloadButton.setEnabled(false);
            Toast.makeText(ActivityTujuan.this, "Memulai mengunduh gambar...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String imageUrl = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(imageUrl).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int progress = values[0];
            progressBar.setProgress(progress);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            progressBar.setVisibility(View.GONE);
            downloadButton.setEnabled(true);
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
                Toast.makeText(ActivityTujuan.this, "Gambar berhasil diunduh", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ActivityTujuan.this, "Gagal mengunduh gambar", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
