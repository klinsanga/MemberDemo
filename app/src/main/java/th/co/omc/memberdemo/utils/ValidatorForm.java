package th.co.omc.memberdemo.utils;

import android.content.Context;
import android.widget.EditText;

import th.co.omc.memberdemo.R;

/**
 * Created by teera-s on 11/8/2016 AD.
 */

public class ValidatorForm {

    public boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    public boolean isAllEmpty(Context contexts, EditText[] fields){
        for(int i = 0; i < fields.length; i++){
            EditText currentField = fields[i];
            if(currentField.getText().toString().length() <= 0){
                currentField.setError(contexts.getResources().getString(R.string.form_empty));
                return false;
            }
        }
        return true;
    }
}
