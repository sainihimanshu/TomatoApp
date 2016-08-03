package toroshu.tomato.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.andreabaccega.formedittextvalidator.Validator;
import com.andreabaccega.widget.FormEditText;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;

import butterknife.BindView;
import butterknife.ButterKnife;
import toroshu.tomato.R;
import toroshu.tomato.core.Phone;

/*
    Plays the role of login + registration screen
*/
public class Login extends AppCompatActivity {

    @BindView(R.id.signButton)
    ButtonRectangle mSign;
    @BindView(R.id.registerButton)
    ButtonRectangle mRegister;
    @BindView(R.id.fypButton)
    ButtonFlat mfyp;

    @BindView(R.id.usernameField)

    FormEditText mUserName;
    @BindView(R.id.heroNumberField)
    FormEditText mPassword;

    @BindView(R.id.welcomeText)
    TextView mWelcomeMessage;

    String username, password;

    Context mContext;
    Phone myPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        mContext = getApplicationContext();
        //for saving data
        myPhone = new Phone(mContext);

        mUserName.setTypeface(myPhone.getTypeface());
        mPassword.setTypeface(myPhone.getTypeface());

        mWelcomeMessage.setTypeface(myPhone.getTypeface());


    }

    @Override
    protected void onResume() {
        super.onResume();


        if (!myPhone.isRegistered()) {
            //sign up
            mSign.setVisibility(View.GONE);
            mfyp.setVisibility(View.GONE);
            register();
        } else {
            //sign in
            mWelcomeMessage.setVisibility(View.VISIBLE);
            mWelcomeMessage.setText("Welcome, " + myPhone.getUsername());
            mUserName.setVisibility(View.GONE);
            mRegister.setVisibility(View.GONE);
            mPassword.requestFocus();
            mSign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (authenticate()) {
                        startActivity(new Intent(mContext, Status.class));
                        Login.this.finish();
                    } else {
                        //invalid details
                        Toast.makeText(Login.this,
                                "Invalid details. Try Again",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

    }

    private boolean authenticate() {
        // returns true if user entered correct password
        password = mPassword.getText().toString().trim();
        return (password.equals(myPhone.getPassword()));
    }


    public void recoverPassword(View v) {
        //redirects to recover password screen
        if (myPhone.isRegistered()) {
            startActivity(new Intent(mContext, RecoverPassword.class));
        }
    }

    public void register() {

        mRegister.setOnClickListener
                (new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         username = mUserName.getText().toString().trim();
                         password = mPassword.getText().toString().trim();

                         mUserName.addValidator(new Validator("Entered name too short ") {
                             @Override
                             public boolean isValid(EditText editText) {
                                 return username.length() >= 4;
                             }
                         });

                         mPassword.addValidator(new Validator("Entered password too short ") {
                             @Override
                             public boolean isValid(EditText editText) {
                                 return password.length() >= 6;
                             }
                         });


                         if (mUserName.testValidity() && mPassword.testValidity()) {
                             myPhone.setPassword(password);
                             myPhone.setUsername(username);
                             startActivity(new Intent(mContext, Register.class));
                         }
                     }
                 }

                );

    }


}
