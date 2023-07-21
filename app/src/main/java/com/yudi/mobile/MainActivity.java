package com.yudi.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Handler;


public class MainActivity extends AppCompatActivity {
    private Button buttonSave;
    private EditText editTextName;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "my_shared_pref";
    private static final String KEY_NAME = "name";
    private static final int DELAY_DURATION = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
//        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        buttonSave = findViewById(R.id.buttonSave);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String savedName = sharedPreferences.getString(KEY_NAME, "");
        editTextName.setText(savedName);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveName();
            }
        });
    }

    private void saveName() {`
        String name = editTextName.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Silakan masukkan nama", Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_NAME, name);
            editor.apply();
            Toast.makeText(this, "Nama berhasil disimpan", Toast.LENGTH_SHORT).show();

            // Menunda pindah ke ActivityTujuan setelah waktu tunggu
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    navigateToActivityTujuan();
                }
            }, DELAY_DURATION);
        }
    }
    private void navigateToActivityTujuan() {
        startActivity(new Intent(MainActivity.this, ActivityTujuan.class));
    }
}