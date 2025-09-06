cmake_minimum_required(VERSION 4.0.2)

project("native-lib")

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

add_library(
        native-lib
        SHARED
        jni_facade.cpp
        aes_helper.cpp
        aes.c
)

find_library(log-lib log)

target_link_libraries(
        native-lib
        ${log-lib}
)