package org.vosie.wikicards.test.mocks;

import org.robolectric.shadows.ShadowContext;

import android.content.res.Resources;

public class MockContext extends ShadowContext {

  public Resources resources;

  @Override
  public Resources getResources() {
    return resources;
  }

}
