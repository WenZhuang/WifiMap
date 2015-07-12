package com.bupt.wifimap;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class DocumentationActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentation);

        TextView docText = (TextView)findViewById(R.id.docText);
        docText.setText("1.\t在应用的主界面，手指从屏幕左侧滑向右侧，或者点击标题栏左侧的左箭头图标时将会呼出导航抽屉。\n\n"+
                "2.\t在手机连接上北邮校园网的情况下，直接打开该应用或者点击左侧抽屉里的信号热图一栏，可以看到BUPT-2的网络信号分布热图。\n\n" +
                "3.\t点击抽屉栏的“系统信息”，左右滑动屏幕或者点击底栏的标签，依次可以查看手机的系统信息，地理位置和信号强度。\n\n" +
                "4.\t数据上传模块分为两种方式，分为手动上传和自动上传。点击手动上传一次上传一组数据；点击自动上传，每隔10秒上传自动一次数据；点击停止自动上传，停止上传。\n\n" +
                "5.\t上传的数据包括BUPT-2信号强度、经度、纬度、时间戳、本机Mac地址。\n");
    }

}
