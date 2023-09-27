package com.jnu.student;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textView1, textView2;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = textView1.getText().toString();
                textView1.setText(textView2.getText().toString());
                textView2.setText(temp);
                showToast("交换成功");
                showAlertDialog("交换成功");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showAlertDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage(message)
                .setPositiveButton("确定", null)
                .show();
    }
}
