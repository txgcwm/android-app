#!/bin/bash
#
# android-ffmpeg.sh, a bash script to build FFmpeg for Android.
#
# Copyright (c) 2012 WuMin <txgcwm@gmail.com>
#
# android-ffmpeg is free software; you can redistribute it and/or
# modify it under the terms of the GNU Lesser General Public
# License as published by the Free Software Foundation; either
# version 3 of the License, or (at your option) any later version.
#



if [ -z "$ANDROID_NDK" -o -z "$ANDROID_SDK" -o -z "$ANDROID_ABI" ]; then
   echo "You must define ANDROID_NDK, ANDROID_SDK and ANDROID_ABI before starting."
   echo "They must point to your NDK and SDK directories.\n"
   echo "ANDROID_ABI should match your ABI: armeabi-v7a, armeabi or ..."
   exit 1
fi

# Set up ABI variables
if [ ${ANDROID_ABI} = "x86" ] ; then
    TARGET_TUPLE="i686-android-linux"
    PATH_HOST="x86"
    HAVE_X86=1
    PLATFORM_SHORT_ARCH="x86"
elif [ ${ANDROID_ABI} = "mips" ] ; then
    TARGET_TUPLE="mipsel-linux-android"
    PATH_HOST=$TARGET_TUPLE
    HAVE_MIPS=1
    PLATFORM_SHORT_ARCH="mips"
else
    TARGET_TUPLE="arm-linux-androideabi"
    PATH_HOST=$TARGET_TUPLE
    HAVE_ARM=1
    PLATFORM_SHORT_ARCH="arm"
fi

if [ ${ANDROID_ABI} = "x86" ] ; then
    # x86 toolchain location changes in NDK r8b
    case "$REL" in
        8?)
            TARGET_TUPLE="i686-linux-android"
        ;;
        *)
            TARGET_TUPLE="i686-android-linux"
        ;;
    esac
fi

if [ -n "$HAVE_ARM" ]; then
    if [ ${ANDROID_ABI} = "armeabi-v7a" ]; then
        export EXTRA_CFLAGS="-march=armv7-a -mfpu=vfpv3-d16 -mfloat-abi=softfp"
      	export EXTRA_LDFLAGS="-Wl,--fix-cortex-a8"
    fi
fi

export TARGET_TUPLE
export PATH_HOST
export HAVE_ARM
export HAVE_X86
export HAVE_MIPS
export PLATFORM_SHORT_ARCH

DESTDIR=`pwd`/build && rm -rf $DESTDIR
export PREFIX="$DESTDIR/${ANDROID_ABI}"
mkdir -p $PREFIX
TOOLCHAIN=/tmp/ffplayer
SYSROOT=$TOOLCHAIN/sysroot/
$ANDROID_NDK/build/tools/make-standalone-toolchain.sh --platform=android-14 --install-dir=$TOOLCHAIN

export PATH=$TOOLCHAIN/bin:$PATH
export CC="ccache arm-linux-androideabi-gcc"
export LD=${TARGET_TUPLE}-ld
export AR=${TARGET_TUPLE}-ar
export STRIP=${TARGET_TUPLE}-strip

sh configure.sh

cd ffmpeg
make -j4 || exit 1
make install || exit 1

rm -f libavcodec/inverse.o libavcodec/log2_tab.o libavformat/log2_tab.o libswresample/log2_tab.o

$CC -lm -lz -shared --sysroot=$SYSROOT -Wl,--no-undefined -Wl,-z,noexecstack $EXTRA_LDFLAGS libavutil/*.o libavutil/arm/*.o libavcodec/*.o libavcodec/arm/*.o libavformat/*.o libswresample/*.o libswresample/arm/*.o libswscale/*.o -o $PREFIX/libffmpeg.so

cp $PREFIX/libffmpeg.so $PREFIX/libffmpeg-debug.so
$STRIP --strip-unneeded $PREFIX/libffmpeg.so
