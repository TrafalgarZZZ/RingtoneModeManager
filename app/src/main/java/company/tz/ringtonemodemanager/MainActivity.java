package company.tz.ringtonemodemanager;


        import android.app.*;
        import android.graphics.Color;
        import android.os.*;
        import android.view.*;
        import android.content.*;
        import android.widget.*;
        import android.widget.Gallery.*;
        import java.util.*;


public class MainActivity extends Activity
{
    private int add=1;
    private long currentTime=0;
    private String alarm_hour,alarm_minute,alarm_ringtonemode;
    private String alarm_hour_finish,alarm_minute_finish;
    private int alarm_forlong;
    private boolean alarm_isforlong;
    private boolean alarm_isLocked;

    Intent intent;
    TextView txt1,txt2,txt3;
    LinearLayout Llayout1;
    int counter=0;
    Calendar cal;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt1=(TextView)findViewById(R.id.txt1);
        txt2=(TextView)findViewById(R.id.txt2);
        Llayout1=(LinearLayout)findViewById(R.id.LinearLayout1);
        cal=Calendar.getInstance();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuItem mun1=menu.add(0, add, 0, "Item 1");
        mun1.setIcon(android.R.drawable.ic_menu_add);
        mun1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        // TODO: Implement this method
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId()==add){
            Intent intent=new Intent(MainActivity.this,NewAlarmActivity.class);
            startActivityForResult(intent,0);



        }
        // TODO: Implement this method
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {

        if(System.currentTimeMillis()-currentTime<=3000){

            finish();
        }
        else{
            Toast.makeText(MainActivity.this,"再按一次返回键退出",Toast.LENGTH_SHORT).show();
            currentTime=System.currentTimeMillis();

        }
        // TODO: Implement this method

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode==0){
            if(resultCode==0){
                Bundle bundle=data.getBundleExtra("bundle");
                if(bundle.getBoolean("if_have_data")){
                    alarm_hour=bundle.getString("hour");
                    alarm_minute=bundle.getString("minute");

                    if(bundle.getString("RingtoneMode").equals("normal")){
                        alarm_ringtonemode="标准";
                    }
                    if(bundle.getString("RingtoneMode").equals( "vibrate")) {
                        alarm_ringtonemode="振动";
                    }

                    if(bundle.getString("RingtoneMode").equals("silent")){
                        alarm_ringtonemode="静音";
                    }


                    alarm_isforlong=bundle.getBoolean("isforLong");
                    if(alarm_isforlong){
                        alarm_forlong=bundle.getInt("forLong");
                        alarm_isLocked=bundle.getBoolean("isLocked");

                    }
                    init();
                }


            }


        }
        // TODO: Implement this method
        super.onActivityResult(requestCode, resultCode, data);
    }



    public void init(){
        if(txt1.getVisibility()==View.VISIBLE){
            Llayout1.removeView(txt1);
            Llayout1.removeView(txt2);
        }

        if(alarm_isforlong){
            //判断是否加0以及是否超出60分钟
            if((Integer.parseInt(alarm_minute)+alarm_forlong%60)>=60){
                if((Integer.parseInt(alarm_minute)+alarm_forlong%60-60)<10){
                    alarm_minute_finish="0"+String.valueOf(Integer.parseInt(alarm_minute)+alarm_forlong%60-60);
                }else{
                    alarm_minute_finish=String.valueOf(Integer.parseInt(alarm_minute)+alarm_forlong%60-60);
                }

            }else{
                if((Integer.parseInt(alarm_minute)+alarm_forlong%60)<10){
                    alarm_minute_finish="0"+String.valueOf(Integer.parseInt(alarm_minute)+alarm_forlong%60);
                }else{
                    alarm_minute_finish=String.valueOf(Integer.parseInt(alarm_minute)+alarm_forlong%60);
                }

            }

            //判断是否需要+1或者加0或者超过24点
            if((Integer.parseInt(alarm_minute)+alarm_forlong%60)>=60){
                if(Integer.parseInt(alarm_hour)+alarm_forlong/60+1<10){
                    alarm_hour_finish="0"+String.valueOf(Integer.parseInt(alarm_hour)+alarm_forlong/60+1);
                }else{
                    if(Integer.parseInt(alarm_hour)+alarm_forlong/60+1<24) {
                        alarm_hour_finish = String.valueOf(Integer.parseInt(alarm_hour) + alarm_forlong / 60 + 1);
                    }else{
                        if(Integer.parseInt(alarm_hour) + alarm_forlong / 60 + 1-24<10) {
                            alarm_hour_finish = "次日" +"0"+String.valueOf(Integer.parseInt(alarm_hour) + alarm_forlong / 60 + 1 - 24);
                        }else{
                            alarm_hour_finish = "次日" +String.valueOf(Integer.parseInt(alarm_hour) + alarm_forlong / 60 + 1 - 24);
                        }
                    }
                }


            }else{
                if(Integer.parseInt(alarm_hour)+alarm_forlong/60<10){
                    alarm_hour_finish="0"+String.valueOf(Integer.parseInt(alarm_hour)+alarm_forlong/60);
                }else{
                    if(Integer.parseInt(alarm_hour)+alarm_forlong/60<24) {
                        alarm_hour_finish = String.valueOf(Integer.parseInt(alarm_hour) + alarm_forlong / 60 );
                    }else{
                        alarm_hour_finish = "次日"+String.valueOf(Integer.parseInt(alarm_hour) + alarm_forlong / 60-24);
                    }
                }


            }



        }





        LinearLayout.LayoutParams Lparams=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        txt3=new TextView(this);
        if(alarm_isforlong){
            if(alarm_isLocked){
                txt3.setText(String.valueOf(alarm_hour) + ":" + String.valueOf(alarm_minute) + "-" + String.valueOf(alarm_hour_finish) + ":" + String.valueOf(alarm_minute_finish) + "    " + String.valueOf(alarm_ringtonemode)+"                      锁定模式");
            }else {
                txt3.setText(String.valueOf(alarm_hour) + ":" + String.valueOf(alarm_minute) + "-" + String.valueOf(alarm_hour_finish) + ":" + String.valueOf(alarm_minute_finish) + "    " + String.valueOf(alarm_ringtonemode));
            }
        }else {
            txt3.setText(String.valueOf(alarm_hour) + ":" + String.valueOf(alarm_minute) + "               " + String.valueOf(alarm_ringtonemode));
        }
        txt3.setTextSize(20.0f);

        if(alarm_ringtonemode.equals("静音")){
            txt3.setTextColor(Color.rgb(152,118,170));
        }else{
            if(alarm_ringtonemode.equals("振动")){
                txt3.setTextColor(Color.rgb(104,151,187));
            }else{
                txt3.setTextColor(Color.rgb(165,194,97));
            }
        }
        Llayout1.addView(txt3,Lparams);




    }


















}
