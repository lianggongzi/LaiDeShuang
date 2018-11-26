package com.example.administrator.laideshuang;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.laideshuang.common.CommonAdapter;
import com.example.administrator.laideshuang.common.ViewHolder;
import com.example.administrator.laideshuang.db.SerialDao;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2018\11\14 0014.
 */

public class Scanning_Fragment extends Fragment {


    @BindView(R.id.scanning_tv)
    TextView scanningTv;
    Unbinder unbinder;
    @BindView(R.id.scanning_lrv)
    LRecyclerView scanningLrv;
    @BindView(R.id.scanning_btn)
    Button scanningBtn;
    @BindView(R.id.scanning_number_tv)
    TextView scanningNumberTv;
    private SweetAlertDialog chongfuDialog;
    SerialDao serialDao;
    private LRecyclerViewAdapter lRecyclerViewAdapter = null;
    private CommonAdapter<NumberBean> adapter;
    private List<NumberBean> datas = new ArrayList<>(); //PDA机屏幕上的List集合
    private SweetAlertDialog sweetAlertDialog;
    SpeechSynthesizer mTts;
    private File file;
    private ArrayList<ArrayList<String>> recordList;
    private static String[] title = {"外部订单号", "傲石订单号", "分运单号", "扫描时间"};

    public static Scanning_Fragment newInstance() {
        Scanning_Fragment fragment = new Scanning_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scanning, container, false);
        unbinder = ButterKnife.bind(this, view);
        //注册订阅者
        EventBus.getDefault().register(this);
        chongfuDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE);
        chongfuDialog.setCancelable(false);
        SpeechUtility.createUtility(getActivity(), SpeechConstant.APPID + "=5be941e0");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        serialDao = new SerialDao(getActivity());
        intiView();
        initAdapter();

    }

    private void intiView() {
        mTts = SpeechSynthesizer.createSynthesizer(getActivity(), null);
    }


    //接受扫码消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        String string = messageEvent.getMessage();
        string.replace(" ", "");
        scanningTv.setText(string);
        initData(string);
    }

    private void initData(String data) {

        List<NumberBean> list = serialDao.select1(data);
        Log.d("feng", list.toString());
        int error = 0;
        for (int i = 0; i < datas.size(); i++) {
            if (data.equals(datas.get(i).getFenyunNumber())) {
                error = 1;
//                return;
            }
        }
        switch (error) {
            case 1:
                chongfuDialog
                        .setTitleText("重复录入...");
                chongfuDialog.setConfirmText("确定");
                chongfuDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        chongfuDialog.dismiss();
                    }
                });
                chongfuDialog.show();
                break;
            default:
                for (int i = 0; i < list.size(); i++) {
                    datas.add(list.get(i));
                    lRecyclerViewAdapter.notifyDataSetChanged();
                    String string = list.get(i).getWaibuNumber().substring(2, 5);
                    intiXunFei(string);
                }
                scanningNumberTv.setText(datas.size()+"");
                chongfuDialog.dismiss();
                break;

        }
    }

    private void initAdapter() {
        adapter = new CommonAdapter<NumberBean>(getActivity(), R.layout.adapter_scanning, datas) {
            @Override
            public void setData(ViewHolder holder, NumberBean numberBean) {
                holder.setText(R.id.adapter_waibu_tv, numberBean.getWaibuNumber());
                holder.setText(R.id.adapter_aoshi_tv, numberBean.getAoshiNumber());
                holder.setText(R.id.adapter_fenyun_tv, numberBean.getFenyunNumber());
            }
        };


        scanningLrv.setLayoutManager(new LinearLayoutManager(getActivity()));
        lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        scanningLrv.setAdapter(lRecyclerViewAdapter);
        scanningLrv.setLoadMoreEnabled(false);
        scanningLrv.setPullRefreshEnabled(false);
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE);
                sweetAlertDialog.showCancelButton(true);
                sweetAlertDialog.setCancelText("取消");
                sweetAlertDialog.setTitleText("确定删除此条信息?");
                sweetAlertDialog.setConfirmText("确定");
                sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        datas.remove(position);
                        lRecyclerViewAdapter.notifyDataSetChanged();
                        sweetAlertDialog.dismiss();
                    }
                });
                sweetAlertDialog.show();
            }
        });
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

    private void intiXunFei(String str) {

//1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener

//2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "100");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "100");//设置音量，范围0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
//设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
//保存在SD卡需要在AndroidManifest.xml添加写SD卡权限
//如果不需要保存合成音频，注释该行代码
//        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
//3.开始合成
        mTts.startSpeaking(str, mSynListener);
    }


    @OnClick({R.id.scanning_btn, R.id.scanning_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.scanning_btn:
                if (datas.size() != 0) {
                    String time = DateUtils.getCurrentTime3();
                    if (time.equals(SPUtils.get(getActivity(), "time", ""))) {
                        exportExcel(time);
                    } else {
                        SPUtils.remove(getActivity(), "fileName");
                        exportExcel(time);
                    }
                    datas.clear();
                    lRecyclerViewAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "信息错误", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.scanning_tv:
//                SPUtils.clear(getActivity());
//                Toast.makeText(getActivity(),"清除",Toast.LENGTH_SHORT).show();
                break;
        }

    }

    /**
     * 导出excel
     *
     * @param
     */
    public void exportExcel(String excelName) {
        file = new File(getSDPath() + "/Record");
        makeDir(file);
        String fileName = (String) SPUtils.get(getActivity(), "fileName", "");
        if (fileName.equals("")) {
            String excelFile = file.toString() + "/" + excelName + ".xls";
            ExcelUtils.initExcels(getRecordData(), excelFile, title, excelName, getActivity());
//            ExcelUtils.writeObjListToExcels(getRecordData(), fileName, excelName,  getActivity());
        } else {
            ExcelUtils.writeObjListToExcels(getRecordData(), fileName, excelName, getActivity());
        }
    }

    /**
     * 将数据集合 转化成ArrayList<ArrayList<String>>
     *
     * @return
     */
    private ArrayList<ArrayList<String>> getRecordData() {
        recordList = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            NumberBean numberBean = datas.get(i);
            ArrayList<String> beanList = new ArrayList<String>();
//            beanList.add("1");
            beanList.add(numberBean.getWaibuNumber());
            beanList.add(numberBean.getAoshiNumber());
            beanList.add(numberBean.getFenyunNumber());
            beanList.add(DateUtils.getCurrentTime2());
            recordList.add(beanList);
            Log.d("feng", numberBean.getWaibuNumber());
        }
        return recordList;
    }

    private String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        String dir = sdDir.toString();
        return dir;
    }

    public void makeDir(File dir) {
        if (!dir.getParentFile().exists()) {
            makeDir(dir.getParentFile());
        }
        dir.mkdir();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        //解除订阅者
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
