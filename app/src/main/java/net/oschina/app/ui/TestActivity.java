package net.oschina.app.ui;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import junit.framework.Test;

import net.oschina.app.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TestActivity extends AppCompatActivity {

    @InjectView(R.id.test_button)
    Button btn;

    @InjectView(R.id.test_imageView)
    ImageView img;

    @InjectView(R.id.test_layout)
    LinearLayout layout;

    @InjectView(R.id.test_editText)
    EditText editText;

    ValueAnimator valueAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ButterKnife.inject(this, this);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueAnimator.cancel();
                Toast.makeText(TestActivity.this,"Img Cilcked",Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void buttonClicked(View view){

        valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(3000);
        valueAnimator.setObjectValues(new PointF(0, 0));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            // fraction = t / duration
            @Override
            public PointF evaluate(float fraction, PointF startValue,
                                   PointF endValue) {
                Log.e("evaluate", fraction * 3 + "");
                // x方向200px/s ，则y方向0.5 * 10 * t
                PointF point = new PointF();
                point.x = 200 * fraction * 3;
                point.y = 0.5f * 200 * (fraction * 3) * (fraction * 3);
                return point;
            }
        });

        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                layout.setX(point.x);
                layout.setY(point.y);

            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

                Toast.makeText(TestActivity.this,"onAnimationStart",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                Toast.makeText(TestActivity.this,"onAnimationEnd",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

                Toast.makeText(TestActivity.this,"onAnimationCancel",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

                Toast.makeText(TestActivity.this,"onAnimationRepeat",Toast.LENGTH_SHORT).show();
            }
        });
    }



}
