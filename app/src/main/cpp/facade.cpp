#include <jni.h>
#include <string>
#include <iostream>
#include <vector>
#include <cstdint>
#include <sstream>
#include <iterator>
#include "xxtea.cpp"

std::string getData(int operationId, const std::string& key, const std::string& data) {
    switch (operationId) {
        // Encrypting
        case 1: {
            auto encryptedBytes = xxtea::encrypt(data, key);

            std::stringstream result;

            std::copy(
                    encryptedBytes.begin(),
                    encryptedBytes.end(),
                    std::ostream_iterator<std::uint32_t>(result, " ")
            );

            return result.str();
        }

            // Decrypting
        case 2: {
            std::istringstream inputStream(data);

            std::vector<std::uint32_t> bytesToDecrypt{
                    std::istream_iterator<std::uint32_t>(inputStream),
                    std::istream_iterator<std::uint32_t>()
            };

            return xxtea::decrypt(bytesToDecrypt, key);
        }

        default: return "";
    }
}

extern "C" jstring
Java_com_wsayan_secureapikey_ApiKeyExtractor_getApiKey(
        JNIEnv *env,
        jobject /* this */,
        jint operationId,
        jstring source
) {
    std::string key = "c}><EQ~72R<jmz8PGGx5@qS[f^0=aB~N8)F7U+}~QD:5mdZ{+A{ov@Fe$/Bx=Pl=";
    std::string data = env->GetStringUTFChars(source, nullptr);

    std::string result = getData(operationId, key, data);
    return env->NewStringUTF(result.c_str());
}