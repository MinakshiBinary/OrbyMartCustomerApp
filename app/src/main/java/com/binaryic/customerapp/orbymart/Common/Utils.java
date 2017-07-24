package com.binaryic.customerapp.orbymart.Common;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.binaryic.customerapp.orbymart.R;


/**
 * Created by HP on 05-Mar-17.
 */

public class Utils {
    public static Dialog msgDialog;
    public static ProgressDialog progressDialog;
    public static TextView btnYes, btnNo;

    public static void addFragment(int containerId, Fragment fragment, Activity context) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(containerId, fragment, fragment.getClass().getName()).commit();
    }

    public static void AddFragmentBack(int containerId, Fragment fragment, Activity context) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(containerId, fragment).addToBackStack(fragment.getClass().getName()).commit();
    }
    public static void ReplaceFragment(int containerId, Fragment fragment, Activity context) {
        try {
            FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
            if (fragmentManager.getBackStackEntryCount() > 0) {
                while (fragmentManager.popBackStackImmediate()) ;
            }
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(containerId, fragment).addToBackStack(fragment.getClass().getName()).commit();
        } catch (Exception ex) {
        }
    }

    public static void AddFragmentBackHome(int containerId, Fragment fragment, Activity context) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            while (fragmentManager.popBackStackImmediate()) ;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(containerId, fragment).addToBackStack(fragment.getClass().getName()).commit();
    }

    public static boolean isConnectingToInternet(Context _context) {
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }


    public static void showProgress(final String title, Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(title);
        progressDialog.show();
    }

    public static void showMessageBox(String Title, String yesButton, String noButton, Boolean noShow, Context cont) {
        msgDialog = new Dialog(cont);
        msgDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        msgDialog.setContentView(R.layout.fragment_dialog);
        WindowManager.LayoutParams wmlp = msgDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER | Gravity.CENTER;
        wmlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        msgDialog.setCanceledOnTouchOutside(false);
        TextView content = (TextView) msgDialog.findViewById(R.id.content);
        btnYes = (TextView) msgDialog.findViewById(R.id.btnDone);
        btnNo = (TextView) msgDialog.findViewById(R.id.btnCancel);
        content.setText(Title);
        btnYes.setText(yesButton);
        btnNo.setText(noButton);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msgDialog.dismiss();
            }
        });
        if (noShow) {
            btnNo.setVisibility(ViewStub.VISIBLE);
        } else {
            btnNo.setVisibility(ViewStub.GONE);
        }
        msgDialog.show();
    }

    public static void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }
    public static void rateUs(Activity context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }

    public static Bitmap uriToBitmap(Context context, String uri) {
        Bitmap bitmap = null;
        try {
            ContentResolver cr = context.getContentResolver();
            Uri bitmapUri = Uri.parse(uri);
            bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, bitmapUri);
        } catch (Exception e) {
        }
        return bitmap;
    }

}
