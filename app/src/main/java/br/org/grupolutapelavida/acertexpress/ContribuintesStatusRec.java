package br.org.grupolutapelavida.acertexpress;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ContribuintesStatusRec extends AppCompatActivity {



    //INSTANCIA OBJETOS
    private ListView listar;
    private ArrayAdapter<String> adpContrib;

    private RegrasBanco Consult;
    private DataBase dataBase;
    private SQLiteDatabase conn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribuintes_status_rec);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            dataBase = new DataBase(this);
            conn = dataBase.getReadableDatabase();//CHAMA CONEXAO BD

            Consult = new RegrasBanco(conn);

            //Consult.InsereContrib();

            adpContrib = Consult.buscaContribRecebidos(this);
            listar = (ListView) findViewById(R.id.listViewTeste);

            //Lista em tela contribuintes.
            listar.setAdapter(adpContrib);

            AlertDialog.Builder men = new AlertDialog.Builder(this);
            men.setMessage("Contribuintes por Status de Recebimento!");
            men.setNeutralButton("OK", null);
            men.show();


        } catch (SQLException X) {
            AlertDialog.Builder men = new AlertDialog.Builder(this);
            men.setMessage("ERRO " + X.getMessage());
            men.setNeutralButton("OK", null);
            men.show();

        }


    }


    public void SairTelaRec(View v)
    {
        finish();
    }

}
