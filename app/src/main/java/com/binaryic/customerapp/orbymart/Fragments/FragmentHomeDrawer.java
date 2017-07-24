package com.binaryic.customerapp.orbymart.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binaryic.customerapp.orbymart.Activity.ComingSoonActivity;
import com.binaryic.customerapp.orbymart.Adapter.MenuAdapter;
import com.binaryic.customerapp.orbymart.Controller.CustomerController;
import com.binaryic.customerapp.orbymart.Controller.HomeController;
import com.binaryic.customerapp.orbymart.Model.CategoryModel;
import com.binaryic.customerapp.orbymart.R;

import java.util.ArrayList;

import static com.binaryic.customerapp.orbymart.Common.Constants.COLUMN_USER_PICTURE;
import static com.binaryic.customerapp.orbymart.Common.Constants.CONTENT_USER;
import static com.binaryic.customerapp.orbymart.Common.Constants.USER_EMAIL;
import static com.binaryic.customerapp.orbymart.Common.Constants.USER_FN;
import static com.binaryic.customerapp.orbymart.Common.Constants.USER_LN;


/**
 * Created by HP on 03-Apr-17.
 */

public class FragmentHomeDrawer extends Fragment {
    ImageView iv_user;
    TextView tv_name, tv_email;
    RecyclerView rv_collection;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_drawer, container, false);
        inIt(view);
        return view;
    }
    private void inIt(View view) {
        iv_user = (ImageView) view.findViewById(R.id.iv_user);
        rv_collection = (RecyclerView) view.findViewById(R.id.rv_collection);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_email = (TextView) view.findViewById(R.id.tv_email);
        LinearLayout ll_setting = (LinearLayout) view.findViewById(R.id.ll_setting);
        LinearLayout ll_sign_out = (LinearLayout) view.findViewById(R.id.ll_sign_out);
        ll_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerController.signOut(getActivity());
            }
        });
        ll_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ComingSoonActivity.class);
                intent.putExtra("name","Settings");
                startActivity(intent);

            }
        });
        getPhoto();
        setUpRecyclerView();
    }

    public void setUpRecyclerView(){
        HomeController homeController = new HomeController();
        ArrayList<CategoryModel> list = homeController.getCategories(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setAutoMeasureEnabled(true);
        rv_collection.setLayoutManager(linearLayoutManager);
        MenuAdapter menuAdapter = new MenuAdapter(getActivity(),list);
        rv_collection.setAdapter(menuAdapter);
    }

    public void getPhoto() {
        String image = "";
        Cursor cursor_Image = getActivity().getContentResolver().query(CONTENT_USER, null, null, null, null);
        Log.e("cursor_Image111", "==" + cursor_Image.getCount());
        if (cursor_Image.getCount() > 0) {
            cursor_Image.moveToLast();
            image = cursor_Image.getString(cursor_Image.getColumnIndex(COLUMN_USER_PICTURE));
            tv_name.setText(cursor_Image.getString(cursor_Image.getColumnIndex(USER_FN)) + " " + cursor_Image.getString(cursor_Image.getColumnIndex(USER_LN)));
            tv_email.setText(cursor_Image.getString(cursor_Image.getColumnIndex(USER_EMAIL)));
        }
        cursor_Image.close();
        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        if (!image.equals("")) {
            RoundedBitmapDrawable circularBitmapDrawable =
                    RoundedBitmapDrawableFactory.create(getResources(), decodedByte);
            circularBitmapDrawable.setCircular(true);
            iv_user.setImageDrawable(circularBitmapDrawable);
        }
    }

}
