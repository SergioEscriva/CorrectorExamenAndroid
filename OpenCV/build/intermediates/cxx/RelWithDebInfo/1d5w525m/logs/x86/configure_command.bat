@echo off
"C:\\Users\\Sergio SM\\AppData\\Local\\Android\\Sdk\\cmake\\3.22.1\\bin\\cmake.exe" ^
  "-HC:\\Users\\Sergio SM\\AndroidStudioProjects\\CorrectorExamenAndroid\\OpenCV\\libcxx_helper" ^
  "-DCMAKE_SYSTEM_NAME=Android" ^
  "-DCMAKE_EXPORT_COMPILE_COMMANDS=ON" ^
  "-DCMAKE_SYSTEM_VERSION=21" ^
  "-DANDROID_PLATFORM=android-21" ^
  "-DANDROID_ABI=x86" ^
  "-DCMAKE_ANDROID_ARCH_ABI=x86" ^
  "-DANDROID_NDK=C:\\Users\\Sergio SM\\AppData\\Local\\Android\\Sdk\\ndk\\26.1.10909125" ^
  "-DCMAKE_ANDROID_NDK=C:\\Users\\Sergio SM\\AppData\\Local\\Android\\Sdk\\ndk\\26.1.10909125" ^
  "-DCMAKE_TOOLCHAIN_FILE=C:\\Users\\Sergio SM\\AppData\\Local\\Android\\Sdk\\ndk\\26.1.10909125\\build\\cmake\\android.toolchain.cmake" ^
  "-DCMAKE_MAKE_PROGRAM=C:\\Users\\Sergio SM\\AppData\\Local\\Android\\Sdk\\cmake\\3.22.1\\bin\\ninja.exe" ^
  "-DCMAKE_LIBRARY_OUTPUT_DIRECTORY=C:\\Users\\Sergio SM\\AndroidStudioProjects\\CorrectorExamenAndroid\\OpenCV\\build\\intermediates\\cxx\\RelWithDebInfo\\1d5w525m\\obj\\x86" ^
  "-DCMAKE_RUNTIME_OUTPUT_DIRECTORY=C:\\Users\\Sergio SM\\AndroidStudioProjects\\CorrectorExamenAndroid\\OpenCV\\build\\intermediates\\cxx\\RelWithDebInfo\\1d5w525m\\obj\\x86" ^
  "-DCMAKE_BUILD_TYPE=RelWithDebInfo" ^
  "-BC:\\Users\\Sergio SM\\AndroidStudioProjects\\CorrectorExamenAndroid\\OpenCV\\.cxx\\RelWithDebInfo\\1d5w525m\\x86" ^
  -GNinja ^
  "-DANDROID_STL=c++_shared"
