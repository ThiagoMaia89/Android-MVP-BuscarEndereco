package com.simplesoftware.buscarcep.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.simplesoftware.buscarcep.model.Endereco;
import com.simplesoftware.buscarcep.R;
import com.simplesoftware.buscarcep.model.RetrofitConfig;
import com.simplesoftware.buscarcep.presenter.MainContract;
import com.simplesoftware.buscarcep.presenter.MainPresenter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MainContract.MvpView {

    MainPresenter mPresenter;

    private EditText et_cep;
    private Button bt_buscar;
    private TextView tv_copiarEndereco;
    private RecyclerView rv_lista_endereco;
    private RecyclerAdapter recyclerAdapter;
    private GridLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instanciarComponentes();

        mPresenter = new MainPresenter(this);
        layoutManager = new GridLayoutManager(this, 1);

        rv_lista_endereco.setLayoutManager(layoutManager);

        bt_buscar.setOnClickListener(v -> {
            mPresenter.handleBtnBuscarOnClick(et_cep, rv_lista_endereco, layoutManager, getApplicationContext());
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        });

        tv_copiarEndereco.setOnClickListener(v -> mPresenter.handleBtnCopiarEnderecoOnClick(et_cep, getApplicationContext()));

    }

    public void instanciarComponentes() {
        et_cep = findViewById(R.id.et_cep);
        bt_buscar = findViewById(R.id.bt_buscar);
        rv_lista_endereco = findViewById(R.id.rv_lista_endereco);
        tv_copiarEndereco = findViewById(R.id.tv_copiarEndereco);

    }

}