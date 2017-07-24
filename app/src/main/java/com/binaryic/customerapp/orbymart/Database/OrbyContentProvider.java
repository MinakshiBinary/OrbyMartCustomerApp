package com.binaryic.customerapp.orbymart.Database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import static com.binaryic.customerapp.orbymart.Common.Constants.PATH_CATEGORY;
import static com.binaryic.customerapp.orbymart.Common.Constants.PATH_HOME;
import static com.binaryic.customerapp.orbymart.Common.Constants.*;


/**
 * Created by HP on 03-Apr-17.
 */

public class OrbyContentProvider extends ContentProvider {
    private static final int CODE_HOME = 1;
    private static final int CODE_CATEGORY = 2;
    private static final int CODE_SETTING = 3;
    private static final int CODE_WISHLIST = 4;
    private static final int CODE_CART = 5;
    private static final int CODE_USER = 6;
    private static final int CODE_RECENT = 7;
    private static final int CODE_ADDRESS = 8;
    private MyDBHelper helper;
    private SQLiteDatabase database;
    private UriMatcher matcher;
    @Override
    public boolean onCreate() {
        helper = new MyDBHelper(getContext());
        database = helper.getWritableDatabase();
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, PATH_HOME, CODE_HOME);
        matcher.addURI(AUTHORITY, PATH_CATEGORY, CODE_CATEGORY);
        matcher.addURI(AUTHORITY, PATH_SETTING, CODE_SETTING);
        matcher.addURI(AUTHORITY, PATH_WISHLIST, CODE_WISHLIST);
        matcher.addURI(AUTHORITY, PATH_CART, CODE_CART);
        matcher.addURI(AUTHORITY, PATH_USER, CODE_USER);
        matcher.addURI(AUTHORITY, PATH_RECENT, CODE_RECENT);
        matcher.addURI(AUTHORITY, PATH_ADDRESS, CODE_ADDRESS);
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int code = matcher.match(uri);
        Cursor cursor = null;
        if (code == CODE_HOME) {
            cursor = database.query(TABLE_HOME, projection, selection, null, null, null, null);
        }else if (code == CODE_CATEGORY) {
            cursor = database.query(TABLE_CATEGORY, projection, selection, null, null, null, null);
        }else if (code == CODE_SETTING) {
            cursor = database.query(TABLE_SETTING, projection, selection, null, null, null, null);
        }else if (code == CODE_WISHLIST) {
            cursor = database.query(TABLE_WISHLIST, projection, selection, null, null, null, null);
        }else if (code == CODE_CART) {
            cursor = database.query(TABLE_CART, projection, selection, null, null, null, null);
        }else if (code == CODE_USER) {
            cursor = database.query(TABLE_USER, projection, selection, null, null, null, null);
        }else if (code == CODE_RECENT) {
            cursor = database.query(TABLE_RECENT, projection, selection, null, null, null, null);
        }else if (code == CODE_ADDRESS) {
            cursor = database.query(TABLE_ADDRESS, projection, selection, null, null, null, null);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int code = matcher.match(uri);
        if (code == CODE_HOME) {
            database.insert(TABLE_HOME, null, values);
        }else if (code == CODE_CATEGORY) {
            database.insert(TABLE_CATEGORY, null, values);
        }else if (code == CODE_SETTING) {
            database.insert(TABLE_SETTING, null, values);
        }else if (code == CODE_WISHLIST) {
            database.insert(TABLE_WISHLIST, null, values);
        }else if (code == CODE_CART) {
            database.insert(TABLE_CART, null, values);
        }else if (code == CODE_USER) {
            database.insert(TABLE_USER, null, values);
        }else if (code == CODE_RECENT) {
            database.insert(TABLE_RECENT, null, values);
        }else if (code == CODE_ADDRESS) {
            database.insert(TABLE_ADDRESS, null, values);
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int code = matcher.match(uri);
        int delete = 0;
        if (code == CODE_HOME) {
            delete = database.delete(TABLE_HOME, selection, selectionArgs);
        }else if (code == CODE_CATEGORY) {
            delete = database.delete(TABLE_CATEGORY, selection, selectionArgs);
        }else if (code == CODE_SETTING) {
            delete = database.delete(TABLE_SETTING, selection, selectionArgs);
        }else if (code == CODE_WISHLIST) {
            delete = database.delete(TABLE_WISHLIST, selection, selectionArgs);
        }else if (code == CODE_CART) {
            delete = database.delete(TABLE_CART, selection, selectionArgs);
        }else if (code == CODE_USER) {
            delete = database.delete(TABLE_USER, selection, selectionArgs);
        }else if (code == CODE_RECENT) {
            delete = database.delete(TABLE_RECENT, selection, selectionArgs);
        }else if (code == CODE_ADDRESS) {
            delete = database.delete(TABLE_ADDRESS, selection, selectionArgs);
        }
        return delete;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int code = matcher.match(uri);
        int row = 0;
        if (code == CODE_HOME) {
            row = database.update(TABLE_HOME, values, selection, selectionArgs);
        }else if (code == CODE_CATEGORY) {
            row = database.update(TABLE_CATEGORY, values, selection, selectionArgs);
        }else if (code == CODE_SETTING) {
            row = database.update(TABLE_SETTING, values, selection, selectionArgs);
        }else if (code == CODE_WISHLIST) {
            row = database.update(TABLE_WISHLIST, values, selection, selectionArgs);
        }else if (code == CODE_CART) {
            row = database.update(TABLE_CART, values, selection, selectionArgs);
        }else if (code == CODE_USER) {
            row = database.update(TABLE_USER, values, selection, selectionArgs);
        }else if (code == CODE_RECENT) {
            row = database.update(TABLE_RECENT, values, selection, selectionArgs);
        }else if (code == CODE_ADDRESS) {
            row = database.update(TABLE_ADDRESS, values, selection, selectionArgs);
        }
        return row;
    }
}
