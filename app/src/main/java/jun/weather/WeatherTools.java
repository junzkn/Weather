package jun.weather;

import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class WeatherTools {
    public static List<WeatherBean> getWeatherInfos(InputStream is) throws Exception{
        //得到pull解析器
        XmlPullParser parser = Xml.newPullParser() ;
        //初始化parser
        parser.setInput(is,"utf-8");
        List<WeatherBean> weatherInfos = null ;
        WeatherBean weatherinfo = null ;

        int type=parser.getEventType() ;
        while(type!=XmlPullParser.END_DOCUMENT) {
            switch(type){
                case XmlPullParser.START_TAG:
                    if(parser.getName().equals("infos")){
                        weatherInfos = new ArrayList<WeatherBean>() ;
                    }
                    else if (parser.getName().equals("city")){
                        weatherinfo = new WeatherBean() ;
                        weatherinfo.id = Integer.parseInt(parser.getAttributeValue(0));
                    }
                    else if (parser.getName().equals("temp")){
                        weatherinfo.temp = parser.nextText();
                    }
                    else if (parser.getName().equals("weather")){
                        weatherinfo.weather = parser.nextText() ;
                    }
                    else if (parser.getName().equals("name")){
                        weatherinfo.name = parser.nextText() ;
                    }
                    else if (parser.getName().equals("pm")){
                        weatherinfo.pm = parser.nextText() ;
                    }
                    else if (parser.getName().equals("wind")){
                        weatherinfo.wind = parser.nextText() ;
                    }
                    else if (parser.getName().equals("clothes")){
                        weatherinfo.clothes = parser.nextText() ;
                    }
                    break ;
                case XmlPullParser.END_TAG :
                    if(parser.getName().equals("city")){
                        weatherInfos.add(weatherinfo);
                        weatherinfo = null ;
                    }
                    break ;
                default : break ;
            }
            type=parser.next() ;
        }
        return weatherInfos ;
    }
}
