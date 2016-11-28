package vn.techplus.clickandgo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.viewpagerindicator.CirclePageIndicator;

import vn.techplus.clickandgo.R;
import vn.techplus.clickandgo.adapter.FragmentIntroduceAdapter;

public class IntroduceActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private CirclePageIndicator circlePageIndicator;
    private Button mGoNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);

        circlePageIndicator = (CirclePageIndicator) findViewById(R.id.circle);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mGoNow = (Button) findViewById(R.id.btnGo);

        mViewPager.setAdapter(new FragmentIntroduceAdapter(getSupportFragmentManager()));
        mViewPager.setOffscreenPageLimit(6);
        circlePageIndicator.setViewPager(mViewPager);

        mGoNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroduceActivity.this, LoginSignupActivity.class);
                startActivity(intent);
            }
        });
    }

}
