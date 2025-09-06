#include <jni.h>
#include <string>
#include "aes_helper.h"

extern "C"
JNIEXPORT jstring JNICALL
Java_com_wsayan_secureapikey_utils_NativeAuthKey_generateAuthKey(JNIEnv* env, jobject) {
    try {
        auto key = AESHelper::generateRandomKey(16);
        std::string authKey = AESHelper::encryptAES("SECRET_API_KEY", key);
        return env->NewStringUTF(authKey.c_str());
    } catch (...) {
        return env->NewStringUTF("");
    }
}