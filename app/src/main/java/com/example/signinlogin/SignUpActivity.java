package com.example.signinlogin;

import static com.android.volley.Request.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    EditText firstName , lastName , password , email ;
    Button signupButton ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signupButton = findViewById(R.id.signup) ;

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
                System.out.println("hello ");
            }
        });
    }
    public void goToHome(View view ){
        Intent intent = new Intent(SignUpActivity.this , MainActivity.class ) ;
        startActivity(intent);
        finish();
    }

    public void goToSignIn(View view) {
        Intent intent = new Intent(SignUpActivity.this , SignInActivity.class) ;
        startActivity(intent);
        finish();
    }
    public boolean validateFields(){
        if( firstName.getText().toString().isEmpty()  || lastName.getText().toString().isEmpty() || password.getText().toString().isEmpty() || email.getText().toString().isEmpty() ){
            Toast.makeText(SignUpActivity.this , "Please Validate Fields" , Toast.LENGTH_SHORT ).show();
            return false;
        }
        RequestQueue requestQueue = Volley.newRequestQueue(SignUpActivity.this) ;
        String url = "http://192.168.43.220:9080/api/user/register" ;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("success")){
                    firstName.setText(null);
                    lastName.setText(null);
                    email.setText(null);
                    password.setText(null);
                    Toast.makeText(SignUpActivity.this , "Register Successfull" , Toast.LENGTH_SHORT ).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.println(error.getMessage());
                Toast.makeText(SignUpActivity.this , "Register UnSuccessfull. Try Again!!" , Toast.LENGTH_SHORT ).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String > params = new HashMap<>();
                params.put("first_name" , firstName.getText().toString());
                params.put("last_name" , lastName.getText().toString());
                params.put("email" , email.getText().toString());
                params.put("password" , password.getText().toString());
                
                return params ;
            }
        } ;

        return true;
    }
}