package com.example.animanga_universe.extras;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Helper para la creación y trabajo con la base de datos interna de SQLite
 * @author Daniel Seregin Kozlov
 */
public class Helper extends SQLiteOpenHelper {
    final String sqlCreacion="create table usuario(usuario text primary key, password text)";
    final String sqlBorrado="drop table if exists usuario";

    /**
     * Construcotr para el helper
     * @param context el contexto
     * @param name nombre de la base de datos
     * @param factory ser utiliza para el cursor
     * @param version version de la bbdd
     */
    public Helper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * creacion de la base de datos
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreacion);
    }

    /**
     * actualizacion de la base de datos
     * @param db The database
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(sqlBorrado);
        db.execSQL(sqlCreacion);
    }
}
