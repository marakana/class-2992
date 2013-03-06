LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := LogNative
LOCAL_SRC_FILES := LogNative.cpp
LOCAL_LDLIBS += -llog

include $(BUILD_SHARED_LIBRARY)
