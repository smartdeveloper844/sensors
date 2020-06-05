package com.smart.admin.sensors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button logBtn;
    EditText loginId,pass;
    boolean match=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginId=findViewById(R.id.loginId);
        pass=findViewById(R.id.password);
        logBtn=findViewById(R.id.logButton);

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query reff= FirebaseDatabase.getInstance().getReference("Users").orderByChild("logId").
                        equalTo(loginId.getText().toString());

                reff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                if(snapshot.child("logPass").getValue().toString().equals(pass.getText().toString())){
                                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    match=true;
                                    startActivity(new Intent(MainActivity.this,Sensors.class));
                                    return;
                                }
                            }
                            if(!match){
                                Toast.makeText(MainActivity.this, "Password is wrong for this login ID", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "No ID exists for this login ID", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
