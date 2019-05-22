package ouc.b304.com.fenceplaying.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import ouc.b304.com.fenceplaying.R;
import ouc.b304.com.fenceplaying.adapter.LightAdapter;
import ouc.b304.com.fenceplaying.entity.DbLight;
import ouc.b304.com.fenceplaying.order.OrderUtils;
import ouc.b304.com.fenceplaying.utils.newUtils.AppConfig;
import ouc.b304.com.fenceplaying.utils.newUtils.ComparatorUtils;
import ouc.b304.com.fenceplaying.utils.newUtils.RealmUtils;
import ouc.b304.com.fenceplaying.utils.newUtils.ToastUtils;

/**
 * //                       _oo0oo_
 * //                      o8888888o
 * //                      88" . "88
 * //                      (| -_- |)
 * //                      0\  =  /0
 * //                    ___/`---'\___
 * //                  .' \\|     |// '.
 * //                 / \\|||  :  |||// \
 * //                / _||||| -:- |||||- \
 * //               |   | \\\  -  /// |   |
 * //               | \_|  ''\---/''  |_/ |
 * //               \  .-\__  '-'  ___/-. /
 * //             ___'. .'  /--.--\  `. .'___
 * //          ."" '<  `.___\_<|>_/___.' >' "".
 * //         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 * //         \  \ `_.   \_ __\ /__ _/   .-` /  /
 * //     =====`-.____`.___ \_____/___.-`___.-'=====
 * //                       `=---='
 * //
 * //
 * //     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * //
 * //               佛祖保佑         永无BUG
 * //
 * //
 * //
 *
 * @author 王海峰 on 2019/5/8 15:41
 */
public class AlterDeviceDialog extends Dialog {
    private ViewHolder mViewHolder;
    private SetOnDialogListener mOnDialogListener;
    private Context mContext;
    private CusProgressDialog mCusProgressDialog;
    /**
     * 倒计时
     */
    private CountDownTimer mTimer = new CountDownTimer(15000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mViewHolder.mNetIn.setText((millisUntilFinished / 1000) + "s");
        }

        @Override
        public void onFinish() {
            mTimer.cancel();
            mExecutorService.shutdownNow();
            mViewHolder.mNetIn.setText("打开入网");
        }
    };

    private LightAdapter mLightAdapter;
    private Realm mRealm;
    private ScheduledExecutorService mExecutorService;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case AppConfig.ACTION_REMOVE_LIGHT:
                    String address = intent.getStringExtra("address");
                    for (final DbLight dbLight : AppConfig.sDbLights) {
                        if (address.equals(dbLight.getAddress())) {
                            if (dbLight.getName().equals("00")) {
                                AppConfig.sDbLights.remove(dbLight);
                            } else {
                                RealmUtils.removeLight(mRealm, dbLight, new RealmUtils.SetOnDeleteListener() {
                                    @Override
                                    public void onDeleteListener(Boolean flag) {
                                        if (flag) {
                                            AppConfig.sDbLights.remove(dbLight);
                                        } else {
                                            ToastUtils.showToast(mContext, "删除失败！", Toast.LENGTH_SHORT);
                                        }
                                    }
                                });
                            }
                            Collections.sort(AppConfig.sDbLights, new ComparatorUtils.LightComparator());
                            mOnDialogListener.onDataChangeListener();
                            mLightAdapter.notifyDataSetChanged();
                            break;
                        }
                    }
                    break;
                case AppConfig.ACTION_ALTER_ROPE:

                    break;
            }

        }
    };

    public  AlterDeviceDialog(Context context, Realm realm, SetOnDialogListener onDialogListener) {
        super(context, R.style.Dialog);
        setCancelable(false);
        mContext = context;
        mOnDialogListener = onDialogListener;
        mRealm = realm;
        registerReceiver();
        createDialog();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mContext.unregisterReceiver(mBroadcastReceiver);
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(AppConfig.ACTION_REMOVE_LIGHT);
        filter.addAction(AppConfig.ACTION_ALTER_ROPE);
        mContext.registerReceiver(mBroadcastReceiver, filter);
    }

    /**
     * 创建
     */
    private void createDialog() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_alter_device, null);
        mViewHolder = new ViewHolder(view);
        mViewHolder.mPanId.setText("Pan_id:" + AppConfig.sDevice.getChannelNumber() + AppConfig.sDevice.getPanId());
        mLightAdapter = new LightAdapter(mContext, AppConfig.sDbLights, new LightAdapter.SetOnLightListener() {
            @Override
            public void onNetInListener(final DbLight light, final String name) {
                light.setPanId(AppConfig.sDevice.getPanId());
                RealmUtils.checkLight(mRealm, light, name, new RealmUtils.SetOnIsHaveListener() {
                    @Override
                    public void onIsHaveListener(Boolean flag) {
                        if (!flag) {
                            final String oldName = light.getName();
                            if (oldName.equals("00")) {
                                light.setName(name);
                                saveLight(light);
                            } else {
                                alterLight(light, name);
                            }
                        } else {
                            ToastUtils.showToast(mContext, "编号已存在！", Toast.LENGTH_SHORT);
                        }
                    }
                });
            }

            @Override
            public void onWithDrawListener(DbLight light) {
                OrderUtils.getInstance().removeNet(light.getAddress());
            }

            @Override
            public void onWithDrawListener2(DbLight light) {
                OrderUtils.getInstance().removeNet(light.getAddress());
                for (final DbLight dbLight : AppConfig.sDbLights) {
                    if (light.getAddress().equals(dbLight.getAddress())) {
                        if (light.getName().equals("00")) {
                            AppConfig.sDbLights.remove(dbLight);
                        } else {
                            RealmUtils.removeLight(mRealm, dbLight, new RealmUtils.SetOnDeleteListener() {
                                @Override
                                public void onDeleteListener(Boolean flag) {
                                    if (flag) {
                                        AppConfig.sDbLights.remove(dbLight);
                                    } else {
                                        ToastUtils.showToast(mContext, "删除失败！", Toast.LENGTH_SHORT);
                                    }
                                }
                            });
                        }
                        Collections.sort(AppConfig.sDbLights, new ComparatorUtils.LightComparator());
                        mOnDialogListener.onDataChangeListener();
                        mLightAdapter.notifyDataSetChanged();
                        break;
                    }

                }
            }

            @Override
            public void onAlterListener(int alterIndex) {
                mLightAdapter.setAlterIndex(alterIndex);
                mLightAdapter.notifyDataSetChanged();
            }
        });
        mViewHolder.mGridView.setLayoutManager(new LinearLayoutManager(mContext));
        mViewHolder.mGridView.setAdapter(mLightAdapter);


        mViewHolder.mNetIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderUtils.getInstance().allowAccessNetwork();
                mExecutorService = new ScheduledThreadPoolExecutor(1);
                mExecutorService.scheduleAtFixedRate(new PowerRunnable(), 2500, 2500, TimeUnit.MILLISECONDS);
                mTimer.start();
            }
        });
        mViewHolder.mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        super.setContentView(view);
    }

    private void alterLight(final DbLight light, final String name) {
        RealmUtils.alterLight(mRealm, light, name, new RealmUtils.SetOnAlterListener() {
            @Override
            public void onAlterListener(final Boolean flag) {
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (flag) {
                            light.setName(name);
                            Collections.sort(AppConfig.sDbLights, new ComparatorUtils.LightComparator());
                            mOnDialogListener.onDataChangeListener();
                            mLightAdapter.setAlterIndex(-1);
                            mLightAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtils.showToast(mContext, "修改失败！", Toast.LENGTH_SHORT);
                        }
                    }
                });
            }
        });
    }

    private void saveLight(final DbLight light) {
        light.setPanId(AppConfig.sDevice.getPanId());
        RealmUtils.saveLight(mRealm, light, new RealmUtils.SetOnSaveListener() {
            @Override
            public void onSaveListener(final Boolean flag) {
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (flag) {
                            Collections.sort(AppConfig.sDbLights, new ComparatorUtils.LightComparator());
                            mOnDialogListener.onDataChangeListener();
                            mLightAdapter.notifyDataSetChanged();
                        } else {
                            light.setName("00");
                            ToastUtils.showToast(mContext, "保存失败！", Toast.LENGTH_SHORT);
                            mLightAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
    }


    public interface SetOnDialogListener {
        /**
         * 数据改变
         */
        void onDataChangeListener();
    }


    static class ViewHolder {
        @BindView(R.id.pan_id)
        TextView mPanId;
        @BindView(R.id.gridView)
        RecyclerView mGridView;
        @BindView(R.id.netIn)
        Button mNetIn;
        @BindView(R.id.cancel)
        Button mCancel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class PowerRunnable implements Runnable {
        @Override
        public void run() {
            ((Activity) mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLightAdapter.notifyDataSetChanged();
                }
            });
        }
    }
}

