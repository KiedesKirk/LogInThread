package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    Button register;
    EditText fname, lName, username, password;
    TextView warning;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        register =findViewById(R.id.register);
        fname =findViewById(R.id.fName);
        lName =findViewById(R.id.lName);
        username =findViewById(R.id.uName);
        password =findViewById(R.id.pwrd);
        warning =findViewById(R.id.invalid);
        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    usernameChecker(username.getText().toString());
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final String fnameTxt =fname.getText().toString();
                final String lNameTxt =lName.getText().toString();
                final String userNameTxt =username.getText().toString();
                final String passwordTxt =password.getText().toString();

                if(fnameTxt.isEmpty() || lNameTxt.isEmpty() || userNameTxt.isEmpty() || passwordTxt.isEmpty()){
                    Toast.makeText(MainActivity2.this, "Fill the fields", Toast.LENGTH_SHORT).show();
                }else{
                    Map<String, Object> user = new HashMap<>();
                    user.put("First name", fnameTxt);
                    user.put("Last Name", lNameTxt);
                    user.put("Username",userNameTxt);
                    user.put("Password",passwordTxt);
                    db.collection("Accounts").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(MainActivity2.this, "Registered", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }public void usernameChecker(String uname){
        userName un = new userName(uname);
        un.start();
    }
    class userName extends Thread{
        String uname;
        userName(String uname){
            this.uname = uname;
        }

        @Override
        public void run() {
            String regex = "^[a-zA-Z0-9]*$"; // regular expression to allow only letters and numbers
            if (!uname.matches(regex)) {
                // username contains special characters, show an error message
                warning.setText("Invalid input");
            }
        }
    }
}
