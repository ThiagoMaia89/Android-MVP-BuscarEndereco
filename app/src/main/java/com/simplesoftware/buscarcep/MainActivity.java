package com.simplesoftware.buscarcep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.simplesoftware.buscarcep.util.RetrofitConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText et_cep;
    private Button bt_buscar;
    private TextView tv_endereco, tv_copiarEndereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instanciarComponentes();

    }

    public void instanciarComponentes(){
        et_cep = findViewById(R.id.et_cep);
        bt_buscar = findViewById(R.id.bt_buscar);
        tv_endereco = findViewById(R.id.tv_endereco);
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
                    tv_endereco.setText(endereco.toString());
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

            ClipData cpy_all = ClipData.newPlainText("text", tv_endereco.getText());
            clipboard.setPrimaryClip(cpy_all);
        } catch (Exception e) {
            Toast.makeText(this, "Tente novamente" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}