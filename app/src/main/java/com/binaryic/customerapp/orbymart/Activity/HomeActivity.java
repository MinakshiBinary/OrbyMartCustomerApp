package com.binaryic.customerapp.orbymart.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.binaryic.customerapp.orbymart.Common.Utils;
import com.binaryic.customerapp.orbymart.Controller.CartController;
import com.binaryic.customerapp.orbymart.Controller.ImageController;
import com.binaryic.customerapp.orbymart.Controller.WishListController;
import com.binaryic.customerapp.orbymart.Fragments.FragmentHome;
import com.binaryic.customerapp.orbymart.Fragments.FragmentHomeDrawer;
import com.binaryic.customerapp.orbymart.Fragments.FragmentMore;
import com.binaryic.customerapp.orbymart.Fragments.FragmentMyAccount;
import com.binaryic.customerapp.orbymart.Fragments.FragmentWishlist;
import com.binaryic.customerapp.orbymart.R;

import static com.binaryic.customerapp.orbymart.Common.Constants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE;
import static com.binaryic.customerapp.orbymart.Common.Constants.CAMERA_GALLARY_CODE;
import static com.binaryic.customerapp.orbymart.Common.Constants.CAMERA_PERMISSION_CODE;
import static com.binaryic.customerapp.orbymart.Common.Constants.PICK_PHOTO;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    public static DrawerLayout drawer;
    public static FrameLayout fl_main, fl_drawer;
    public ImageView btnHome, btnMyAccount, btnMore, imgFav;
    public TextView toolbarTitle, tvCount, tvCountWish;
    RelativeLayout btnCart, btnFav;
    ImageView btnSearch;
    FragmentHomeDrawer fragmentHomeDrawer;
    FragmentHome fragmentHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setSideMenu();
        addFragmentDrawer();
        addFragmentHome();
    }

    private void setSideMenu() {
        try {
            Toolbar toolbar = ((Toolbar) findViewById(R.id.toolbar));
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    if (fragmentHomeDrawer != null) {
                        fragmentHomeDrawer.getPhoto();
                        fragmentHomeDrawer.setUpRecyclerView();
                    }
                    //Call when drawer open so set user data.
                }
            };
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            if (Build.VERSION.SDK_INT >= 14)
                drawer.setFitsSystemWindows(true);
            Init();
        } catch (Exception ex) {
        }
    }

    private void Init() {
        try {
            fl_main = (FrameLayout) findViewById(R.id.fl_main);
            fl_drawer = (FrameLayout) findViewById(R.id.fl_drawer);
            btnHome = (ImageView) findViewById(R.id.btnHome);
            btnFav = (RelativeLayout) findViewById(R.id.btnFav);
            btnMyAccount = (ImageView) findViewById(R.id.btnMyAccount);
            imgFav = (ImageView) findViewById(R.id.imgFav);
            btnMore = (ImageView) findViewById(R.id.btnMore);
            toolbarTitle = (TextView) findViewById(R.id.toolbarTitle);
            tvCount = (TextView) findViewById(R.id.tvCount);
            tvCountWish = (TextView) findViewById(R.id.tvCountWish);
            btnCart = (RelativeLayout) findViewById(R.id.btnCart);
            btnSearch = (ImageView) findViewById(R.id.btnSearch);
            btnHome.setOnClickListener(this);
            btnFav.setOnClickListener(this);
            btnMyAccount.setOnClickListener(this);
            btnMore.setOnClickListener(this);
            btnCart.setOnClickListener(this);

        } catch (Exception ex) {
        }
    }

    private void addFragmentDrawer() {
        fragmentHomeDrawer = new FragmentHomeDrawer();
        Utils.addFragment(fl_drawer.getId(), fragmentHomeDrawer, HomeActivity.this);
    }

    private void addFragmentHome() {
        if (fragmentHome == null)
            fragmentHome = new FragmentHome();
        Utils.addFragment(fl_main.getId(), fragmentHome, HomeActivity.this);
        btnHome.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_black_24px));
        imgFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_home));
        btnMyAccount.setImageDrawable(getResources().getDrawable(R.drawable.ic_person));
        btnMore.setImageDrawable(getResources().getDrawable(R.drawable.ic_more_home));
        toolbarTitle.setText("Orby Mart");
    }

    public void HomeClicked() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() != 0) {
            onBackPressed();
            btnHome.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_press_24px));
            imgFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_home));
            btnMyAccount.setImageDrawable(getResources().getDrawable(R.drawable.ic_person));
            btnMore.setImageDrawable(getResources().getDrawable(R.drawable.ic_more_home));
            toolbarTitle.setText("Orby Mart");
        }
    }

    private void AddWishFragment() {
        try {
            FragmentWishlist fragment = new FragmentWishlist();
            Utils.AddFragmentBackHome(fl_main.getId(), fragment, this);
            btnHome.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_black_24px));
            imgFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_home_selected));
            btnMyAccount.setImageResource(R.drawable.ic_person);
            btnMore.setImageResource(R.drawable.ic_more_home);
            toolbarTitle.setText("Wishlist");
        } catch (Exception ex) {
        }
    }

    private void AddMyAccountFragment() {
        try {
            FragmentMyAccount fragment = new FragmentMyAccount();
            Utils.AddFragmentBackHome(fl_main.getId(), fragment, this);
            btnHome.setImageResource(R.drawable.ic_home_black_24px);
            imgFav.setImageResource(R.drawable.ic_favorite_home);
            btnMyAccount.setImageResource(R.drawable.ic_person_home_selected);
            btnMore.setImageResource(R.drawable.ic_more_home);
            toolbarTitle.setText("My Account");
        } catch (Exception ex) {
        }
    }

    private void AddMoreFragment() {
        try {
            FragmentMore fragment = new FragmentMore();
            Utils.AddFragmentBackHome(fl_main.getId(), fragment, this);
            imgFav.setImageResource(R.drawable.ic_favorite_home);
            btnMyAccount.setImageResource(R.drawable.ic_person);
            btnMore.setImageResource(R.drawable.ic_more_home_selected);
            toolbarTitle.setText("More");
        } catch (Exception ex) {
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnHome:
                HomeClicked();
                break;
            case R.id.btnFav:
                AddWishFragment();
                break;
            case R.id.btnMyAccount:
                AddMyAccountFragment();
                break;
            case R.id.btnMore:
                AddMoreFragment();
                break;
            case R.id.btnCart:
                startActivity(new Intent(this, CartActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("oncreate", "Resume");
        setCount();
    }

    private void setCount() {
        try {
            int count = CartController.GetCartCount(this);
            if (count == 0) {
                tvCount.setVisibility(View.GONE);
            } else {
                tvCount.setText(count + "");
                tvCount.setVisibility(View.VISIBLE);
            }
            int countWish = WishListController.GetWishCount(this);
            if (countWish == 0) {
                tvCountWish.setVisibility(View.GONE);
            } else {
                tvCountWish.setText(countWish + "");
                tvCountWish.setVisibility(View.VISIBLE);
            }
            Fragment f = getSupportFragmentManager().findFragmentById(fl_main.getId());
            if (f instanceof FragmentWishlist) {
                ((FragmentWishlist) f).ResetList();
            } else if (f instanceof FragmentMyAccount) {
                ((FragmentMyAccount) f).setData();
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Log.e("count", fragmentManager.getBackStackEntryCount() + "");
            if (fragmentManager.getBackStackEntryCount() == 0) {
                Utils.showMessageBox("Are you sure, Do you want to Exit ?", "YES", "NO", true, HomeActivity.this);
                Utils.btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.msgDialog.dismiss();
                        finishAffinity();
                    }
                });
                Utils.btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.msgDialog.dismiss();
                    }
                });
            } else {
                super.onBackPressed();
                if (fragmentManager.getBackStackEntryCount() == 0) {
                    imgFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_home));
                    btnMyAccount.setImageDrawable(getResources().getDrawable(R.drawable.ic_person));
                    btnMore.setImageDrawable(getResources().getDrawable(R.drawable.ic_more_home));
                    toolbarTitle.setText("Orby Mart");

                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == PICK_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null) {
                try {
                    Uri uri = data.getData();
                    try {
                        Intent i = new Intent(this, CaptureImageProfile.class);
                        i.putExtra("imageBit", uri.toString());
                        startActivityForResult(i, 101);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            } else if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
                Intent i = new Intent(this, CaptureImageProfile.class);
                i.putExtra("imageBit", ImageController.fileUri.toString());
                startActivityForResult(i, 101);
            } else if (requestCode == 101 && resultCode == RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getParcelableExtra("bitmap");
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.fl_main);
                if (f instanceof FragmentMyAccount) {
                    ((FragmentMyAccount) f).getUserPic(bitmap);
                }
            }
        } catch (Exception e) {
            Log.e(this.getClass().getName(), "onActivityResult " + e.getMessage());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length == 2
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                new ImageController().openCamera(HomeActivity.this);
            } else
                Toast.makeText(this, "Please accept permission for go ahead.", Toast.LENGTH_LONG).show();
        } else if (requestCode == CAMERA_GALLARY_CODE) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                new ImageController().openGallery(HomeActivity.this);
            } else
                Toast.makeText(this, "Please accept permission for go ahead.", Toast.LENGTH_LONG).show();
        }

    }

}
