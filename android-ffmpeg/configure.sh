#!/bin/sh
#
# configure.sh, a bash script to update ffmpeg code and configure ffmpeg.
#
# Copyright (c) 2012 WuMin <txgcwm@gmail.com>
#



if [ -d ffmpeg ]; then
	cd ffmpeg
	make clean
	git reset --hard origin
	git pull origin master
	git checkout -B master
	patch -p1 <../android-ffmpeg.patch
else
  	git clone git://source.ffmpeg.org/ffmpeg.git ffmpeg
  	cd ffmpeg
	patch -p1 <../android-ffmpeg.patch
fi

git log | head -n 1 | awk '{print $2}' > ../ffmpeg-version

CFLAGS="-O3 -Wall -marm -pipe -fpic -fasm \
  -finline-limit=300 -ffast-math \
  -fstrict-aliasing -Werror=strict-aliasing \
  -fmodulo-sched -fmodulo-sched-allow-regmoves \
  -Wno-psabi -Wa,--noexecstack \
  -D__ARM_ARCH_5__ -D__ARM_ARCH_5E__ -D__ARM_ARCH_5T__ -D__ARM_ARCH_5TE__ \
  -DANDROID -DNDEBUG"

FFMPEG_FLAGS="--target-os=linux \
  --arch=${PLATFORM_SHORT_ARCH} \
  --enable-cross-compile \
  --cross-prefix=${TARGET_TUPLE}- \
  --enable-shared \
  --disable-symver \
  --disable-doc \
  --disable-ffplay \
  --disable-ffmpeg \
  --disable-ffprobe \
  --disable-ffserver \
  --disable-avdevice \
  --disable-avfilter \
  --disable-encoders  \
  --disable-encoders \
  --disable-muxers \
  --disable-bsfs \
  --disable-filters \
  --disable-devices \
  --disable-everything \
  --enable-protocols  \
  --enable-parsers \
  --enable-demuxers \
  --disable-demuxer=sbg \
  --enable-decoders \
  --enable-network \
  --enable-swscale  \
  --enable-asm \
  --enable-version3"

sh ./configure $FFMPEG_FLAGS --prefix=$PREFIX --extra-cflags="$CFLAGS $EXTRA_CFLAGS" --extra-ldflags="$EXTRA_LDFLAGS" | tee $PREFIX/configuration.txt
cp config.* $PREFIX
[ $PIPESTATUS == 0 ] || exit 1
