package com.chris.shelltest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.R.attr.process;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button mButtonExecute;
    TextView mTextViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonExecute = (Button) findViewById(R.id.btn_execute);
        mTextViewResult = (TextView) findViewById(R.id.tv_result);

        mButtonExecute.setOnClickListener(this);
    }

    private String exec(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            int read;
            char[] buffer = new char[4096];
            StringBuffer output = new StringBuffer();
            while ((read = reader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            reader.close();
            process.waitFor();
            return output.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "error!";
    }

    public void sendTest(String command) {
        if (command == null) {
            return;
        }
        try {
            Process process = Runtime.getRuntime().exec(command);
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.write(command.getBytes());
            os.writeBytes("\n");
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        System.out.println("Hello, I am started by app_process!");
        String cmd1 = "settings put secure enabled_accessibility_services com.zyy.rob.robredpackage/com.zyy.rob.robredpackage.RobService";
        String cmd2 = "settings put secure accessibility_enabled 1";
        sendTest("dumpsys activity");
        sendTest("su");
//
//        String result = exec("dumpsys activity");
//        System.out.println("dumpsys activity result = " + result);
        sendTest("dumpsys activity");

        sendTest(cmd1);

        sendTest(cmd2);
    }
}
