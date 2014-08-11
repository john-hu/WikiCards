package org.vosie.wikicards.utils.test;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;

public class ErrorUtilsTestRunner extends RobolectricTestRunner {
  public ErrorUtilsTestRunner(Class<?> arg0) throws InitializationError {
    super(arg0);
    //addClassOrPackageToInstrument("com.package.ClassToShadow");
  }

  //@Override
  protected void bindShadowClasses() {

    //super.bindShadowClasses();
    //Robolectric.bindShadowClass(ShadowClassToShadow.class);
  }
}
