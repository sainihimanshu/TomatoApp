package toroshu.tomato.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import toroshu.tomato.R;
import toroshu.tomato.core.Phone;

/*
    Displays a recover password screen
*/

public class RecoverPassword extends Activity {

    @BindView(R.id.heroNumberField)
    FormEditText mguessNumberField;
    Phone myPhone;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);

        initView();

    }

    private void initView() {
        ButterKnife.bind(this);
        mContext = getApplicationContext();
        myPhone = new Phone(mContext);

        mguessNumberField.setTypeface(myPhone.getTypeface());
        mguessNumberField.setHint(getResources().getString(R.string.msg_guess_number) + " " +
                myPhone.getFSH().substring(0, 3)
                + "*******");
    }

    public void proceed(View v) {


        if (mguessNumberField.testValidity() &&
                myPhone.getFSH().equals(mguessNumberField.getText().toString())) {

            SmsManager manager = SmsManager.getDefault();
            manager.sendTextMessage(
                    myPhone.getFSH(),
                    null,
                    "Your password is: " + myPhone.getPassword() +
                            " . Sent via Tomato Safety App.", null, null);
            Toast.makeText(RecoverPassword.this,
                    "Password Sent", Toast.LENGTH_SHORT).show();
            this.finish();

        } else {
            Toast.makeText(RecoverPassword.this,
                    "Invalid Number. Try Again", Toast.LENGTH_SHORT).show();

        }
    }


}
