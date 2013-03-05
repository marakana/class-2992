/*
 * FibNative.c
 *
 *  Created on: Mar 5, 2013
 *      Author: marko
 */

#include "com_intel_fibnative_FibLib.h"

/* Plain C code */
long fib(long n) {
	if (n == 0)
		return 0;
	if (n == 1)
		return 1;
	return fib(n - 1) + fib(n - 2);
}

/* JNI Wrapper */
JNIEXPORT jlong JNICALL Java_com_intel_fibnative_FibLib_fibN(JNIEnv *env,
		jclass clazz, jlong n) {
	return (jlong)fib((long)n);
}

