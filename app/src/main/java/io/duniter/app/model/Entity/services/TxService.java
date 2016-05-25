package io.duniter.app.model.Entity.services;

import android.content.Context;
import android.util.Log;

import java.util.List;

import io.duniter.app.model.Entity.Tx;
import io.duniter.app.model.Entity.Wallet;
import io.duniter.app.model.Entity.json.TxJson;
import io.duniter.app.model.EntityWeb.TxWeb;
import io.duniter.app.model.services.WebService;
import io.duniter.app.technical.callback.CallbackTx;

/**
 * Created by naivalf27 on 02/05/16.
 */
public class TxService {

    public static void getListTx(Context context, final Wallet wallet, final CallbackTx callback){
        TxWeb txWeb = new TxWeb(context,wallet.getCurrency(),wallet.getPublicKey());
        txWeb.getData(new WebService.WebServiceInterface() {
            @Override
            public void getDataFinished(int code, String response) {
                if (code == 200){
                    TxJson txJson = TxJson.fromJson(response);
                    List<Tx> txList = TxJson.fromTx(txJson,wallet);
                    List<String[]> listSource = TxJson.fromTxGetSourcesPending(txJson);
                    if (callback!=null){
                        callback.methode(txList,listSource);
                    }
                }else{
                    Log.d("TX SERVICE", "error get Tx code:"+code);
                }
            }
        });
    }
}
