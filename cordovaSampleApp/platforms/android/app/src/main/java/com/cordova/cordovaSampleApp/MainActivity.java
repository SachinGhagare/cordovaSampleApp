/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.cordova.cordovaSampleApp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentCallbacks;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.apache.cordova.*;

public class MainActivity extends CordovaActivity
{
    private static final String TAG = "com.cordova,cordovaSampleApp";
    private Button storage, camera;

    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LOG.d(TAG, "onCreate the activity.");

        // enable Cordova apps to be started in the background
        /* Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getBoolean("cdvStartInBackground", false)) {
            moveTaskToBack(true);
        }

        // Set by <content src="index.html" /> in config.xml
        loadUrl(launchUrl); */

        setContentView(R.layout.layout);
        storage = findViewById(R.id.storage);
        camera = findViewById(R.id.camera);


        // Set Buttons on Click Listeners
        storage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                checkStoragePermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                checkCameraPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
            }
        });
        AllPermissionGranted();
    }

    // Function to check and request permission
    public void checkStoragePermission(String permission, int requestCode)
    {
        // Checking if permission is not granted

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                MainActivity.this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
            }
            else {
                Toast.makeText(MainActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void checkCameraPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                MainActivity.this.requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
            }
            else {
                // loadUrl(launchUrl);
                AllPermissionGranted();
                Toast.makeText(MainActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void AllPermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                loadUrl(launchUrl);
            } else {
                Toast.makeText(MainActivity.this, "Permission not granted, please grant it", Toast.LENGTH_SHORT).show();
            }
        }
    }
    protected void onStart(){
        super.onStart();
        LOG.d(TAG, "onStart the activity.");
    }

    protected void onRestart(){
        super.onRestart();
        LOG.d(TAG, "onRestart the activity.");
    }

    protected void onResume() {
        super.onResume();
        LOG.d(TAG, "onResume the activity.");
    }

    protected void onPause(){
        super.onPause();
        LOG.d(TAG, "onPause the activity.");
    }

    protected void onStop(){
        super.onStop();
        LOG.d(TAG, "onStop the activity.");
    }

    public void onDestroy(){
        super.onDestroy();
        LOG.d(TAG, "onDestroy the activity.");
    }

    public void customDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.myDialog);
        builder.setMessage("Custom Dial Info");
        builder.setTitle("Title Dialog");
        builder.setCancelable(false);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                loadUrl(launchUrl);
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {

            // Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Showing the toast message
                // customDialog();

                Toast.makeText(MainActivity.this, "Camera Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
