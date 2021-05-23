package com.simplesoftware.buscarcep.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.simplesoftware.buscarcep.model.Endereco;
import com.simplesoftware.buscarcep.R;
import com.simplesoftware.buscarcep.util.RecyclerAdapter;
import com.simplesoftware.buscarcep.util.RetrofitConfig;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText et_cep;
    private Button bt_buscar;
    private TextView tv_copiarEndereco;
    private RecyclerView rv_lista_endereco;
    private RecyclerAdapter recyclerAdapter;
    private GridLayoutManager layoutManager;
    private String copiarEndereco;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instanciarComponentes();

        layoutManager = new GridLayoutManager(this, 1);

    }

    public void instanciarComponentes(){
        et_cep = findViewById(R.id.et_cep);
        bt_buscar = findViewById(R.id.bt_buscar);
        rv_lista_endereco = findViewById(R.id.rv_lista_endereco);
        tv_copiarEndereco = findViewById(R.id.tv_copiarEndereco);

    }

    public void buscarCEP(View v){
        Call<Endereco> callEndereco = new RetrofitConfig().getServiceConfig().buscarEndereco(et_cep.getText().toString());
        callEndereco.enqueue(new Callback<Endereco>() {
            @Override
            public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, response.code() + ": " + response.message(), Toast.LENGTH_SHORT).show();
                } else {
                    Endereco endereco = response.body();
                    copiarEndereco = endereco.toString();
                    ArrayList<String> listaEndereco = new ArrayList<>();
                    listaEndereco.add(endereco.getCep());
                    listaEndereco.add(endereco.getLogradouro());
                    listaEndereco.add(endereco.getBairro());
                    listaEndereco.add(endereco.getLocalidade());
                    listaEndereco.add(endereco.getUf());
                    listaEndereco.add(endereco.getIbge());
                    listaEndereco.add(endereco.getDdd());

                    recyclerAdapter = new RecyclerAdapter(listaEndereco);
                    rv_lista_endereco.setAdapter(recyclerAdapter);
                    rv_lista_endereco.setLayoutManager(layoutManager);

                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void copiarEndereco(View v){

        try {
            Toast.makeText(MainActivity.this, "Endereço copiado para a Área de Transferência", Toast.LENGTH_SHORT).show();

            ClipboardManager clipboard = (ClipboardManager)
                    getSystemService(Context.CLIPBOARD_SERVICE);

            ClipData cpy_all = ClipData.newPlainText("text", copiarEndereco);
            clipboard.setPrimaryClip(cpy_all);
        } catch (Exception e) {
            Toast.makeText(this, "Tente novamente" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}