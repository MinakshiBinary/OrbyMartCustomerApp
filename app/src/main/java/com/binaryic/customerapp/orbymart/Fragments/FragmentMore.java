package com.binaryic.customerapp.orbymart.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.binaryic.customerapp.orbymart.Activity.ComingSoonActivity;
import com.binaryic.customerapp.orbymart.Common.Utils;
import com.binaryic.customerapp.orbymart.R;


/**
 * Created by User on 09-09-2016.
 */
public class FragmentMore extends Fragment implements View.OnClickListener {

    private RelativeLayout rl_About_Us;
    private RelativeLayout rl_Rate_Us;
    private RelativeLayout rl_Contact_Us;
    private RelativeLayout rl_Return_Policy;
    private RelativeLayout rl_FAQ;
    private RelativeLayout rl_Shiping_Policy;
    private RelativeLayout rl_Terms_Of_Use;
    private RelativeLayout rl_Customer_Service;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        rl_About_Us = (RelativeLayout) view.findViewById(R.id.rl_About_Us);
        rl_Rate_Us = (RelativeLayout) view.findViewById(R.id.rl_Rate_Us);
        rl_FAQ = (RelativeLayout) view.findViewById(R.id.rl_FAQ);
        rl_Contact_Us = (RelativeLayout) view.findViewById(R.id.rl_Contact_Us);
        rl_Return_Policy = (RelativeLayout) view.findViewById(R.id.rl_Return_Policy);
        rl_Shiping_Policy = (RelativeLayout) view.findViewById(R.id.rl_Shiping_Policy);
        rl_Customer_Service = (RelativeLayout) view.findViewById(R.id.rl_Customer_Service);
        rl_Terms_Of_Use = (RelativeLayout) view.findViewById(R.id.rl_Terms_Of_Use);
        rl_About_Us.setOnClickListener(this);
        rl_Rate_Us.setOnClickListener(this);
        rl_Contact_Us.setOnClickListener(this);
        rl_Return_Policy.setOnClickListener(this);
        rl_FAQ.setOnClickListener(this);
        rl_Shiping_Policy.setOnClickListener(this);
        rl_Terms_Of_Use.setOnClickListener(this);
        rl_Customer_Service.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_About_Us:
                Intent intent = new Intent(getActivity(), ComingSoonActivity.class);
                intent.putExtra("name","About Us");
                startActivity(intent);
                break;
            case R.id.rl_Contact_Us:
                Intent intentC = new Intent(getActivity(), ComingSoonActivity.class);
                intentC.putExtra("name","Contact Us");
                startActivity(intentC);
                break;
            case R.id.rl_Return_Policy:
                Intent intentR = new Intent(getActivity(), ComingSoonActivity.class);
                intentR.putExtra("name","Return Policy");
                startActivity(intentR);
                break;
            case R.id.rl_FAQ:
                Intent intentF = new Intent(getActivity(), ComingSoonActivity.class);
                intentF.putExtra("name","faq");
                startActivity(intentF);
                break;
            case R.id.rl_Shiping_Policy:
                Intent intentS = new Intent(getActivity(), ComingSoonActivity.class);
                intentS.putExtra("name","Shipping Plicy");
                startActivity(intentS);
                break;
            case R.id.rl_Terms_Of_Use:
                Intent intentT = new Intent(getActivity(), ComingSoonActivity.class);
                intentT.putExtra("name","Terms of use");
                startActivity(intentT);
                break;
            case R.id.rl_Customer_Service:
                Intent intentCS = new Intent(getActivity(), ComingSoonActivity.class);
                intentCS.putExtra("name","Customer Service");
                startActivity(intentCS);
                break;
            case R.id.rl_Rate_Us:
                Utils.rateUs(getActivity());
                break;

        }
    }
}
