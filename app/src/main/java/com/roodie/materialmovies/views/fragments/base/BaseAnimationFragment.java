package com.roodie.materialmovies.views.fragments.base;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.base.Preconditions;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.roodie.materialmovies.R;
import com.roodie.materialmovies.util.MMoviesPreferences;
import com.roodie.materialmovies.views.custom_views.MMoviesImageView;
import com.roodie.materialmovies.views.listeners.RotateAnimationListener;

import java.io.Serializable;

import butterknife.InjectView;
import butterknife.Optional;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import io.codetail.animation.arcanimator.ArcAnimator;
import io.codetail.animation.arcanimator.Side;

/**
 * Created by Roodie on 12.08.2015.
 */
public abstract class BaseAnimationFragment<M extends Serializable> extends BaseDetailFragment<M> {

    protected static final String KEY_REVEAL_START_LOCATION = "reveal_start_location";
    protected static final String KEY_VIEW = "image_view";
    protected static final String KEY_IMAGE_POSITION = "_position";

    @Optional @InjectView(R.id.animation_layout)
    FrameLayout mAnimationLayout;
    protected ImageView mAnimationStarImageView;
    protected TextView mAnimationTextView;

    @Optional @InjectView(R.id.button_fab)
    protected FloatingActionButton mFloatingButton;

    @Optional @InjectView(R.id.animation_container)
    protected FrameLayout mAnimationContainer;

    @Optional @InjectView(R.id.data_container)
    protected View mDataContainer;

    @Optional @InjectView(R.id.poster_image)
    protected MMoviesImageView mPosterImageView;

    protected float startAnimationX, startAnimationY;

    protected int endAnimationX, endAnimationY;
    protected   int startAnimationPairBottom;

    final static AccelerateInterpolator ACCELERATE = new AccelerateInterpolator();
    final static DecelerateInterpolator DECELERATE = new DecelerateInterpolator();
    final static AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
    final static  Interpolator INTERPOLATOR = new DecelerateInterpolator();

    protected static final int SCALE_DELAY = 30;

    public static Interpolator getInterpolator() {
        return INTERPOLATOR;
    }

    protected boolean hasAnimationContainer() {
        return mAnimationContainer != null;
    }

    protected boolean hasFAB() {
        return mFloatingButton != null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mAnimationStarImageView =  (ImageView) view.findViewById(R.id.image_view_star);

        mAnimationTextView =  (TextView) view.findViewById(R.id.confirmation_text_view);

        if (hasAnimationContainer()) {
            mAnimationStarImageView.setVisibility(View.GONE);
            mAnimationTextView.setVisibility(View.GONE);
            mAnimationContainer.setVisibility(View.GONE);
            mDataContainer.setVisibility(View.VISIBLE);
        }

        initiaizeStartAnimation();

        if (hasFAB() && hasAnimationContainer()) {
            mFloatingButton.setVisibility(View.VISIBLE);
            mFloatingButton.setOnClickListener(mOnClickListener);
        }

        super.onViewCreated(view, savedInstanceState);

    }

    final private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onFabClicked();
        }
    };

    public abstract void onFabClicked();

   private void initiaizeStartAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && MMoviesPreferences.areAnimationsEnabled(getContext())) {
            configureEnterTransition();
        }
    }

    protected abstract void configureEnterTransition();

    protected void startCircleAnimation() {
        Preconditions.checkNotNull(mAnimationLayout, "Animation Layout can not be null");
        Preconditions.checkNotNull(mFloatingButton, "FAB can not be null");
        mFloatingButton.setVisibility(View.INVISIBLE);
        mDataContainer.setVisibility(View.GONE);
        mAnimationContainer.setVisibility(View.VISIBLE);
        mAnimationLayout.setVisibility(View.VISIBLE);

        mAnimationLayout.post(new Runnable() {
            @Override
            public void run() {
                float finalRadius = Math.max(mAnimationLayout.getWidth(), mAnimationLayout.getHeight()) * 1.5f;

                SupportAnimator animator = ViewAnimationUtils.createCircularReveal(mAnimationLayout, endAnimationX, endAnimationY, mFloatingButton.getWidth() / 2f,
                        finalRadius);
                animator.setDuration(500);
                animator.setInterpolator(ACCELERATE);
                animator.addListener(new SimpleAnimationListener() {
                    @Override
                    public void onAnimationEnd() {
                        animateConfirmationView();
                    }
                });
                animator.start();
            }
        });
    }

    private void animateConfirmationView() {

        mAnimationStarImageView.setVisibility(View.VISIBLE);
        mAnimationTextView.setVisibility(View.VISIBLE);

        Drawable drawable = mAnimationStarImageView.getDrawable();

            Animation rotate = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                    R.anim.appear_rotate);
        rotate.setAnimationListener(new RotateAnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                super.onAnimationEnd(animation);
                mAnimationStarImageView.setVisibility(View.GONE);
                mAnimationTextView.setVisibility(View.GONE);
                disappearBackgroundAnimation();
            }
        });
            mAnimationStarImageView.startAnimation(rotate);
    }

    private void disappearBackgroundAnimation(){
        float finalRadius = Math.max(mAnimationLayout.getWidth(), mAnimationLayout.getHeight()) * 1.5f;

        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(mAnimationLayout, endAnimationX, endAnimationY,
                finalRadius, 10);
        animator.setDuration(500);
        animator.addListener(new SimpleAnimationListener() {
            @Override
            public void onAnimationEnd() {
                mAnimationLayout.setVisibility(View.GONE);
                mAnimationContainer.setVisibility(View.GONE);
                mDataContainer.setVisibility(View.VISIBLE);
                returnFloatingButtonAnimation();
            }
        });
        animator.setInterpolator(DECELERATE);
        animator.start();
    }

    private void returnFloatingButtonAnimation(){
        mFloatingButton.setVisibility(View.VISIBLE);
        if (endAnimationX != startAnimationX && endAnimationY != startAnimationY) {
            ArcAnimator arcAnimator = ArcAnimator.createArcAnimator(mFloatingButton, startAnimationX,
                    startAnimationY, 90, Side.RIGHT)
                    .setDuration(500);

            arcAnimator.addListener(new SimpleAnimationListener() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    //enable scrolling when FAB returned to starting position
                    getRecyclerView().mRecyclerView.setNestedScrollingEnabled(true);
                }
            });
            arcAnimator.start();
        } else {
            getRecyclerView().mRecyclerView.setNestedScrollingEnabled(true);
        }
    }

    private void raiseUpAnimation(){
        startAnimationPairBottom = mAnimationLayout.getBottom();
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(mAnimationLayout, "bottom", mAnimationLayout.getBottom(), mAnimationLayout.getTop() + dpToPx(100));
        objectAnimator.addListener(new SimpleAnimationListener() {
            @Override
            public void onAnimationEnd(Animator animation) {

            }
        });
        objectAnimator.setInterpolator(ACCELERATE_DECELERATE);
        objectAnimator.start();
    }

    private void comeDownAnimation(){
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(mAnimationLayout, "bottom", mAnimationLayout.getBottom(), startAnimationPairBottom);
        objectAnimator.addListener(new SimpleAnimationListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
            }
        });
        objectAnimator.setInterpolator(ACCELERATE_DECELERATE);
        objectAnimator.start();
    }

    public int dpToPx(int dp){
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    protected void setEndAnimationX(int endAnimationX) {
        this.endAnimationX = endAnimationX;
    }

    protected void setEndAnimationY(int endAnimationY) {
        this.endAnimationY = endAnimationY;
    }

    protected void setStartAnimationPairBottom(int startAnimationPairBottom) {
        this.startAnimationPairBottom = startAnimationPairBottom;
    }



    public static class SimpleAnimationListener implements SupportAnimator.AnimatorListener, ObjectAnimator.AnimatorListener{

        @Override
        public void onAnimationStart() {
        }

        @Override
        public void onAnimationEnd() {
        }

        @Override
        public void onAnimationCancel() {
        }

        @Override
        public void onAnimationRepeat() {
        }

        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    }

}
