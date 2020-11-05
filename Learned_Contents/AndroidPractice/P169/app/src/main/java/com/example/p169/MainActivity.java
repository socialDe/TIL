package com.example.p169;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button sendButton;
    EditText smsInput;
    TextView byteView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendButton = findViewById(R.id.button);
        smsInput = findViewById(R.id.editTextTextPersonName);
        byteView = findViewById(R.id.textView);

        smsInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bytesCounter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public void bytesCounter(CharSequence s){
        byteView.setText(""+s.toString().getBytes().length + "/ 80 바이트");
    }

    public void showToast(String str){
        Toast.makeText(getApplicationContext(),str, Toast.LENGTH_LONG).show();
    }

    public void onSendButtonClicked(View v){
        String message = smsInput.getText().toString();
        showToast(message);
    }
}