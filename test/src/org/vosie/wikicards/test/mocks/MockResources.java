package org.vosie.wikicards.test.mocks;

import org.robolectric.shadows.ShadowResources;

public class MockResources extends ShadowResources {

  public String getString(int id) {
    return "" + id;
  }

}
