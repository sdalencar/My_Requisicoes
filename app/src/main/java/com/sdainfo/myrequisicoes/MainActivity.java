package com.sdainfo.myrequisicoes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private EditText et_buscar;
    private TextView tv_resultado_cep, tv_resultado_preco;
    private String retornoBausca;
    private Button botaoRecuperar;
    private String getRetornoBausca;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_buscar = findViewById(R.id.et_buscar);
        tv_resultado_preco = findViewById(R.id.textView_resultado_preco);
        tv_resultado_cep = findViewById(R.id.textView_resultado_cep);
        et_buscar.setText("02114-010");
        botaoRecuperar = findViewById(R.id.button_buscar);


        botaoRecuperar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTask task = new MyTask();

                String urlCotacao = "https://blockchain.info/ticker";


                String cep = et_buscar.getText().toString();
                String urlCep = "https://viacep.com.br/ws/" + cep + "/json/";

                task.execute(urlCotacao);
            }
        });

    }

    class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            String stringUrl = strings[0];
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            StringBuffer buffer = null;

            try {

                URL url = new URL(stringUrl);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                // Recupera os dados em Bytes
                inputStream = conexao.getInputStream();

                //inputStreamReader lê os dados em Bytes e decodifica para caracteres
                inputStreamReader = new InputStreamReader(inputStream);

                //Objeto utilizado para leitura dos caracteres do InpuStreamReader
                BufferedReader reader = new BufferedReader(inputStreamReader);
                buffer = new StringBuffer();
                String linha = "";

                while ((linha = reader.readLine()) != null) {
                    buffer.append(linha);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);

           // String a = "", b = "", c = "", d = "", e = "", f = "";


           // String AUD1 = "", BRL1 = "", CAD1 = "", CHF1 = "", CLP1 = "", CNY1 = "", DKK1 = "", EUR1 = "", GBP1 = "", HKD1 = "", INR1 = "", ISK1 = "", JPY1 = "", KRW1 = "", NZD1 = "", PLN1 = "", RUB1 = "", SEK1 = "", SGD1 = "", THB1 = "", TRY1 = "", TWD1 = "";

            String objtoCompleto = null;
            String objtoCompletocampo1, objtoCompletocampo2, objtoCompletocampo3, objtoCompletocampo4;

            JSONObject jsonObjectRecebido = null;

            String ultimo = null, comprar = null, vender = null, simbolo = null;


            try {
              /*  jsonObject = new JSONObject(resultado);
                a = jsonObject.getString("logradouro");
                b = jsonObject.getString("cep");
                c = jsonObject.getString("bairro");
                d = jsonObject.getString("localidade");
                e = jsonObject.getString("uf");
                f = jsonObject.getString("complemento"); */

                jsonObjectRecebido = new JSONObject(resultado);
                objtoCompleto = jsonObjectRecebido.getString("BRL");
                JSONObject camposObjecto = new JSONObject(objtoCompleto);

                ultimo = camposObjecto.getString("last");
                comprar = camposObjecto.getString("buy");
                vender = camposObjecto.getString("sell");
                simbolo = camposObjecto.getString("symbol");



                /*
                AUD1 = jsonObject.getString("AUD");

                CAD1 = jsonObject.getString("CAD");
                CHF1 = jsonObject.getString("CHF");
                CLP1 = jsonObject.getString("CLP");
                CNY1 = jsonObject.getString("CNY");
                DKK1 = jsonObject.getString("DKK");
                EUR1 = jsonObject.getString("EUR");
                GBP1 = jsonObject.getString("GBP");
                HKD1 = jsonObject.getString("HKD");
                INR1 = jsonObject.getString("INR");
                ISK1 = jsonObject.getString("ISK");
                JPY1 = jsonObject.getString("JPY");
                KRW1 = jsonObject.getString("KRW");
                NZD1 = jsonObject.getString("NZD");
                PLN1 = jsonObject.getString("PLN");
                RUB1 = jsonObject.getString("RUB");
                SEK1 = jsonObject.getString("SEK");
                SGD1 = jsonObject.getString("SGD");
                THB1 = jsonObject.getString("THB");
                TRY1 = jsonObject.getString("TRY");
                TWD1 = jsonObject.getString("TWD");

                */


            } catch (JSONException g) {

            }

            //tv_resultado_cep.setText( resultado );

            //String res = "Logradouro: " + a + "\ncep: " +b+ "\nbairro: " +c+ "\nlocal: " +d+ "\nUF: "+e+ "\ncomplemento: "+f;
            //tv_resultado_cep.setText( res );

            //tv_resultado_preco.setText("Valor do Real : " + BRL1);
            tv_resultado_preco.setText("Preço do Bitcoin \nultimo : " + simbolo +" "+ ultimo + "\ncomprar : " + simbolo +" "+ comprar + "\nvender : " + simbolo +" "+ vender);
        }
    }

}
