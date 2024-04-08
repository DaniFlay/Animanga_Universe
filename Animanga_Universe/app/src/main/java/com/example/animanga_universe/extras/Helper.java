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

    public Helper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreacion);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(sqlBorrado);
        db.execSQL(sqlCreacion);
    }
}
