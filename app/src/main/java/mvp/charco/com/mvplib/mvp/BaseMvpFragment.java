package mvp.charco.com.mvplib.mvp;

import android.content.Context;
import android.support.v4.app.Fragment;


/**
 * @author Corwin
 * @date 2016/5/16.
 * MVP fragment继承类
 */
public abstract class BaseMvpFragment<V extends MvpView, T extends BasePresenter<V>> extends Fragment {

    private T mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mPresenter == null) {
            mPresenter = createPresenter();
        }

        if (mPresenter != null && !mPresenter.isViewAttached()) {
            mPresenter.attachView((V) this);
            mPresenter.initial(getActivity());
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
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter.cancelApi();
        }
        super.onDestroy();
    }

}
