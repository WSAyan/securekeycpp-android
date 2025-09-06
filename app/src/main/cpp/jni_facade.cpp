#include <jni.h>
#include <string>
#include "aes_helper.h"

extern "C"
JNIEXPORT jstring JNICALL
Java_com_wsayan_secureapikey_NativeAuthKey_generateAuthKey(JNIEnv* env, jobject /* this */) {
    try {
        auto key = AESHelper::generateRandomKey(16); // AES-128
        std::string authKey = AESHelper::encryptAES("MySecretPayload", key);
        return env->NewStringUTF(authKey.c_str());
    } catch (...) {
        return env->NewStringUTF("");
    }
}
