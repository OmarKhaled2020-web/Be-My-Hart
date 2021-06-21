package com.omar.bemyhart;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FireBaseMethod {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private boolean state_registration;

    public boolean RegisterMethod(String name, String email, String pass){

        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    state_registration = true;
                }
                else {
                    state_registration = false;
                }
            }
        });
        return state_registration;
    }


}
