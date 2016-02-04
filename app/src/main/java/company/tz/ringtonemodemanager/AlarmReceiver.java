package company.tz.ringtonemodemanager;

/**
 * Created by xzh on 2015/10/4.
 */


        import android.widget.*;
        import android.content.*;
        import android.media.*;


public class AlarmReceiver extends BroadcastReceiver{
    AudioManager audioManager;
    public void onReceive(Context context,Intent intent){
        audioManager=(AudioManager)context.getSystemService(context.AUDIO_SERVICE);
        if(intent.getAction()=="normal"){
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            Toast.makeText(context,"情景模式已更改为标准",Toast.LENGTH_LONG).show();
        }
        else{
            if(intent.getAction()=="silent"){
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                Toast.makeText(context,"情景模式已更改为静音",Toast.LENGTH_LONG).show();
            }
            else{
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                Toast.makeText(context,"情景模式已更改为振动",Toast.LENGTH_LONG).show();


            }
        }
    }





}




