package com.usepropeller.webactivity.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.usepropeller.webactivity.WebActivity;

/**
 * Created by clayallsopp on 8/4/13.
 */
public class MainActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final EditText urlEditText = (EditText) findViewById(android.R.id.text1);

        Button openNative = (Button) findViewById(R.id.open_native);
        openNative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = WebActivity.forUrl(urlEditText.getText().toString(), MainActivity.this);
                startActivity(intent);
            }
        });

        Button openSupport = (Button) findViewById(R.id.open_support);
        openSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = com.usepropeller.webactivity.support.WebActivity.forUrl(urlEditText.getText().toString(), MainActivity.this);
                startActivity(intent);
            }
        });
    }
}