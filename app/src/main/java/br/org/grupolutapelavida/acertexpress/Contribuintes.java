package br.org.grupolutapelavida.acertexpress;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.database.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Arrays;
import br.org.grupolutapelavida.acertexpress.RegrasBanco;

public class Contribuintes extends AppCompatActivity implements View.OnClickListener {


    public static int ENABLE_BLUETOOTH = 1;
    ConnectionThread connect;


    private RegrasBanco Consult;
    private String dados;
    private String StatusRec;
    private int posicaoSelect;


    //INSTANCIA OBJETOS
    private ListView listar;
    private ArrayAdapter<String> adpContrib;



    private DataBase dataBase;
    private SQLiteDatabase conn;

    static TextView statusMessage;
    static TextView textSpace;
    static ImageView Feliz;
    static EditText pesquisa;
    Intent data;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribuintes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        statusMessage = (TextView) findViewById(R.id.textView4);
        Feliz = (ImageView) findViewById(R.id.imageViewFliz);
        pesquisa = (EditText) findViewById(R.id.edtPesquisa);


        /*Ativa Bluetooth automatico*/

        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        btAdapter.enable();

        if (btAdapter == null)
        {
            statusMessage.setText("Que pena! Hardware Bluetooth não está funcionando!");
            Feliz.setBackgroundResource(R.drawable.next);
        } else {
            statusMessage.setText("Ótimo! Hardware Bluetooth está funcionando!");
        }

        if (!btAdapter.isEnabled())
        {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, ENABLE_BLUETOOTH);
            statusMessage.setText("Solicitando ativação do Bluetooth...");
        } else {
            statusMessage.setText("Bluetooth já ativado!");
            Feliz.setBackgroundResource(R.drawable.felz);
        }



            /*Conexao BD*/
        try {
            dataBase = new DataBase(this);
            conn = dataBase.getReadableDatabase();//CHAMA CONEXAO BD

            Consult = new RegrasBanco(conn);

            adpContrib = Consult.buscaContrib(this);
            listar = (ListView) findViewById(R.id.listViewTeste);

            listar.setAdapter(adpContrib);


            //Conecta com IMPRESSORA
            connect();
            //Item selecionado
            MostrarItem();


            AlertDialog.Builder men = new AlertDialog.Builder(this);
            men.setMessage("Contribuintes para recebimeto!");
            men.setNeutralButton("OK", null);
            men.show();


        } catch (SQLException X) {
            AlertDialog.Builder men = new AlertDialog.Builder(this);
            men.setMessage("ERRO " + X.getMessage());
            men.setNeutralButton("OK", null);
            men.show();

        }



        /*Filtro de Contribuintes*/


        pesquisa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().equals(""))
                {
                    //CarregaLista
                    CarregaListContrib();
                }
                else
                {
                    //Busca Lista
                    pesquisaItem(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }



    /*Pesquiisa Contribuintes*/
    public void  pesquisaItem(String Item)
    {

        Consult.Pesquisa(Item);
        adpContrib = Consult.PesquisaContrib(this);
        listar = (ListView) findViewById(R.id.listViewTeste);
        listar.setAdapter(adpContrib);

    }

    public void CarregaListContrib()
    {
        adpContrib = Consult.buscaContrib(this);
        listar = (ListView) findViewById(R.id.listViewTeste);

        listar.setAdapter(adpContrib);
    }



    //Conexao com o Impressora
    public void  connect()
    {
        connect = new ConnectionThread("00:02:5B:B4:13:6E"); //MAC da impressora disponibilizada para mensageiro
        connect.start();

    }

    public void canconexImpressora(View v)
    {
        AlertDialog.Builder men = new AlertDialog.Builder(Contribuintes.this);
        men.setTitle("Informação..");
        men.setMessage("Voltar MENU Contribuinte?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                        connect.cancel();

                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                }).show();



    }



    //Conexao com Thread
    public void waitConnection(View view) {

        ConnectionThread connect = new ConnectionThread();
        connect.start();
    }


    //Objeto que recebe mensagem de outra CennectiThread
    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            Bundle bundle = msg.getData();
            byte[] data = bundle.getByteArray("data");
            String dataString = new String(data);

            if (dataString.equals("---N")){
                statusMessage.setText("Não conectado!");
                Feliz.setBackgroundResource(R.drawable.tris);}
            else if (dataString.equals("---S")){
                statusMessage.setText("Dispositivo Conectado com a Impressora!");
                Feliz.setBackgroundResource(R.drawable.felz);}
            else {

                textSpace.setText(new String(data + "\n\n\n"));
            }
        }
    };


    //Obtem o ITEM SELECIONADO LISTVIEWR
    private void MostrarItem() {
        listar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder men = new AlertDialog.Builder(Contribuintes.this);
                men.setTitle("Informação..");
                men.setMessage("Deseja imprimir o Recibo?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                //Recebe dados do item selecionado
                                posicaoSelect = position;
                                dados = listar.getItemAtPosition(position).toString();
                                Toast.makeText(Contribuintes.this, dados, Toast.LENGTH_SHORT).show();


                                //Imprime recibo
                                String messageBoxString = "\nRECIBO DE DOACAO - ACERTEXPRESS\n\n" + dados.toString() + "\n\n" + "         MOBILE-TIME" + "\n"
                                        + "_____________________________"+"\n\n\n";
                                //Converte em Byte
                                byte[] data = messageBoxString.getBytes();
                                connect.write(data);

                                //Muda status recibo para R - RECEBIDO e Insere no BD
                                StatusRec = "R";
                                Consult.update(StatusRec);

                                //listar.getChildAt(position).setBackgroundColor(Color.BLUE);

                                //Remove da lista APÓS IMPRESSÃO
                                adpContrib.remove(adpContrib.getItem(position));
                            }

                        })
                        .setNeutralButton("Cancelar Recibo", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                //Muda status recibo para C - CANCELADO
                                StatusRec = "C";
                                Consult.update(StatusRec);

                                //Remove da lista APÓS CANCELAMENTO
                                adpContrib.remove(adpContrib.getItem(position));
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();

            }
        });
    }

    @Override
    public void onBackPressed() {
        // Trava voltar tela contrib
    }

    @Override
    public void onClick(View v) {


    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Contribuintes Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://br.org.grupolutapelavida.acertexpress/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Contribuintes Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://br.org.grupolutapelavida.acertexpress/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
