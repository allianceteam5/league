package com.league.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.league.utils.IContants;
import com.mine.league.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RuleActivity extends BaseActivity implements IContants{
    @Bind(R.id.ib_back)
    ImageButton ibBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_rule)
    TextView tvRule;
    int ruleTyle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule);
        ButterKnife.bind(this);
        ibBack.setVisibility(View.VISIBLE);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ruleTyle = getIntent().getIntExtra(RuleType, 0);
        switch (ruleTyle){
            case 1:
                tvTitle.setText("联盟奖励规则");
                tvRule.setText(getString(R.string.rule1));
                break;
            case 2:
                tvTitle.setText("抢红包规则");
                tvRule.setText(getString(R.string.rule2));
                break;
            case 3:
                tvTitle.setText("一元夺宝规则");
                tvRule.setText(getString(R.string.rule3));
                break;
            case 4:
                tvTitle.setText("保本夺金规则");
                tvRule.setText(getString(R.string.rule4));
                break;
            case 5:
                tvTitle.setText("");
                break;
        }
    }
}
