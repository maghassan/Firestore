package com.maghassan.firestore;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore   firestore;
    EditText    name,   skills;
    Button  submit;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(context);
        setContentView(R.layout.activity_main);

        firestore   =   FirebaseFirestore.getInstance();

        name    =   findViewById(R.id.name);
        skills  =   findViewById(R.id.skill);

        submit  =   findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDataToDB();
            }
        });
    }

    private void addDataToDB() {
        String  nameText    =   name.getText().toString();
        String  skillText   =   skills.getText().toString();

        if (TextUtils.isEmpty(nameText)    &&  TextUtils.isEmpty(skillText))  {
            Toast.makeText(MainActivity.this,   "Check all the field please!",  Toast.LENGTH_LONG).show();
        }   else {
            Map<String, Object> addMap  =   new HashMap<>();

            addMap.put("name",  nameText);
            addMap.put("skill", skillText);

            firestore.collection("OurData")
                    .add(addMap)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful())    {
                                Toast.makeText(MainActivity.this,   "Data Added Successfully",  Toast.LENGTH_LONG).show();
                            }   else {
                                Toast.makeText(MainActivity.this,   "Error: "   +   task.getException().getMessage(),  Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}
