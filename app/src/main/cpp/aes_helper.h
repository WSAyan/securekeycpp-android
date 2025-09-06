#pragma once

#include <string>
#include <vector>

namespace AESHelper {
    std::vector <uint8_t> generateRandomKey(size_t length);

    std::string encryptAES(const std::string &plaintext, const std::vector <uint8_t> &key);
}
