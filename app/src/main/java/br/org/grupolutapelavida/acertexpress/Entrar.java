package br.org.grupolutapelavida.acertexpress;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class Entrar extends AppCompatActivity {

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private RegrasBanco Consult;

    private EditText NOME;
    private EditText SENHA;
    private String NOMEUSER;
    private String SENHAUSE;

    private String NOMEBD;
    private String SENHAUSEBD;


    private String User;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * Inícia contrução e Conexao BD
         *
         * **/
        try
        {
            dataBase =  new DataBase(this);
            conn = dataBase.getReadableDatabase();//Chama conexao BD
            Consult = new RegrasBanco(conn);
            Consult.InsereUser();


        }catch (SQLException X)
        {
            AlertDialog.Builder men = new AlertDialog.Builder(this);
            men.setMessage("ERRO " + X.getMessage());
            men.setNeutralButton("OK", null);
            men.show();

        }

    }

    public void Entrar(View view)
    {
        NOME = (EditText) findViewById(R.id.edtNomeUser);
        NOMEUSER = NOME.getText().toString();
        SENHA = (EditText) findViewById(R.id.edtSenhaUser);
        SENHAUSE = SENHA.getText().toString();

        //Concatena String para verificar usuário
        SENHAUSEBD = NOMEUSER+""+SENHAUSE;


        dataBase = new DataBase(this);
        conn = dataBase.getReadableDatabase();//CHAMA CONEXAO BD

        Consult = new RegrasBanco(conn);

        User = Consult.buscaUser(this);
        NOMEBD = User.toString();

        if(SENHAUSEBD.equals(NOMEBD))
        {
            Intent intent =  new Intent(this,PrincipalContrib.class);
            startActivity(intent);
        }else
        {
                Toast toast = Toast.makeText(this, "Usuário não cadastrado!",Toast.LENGTH_SHORT);
                toast.show();
        }


        //setContentView(R.layout.activity_principal_contrib);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_entrar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
