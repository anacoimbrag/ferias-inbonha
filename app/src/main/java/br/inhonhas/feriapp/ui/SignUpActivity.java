package br.inhonhas.feriapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.inhonhas.feriapp.R;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Fazer Cadastro");
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
