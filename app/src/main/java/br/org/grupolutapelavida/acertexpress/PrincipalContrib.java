package br.org.grupolutapelavida.acertexpress;

import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class PrincipalContrib extends AppCompatActivity {


    private DataBase dataBase;
    private SQLiteDatabase conn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_contrib);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * Conexao BD
         *
         * **/
       /* try
        {
            dataBase =  new DataBase(this);
            conn = dataBase.getReadableDatabase();//Chama conexao BD


            /*AlertDialog.Builder men = new AlertDialog.Builder(this);
            men.setMessage("Banco Iniciado");
            men.setNeutralButton("OK", null);
            men.show();*/


        /*}catch (SQLException X)
        {
            AlertDialog.Builder men = new AlertDialog.Builder(this);
            men.setMessage("ERRO " + X.getMessage());
            men.setNeutralButton("OK", null);
            men.show();

        }*/

    }


    //Tela Contribuintes para impressao
    public void EntraContrib(View V)
    {
        Intent intent = new Intent(this, Contribuintes.class);
        startActivity(intent);
    }


    //Adiciona novo contribuinte BD
    public void AddContrib(View V)
    {
        Intent intent = new Intent(this, AddContribuintes.class);
        startActivity(intent);
    }

    //Adiciona novo contribuinte BD
    public void StatusRec(View V)
    {
        Intent intent = new Intent(this, ContribuintesStatusRec.class);
        startActivity(intent);
    }


}
