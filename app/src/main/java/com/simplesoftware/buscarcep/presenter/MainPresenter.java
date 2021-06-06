package com.simplesoftware.buscarcep.presenter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.simplesoftware.buscarcep.model.Endereco;
import com.simplesoftware.buscarcep.model.RetrofitConfig;
import com.simplesoftware.buscarcep.view.MainActivity;
import com.simplesoftware.buscarcep.view.RecyclerAdapter;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.MvpView mView;


    public MainPresenter(MainContract.MvpView mView) {
        this.mView = mView;
    }


    @Override
    public void handleBtnBuscarOnClick(EditText et_cep, RecyclerView rv_lista_endereco, GridLayoutManager layoutManager, Context context) {
        if (et_cep.getText().toString().equals("")) {
            Toasty.error(context, "Cep não fornecido", Toasty.LENGTH_SHORT).show();
        } else {
            Call<Endereco> callEndereco = new RetrofitConfig().getServiceConfig().buscarEndereco(et_cep.getText().toString());
            callEndereco.enqueue(new Callback<Endereco>() {
                @Override
                public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                    Endereco endereco = response.body();
                    ArrayList<String> listaEndereco = new ArrayList<>();
                    listaEndereco.add(endereco.getCep());
                    listaEndereco.add(endereco.getLogradouro());
                    listaEndereco.add(endereco.getBairro());
                    listaEndereco.add(endereco.getLocalidade());
                    listaEndereco.add(endereco.getUf());
                    listaEndereco.add(endereco.getIbge());
                    listaEndereco.add(endereco.getDdd());
                    RecyclerAdapter recyclerAdapter = new RecyclerAdapter(listaEndereco);
                    rv_lista_endereco.setAdapter(recyclerAdapter);
                    rv_lista_endereco.setLayoutManager(layoutManager);
                }

                @Override
                public void onFailure(Call<Endereco> call, Throwable t) {
                    Toasty.error(context, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    @Override
    public void handleBtnCopiarEnderecoOnClick(EditText et_cep, Context context) {

        if (et_cep.getText().toString().equals("")) {
            Toasty.error(context, "Nada para copiar, favor informar um cep", Toasty.LENGTH_SHORT).show();
        } else {
            Call<Endereco> callEndereco = new RetrofitConfig().getServiceConfig().buscarEndereco(et_cep.getText().toString());
            callEndereco.enqueue(new Callback<Endereco>() {
                @Override
                public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                    Endereco endereco = response.body();
                    String copiarEndereco = endereco.toString();
                    try {
                        Toasty.success(context, "Endereço copiado para a área de transferência", Toasty.LENGTH_SHORT).show();
                        ClipboardManager clipboard = (ClipboardManager)
                                context.getSystemService(Context.CLIPBOARD_SERVICE);

                        ClipData cpy_all = ClipData.newPlainText("text", copiarEndereco);
                        clipboard.setPrimaryClip(cpy_all);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Endereco> call, Throwable t) {
                    Toast.makeText(context, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
