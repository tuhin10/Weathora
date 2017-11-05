package com.tuhin.weathora.presenter;

import com.tuhin.weathora.R;
import com.tuhin.weathora.contract.HomeMVP;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HomePresenterTest extends TestCase {
    private HomePresenter homePresenter;
    @Mock
    private HomeMVP.HomeView homeView;

    @Before
    public void setUp()  throws Exception{
        homePresenter = new HomePresenter(homeView);

        // Overriding RxJava schedulers
        RxJavaPlugins.setComputationSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) throws Exception {
                return Schedulers.computation();
            }
        });

        RxJavaPlugins.setIoSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) throws Exception {
                return Schedulers.computation();
            }
        });

        RxJavaPlugins.setNewThreadSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) throws Exception {
                return Schedulers.computation();
            }
        });

        // Override RxAndroid schedulers as JUnit does not have Android schedulers
        RxAndroidPlugins.setMainThreadSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) throws Exception {
                return Schedulers.computation();
            }
        });
    }

    @Test
    public void shouldShowErrorMsgWhenQueryAndAppIdisEmptyforWeatherRequest() throws Exception {
        when(homeView.getLastSavedQuery()).thenReturn("");
        when(homeView.getOwmAppId()).thenReturn("");
        homePresenter.requestWeatherDetails(homeView.getLastSavedQuery(), homeView.getOwmAppId());
        verify(homeView).showErrorMessageView(R.string.invalid_city);
    }

    @Test
    public void shouldShowErrorMsgWhenQueryisEmptyforWeatherRequest() throws Exception {
        when(homeView.getLastSavedQuery()).thenReturn("");
        homePresenter.requestWeatherDetails(homeView.getLastSavedQuery(), homeView.getOwmAppId());
        verify(homeView).showErrorMessageView(R.string.invalid_city);
    }

    @Test
    public void shouldShowErrorMsgWhenAppIdisEmptyforWeatherRequest() throws Exception {
        when(homeView.getOwmAppId()).thenReturn("");
        homePresenter.requestWeatherDetails(homeView.getLastSavedQuery(), homeView.getOwmAppId());
        verify(homeView).showErrorMessageView(R.string.invalid_city);
    }

    @Test
    public void shouldShowErrorMsgWhenQueryAndAppIdisNullforWeatherRequest() throws Exception {
        when(homeView.getLastSavedQuery()).thenReturn(null);
        when(homeView.getOwmAppId()).thenReturn(null);
        homePresenter.requestWeatherDetails(homeView.getLastSavedQuery(), homeView.getOwmAppId());
        verify(homeView).showErrorMessageView(R.string.invalid_city);
    }

    @Test
    public void shouldShowErrorMsgWhenQueryisNullforWeatherRequest() throws Exception {
        when(homeView.getLastSavedQuery()).thenReturn(null);
        homePresenter.requestWeatherDetails(homeView.getLastSavedQuery(), homeView.getOwmAppId());
        verify(homeView).showErrorMessageView(R.string.invalid_city);
    }

    @Test
    public void shouldShowErrorMsgWhenAppIdisNullforWeatherRequest() throws Exception {
        when(homeView.getOwmAppId()).thenReturn(null);
        homePresenter.requestWeatherDetails(homeView.getLastSavedQuery(), homeView.getOwmAppId());
        verify(homeView).showErrorMessageView(R.string.invalid_city);
    }
}