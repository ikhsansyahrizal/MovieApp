#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring

JNICALL
Java_com_example_movieapp_core_datasource_RemoteDataSourceImp_getEncryptedKey(JNIEnv *env, jobject object) {
    std::string encrypted_key = "api-key";
    return env->NewStringUTF(encrypted_key.c_str());
}