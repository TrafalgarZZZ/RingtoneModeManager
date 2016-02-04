package company.tz.ringtonemodemanager;

/**
 * Created by xzh on 2015/10/4.
 */


        import android.app.*;
        import android.media.AudioManager;
        import android.os.*;
        import android.provider.Settings;
        import android.view.*;
        import android.widget.*;
        import android.widget.TimePicker.*;
        import java.util.*;
        import android.content.*;
        import android.graphics.*;

public class NewAlarmActivity extends Activity
{

    AlarmManager am;
    AudioManager audioManager;
    Calendar cal1,cal2;
    PendingIntent pIntent1,pIntent2,lockReceiver_enable_pIntent,lockReceiver_disable_pIntent;
    Button btn1;
    TimePicker timepicker;
    RadioGroup radiogroup;
    RadioButton r1,r2,r3;
    EditText timeEdit;
    Switch lock_switch;
    Intent intent_start,intent_finish,lockReceiver_enable_intent,lockReceiver_disable_intent;
    boolean flag,forlong_flag;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newalarm);

        btn1=(Button)findViewById(R.id.btn1);
        timepicker=(TimePicker)findViewById(R.id.TimePicker);
        radiogroup=(RadioGroup)findViewById(R.id.radiogroup);
        r1=(RadioButton)findViewById(R.id.RButton1);
        r2=(RadioButton)findViewById(R.id.RButton2);
        r3=(RadioButton)findViewById(R.id.RButton3);
        timeEdit=(EditText)findViewById(R.id.timeEdit);
        lock_switch=(Switch)findViewById(R.id.lock_switch);
        am=(AlarmManager)getSystemService(ALARM_SERVICE);
        audioManager=(AudioManager)getSystemService(AUDIO_SERVICE);
        //获取日历实例
        cal1=Calendar.getInstance();
        cal2=Calendar.getInstance();

        forlong_flag=false;

        intent_start=new Intent();
        intent_start.setAction("normal");   //默认为normal
        intent_finish=new Intent();

        long time=System.currentTimeMillis();
        cal1.setTimeInMillis(time);              //显示为当前系统时间



        timepicker.setIs24HourView(true);       //设定TimePicker为24小时制
        timepicker.setCurrentHour(cal1.get(Calendar.HOUR_OF_DAY));
        timepicker.setCurrentMinute(cal1.get(Calendar.MINUTE));

        radiogroup.check(R.id.RButton1);




        //获取一个AlarmReceiver的PendingIntent



        //设定Alarm_Silent
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

               switch(audioManager.getRingerMode()){
                    case AudioManager.RINGER_MODE_NORMAL:intent_finish.setAction("normal");break;
                    case AudioManager.RINGER_MODE_SILENT:intent_finish.setAction("silent");break;
                    case AudioManager.RINGER_MODE_VIBRATE:intent_finish.setAction("vibrate");break;
                    default:break;
                }

                //重复间隔一天
                //am.setInexactRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pIntent);

                flag = true;

                /*向MainActivity的返回值开始*/
                Intent backToMainIntent = new Intent();
                Bundle bundle = new Bundle();
                Bundle bundle_finish = new Bundle();

                if (timepicker.getCurrentHour() < 10)
                    bundle.putString("hour", "0" + String.valueOf(timepicker.getCurrentHour()));
                else
                    bundle.putString("hour", String.valueOf(timepicker.getCurrentHour()));
                if (timepicker.getCurrentMinute() < 10)
                    bundle.putString("minute", "0" + String.valueOf(timepicker.getCurrentMinute()));
                else
                    bundle.putString("minute", String.valueOf(timepicker.getCurrentMinute()));

                if(timeEdit.getText().toString().equals("")){
                    timeEdit.setText("0");
                }

                if(Integer.parseInt(timeEdit.getText().toString())!=0){
                    forlong_flag=true;
                    bundle.putBoolean("isforLong",forlong_flag);
                    bundle.putBoolean("isLocked",(lock_switch.isChecked()));
                    bundle.putInt("forLong",Integer.parseInt(timeEdit.getText().toString()));
                }

                bundle.putString("RingtoneMode", intent_start.getAction());                       //获取所选情景模式

                bundle.putBoolean("if_have_data", flag);
                backToMainIntent.putExtra("bundle", bundle);
                /*向MainAcitivity的返回值完成*/


                pIntent1 = PendingIntent.getBroadcast(NewAlarmActivity.this, 0, intent_start, PendingIntent.FLAG_ONE_SHOT);
                cal1.set(Calendar.HOUR_OF_DAY, timepicker.getCurrentHour());
                cal1.set(Calendar.MINUTE, timepicker.getCurrentMinute());
                cal1.set(Calendar.SECOND,0);

                am.set(AlarmManager.RTC_WAKEUP, cal1.getTimeInMillis(), pIntent1);

                //if时间段，进行恢复情景模式处理
                if(Integer.parseInt(timeEdit.getText().toString())!=0) {
                    cal2.set(Calendar.HOUR_OF_DAY, timepicker.getCurrentHour());
                    cal2.set(Calendar.MINUTE, timepicker.getCurrentMinute());
                    cal2.add(Calendar.MINUTE, Integer.parseInt(timeEdit.getText().toString()));
                    pIntent2 = PendingIntent.getBroadcast(NewAlarmActivity.this, 1, intent_finish, PendingIntent.FLAG_ONE_SHOT);
                    cal2.set(Calendar.SECOND, 0);
                    am.set(AlarmManager.RTC_WAKEUP, cal2.getTimeInMillis(), pIntent2);

                    //锁定模式
                    if(lock_switch.isChecked()){
                        Bundle enable_bundle=new Bundle();
                        switch (intent_start.getAction()){
                            case "normal":enable_bundle.putInt("RingtoneMode",AudioManager.RINGER_MODE_NORMAL);break;
                            case "vibrate":enable_bundle.putInt("RingtoneMode",AudioManager.RINGER_MODE_VIBRATE);break;
                            case "silent":enable_bundle.putInt("RingtoneMode",AudioManager.RINGER_MODE_SILENT);break;

                        }

                        lockReceiver_enable_intent=new Intent();
                        lockReceiver_enable_intent.setAction("Enable_lockReceiver");
                        lockReceiver_enable_intent.putExtra("bundle",enable_bundle);
                        lockReceiver_enable_pIntent=PendingIntent.getBroadcast(NewAlarmActivity.this,30,lockReceiver_enable_intent,PendingIntent.FLAG_ONE_SHOT) ;
                        am.set(AlarmManager.RTC_WAKEUP, cal1.getTimeInMillis(), lockReceiver_enable_pIntent);

                        lockReceiver_disable_intent=new Intent();
                        lockReceiver_disable_intent.setAction("Disable_lockReceiver");
                        lockReceiver_disable_pIntent=PendingIntent.getBroadcast(NewAlarmActivity.this,40,lockReceiver_disable_intent,PendingIntent.FLAG_ONE_SHOT) ;
                        am.set(AlarmManager.RTC_WAKEUP, cal2.getTimeInMillis(), lockReceiver_disable_pIntent);
                    }


                }
                setResult(0, backToMainIntent);
                finish();


            }


        });
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup rg, int checkedId) {
                if (checkedId == R.id.RButton1) {
                    intent_start.setAction("normal");
                    r1.setTextColor(Color.parseColor("#0088FC"));
                    r2.setTextColor(Color.parseColor("#FFFFFF"));
                    r3.setTextColor(Color.parseColor("#FFFFFF"));

                    //Toast.makeText(NewAlarmActivity.this,"normal",Toast.LENGTH_LONG).show();
                } else {
                    if (checkedId == R.id.RButton2) {
                        intent_start.setAction("silent");
                        r2.setTextColor(Color.parseColor("#0088FC"));
                        r1.setTextColor(Color.parseColor("#FFFFFF"));
                        r3.setTextColor(Color.parseColor("#FFFFFF"));

                        //Toast.makeText(NewAlarmActivity.this,"silent",Toast.LENGTH_LONG).show();
                    } else {
                        intent_start.setAction("vibrate");
                        r3.setTextColor(Color.parseColor("#0088FC"));
                        r2.setTextColor(Color.parseColor("#FFFFFF"));
                        r1.setTextColor(Color.parseColor("#FFFFFF"));


                    }

                }

            }


        });



    }
    @Override
    public void onBackPressed()
    {
        Bundle bundle=new Bundle();
        flag=false;
        Intent nodata=new Intent();
        bundle.putBoolean("if_have_data",flag);
        nodata.putExtra("bundle",bundle);
        setResult(0,nodata);
        // TODO: Implement this method
        super.onBackPressed();
    }

}
