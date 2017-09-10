package br.org.grupolutapelavida.acertexpress;

import android.content.DialogInterface;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.sql.Time;

import br.org.grupolutapelavida.acertexpress.RegrasBanco;

public class AddContribuintes extends AppCompatActivity {

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private RegrasBanco Consult;

    private EditText NOME;
    private EditText TELEFONE;
    private EditText ENDERECO;
    private EditText VALOR;
    private EditText DATAPREC;


    private String nome;
    private String telefone;
    private String endereco;
    private String valor;
    private String datarec;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contribuintes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        //MASK PHONE

        final EditText campo_telefone = (EditText) findViewById(R.id.edtTelefone);
        campo_telefone.addTextChangedListener(Masck.insert("(##)#####-####", campo_telefone));

        //final EditText campo_Valor = (EditText) findViewById(R.id.edtValor);
        //campo_Valor.addTextChangedListener(Masck.insert("##,##", campo_Valor));

        //MASK DATE
        final EditText dt = (EditText) findViewById(R.id.edtDataRec);
        dt.addTextChangedListener(Masck.insert("##/##/####", dt));



        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void Insert(View v)
    {

        try {

            NOME = (EditText) findViewById(R.id.edtNome);
            nome = NOME.getText().toString();
            TELEFONE = (EditText) findViewById(R.id.edtTelefone);
            telefone = TELEFONE.getText().toString();
            ENDERECO = (EditText) findViewById(R.id.edtEndereco);
            endereco = ENDERECO.getText().toString();
            VALOR = (EditText) findViewById(R.id.edtValor);
            valor = VALOR.getText().toString();
            DATAPREC = (EditText)findViewById(R.id.edtDataRec);
            datarec = DATAPREC.getText().toString();


            dataBase = new DataBase(this);
            conn = dataBase.getReadableDatabase();//CHAMA CONEXAO BD
            Consult = new RegrasBanco(conn);


            AlertDialog.Builder men = new AlertDialog.Builder(this);
            men.setTitle("Informação..");
            men.setMessage("Confirma Inclusão?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which)
                        {

                            if (NOME.getText().toString().equals("") || TELEFONE.getText().toString().equals("") || VALOR.getText().toString().equals(""))
                            {
                                AlertDialog.Builder Mensa = new AlertDialog.Builder(AddContribuintes.this);
                                Mensa.setTitle("Informação!");
                                Mensa.setMessage("Os campos NOME,ENDEREÇO e VALOR são obrigatórios!");
                                Mensa.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();

                                    }
                                });
                                Mensa.show();


                            }else
                            {  //Insere BD
                                Consult.InsereContrib(nome,telefone,endereco,valor, datarec);
                                finish();

                            }


                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which)
                        {

                            dialog.cancel();
                        }
                    }).show();




        } catch (SQLException X) {
            AlertDialog.Builder men = new AlertDialog.Builder(this);
            men.setMessage("ERRO " + X.getMessage());
            men.setNeutralButton("OK", null);
            men.show();

        }
    }

    public void Sair(View v)
    {
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "AddContribuintes Page", // TODO: Define a title for the content shown.
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
                "AddContribuintes Page", // TODO: Define a title for the content shown.
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
