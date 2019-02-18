
# brive-react-native-zendrive

iOS &amp; Android React-Native Wrapper for the Zendrive SDK for BRIVE

## Getting started

`$ npm install react-native-brive-react-native-zendrive --save`

### Mostly automatic installation

`$ react-native link react-native-brive-react-native-zendrive`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-brive-react-native-zendrive` and add `RNBriveReactNativeZendrive.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNBriveReactNativeZendrive.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNBriveReactNativeZendrivePackage;` to the imports at the top of the file
  - Add `new RNBriveReactNativeZendrivePackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-brive-react-native-zendrive'
  	project(':react-native-brive-react-native-zendrive').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-brive-react-native-zendrive/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-brive-react-native-zendrive')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNBriveReactNativeZendrive.sln` in `node_modules/react-native-brive-react-native-zendrive/windows/RNBriveReactNativeZendrive.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Brive.React.Native.Zendrive.RNBriveReactNativeZendrive;` to the usings at the top of the file
  - Add `new RNBriveReactNativeZendrivePackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNBriveReactNativeZendrive from 'react-native-brive-react-native-zendrive';

// TODO: What to do with the module?
RNBriveReactNativeZendrive;
```
  