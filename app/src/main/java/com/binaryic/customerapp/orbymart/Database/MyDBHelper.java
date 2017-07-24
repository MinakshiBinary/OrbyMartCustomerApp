package com.binaryic.customerapp.orbymart.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.binaryic.customerapp.orbymart.Common.Constants.AREA;
import static com.binaryic.customerapp.orbymart.Common.Constants.COLUMN_USER_PICTURE;
import static com.binaryic.customerapp.orbymart.Common.Constants.LANDMARK;
import static com.binaryic.customerapp.orbymart.Common.Constants.TABLE_ADDRESS;
import static com.binaryic.customerapp.orbymart.Common.Constants.TABLE_USER;
import static com.binaryic.customerapp.orbymart.Common.Constants.*;


/**
 * Created by HP on 10-Mar-17.
 */

public class MyDBHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "com.binaryic.orbymart";
    public static int DATABASE_VERSION = 1;

    static String CREATE_USER = "create table " + TABLE_USER + "( " + USER_EMAIL + " text, " + USER_MOBILE + " text, " + USER_FN + " text, " + USER_LN + " text, " + ADDRESS + " text, " + LANDMARK + " text, " + AREA + " text, " + CITY + " text, " + PINCODE + " text, " + STATE + " text, "  + COLUMN_USER_PICTURE + " text, " + USER_ID + " text );";
    static String CREATE_ADDRESS = "create table " + TABLE_ADDRESS + "( " + ADDRESS_ID + " text, " + ADDRESS + " text, " + LANDMARK + " text, " + AREA + " text, " + CITY + " text, " + PINCODE + " text, " + STATE + " text, "  + ADDRESS_FNAME + " text, " + ADDRESS_LNAME + " text, "  + ADDRESS_MOBILE + " text, " + IS_DEFAULT + " text );";
    static String CREATE_HOME = "create table " + TABLE_HOME + "( " + COLLECTION_JSON + " text, " + SLIDER_JSON + " text, " + PROMOTIONAL_JSON + " text );";
    static String CREATE_CATEGORY = "create table " + TABLE_CATEGORY + "( " + CATEGORY_ID + " text, " + CATEGORY_NAME + " text );";
    static String CREATE_SETTING = "create table " + TABLE_SETTING + "( " + SELLER_ID + " text, " + ACCESS_TOKEN + " text );";
    static String CREATE_WISHLIST = "create table " + TABLE_WISHLIST + "( " + WISHLIST_PRODUCT_ID + " text, " + WISHLIST_PRODUCT + " text );";
    static String CREATE_CART = "create table " + TABLE_CART + "( " + CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CART_PRODUCT_ID + " text, " + CART_PRODUCT + " text, " + CART_QTY + " text );";
    static String CREATE_RECENT = "create table " + TABLE_RECENT + "( " + RECENT_PRODUCT_ID + " text, " + RECENT_PRODUCT + " text );";

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CATEGORY);
        db.execSQL(CREATE_SETTING);
        db.execSQL(CREATE_WISHLIST);
        db.execSQL(CREATE_CART);
        db.execSQL(CREATE_HOME);
        db.execSQL(CREATE_RECENT);
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_ADDRESS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WISHLIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESS);
        onCreate(db);
    }
}
