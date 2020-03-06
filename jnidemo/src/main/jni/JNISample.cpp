//
// Created by Dio on 2020/3/3.
//

#include "com_myself_jnidemo_JniUtils.h"

JNIEXPORT jstring JNICALL Java_com_myself_jnidemo_JniUtils_getJniString
        (JNIEnv *env, jclass) {
    // new 一个字符串，返回Hello World
    return env -> NewStringUTF("Hello World");
}