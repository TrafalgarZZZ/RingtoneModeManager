package company.tz.ringtonemodemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by xzh on 2015/10/4.
 */
public class LoginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                Intent login_intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivityForResult(login_intent, 0);
                finish();
            }
        };
        Handler handler =new Handler();
        handler.postDelayed(runnable,2500);



    }


}

