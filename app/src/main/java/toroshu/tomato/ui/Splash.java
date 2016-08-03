package toroshu.tomato.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import toroshu.tomato.R;
import toroshu.tomato.core.Constants;
import toroshu.tomato.core.Phone;


public class Splash extends AppCompatActivity {

    @BindView(R.id.logo)
    ImageView logo; //logo

    Phone phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);
        phone = new Phone(this);

        if (phone.arePlayServicesInstalled() && arePlayServicesInstalled())
            showLogin();
    }

    @OnClick(R.id.logo)
    public void showLogin() {
        startActivity(new Intent(getBaseContext(), Login.class));
    }


    @OnClick(R.id.mid)
    public void gotomid() {
        Intent intent = new Intent(Intent.ACTION_VIEW)
                .setData(Uri.parse("http://www.madewithlove.org.in"));
        startActivity(intent);
    }


    private boolean arePlayServicesInstalled() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        Constants.PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            return false;
        }
        phone.playServicesAreInstalled();
        return true;
    }

}


