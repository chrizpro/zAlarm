package com.williams.zalarm.activity;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.joda.time.DateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Mock
    private List<String> list;
    @Rule
    public ActivityTestRule<MainActivity> rule  = new  ActivityTestRule<>(MainActivity.class);

    private MainActivity activity;
    private DateTime setAlarmTime = new DateTime(2019, 7, 4, 10, 17);
    private DateTime previousTime = new DateTime(2019, 7, 3, 9, 17, 17);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        activity = rule.getActivity();
        when(list.size()).thenReturn(10);
        System.out.println(list.size());
    }

    @Test
    public void diffBetweenTimes() {
        assertThat(activity.diffBetweenTimes(previousTime, setAlarmTime).getSeconds(), is(89983) );

    }

    @After
    public void tearDown() throws Exception {
    }

}