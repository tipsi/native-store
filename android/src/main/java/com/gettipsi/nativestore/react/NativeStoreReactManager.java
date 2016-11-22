package com.gettipsi.nativestore.react;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.widget.EditText;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.text.ReactTextView;

import org.xmlpull.v1.XmlPullParser;

/**
 * Created by dmitriy on 11/15/16
 */

public class NativeStoreReactManager extends SimpleViewManager<ReactTextView> {

    public static final String REACT_CLASS = "CreditCardForm";
    private static final String TAG = NativeStoreReactManager.class.getSimpleName();
    private static final String NUMBER = "number";
    private static final String EXP_MONTH = "expMonth";
    private static final String EXP_YEAR = "expYear";
    private static final String CCV = "cvc";

    private ThemedReactContext reactContext;
    private WritableMap currentParams;

    private String currentNumber;
    private int currentMonth;
    private int currentYear;
    private String currentCCV;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected ReactTextView createViewInstance(ThemedReactContext reactContext) {
        this.reactContext = reactContext;
        return new ReactTextView(reactContext);
    }

//    @ReactProp(name = "cardNumber")
//    public void setCardNumber(ReactTextView view, String cardNumber) {
//        view.setCardNumber(cardNumber, true);
//    }
//
//    @ReactProp(name = "setEnabled")
//    public void setEnabled(CreditCardForm view, boolean enabled) {
//        view.setEnabled(enabled);
//    }
//
//    @ReactProp(name = "backgroundColor")
//    public void setBackgroundColor(CreditCardForm view, int color) {
//        Log.d("TAG", "setBackgroundColor: "+color);
//        view.setBackgroundColor(color);
//    }
//
//    @ReactProp(name = "expDate")
//    public void setExpDate(CreditCardForm view, String expDate) {
//        view.setExpDate(expDate, true);
//    }
//
//
//    private void setListeners(final CreditCardForm view){
//
//        final EditText ccNumberEdit = (EditText) view.findViewById(R.id.cc_card);
//        final EditText ccExpEdit = (EditText) view.findViewById(R.id.cc_exp);
//        final EditText ccCcvEdit = (EditText) view.findViewById(R.id.cc_ccv);
//
//        ccNumberEdit.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Log.d(TAG, "onTextChanged: cardNumber = "+charSequence);
//                currentNumber = charSequence.toString();
//                postEvent(view);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        });
//
//        ccExpEdit.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Log.d(TAG, "onTextChanged: EXP_YEAR = "+charSequence);
//                try {
//                    currentMonth = view.getCreditCard().getExpMonth();
//                }catch (Exception e){
//                    if(charSequence.length() == 0)
//                        currentMonth = 0;
//                }
//                try {
//                    currentYear = view.getCreditCard().getExpYear();
//                }catch (Exception e){
//                    currentYear = 0;
//                }
//                postEvent(view);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        });
//
//        ccCcvEdit.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Log.d(TAG, "onTextChanged: CCV = "+charSequence);
//                currentCCV = charSequence.toString();
//                postEvent(view);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        });
//    }
//
//    private void postEvent(CreditCardForm view){
//        currentParams = Arguments.createMap();
//        currentParams.putString(NUMBER, currentNumber);
//        currentParams.putInt(EXP_MONTH, currentMonth);
//        currentParams.putInt(EXP_YEAR, currentYear);
//        currentParams.putString(CCV, currentCCV);
//        reactContext.getNativeModule(UIManagerModule.class)
//                .getEventDispatcher().dispatchEvent(
//                new UpdateDataEvent(view.getId(), currentParams, view.isCreditCardValid()));
//    }
//
//    private void updateView(CreditCardForm view){
//
//    }
}
