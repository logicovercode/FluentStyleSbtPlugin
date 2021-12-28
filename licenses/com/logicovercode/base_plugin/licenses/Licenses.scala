package com.logicovercode.base_plugin.licenses

import org.apache.ivy.core.module.descriptor.License

trait Licenses {

  val Apache_2_0_License =
    new License("Apache-2.0", "http://www.apache.org/licenses/LICENSE-2.0")
  val MIT_License = new License("MIT", "https://opensource.org/licenses/MIT")
}
