#include "aes_helper.h"
#include <random>
#include <sstream>
#include <iomanip>
#include <cstring>

extern "C" {
#include "aes.h"
}

namespace AESHelper {

    std::vector <uint8_t> generateRandomKey(size_t length) {
        std::vector <uint8_t> key(length);
        std::random_device rd;
        std::mt19937 gen(rd());
        std::uniform_int_distribution<> dis(0, 255);
        for (size_t i = 0; i < length; i++) {
            key[i] = static_cast<uint8_t>(dis(gen));
        }
        return key;
    }

    std::string encryptAES(const std::string &plaintext, const std::vector <uint8_t> &key) {
        if (!(key.size() == 16 || key.size() == 24 || key.size() == 32)) {
            throw std::runtime_error("Invalid AES key size");
        }

        // Pad plaintext to 16 bytes
        size_t paddedSize = ((plaintext.size() + 15) / 16) * 16;
        std::vector <uint8_t> input(paddedSize, 0);
        std::memcpy(input.data(), plaintext.data(), plaintext.size());

        std::vector <uint8_t> output(paddedSize, 0);

        struct AES_ctx ctx;
        AES_init_ctx(&ctx, key.data());
        std::memcpy(output.data(), input.data(), paddedSize);
        AES_ECB_encrypt(&ctx, output.data());

        // Convert to hex string
        std::ostringstream hexStream;
        for (uint8_t byte: output) {
            hexStream << std::hex << std::setw(2) << std::setfill('0') << (int) byte;
        }
        return hexStream.str();
    }
}
