package com.example.dell.task4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    EditText etUsername,etEmail;
    Button btnSave,btnRetrieve,btnDelete;
    TextView txtShowResult;
    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername=findViewById(R.id.etUsername);
        etEmail=findViewById(R.id.etEmail);
        btnSave=findViewById(R.id.btnSave);
        btnRetrieve=findViewById(R.id.btnRetrieve);
        btnDelete=findViewById(R.id.btnDelete);
        txtShowResult=findViewById(R.id.txtShowResult);
        realm=Realm.getDefaultInstance();
         btnSave.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 realm.executeTransactionAsync(new Realm.Transaction() {
                     @Override
                     public void execute(Realm realm) {
                        UserDB user=realm.createObject(UserDB.class);
                        user.setUsername(etUsername.getText().toString().trim());
                        user.setEmail(etEmail.getText().toString().trim());
                     }
                 }, new Realm.Transaction.OnSuccess() {
                     @Override
                     public void onSuccess() {
                         etUsername.setText("");
                         etEmail.setText("");
                         etUsername.requestFocus();
                        Toast.makeText(getApplicationContext(),"Success...",Toast.LENGTH_SHORT).show();
                     }
                 }, new Realm.Transaction.OnError() {
                     @Override
                     public void onError(Throwable error) {
                         Toast.makeText(getApplicationContext(),"Failed...",Toast.LENGTH_SHORT).show();
                     }
                 });
             }
         });
         btnRetrieve.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 RealmResults<UserDB>realmResults=realm.where(UserDB.class).findAll();
                 String output="";
                 if(!realmResults.isEmpty()) {
                     /*for (UserDB userDB : realmResults) {
                         output += realmResults;
                     }*/
                     for(int i=0;i<realmResults.size();i++){
                         output+=realmResults.get(i);
                     }
                     txtShowResult.setText(output);
                 }
                 else{
                     Toast.makeText(getApplicationContext(),"File Not Found...",Toast.LENGTH_SHORT).show();
                 }
             }
         });
         btnDelete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 RealmResults<UserDB>realmResults=realm.where(UserDB.class).findAll();
                 UserDB userDB=realmResults.where().equalTo("username",etUsername.getText().toString()).findFirst();
                 if (userDB!=null){
                     if (!realm.isInTransaction()){
                        realm.beginTransaction();
                     }
                     userDB.deleteFromRealm();
                     realm.commitTransaction();
                     txtShowResult.setText("User: "+etUsername.getText().toString()+" was successfully deleted from database...");
                 }
                 else
                     Toast.makeText(getApplicationContext(),"File not found...",Toast.LENGTH_SHORT).show();

             }
         });
    }
}
