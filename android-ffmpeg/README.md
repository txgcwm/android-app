android-ffmpeg is used to compile ffmpeg for android.

1、设置环境变量

配置ANDROID_SDK 

＃export ANDROID_SDK=/path/to/android-sdk（请把path改为自己的路径）

配置ANDROID_NDK

＃export ANDROID_NDK=/path/to/android-ndk

配置PATH变量

＃export PATH=$PATH:$ANDROID_SDK/tools:$ANDROID_SDK/platform-tools

配置ABI

＃export ANDROID_ABI=armeabi-v7a


2、编译ffmpeg源码

＃./android-ffmpeg.sh