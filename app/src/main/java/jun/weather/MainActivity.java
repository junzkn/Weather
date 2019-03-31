package jun.weather;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Context mContext ;
    private Button btn_city_GD ;
    private Button btn_city_BJ ;
    private Button btn_city_SH ;
    private Button btn_city_JL ;
    private TextView tv_city ;
    private TextView tv_weather ;
    private TextView tv_temp ;
    private TextView tv_wind ;
    private TextView tv_pm ;
    private TextView tv_clothes ;
    private ImageView img_weather ;
    private ImageView img_bj ;
    private Map<String,String> map ;
    private List<Map<String,String>> list ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this ;
        init() ;
        img_bj.setBackgroundResource(R.drawable.bj_gd);
        set(0) ;
    }

    private void init() {
        btn_city_GD = (Button) findViewById(R.id.btn_city_GD);
        btn_city_BJ = (Button) findViewById(R.id.btn_city_BJ);
        btn_city_SH = (Button) findViewById(R.id.btn_city_SH);
        btn_city_JL = (Button) findViewById(R.id.btn_city_JL);
        tv_city = (TextView) findViewById(R.id.tv_city);
        tv_weather = (TextView) findViewById(R.id.tv_weather);
        tv_temp = (TextView) findViewById(R.id.tv_temp);
        tv_wind = (TextView) findViewById(R.id.tv_wind);
        tv_pm = (TextView) findViewById(R.id.tv_pm);
        tv_clothes = (TextView) findViewById(R.id.tv_clothes);
        img_weather = (ImageView) findViewById(R.id.img_weather);
        img_bj = (ImageView) findViewById(R.id.img_bj);
        MListener mListener = new MListener();
        btn_city_GD.setOnClickListener(mListener);
        btn_city_BJ.setOnClickListener(mListener);
        btn_city_SH.setOnClickListener(mListener);
        btn_city_JL.setOnClickListener(mListener);
        try{
            List<WeatherBean> infos = WeatherTools.getWeatherInfos(this.getClass().getClassLoader().getResourceAsStream("weather.xml")) ;
            list = new ArrayList<Map<String, String>>() ;
            for(WeatherBean bean : infos){
                map = new HashMap<String, String>() ;
                map.put("temp",bean.temp);
                map.put("weather",bean.weather) ;
                map.put("name",bean.name) ;
                map.put("pm",bean.pm) ;
                map.put("wind",bean.wind) ;
                map.put("clothes",bean.clothes) ;
                list.add(map);
            }
            Toast.makeText(mContext,"读取success", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(mContext,"读取数据失败", Toast.LENGTH_SHORT).show();
        }
    }

    private class MListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.btn_city_GD :
                    img_bj.setBackgroundResource(R.drawable.bj_gd);
                    set(0) ;
                    break;
                case R.id.btn_city_BJ :
                    img_bj.setBackgroundResource(R.drawable.bj_bj);
                    set(1) ;
                    break;
                case R.id.btn_city_SH :
                    img_bj.setBackgroundResource(R.drawable.bj_sh);
                    set(2) ;
                    break;
                case R.id.btn_city_JL :
                    img_bj.setBackgroundResource(R.drawable.bj_jl);
                    set(3) ;
                    break;
                default:break;
            }
        }
    }

    private void set(int i) {
        Map<String,String> mMap = list.get(i) ;
        tv_city.setText(mMap.get("name"));
        tv_temp.setText(mMap.get("temp"));
        tv_weather.setText(mMap.get("weather"));
        tv_clothes.setText("穿衣指数："+mMap.get("clothes"));
        tv_pm.setText("PM："+mMap.get("pm"));
        tv_wind.setText("风力："+mMap.get("wind"));
        switch (mMap.get("weather")) {
            case "多云" :
                img_weather.setBackgroundResource(R.drawable.clouds);
                break ;
            case "晴天有云" :
                img_weather.setBackgroundResource(R.drawable.cloud_sun);
                break ;
            case "晴天" :
                img_weather.setBackgroundResource(R.drawable.sun);
                break ;
            default : break ;
        }
    }
}
