package com.binaryic.customerapp.orbymart.Common;

import android.net.Uri;

/**
 * Created by HP on 03-Apr-17.
 */

public class Constants {

    public static final int PICK_PHOTO = 1001;
    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 1002;
    public static final int CAMERA_PERMISSION_CODE = 101;
    public static final int CAMERA_GALLARY_CODE = 102;

    public static final String SH_USER_LOGIN ="USER_LOGIN";
    public static final String SH_USER_ID ="USER_ID";

    public static final String AUTHORITY = "com.binaryic.orbymart";
    public static final String CONETNT_PROTOCOL = "content://";
    public static final String PATH_HOME = "_lyhome";
    public static final String PATH_CATEGORY = "_lycategory";
    public static final String PATH_SETTING = "_lysetting";
    public static final String PATH_WISHLIST = "_lywishlist";
    public static final String PATH_CART = "_lycart";
    public static final String PATH_USER = "_lyuser";
    public static final String PATH_RECENT = "_lyrecent";
    public static final String PATH_ADDRESS = "_lyaddress";

    public static final Uri CONTENT_HOME = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_HOME);
    public static final Uri CONTENT_CATEGORY = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_CATEGORY);
    public static final Uri CONTENT_SETTING = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_SETTING);
    public static final Uri CONTENT_WISHLIST = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_WISHLIST);
    public static final Uri CONTENT_CART = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_CART);
    public static final Uri CONTENT_USER = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_USER);
    public static final Uri CONTENT_RECENT = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_RECENT);
    public static final Uri CONTENT_ADDRESS = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_ADDRESS);

    public static String TABLE_USER = "tbl_user";
    public static String USER_EMAIL = "user_email";
    public static String USER_FN = "user_firstName";
    public static String USER_LN = "user_lastName";
    public static String USER_ID = "user_id";
    public static String USER_MOBILE = "user_mobile";

    public static String COLUMN_USER_PICTURE = "COLUMN_USER_PICTURE";

    public static String TABLE_HOME = "tbl_home";
    public static String SLIDER_JSON = "slider_banner";
    public static String PROMOTIONAL_JSON = "promotional_banner";
    public static String COLLECTION_JSON = "productsdata";

    public static String TABLE_CATEGORY = "tbl_category";
    public static String CATEGORY_ID = "category_id";
    public static String CATEGORY_NAME = "category_name";

    public static String TABLE_SETTING = "tbl_setting";
    public static String SELLER_ID = "seller_id";
    public static String ACCESS_TOKEN = "access_token";

    public static String TABLE_WISHLIST = "tbl_wishlist";
    public static String WISHLIST_PRODUCT_ID = "wishlist_product_id";
    public static String WISHLIST_PRODUCT = "wishlist_product";

    public static String TABLE_CART = "tbl_cart";
    public static String CART_PRODUCT_ID = "cart_product_id";
    public static String CART_PRODUCT = "cart_product";
    public static String CART_QTY = "cart_qty";
    public static String CART_ID = "cart_id";

    public static String TABLE_RECENT = "tbl_recent";
    public static String RECENT_PRODUCT_ID = "recent_product_id";
    public static String RECENT_PRODUCT = "recent_product";

    public static String TABLE_ADDRESS = "tbl_address";
    public static String ADDRESS_ID = "address_id";
    public static String ADDRESS_FNAME = "address_fname";
    public static String ADDRESS_LNAME = "address_lname";
    public static String ADDRESS_MOBILE = "address_mobile";
    public static String ADDRESS = "address";
    public static String LANDMARK = "landmark";
    public static String AREA = "area";
    public static String PINCODE = "pincode";
    public static String CITY = "city";
    public static String STATE = "state";
    public static String IS_DEFAULT = "is_default";
}
