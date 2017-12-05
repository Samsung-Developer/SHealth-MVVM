#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_local_sapl_dev_shealth_mvvm_SplashActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
