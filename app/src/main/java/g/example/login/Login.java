package g.example.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Login extends AppCompatActivity implements View.OnClickListener {

    //constants
    private static final String TAG = Login.class.getSimpleName();
    private static final String DATA = "data";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String NULL = "null";

    //widgets
    private TextInputLayout lEmail, lPassword;
    private TextInputEditText eEmail, ePassword;
    private Button btnLogin;
    private ProgressBar pbLogin;

    //vars
    SharedPreferences data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = getSharedPreferences(DATA, Context.MODE_PRIVATE);
        if (!data.getString(EMAIL, NULL).equals(NULL)
                && !data.getString(PASSWORD, NULL).equals(NULL)) {
            startActivity(new Intent(this, Dashboard.class));
        }else  {
            setContentView(R.layout.login);
            init();
        }
    }

    private void init() {
        lEmail = findViewById(R.id.login_layout_edit_email);
        lPassword = findViewById(R.id.login_layout_edit_password);
        eEmail = findViewById(R.id.login_edit_email);
        ePassword = findViewById(R.id.login_edit_password);
        btnLogin = findViewById(R.id.login_btn_sigin);
        btnLogin.setOnClickListener(this);
        pbLogin = findViewById(R.id.progress_circular);
    }

    private void validateFields() {
        if (!eEmail.getText().toString().trim().equals("")
                && !eEmail.getText().toString().equals("")) {
            if (!ePassword.getText().toString().trim().equals("")
                    && !ePassword.getText().toString().equals("")) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideView(1);
                        Toast.makeText(Login.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                        saveUser();
                        startActivity(new Intent(Login.this, Dashboard.class));
                    }
                }, 1500);
            } else {
                Log.d(TAG, "validateFields: Error password is null");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideView(1);
                    }
                }, 1500);
                lPassword.setError("Error, campo contraseña vacío");
            }
        } else {
            Log.d(TAG, "validateFields: Error email is null");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    hideView(1);
                }
            }, 1500);
            lPassword.setError("Error, campo Correo Electrónico vacío");
        }
    }

    private void saveUser() {
        SharedPreferences.Editor saveData = data.edit();
        saveData.putString(EMAIL, eEmail.getText().toString());
        saveData.putString(PASSWORD, ePassword.getText().toString());
        saveData.apply();
    }

    private void hideView(int view) {
        if (view == 0) {
            btnLogin.setVisibility(View.GONE);
            pbLogin.setVisibility(View.VISIBLE);
        }else {
            pbLogin.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        lEmail.setError(null);
        lPassword.setError(null);
        hideView(0);
        validateFields();
    }
}
