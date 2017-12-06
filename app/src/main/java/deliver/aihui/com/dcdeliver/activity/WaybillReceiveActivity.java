package deliver.aihui.com.dcdeliver.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import deliver.aihui.com.dcdeliver.R;
import deliver.aihui.com.dcdeliver.adapter.InspectPagerAdapter;


public class WaybillReceiveActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.tb)
    TabLayout    mTb;
    @BindView(R.id.vp)
    ViewPager    mVp;
    private InspectPagerAdapter mPagerAdapter;

    String[] mTitleStrings = {
            "待接单"
            , "已结单"
    };
    //存放列表的
    private List<RecyclerView> mViewList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waybill_receive);
        ButterKnife.bind(this);
        initView();


    }

    private void initView() {
        initRecyclView();
        initTabLayout();

    }

    private void initRecyclView() {
        RecyclerView waitingBill = (RecyclerView) View.inflate(this, R.layout.recycleview, null);
        RecyclerView receivingBill = (RecyclerView) View.inflate(this, R.layout.recycleview, null);
        mViewList.add(waitingBill);
        mViewList.add(receivingBill);

    }

    private void initTabLayout() {
        mPagerAdapter = new InspectPagerAdapter(mTitleStrings, mViewList, this);
        mVp.setAdapter(mPagerAdapter);
        mTb.setupWithViewPager(mVp);
        mTb.addOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
