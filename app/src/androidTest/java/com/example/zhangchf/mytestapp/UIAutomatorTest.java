package com.example.zhangchf.mytestapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.EventCondition;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by zhangchf on 25/04/2017.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class UIAutomatorTest {

    private static final String MY_PACKAGE = "com.example.zhangchf.mytestapp";
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String STRING_TO_BE_TYPED = "UiAutomator";
    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        mDevice.pressHome();

        String launcherPackage = mDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        Context context = InstrumentationRegistry.getContext();
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(MY_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        mDevice.wait(Until.hasObject(By.pkg(MY_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void testUITestActivity() throws UiObjectNotFoundException, InterruptedException {
/*        UiObject button = mDevice.findObject(new UiSelector().resourceId("uiTestActivity"));
        button.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);*/

        UiObject2 uiTestActivityButton = mDevice.findObject(By.res(MY_PACKAGE, "uiTestActivity"));
        uiTestActivityButton.clickAndWait(Until.newWindow(), LAUNCH_TIMEOUT);

        String testText = "Ui Automator";

        UiObject2 editText = mDevice.findObject(By.res(MY_PACKAGE, "editText"));
//        editText.drag(new Point(500, 500), 10);
        editText.setText(testText);

        UiObject2 displayInput = mDevice.findObject(By.res(MY_PACKAGE, "displayInput"));
        displayInput.click();

        UiObject2 tvShowInput = mDevice.findObject(By.res(MY_PACKAGE, "tvShowInput"));
        assertEquals(tvShowInput.getText(), testText);

        wait(LAUNCH_TIMEOUT);
    }
}
