package com.example.administrator.laideshuang;


import android.Manifest;
import android.app.ActivityManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {


    Scanning_Fragment scanningFragment;
    Customer_Fragment customerFragment;
    @BindView(R.id.fragment_demo_ll)
    LinearLayout fragmentDemoLl;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    private SweetAlertDialog MEIDDialog;
    private Fragment mContent;
    SpeechSynthesizer mTts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5be941e0");
//        intiXunFei();
        MEIDDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        MEIDDialog.setCancelable(false);
        mTts = SpeechSynthesizer.createSynthesizer(this, null);

        TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        Log.d("feng",tm.getDeviceId());
//        if (tm.getDeviceId().equals("86362903009067")) {
//            intiView();
//        } else {
//            initDialog();
//        }
        intiView();
    }

    private void initDialog() {
        MEIDDialog
                .setTitleText("请联系商家");
        MEIDDialog.setConfirmText("确定");
        MEIDDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                MEIDDialog.dismiss();
                System.exit(0);
            }
        });
        MEIDDialog.show();
    }

    @Override
    public void updateCount() {

    }

    @Override
    public void updateList(String data) {
        Log.d("feng", data);
        EventBus.getDefault().post(new MessageEvent(data));
    }

    private void intiView() {
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.scanning, "扫码").setActiveColor("#3F51B5"))
                .addItem(new BottomNavigationItem(R.drawable.customer, "数据").setActiveColor("#3F51B5"))
                .setFirstSelectedPosition(0)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
//        setDefaultFragment();

        setDefaultFragment();
    }


    /**
     * 设置默认的fragment，即//第一次加载界面;
     */
    private void setDefaultFragment() {
        scanningFragment = Scanning_Fragment.newInstance();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_demo_ll, scanningFragment).commit();
        mContent = scanningFragment;
    }

    /**
     * 修改显示的内容 不会重新加载 *
     */
    public void switchContent(Fragment to) {
        FragmentManager fragmentManager = getFragmentManager();
        if (mContent != to) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.hide(mContent).add(R.id.fragment_demo_ll, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(mContent).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
            mContent = to;
        }
    }

    @Override
    public void onTabSelected(int position) {
        FragmentManager fm = this.getFragmentManager();
        //开启事务
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                if (scanningFragment == null) {
                    scanningFragment = Scanning_Fragment.newInstance();
                }
//                showCustomizeDialog(srttingFragmenr, (Integer) SPUtils.get(this, "position", 1));
                //将当前的事务添加到了回退栈
//                transaction.addToBackStack(null);
//                transaction.add(R.id.fragment_demo_ll, fragment1);
                switchContent(scanningFragment);
                break;
            case 1:
                if (customerFragment == null) {
                    customerFragment = Customer_Fragment.newInstance();
                }
//                transaction.add(R.id.fragment_demo_ll, fragment2);
                switchContent(customerFragment);
                break;
            default:
                break;
        }
        // 事务提交
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    private void intiXunFei() {

//1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener

//2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "100");//设置音量，范围0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
//设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
//保存在SD卡需要在AndroidManifest.xml添加写SD卡权限
//如果不需要保存合成音频，注释该行代码
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
//3.开始合成
        mTts.startSpeaking("007", mSynListener);
    }

    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
        }

        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        }

        //开始播放
        public void onSpeakBegin() {
        }

        //暂停播放
        public void onSpeakPaused() {
        }

        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        //恢复播放回调接口
        public void onSpeakResumed() {
        }

        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
        }
    };

}
