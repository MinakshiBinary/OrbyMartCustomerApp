package com.binaryic.customerapp.orbymart.Controller;

import android.os.AsyncTask;

import com.binaryic.customerapp.orbymart.Common.MyApplication;
import com.payu.india.Extras.PayUChecksum;
import com.payu.india.Model.PaymentParams;
import com.payu.india.Model.PayuHashes;
import com.payu.india.Model.PostData;
import com.payu.india.Payu.PayuConstants;
import com.payu.india.Payu.PayuErrors;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Iterator;

/**
 * Created by Asd on 13-10-2016.
 */
public class PaymentController {

    private CloseListener closeListener;
    PaymentParams mPaymentParams;

    PayUChecksum checksum;
    public void setCloseListener(CloseListener closeListener) {
        this.closeListener = closeListener;
    }

    //Name and email is not mandatory.
    //Random generated txnId
    public void GetPaymentObject(String key, String amount, String productInfo, String name, String email, String txnId){

        mPaymentParams = new PaymentParams();
        mPaymentParams.setKey(key);
        //PayuHashes payuHashes = new PayuHashes();
        //payuHashes.setPaymentHash("MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCrJLhinKkKY8QRhq9ki54faWJ8M+r+y1NaAIEv5A8w6OnqfJdEiTHv+H/n7m3xe8Eq55Pe7WUTlc6LDiXUNhb/6OGmMmAIQ8mA6dkFMaSvzwfVCUbSdMvwXV8TZC5AWRHNJGQyZ3etwPcXEyBbAI6fTAccEPjKgdbea9z5A3KbuLJciJCGIkdN88bG2RzRN9CuJ0Nu+CDHpkCirs/UwZ+F/meTihvz5bVkSkQ4gKZitNArcNmSA4x/VjX7Y9VTlehF/FhNvQD2/PYkH4VuYOkH9JUHYpwX1FzXxpmpV4lhCjjDJ5YoXHoByD5nekQ4ZT6JHZwoJXuvGh4xe+lul3YxAgMBAAECggEBAKaCxI7VIzaDmJkAtJcbrA2dx/9VuBaMlp0jjo2c0xbgFe5ZqAc7gIWc/j0W4osnv+pRg7JKwKPMhjcO3clZkwkLeLftnTeNVHkDjG2cPkH7KQCh1PEgR+elwMIqH0HmJwazmHTd7Df2XbFqn3lK0ni0TNqGvLiymO6wfT8dlp1f/sakA0y7RzbHzrEYKJJ6Qx2rbCzM4pysseUrY4APRq3JT+7VSa2y5KzFBqWL0Vw4OiQL6aFni5Nv08JpZ6+s7brcJE2jiBJ2N6+F16ZUttaCgHhz+hvvWGPKJEHxHGH442dcHmSZIP6nwPdYNw5dLv9Rz6NHGGv9rptApiUh8J0CgYEA3ksA85qIhHuYiLX4ACno/4WGS+VtwllS2IDupGtmh62JtSTPYE65XHJkAt3G19xZDq4Ow4nnLHd+SEOv12rYQApXGADiFyQcl3I1wtThs7N0zzitI2cgoevPCyr3fRO9wfHbuJMBn+CRBmGa/Fs2hahlofCyoiK5S6tHY9d0Q2MCgYEAxRgv7rlpl0KZmG/eIVYalMqbQBng9sG90Yv3hA7ahJ/FHKY30GsORDzSc8lwHDZeK9vL61URLocDRbXiOgX/rQ9JJ0nZQaMO5tDq9sOadu6e2ATiOhhEjJTj86sAwwvdAHp06RlkzXOgOacPdBj0c/Th0QcYOjfN+u4c6KdzFlsCgYA2Ws6zwlCpXHNy0lP+7kCL4oFxI3uQLzlDuVshFyXt/TAT/Bk9KmLGyS3NSZ9qfvXD0inIBHtjcyCM2QgxO23/UsfzFZwzHmBjY8jjiBxcTYA9f1VOt9PR5BAvYeU8T0xyFQqt+jxGdKnhjpJQLJmLWx7m1y1G2gjHCO2e6zsBkwKBgQCTkORybEfuhGLTiw5ZD0qflIJgn/+bQLuKJK2RJj3dhnHiXWD0nvLzjEZVgsB5nwsq80neTLasi1Gb4+4ZW1xuij9PNy9+wULq9gi18QW5fjdXrXBj5r+MMf3p9mJ/pmY1cXoO/IBIgwW3pKkhV33PQ82+3vz4ucdy1My4NIcqeQKBgQDMbxGcbbrpi+FZhALI/MfNu5C9aBYcNzP1Iv63l1V1kfXuP5K9ONk+By1HzvpF/hnPSVVOwizUQEAWvtjbtqHeaeBILWp/zFqKEnRNEa0bCr6PpCo+EhUvPrlJiC9Vd0fKDlx6KicafKXMTjkTFXOolx1t+YZMpQqv6TWLdg8Gsw==");
        //mPaymentParams.setHash("MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCrJLhinKkKY8QRhq9ki54faWJ8M+r+y1NaAIEv5A8w6OnqfJdEiTHv+H/n7m3xe8Eq55Pe7WUTlc6LDiXUNhb/6OGmMmAIQ8mA6dkFMaSvzwfVCUbSdMvwXV8TZC5AWRHNJGQyZ3etwPcXEyBbAI6fTAccEPjKgdbea9z5A3KbuLJciJCGIkdN88bG2RzRN9CuJ0Nu+CDHpkCirs/UwZ+F/meTihvz5bVkSkQ4gKZitNArcNmSA4x/VjX7Y9VTlehF/FhNvQD2/PYkH4VuYOkH9JUHYpwX1FzXxpmpV4lhCjjDJ5YoXHoByD5nekQ4ZT6JHZwoJXuvGh4xe+lul3YxAgMBAAECggEBAKaCxI7VIzaDmJkAtJcbrA2dx/9VuBaMlp0jjo2c0xbgFe5ZqAc7gIWc/j0W4osnv+pRg7JKwKPMhjcO3clZkwkLeLftnTeNVHkDjG2cPkH7KQCh1PEgR+elwMIqH0HmJwazmHTd7Df2XbFqn3lK0ni0TNqGvLiymO6wfT8dlp1f/sakA0y7RzbHzrEYKJJ6Qx2rbCzM4pysseUrY4APRq3JT+7VSa2y5KzFBqWL0Vw4OiQL6aFni5Nv08JpZ6+s7brcJE2jiBJ2N6+F16ZUttaCgHhz+hvvWGPKJEHxHGH442dcHmSZIP6nwPdYNw5dLv9Rz6NHGGv9rptApiUh8J0CgYEA3ksA85qIhHuYiLX4ACno/4WGS+VtwllS2IDupGtmh62JtSTPYE65XHJkAt3G19xZDq4Ow4nnLHd+SEOv12rYQApXGADiFyQcl3I1wtThs7N0zzitI2cgoevPCyr3fRO9wfHbuJMBn+CRBmGa/Fs2hahlofCyoiK5S6tHY9d0Q2MCgYEAxRgv7rlpl0KZmG/eIVYalMqbQBng9sG90Yv3hA7ahJ/FHKY30GsORDzSc8lwHDZeK9vL61URLocDRbXiOgX/rQ9JJ0nZQaMO5tDq9sOadu6e2ATiOhhEjJTj86sAwwvdAHp06RlkzXOgOacPdBj0c/Th0QcYOjfN+u4c6KdzFlsCgYA2Ws6zwlCpXHNy0lP+7kCL4oFxI3uQLzlDuVshFyXt/TAT/Bk9KmLGyS3NSZ9qfvXD0inIBHtjcyCM2QgxO23/UsfzFZwzHmBjY8jjiBxcTYA9f1VOt9PR5BAvYeU8T0xyFQqt+jxGdKnhjpJQLJmLWx7m1y1G2gjHCO2e6zsBkwKBgQCTkORybEfuhGLTiw5ZD0qflIJgn/+bQLuKJK2RJj3dhnHiXWD0nvLzjEZVgsB5nwsq80neTLasi1Gb4+4ZW1xuij9PNy9+wULq9gi18QW5fjdXrXBj5r+MMf3p9mJ/pmY1cXoO/IBIgwW3pKkhV33PQ82+3vz4ucdy1My4NIcqeQKBgQDMbxGcbbrpi+FZhALI/MfNu5C9aBYcNzP1Iv63l1V1kfXuP5K9ONk+By1HzvpF/hnPSVVOwizUQEAWvtjbtqHeaeBILWp/zFqKEnRNEa0bCr6PpCo+EhUvPrlJiC9Vd0fKDlx6KicafKXMTjkTFXOolx1t+YZMpQqv6TWLdg8Gsw==");
        //mPaymentParams.setHash(payuHashes);
        mPaymentParams.setAmount(amount);
        mPaymentParams.setProductInfo(productInfo);
        mPaymentParams.setFirstName(name);
        mPaymentParams.setEmail(email);
        mPaymentParams.setTxnId(txnId);
        mPaymentParams.setSurl(MyApplication.SURL);
        //mPaymentParams.setSurl("https://www.payu.herokuapp.com/success");
        mPaymentParams.setFurl(MyApplication.FURL);
        mPaymentParams.setUdf1("");
        mPaymentParams.setUdf2("");
        mPaymentParams.setUdf3("");
        mPaymentParams.setUdf4("");
        mPaymentParams.setUdf5("");

        generateHashFromSDK(mPaymentParams,MyApplication.SALT);
    }
    protected String concatParams(String key, String value) {
        return key + "=" + value + "&";
    }
    public void generateHashFromServer(PaymentParams mPaymentParams){
        //nextButton.setEnabled(false); // lets not allow the user to click the button again and again.
        // lets create the post params

        StringBuffer postParamsBuffer = new StringBuffer();
        postParamsBuffer.append(concatParams(PayuConstants.KEY, mPaymentParams.getKey()));
        postParamsBuffer.append(concatParams(PayuConstants.AMOUNT, mPaymentParams.getAmount()));
        postParamsBuffer.append(concatParams(PayuConstants.TXNID, mPaymentParams.getTxnId()));
        postParamsBuffer.append(concatParams(PayuConstants.EMAIL, null == mPaymentParams.getEmail() ? "" : mPaymentParams.getEmail()));
        postParamsBuffer.append(concatParams(PayuConstants.PRODUCT_INFO, mPaymentParams.getProductInfo()));
        postParamsBuffer.append(concatParams(PayuConstants.FIRST_NAME, null == mPaymentParams.getFirstName() ? "" : mPaymentParams.getFirstName()));
        postParamsBuffer.append(concatParams(PayuConstants.UDF1, mPaymentParams.getUdf1() == null ? "" : mPaymentParams.getUdf1()));
        postParamsBuffer.append(concatParams(PayuConstants.UDF2, mPaymentParams.getUdf2() == null ? "" : mPaymentParams.getUdf2()));
        postParamsBuffer.append(concatParams(PayuConstants.UDF3, mPaymentParams.getUdf3() == null ? "" : mPaymentParams.getUdf3()));
        postParamsBuffer.append(concatParams(PayuConstants.UDF4, mPaymentParams.getUdf4() == null ? "" : mPaymentParams.getUdf4()));
        postParamsBuffer.append(concatParams(PayuConstants.UDF5, mPaymentParams.getUdf5() == null ? "" : mPaymentParams.getUdf5()));
        postParamsBuffer.append(concatParams(PayuConstants.USER_CREDENTIALS, mPaymentParams.getUserCredentials() == null ? PayuConstants.DEFAULT : mPaymentParams.getUserCredentials()));

        // for offer_key
        if(null != mPaymentParams.getOfferKey())
            postParamsBuffer.append(concatParams(PayuConstants.OFFER_KEY, mPaymentParams.getOfferKey()));


        String postParams = postParamsBuffer.charAt(postParamsBuffer.length() - 1) == '&' ? postParamsBuffer.substring(0, postParamsBuffer.length() - 1).toString() : postParamsBuffer.toString();
        // make api call
        GetHashesFromServerTask getHashesFromServerTask = new GetHashesFromServerTask();
        getHashesFromServerTask.execute(postParams);
    }
    class GetHashesFromServerTask extends AsyncTask<String, String, PayuHashes> {

        @Override
        protected PayuHashes doInBackground(String ... postParams) {
            PayuHashes payuHashes = new PayuHashes();
            try {
//                URL url = new URL(PayuConstants.MOBILE_TEST_FETCH_DATA_URL);
//                        URL url = new URL("http://10.100.81.49:80/merchant/postservice?form=2");;

                URL url = new URL("https://payu.herokuapp.com/get_hash");

                // get the payuConfig first
                String postParam = postParams[0];

                byte[] postParamsByte = postParam.getBytes("UTF-8");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", String.valueOf(postParamsByte.length));
                conn.setDoOutput(true);
                conn.getOutputStream().write(postParamsByte);

                InputStream responseInputStream = conn.getInputStream();
                StringBuffer responseStringBuffer = new StringBuffer();
                byte[] byteContainer = new byte[1024];
                for (int i; (i = responseInputStream.read(byteContainer)) != -1; ) {
                    responseStringBuffer.append(new String(byteContainer, 0, i));
                }

                JSONObject response = new JSONObject(responseStringBuffer.toString());

                Iterator<String> payuHashIterator = response.keys();
                while(payuHashIterator.hasNext()){
                    String key = payuHashIterator.next();
                    switch (key){
                        case "payment_hash":
                            payuHashes.setPaymentHash(response.getString(key));
                            break;
                        case "get_merchant_ibibo_codes_hash": //
                            payuHashes.setMerchantIbiboCodesHash(response.getString(key));
                            break;
                        case "vas_for_mobile_sdk_hash":
                            payuHashes.setVasForMobileSdkHash(response.getString(key));
                            break;
                        case "payment_related_details_for_mobile_sdk_hash":
                            payuHashes.setPaymentRelatedDetailsForMobileSdkHash(response.getString(key));
                            break;
                        case "delete_user_card_hash":
                            payuHashes.setDeleteCardHash(response.getString(key));
                            break;
                        case "get_user_cards_hash":
                            payuHashes.setStoredCardsHash(response.getString(key));
                            break;
                        case "edit_user_card_hash":
                            payuHashes.setEditCardHash(response.getString(key));
                            break;
                        case "save_user_card_hash":
                            payuHashes.setSaveCardHash(response.getString(key));
                            break;
                        case "check_offer_status_hash":
                            payuHashes.setCheckOfferStatusHash(response.getString(key));
                            break;
                        case "check_isDomestic_hash":
                            payuHashes.setCheckIsDomesticHash(response.getString(key));
                            break;
                        default:
                            break;
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return payuHashes;
        }

        @Override
        protected void onPostExecute(PayuHashes payuHashes) {
            super.onPostExecute(payuHashes);
            if(closeListener != null)
                closeListener.Close(payuHashes,mPaymentParams);
            //nextButton.setEnabled(true);
            //launchSdkUI(payuHashes);
        }
    }
    public interface CloseListener {
        public void Close(PayuHashes payuHashes, PaymentParams mPaymentParams);
    }
    public void generateHashFromSDK(PaymentParams mPaymentParams, String salt){
        PayuHashes payuHashes = new PayuHashes();
        PostData postData = new PostData();

        // payment Hash;
        checksum = null;
        checksum = new PayUChecksum();
        checksum.setAmount(mPaymentParams.getAmount());
        checksum.setKey(mPaymentParams.getKey());
        checksum.setTxnid(mPaymentParams.getTxnId());
        checksum.setEmail(mPaymentParams.getEmail());
        checksum.setSalt(salt);
        checksum.setProductinfo(mPaymentParams.getProductInfo());
        checksum.setFirstname(mPaymentParams.getFirstName());
        checksum.setUdf1(mPaymentParams.getUdf1());
        checksum.setUdf2(mPaymentParams.getUdf2());
        checksum.setUdf3(mPaymentParams.getUdf3());
        checksum.setUdf4(mPaymentParams.getUdf4());
        checksum.setUdf5(mPaymentParams.getUdf5());

        postData = checksum.getHash();
        if(postData.getCode() == PayuErrors.NO_ERROR){
            payuHashes.setPaymentHash(postData.getResult());
        }

        // checksum for payemnt related details
        // var1 should be either user credentials or default
        String var1 = null;
        var1 = var1 == null ? PayuConstants.DEFAULT : var1 ;
        String key = mPaymentParams.getKey();
        if((postData = calculateHash(key, PayuConstants.PAYMENT_RELATED_DETAILS_FOR_MOBILE_SDK, var1, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR) // Assign post data first then check for success
            payuHashes.setPaymentRelatedDetailsForMobileSdkHash(postData.getResult());
        //vas
        if((postData = calculateHash(key, PayuConstants.VAS_FOR_MOBILE_SDK, PayuConstants.DEFAULT, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR)
            payuHashes.setVasForMobileSdkHash(postData.getResult());

        // getIbibocodes
        if((postData = calculateHash(key, PayuConstants.GET_MERCHANT_IBIBO_CODES, PayuConstants.DEFAULT, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR)
            payuHashes.setMerchantIbiboCodesHash(postData.getResult());

        if(!var1.contentEquals(PayuConstants.DEFAULT)){
            // get user card
            if((postData = calculateHash(key, PayuConstants.GET_USER_CARDS, var1, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR) // todo rename storedc ard
                payuHashes.setStoredCardsHash(postData.getResult());
            // save user card
            if((postData = calculateHash(key, PayuConstants.SAVE_USER_CARD, var1, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR)
                payuHashes.setSaveCardHash(postData.getResult());
            // delete user card
            if((postData = calculateHash(key, PayuConstants.DELETE_USER_CARD, var1, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR)
                payuHashes.setDeleteCardHash(postData.getResult());
            // edit user card
            if((postData = calculateHash(key, PayuConstants.EDIT_USER_CARD, var1, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR)
                payuHashes.setEditCardHash(postData.getResult());
        }

        if(mPaymentParams.getOfferKey() != null ){
            postData = calculateHash(key, PayuConstants.OFFER_KEY, mPaymentParams.getOfferKey(), salt);
            if(postData.getCode() == PayuErrors.NO_ERROR){
                payuHashes.setCheckOfferStatusHash(postData.getResult());
            }
        }

        if(mPaymentParams.getOfferKey() != null && (postData = calculateHash(key, PayuConstants.CHECK_OFFER_STATUS, mPaymentParams.getOfferKey(), salt)) != null && postData.getCode() == PayuErrors.NO_ERROR ){
            payuHashes.setCheckOfferStatusHash(postData.getResult());
        }

        // we have generated all the hases now lest launch sdk's ui
        if(closeListener != null)
            closeListener.Close(payuHashes,mPaymentParams);
    }
    private PostData calculateHash(String key, String command, String var1, String salt) {
        checksum = null;
        checksum = new PayUChecksum();
        checksum.setKey(key);
        checksum.setCommand(command);
        checksum.setVar1(var1);
        checksum.setSalt(salt);
        return checksum.getHash();
    }
}
