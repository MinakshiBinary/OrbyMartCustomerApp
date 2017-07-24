package com.binaryic.customerapp.orbymart.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.binaryic.customerapp.orbymart.Activity.SavedAddressActivity;
import com.binaryic.customerapp.orbymart.Adapter.AddressAdapter;
import com.binaryic.customerapp.orbymart.Common.Utils;
import com.binaryic.customerapp.orbymart.Controller.CallBackResult;
import com.binaryic.customerapp.orbymart.Controller.CustomerController;
import com.binaryic.customerapp.orbymart.Model.Address;
import com.binaryic.customerapp.orbymart.R;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Asd on 10-10-2016.
 */
public class FragmentAddresses extends Fragment implements AddressAdapter.ClickListener, FragmentAddNewAddress.AddAddressCloseListener, FragmentEditAddress.EditAddressCloseListener {
    RecyclerView recyclerAddress;
    RelativeLayout btnAddAddress;
    AddressAdapter adapter;
    ArrayList<Address> list;
    AddressesCloseListener addressesCloseListener;

    public void setAddressesCloseListener(AddressesCloseListener addressesCloseListener) {
        this.addressesCloseListener = addressesCloseListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addresses, container, false);
        try {
            Init(view);
        } catch (Exception e) {
        }
        return view;
    }

    private void Init(View view) {
        try {
            recyclerAddress = (RecyclerView) view.findViewById(R.id.recyclerAddress);
            btnAddAddress = (RelativeLayout) view.findViewById(R.id.btnAddAddress);
            btnAddAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentAddNewAddress fragmentAddNewAddress = new FragmentAddNewAddress();
                    fragmentAddNewAddress.setAddAddressCloseListener(FragmentAddresses.this);
                    if (getActivity() instanceof SavedAddressActivity) {
                        Utils.AddFragmentBack(((SavedAddressActivity) getActivity()).layMain.getId(), fragmentAddNewAddress, getActivity());
                    } else
                        Utils.AddFragmentBack(FragmentDelivery.layAddress.getId(), fragmentAddNewAddress, getActivity());
                }
            });
        } catch (Exception ex) {
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        GetAddresses();
    }

    private void SetUpRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerAddress.setLayoutManager(layoutManager);
        adapter = new AddressAdapter(getActivity(), list);
        adapter.setClickListener(this);
        recyclerAddress.setAdapter(adapter);

    }

    @Override
    public void ItemClicked(View view, final int position) {
        try {
            if (view instanceof AppCompatCheckBox) {
                UpdateAdd(position);
            } else if (view instanceof LinearLayout) {
                if (view.getId() == R.id.btnRemove) {
                    RemoveAdd(position);
                } else if (view.getId() == R.id.btnEdit) {
                    FragmentEditAddress fragmentEditAddress = new FragmentEditAddress();
                    Bundle bundle = new Bundle();
                    bundle.putString("address", new Gson().toJson(list.get(position)));
                    fragmentEditAddress.setArguments(bundle);
                    fragmentEditAddress.setEditAddressCloseListener(FragmentAddresses.this);
                    if (getActivity() instanceof SavedAddressActivity) {
                        Utils.AddFragmentBack(((SavedAddressActivity) getActivity()).layMain.getId(), fragmentEditAddress, getActivity());
                    } else
                        Utils.AddFragmentBack(FragmentDelivery.layAddress.getId(), fragmentEditAddress, getActivity());
                }
            }
        } catch (Exception ex) {
        }
    }

    private void GetAddresses() {
        list = CustomerController.GetAddresses(getActivity());
        if (list != null) {
            SetUpRecyclerView();
        }
    }

    private void UpdateAdd(final int position) {
        try {
            if (!list.get(position).getDefaultAddress()) {
                UpdateStatusAddress(list.get(position));
            }
        } catch (Exception ex) {
        }
    }

    private void RemoveAdd(final int position) {
        try {
            if (!list.get(position).getDefaultAddress()) {
                RemoveAddress(list.get(position));
            } else {
                Utils.showMessageBox("You can't remove default address.", "OK", "", false, getActivity());
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void AddAddressClose() {
        getActivity().onBackPressed();
        getAddressApi();
        if (addressesCloseListener != null)
            addressesCloseListener.AddressesClose(false);
    }

    @Override
    public void EditAddressClose() {
        getActivity().onBackPressed();
        getAddressApi();
        if (addressesCloseListener != null)
            addressesCloseListener.AddressesClose(false);
    }

    public interface AddressesCloseListener {
        public void AddressesClose(boolean isClose);
    }

    private void getAddressApi() {
        if (Utils.isConnectingToInternet(getActivity())) {
            Utils.showProgress("Loading...", getActivity());
            CustomerController customerController = new CustomerController();
            customerController.getAddress(getActivity(), new CallBackResult<String>() {
                @Override
                public void onSuccess(String s) {
                    Utils.progressDialog.dismiss();
                    GetAddresses();
                }

                @Override
                public void onError(String error) {
                    Utils.progressDialog.dismiss();
                    GetAddresses();
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please check internet.", Toast.LENGTH_SHORT).show();
            GetAddresses();
        }
    }

    private void RemoveAddress(Address address) {
        try {
            if (Utils.isConnectingToInternet(getActivity())) {
                Utils.showProgress("Remove Address...", getActivity());
                CustomerController customerController = new CustomerController();
                customerController.removeAddress(getActivity(), address.getId(), new CallBackResult<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Utils.progressDialog.dismiss();
                        getAddressApi();
                    }

                    @Override
                    public void onError(String error) {
                        Utils.progressDialog.dismiss();
                        Utils.showMessageBox(error, "OK", "", false, getActivity());
                    }
                });
            } else {
                Toast.makeText(getActivity(), "Please check internet.", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {
        }
    }

    private void UpdateStatusAddress(Address address) {
        try {
            if (Utils.isConnectingToInternet(getActivity())) {
                Utils.showProgress("Set Default Address...", getActivity());
                CustomerController customerController = new CustomerController();
                customerController.setDefaultAddress(getActivity(), address.getId(), new CallBackResult<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Utils.progressDialog.dismiss();
                        getAddressApi();
                    }

                    @Override
                    public void onError(String error) {
                        Utils.progressDialog.dismiss();
                        Utils.showMessageBox(error, "OK", "", false, getActivity());
                    }
                });
            } else {
                Toast.makeText(getActivity(), "Please check internet.", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {
        }
    }
}
