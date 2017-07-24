package com.binaryic.customerapp.orbymart.Fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.binaryic.customerapp.orbymart.Activity.ComingSoonActivity;
import com.binaryic.customerapp.orbymart.Activity.LoginActivity;
import com.binaryic.customerapp.orbymart.Activity.OrderActivity;
import com.binaryic.customerapp.orbymart.Activity.SavedAddressActivity;
import com.binaryic.customerapp.orbymart.Common.MyApplication;
import com.binaryic.customerapp.orbymart.Controller.CustomerController;
import com.binaryic.customerapp.orbymart.Controller.ImageController;
import com.binaryic.customerapp.orbymart.Model.Customer;
import com.binaryic.customerapp.orbymart.R;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.binaryic.customerapp.orbymart.Common.Constants.COLUMN_USER_PICTURE;
import static com.binaryic.customerapp.orbymart.Common.Constants.CONTENT_USER;
import static com.binaryic.customerapp.orbymart.Common.Constants.SH_USER_LOGIN;


/**
 * Created by User on 08-09-2016.
 */
public class FragmentMyAccount extends Fragment implements View.OnClickListener {
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
    private TextView tv_Name,lblLogin;
    private TextView tv_Email;
    private RelativeLayout rl_SignOut;
    private CircleImageView civ_Profile;
    private RelativeLayout rl_My_Orders, rl_address, rlNotif;
    Bitmap bitmap_user = null;
    LinearLayout ll_login,ll_logout;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myaccount, container, false);
        tv_Name = (TextView) view.findViewById(R.id.tv_Name);
        tv_Email = (TextView) view.findViewById(R.id.tv_Email);
        rl_SignOut = (RelativeLayout) view.findViewById(R.id.rl_SignOut);
        civ_Profile = (CircleImageView) view.findViewById(R.id.civ_Profile);
        rl_My_Orders = (RelativeLayout) view.findViewById(R.id.rl_My_Orders);
        rlNotif = (RelativeLayout) view.findViewById(R.id.rlNotif);
        rl_address = (RelativeLayout) view.findViewById(R.id.rl_address);
        ll_login = (LinearLayout) view.findViewById(R.id.ll_login);
        ll_logout = (LinearLayout) view.findViewById(R.id.ll_logout);
        lblLogin = (TextView) view.findViewById(R.id.lblLogin);
        setData();
        rl_SignOut.setOnClickListener(this);
        rlNotif.setOnClickListener(this);
        civ_Profile.setOnClickListener(this);
        rl_My_Orders.setOnClickListener(this);
        rl_address.setOnClickListener(this);
        lblLogin.setOnClickListener(this);
        getPhoto();
        return view;
    }

    public void setData(){
        if(MyApplication.setting.getBoolean(SH_USER_LOGIN,false)) {
            Customer customer = CustomerController.GetCustomerData(getActivity());
            tv_Name.setText(customer.getFirstName() + " " + customer.getLastName());
            tv_Email.setText(customer.getEmail());
            ll_login.setVisibility(View.VISIBLE);
            ll_logout.setVisibility(View.GONE);
        }else{
            ll_login.setVisibility(View.GONE);
            ll_logout.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_SignOut:
                CustomerController.signOut(getActivity());
                break;
            case R.id.civ_Profile:
                getProfilePicture();
                break;
            case R.id.rl_My_Orders:
                startActivity(new Intent(getActivity(), OrderActivity.class));
                break;
            case R.id.rl_address:
                startActivity(new Intent(getActivity(), SavedAddressActivity.class));
                break;
            case R.id.rlNotif:
                Intent intent = new Intent(getActivity(), ComingSoonActivity.class);
                intent.putExtra("name", "Notification");
                startActivity(intent);
                break;
            case R.id.lblLogin:
                Intent intentLogin = new Intent(getActivity(), LoginActivity.class);
                intentLogin.putExtra("type", "Login");
                startActivity(intentLogin);
                break;
        }
    }


    private void getProfilePicture() {
        ImageController controller = new ImageController();
        controller.selectUploadType(getActivity());

    }

    public void getUserPic(Bitmap bitmap) {
        try {
            RoundedBitmapDrawable circularBitmapDrawable =
                    RoundedBitmapDrawableFactory.create(getResources(), bitmap);
            circularBitmapDrawable.setCircular(true);
            civ_Profile.setImageDrawable(circularBitmapDrawable);
            bitmapToBase64(bitmap);
        } catch (Exception ex) {
        }
    }

    private void savePhoto(String image) {
        Cursor cursor_Image = getActivity().getContentResolver().query(CONTENT_USER, null, null, null, null);
        Log.e("cursor_Image111", "==" + cursor_Image.getCount());
        if (cursor_Image.getCount() > 0) {
            cursor_Image.moveToLast();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_USER_PICTURE, image);
            getActivity().getContentResolver().update(CONTENT_USER, contentValues, null, null);

        }
        cursor_Image.close();
    }

    private void getPhoto() {
        String image = "";
        Cursor cursor_Image = getActivity().getContentResolver().query(CONTENT_USER, null, null, null, null);
        Log.e("cursor_Image111", "==" + cursor_Image.getCount());
        if (cursor_Image.getCount() > 0) {
            cursor_Image.moveToLast();
            image = cursor_Image.getString(cursor_Image.getColumnIndex(COLUMN_USER_PICTURE));
        }
        cursor_Image.close();
        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        if (!image.equals("")) {
            RoundedBitmapDrawable circularBitmapDrawable =
                    RoundedBitmapDrawableFactory.create(getResources(), decodedByte);
            circularBitmapDrawable.setCircular(true);
            civ_Profile.setImageDrawable(circularBitmapDrawable);
        }
    }

    private void bitmapToBase64(Bitmap bit) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        savePhoto(encoded);
    }


}
