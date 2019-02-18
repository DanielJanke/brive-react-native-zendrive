using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace Brive.React.Native.Zendrive.RNBriveReactNativeZendrive
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNBriveReactNativeZendriveModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNBriveReactNativeZendriveModule"/>.
        /// </summary>
        internal RNBriveReactNativeZendriveModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNBriveReactNativeZendrive";
            }
        }
    }
}
