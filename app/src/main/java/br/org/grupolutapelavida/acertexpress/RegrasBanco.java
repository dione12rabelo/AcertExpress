package br.org.grupolutapelavida.acertexpress;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Dione on 22/10/2016.
 */
public class RegrasBanco {

    //Contrib
    private SQLiteDatabase conn;
    private String ID;
    private String NOME;
    private String TELEFONE;
    private String ENDERECO;
    private String VALOR;
    private String DTRECEBIMENTO;
    private String STATUSREC;
    private String DadosPesquisa;


    //User
    private String NOMEUSER = "DIONE";
    private String CPF = "00000000";
    private String ENDERECOUSER = "";
    private String TELEFONEUSER = "";
    private String SENHA = "1234";
    private String Concater;

    //CONSTRUTOR conexao
    public RegrasBanco(SQLiteDatabase conn)
    {
        this.conn = conn;
    }

    //Insere USUARIO BANCO
    public void InsereUser()
    {



        ContentValues values = new ContentValues();
        values.put("NOME", ""+NOMEUSER.toString());
        values.put("CPF",""+CPF.toString());
        values.put("ENDERECO",""+ENDERECOUSER.toString());
        values.put("TELEFONE",""+TELEFONEUSER.toString());
        values.put("SENHA",""+SENHA.toString());
        //values.put("DATARECEBIMENTO",""+currentDateTimeString.toString());

        conn.insertOrThrow("TUSUARIO", null, values);

    }


    //Seleciona USUARIIOS BD
    public String buscaUser(Context context)
    {

        //Objeto
        //ArrayAdapter<String> adpContrib =  new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);


        Cursor cursor = conn.rawQuery("SELECT * FROM TUSUARIO", null);


        //BUSCA REGISTROS
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();//POSICIONA PRIMEIRO REGISTRO

            //do {

                NOMEUSER =  cursor.getString(1);
                SENHA =  cursor.getString(5);

                //adpContrib.add("NOME: "+NOMEUSER +"\n"+"SENHA: "+SENHA +"\n");
                Concater = NOMEUSER +""+SENHA;

            //}while (cursor.moveToNext());
            cursor.moveToNext();

        }
        return Concater;
    }






    //Insere contribuintes
    public void InsereContrib(String NOME, String TELEFONE, String ENDERE, String VALOR, String DATAPREC)
    {
        String currentDateTimeString = DateFormat.getDateInstance().format(new Date());


            ContentValues values = new ContentValues();
            values.put("NOME", ""+NOME.toString());
            values.put("TELEFONE",""+TELEFONE.toString());
            values.put("ENDERECO",""+ENDERE.toString());
            values.put("VALOR",""+VALOR.toString());
            values.put("DATARECEBIMENTO",""+DATAPREC.toString());
            //values.put("DATARECEBIMENTO",""+currentDateTimeString.toString());

            conn.insertOrThrow("TCONTRIBUINTES", null, values);

    }


    public void Pesquisa(String dado)
    {
        DadosPesquisa = dado;
    }

    //Busca CONTRIBUINTES pelo Filtro
    public ArrayAdapter<String> PesquisaContrib(Context context)
    {

        //Objeto
        ArrayAdapter<String> adpContrib =  new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);


        Cursor cursor = conn.rawQuery("SELECT * FROM TCONTRIBUINTES WHERE NOME LIKE '%"+ DadosPesquisa +"%'", null);


        //BUSCA REGISTROS
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();//POSICIONA PRIMEIRO REGISTRO

            do {

                ID = cursor.getString(0);
                NOME =  cursor.getString(1);
                TELEFONE =  cursor.getString(2);
                ENDERECO =  cursor.getString(3);
                VALOR =  cursor.getString(4);
                DTRECEBIMENTO =  cursor.getString(5);

                adpContrib.add("NOME: "+NOME +"\n"+"TELEFONE: "+TELEFONE +"\n"+"ENDERECO: "+ENDERECO+"\n" +"VALOR DOACAO: R$"+VALOR+"\n"+"DATA RECEBIMENTO: "+DTRECEBIMENTO+"\n");

            }while (cursor.moveToNext());

        }
        return adpContrib;
    }




    //Busca CONTRIBUINTES para recebimento
    public ArrayAdapter<String> buscaContrib(Context context)
    {

        //Objeto
        ArrayAdapter<String> adpContrib =  new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = conn.rawQuery("SELECT * FROM TCONTRIBUINTES WHERE STATUSREC IS NULL", null);

        //BUSCA REGISTROS
       if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();//POSICIONA PRIMEIRO REGISTRO

            do {

                ID = cursor.getString(0);
                NOME =  cursor.getString(1);
                TELEFONE =  cursor.getString(2);
                ENDERECO =  cursor.getString(3);
                VALOR =  cursor.getString(4);
                DTRECEBIMENTO =  cursor.getString(5);

                adpContrib.add("NOME: "+NOME +"\n"+"TELEFONE: "+TELEFONE +"\n"+"ENDERECO: "+ENDERECO+"\n" +"VALOR DOACAO: R$"+VALOR+"\n"+"DATA RECEBIMENTO: "+DTRECEBIMENTO+"\n");

            }while (cursor.moveToNext());

        }
        return adpContrib;
    }


    //UPDATE STATUSREC
    public void update(String Status)
    {

        String currentDateTimeString = DateFormat.getDateInstance().format(new Date());

        ContentValues values = new ContentValues();
        values.put("STATUSREC",""+Status.toString());
        values.put("DATARECEBIMENTO",""+currentDateTimeString.toString());

        conn.update("TCONTRIBUINTES", values, "STATUSREC IS NULL AND _ID = "+ID,null);

    }


    //Busca CONTRIBUINTES por STATUS de recebimento
    public ArrayAdapter<String> buscaContribRecebidos(Context context)
    {
        //Objeto
        ArrayAdapter<String> adpContrib =  new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);

        Cursor cursor = conn.rawQuery("SELECT * FROM TCONTRIBUINTES WHERE NOT STATUSREC IS NULL", null);


        //BUSCA REGISTROS
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();//POSICIONA PRIMEIRO REGISTRO

            do {

                ID = cursor.getString(0);
                NOME =  cursor.getString(1);
                TELEFONE =  cursor.getString(2);
                ENDERECO =  cursor.getString(3);
                VALOR =  cursor.getString(4);
                DTRECEBIMENTO =  cursor.getString(5);
                STATUSREC =  cursor.getString(6);

                adpContrib.add("CODIGO: "+ID+"\n"+"NOME: "+NOME +"\n"+"TELEFONE: "+TELEFONE +"\n"+"ENDERECO: "+ENDERECO+"\n" +"VALOR DOACAO: R$"+VALOR+"\n"+"DATA RECEBIMENTO: "+DTRECEBIMENTO+"\n" + "STATUSREC: "+STATUSREC+"\n");

            }while (cursor.moveToNext());

        }
        return adpContrib;
    }

}
