package mvp.charco.com.mvplib.mvp;

import android.os.Bundle;

/**
 * @author Corwin
 * @date 2016/5/16.
 */
public abstract class BaseMvpActivity<V extends IMvpView, T extends BasePresenter<V>> extends BaseActivity {

    private T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mPresenter == null) {
            mPresenter = createPresenter();
        }
        if (mPresenter != null && !mPresenter.isViewAttached()) {
            mPresenter.attachView((V) this);
            mPresenter.initial(this);
        }
    }


    protected abstract T createPresenter();

    /**
     * 获得Presenter实例
     *
     * @return
     */
    public T getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter.cancelApi();
        }
    }

}
