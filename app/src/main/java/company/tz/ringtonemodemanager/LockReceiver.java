package company.tz.ringtonemodemanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.widget.Toast;

/**
        * Created by xzh on 2015/10/6.
                */
        public class LockReceiver extends BroadcastReceiver {
            AudioManager Lock_AudioManager;
            static boolean flag=false;
            static int RingtoneMode;


            public void onReceive(Context context,Intent intent){
                if(intent.getAction()=="Disable_lockReceiver"){
                    flag=false;

                }
                if(intent.getAction()=="Enable_lockReceiver"){
                    flag=true;
                    Bundle Receiver_bundle=new Bundle();
                    Receiver_bundle=intent.getBundleExtra("bundle");
                    RingtoneMode=Receiver_bundle.getInt("RingtoneMode");

                }

                if(intent.getAction()=="android.media.RINGER_MODE_CHANGED"){
                   if(flag==true){
                        Lock_AudioManager=(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
                        Lock_AudioManager.setRingerMode(RingtoneMode);

                    }
                }





    }

}
