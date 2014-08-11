package org.vosie.wikicards.utils.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.vosie.wikicards.MainActivity;
import org.vosie.wikicards.R;
import org.vosie.wikicards.data.DownloadWordListener;
import org.vosie.wikicards.utils.ErrorUtils;

import android.app.Activity;
import android.content.Context;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class ErrorUtilsTest {

  @Test
  public void testGetErrorTitle() {
    Context ctx = Robolectric.application;
    // http error
    String title = ErrorUtils.getErrorTitle(ctx,
            DownloadWordListener.HTTP_ERROR);
    String expected = ctx.getString(R.string.dialog_title_http_error);
    Assert.assertEquals(expected, title);
    // http internal error
    title = ErrorUtils.getErrorTitle(ctx, DownloadWordListener.INTERNAL_ERROR);
    expected = ctx.getString(R.string.dialog_title_internal_error);
    Assert.assertEquals(expected, title);
    // network error
    title = ErrorUtils.getErrorTitle(ctx, DownloadWordListener.NETWORK_ERROR);
    expected = ctx.getString(R.string.dialog_title_network_error);
    Assert.assertEquals(expected, title);
  }

  @Test
  public void testGetErrorTitleErrorCase() {
    try {
      ErrorUtils.getErrorTitle(null, 0);
      Assert.fail("An exception should be thrown when we give null as ctx");
    } catch (NullPointerException e) {
    }

    String unknownError = ErrorUtils.getErrorTitle(Robolectric.application, -1);
    Assert.assertEquals("", unknownError);
  }

  @Test
  public void testGetErrorDesc() {
    Context ctx = Robolectric.application;
    // http error
    String desc = ErrorUtils.getErrorDesc(ctx,
            DownloadWordListener.HTTP_ERROR);
    String expected = ctx.getString(R.string.dialog_desc_http_error);
    Assert.assertEquals(expected, desc);
    // http internal error
    desc = ErrorUtils.getErrorDesc(ctx, DownloadWordListener.INTERNAL_ERROR);
    expected = ctx.getString(R.string.dialog_desc_internal_error);
    Assert.assertEquals(expected, desc);
    // network error
    desc = ErrorUtils.getErrorDesc(ctx, DownloadWordListener.NETWORK_ERROR);
    expected = ctx.getString(R.string.dialog_desc_network_error);
    Assert.assertEquals(expected, desc);
  }

  @Test
  public void testGetErrorDescErrorCase() {
    try {
      ErrorUtils.getErrorDesc(null, 0);
      Assert.fail("An exception should be thrown when we give null as ctx");
    } catch (NullPointerException e) {
    }

    String unknownError = ErrorUtils.getErrorDesc(Robolectric.application, -1);
    Assert.assertEquals("", unknownError);
  }

  @Test
  public void testHandleDownloadkError() {
    final Activity inputCtx = Robolectric.buildActivity(
            MainActivity.class).create().get();
    final String inputTitle = inputCtx.getString(R.string.dialog_title_http_error);
    final String inputDesc = inputCtx.getString(R.string.dialog_desc_http_error);

    ErrorUtils.handleDownloadkError(inputCtx, DownloadWordListener.HTTP_ERROR, false);
  }

}
