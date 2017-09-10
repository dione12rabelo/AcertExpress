package br.org.grupolutapelavida.acertexpress;

import android.content.Context;
import android.database.sqlite.*;

/**
 * Created by Dione on 22/10/2016.
 */
public class DataBase extends SQLiteOpenHelper{

    //Construtor
    public DataBase(Context context)
    {
        //Nome BD e versão
        super(context,"ACERT_EXPRESS", null, 3);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //Criação Tabela
        //db.execSQL(Script.getMostraCOntrib());

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE TCONTRIBUINTES ( ");
        sqlBuilder.append("_ID INTEGER NOT NULL ");
        sqlBuilder.append("PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("NOME VARCHAR (50), ");
        sqlBuilder.append("TELEFONE VARCHAR (14), ");
        sqlBuilder.append("ENDERECO VARCHAR (50), ");
        sqlBuilder.append("VALOR DOUBLE, ");
        sqlBuilder.append("DATARECEBIMENTO DATE, ");
        sqlBuilder.append("STATUSREC VARCHAR (5)");
        sqlBuilder.append(");");

        StringBuilder sqlBuilder1 = new StringBuilder();
        sqlBuilder1.append("CREATE TABLE TUSUARIO ( ");
        sqlBuilder1.append("_ID INTEGER NOT NULL ");
        sqlBuilder1.append("PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder1.append("NOME VARCHAR (50), ");
        sqlBuilder1.append("CPF VARCHAR (16), ");
        sqlBuilder1.append("ENDERECO VARCHAR (50), ");
        sqlBuilder1.append("TELEFONE VARCHAR (14), ");
        sqlBuilder1.append("SENHA INTERGER ");
        sqlBuilder1.append(");");

        db.execSQL(sqlBuilder.toString());
        db.execSQL(sqlBuilder1.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
