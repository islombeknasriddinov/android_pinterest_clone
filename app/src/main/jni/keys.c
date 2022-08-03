#include <jni.h>
JNIEXPORT jstring JNICALL
Java_com_example_pinterest_1clone_network_Server_getDevelopment(JNIEnv *env, jobject instance) {
    return (*env)->  NewStringUTF(env, "https://api.unsplash.com/");
}

JNIEXPORT jstring JNICALL
Java_com_example_pinterest_1clone_network_Server_getProduction(JNIEnv *env, jobject instance) {
    return (*env)->NewStringUTF(env, "https://api.unsplash.com/");
}

JNIEXPORT jstring JNICALL
Java_com_example_pinterest_1clone_network_Server_getAccessKey(JNIEnv *env, jobject instance) {
    return (*env)->NewStringUTF(env, "Xx9ZDZTlUn7YsfX-kKUaPtIl4HfBGW50qFH1UnIJsU8");
}

JNIEXPORT jstring JNICALL
Java_com_example_pinterest_1clone_network_Server_getClientId(JNIEnv *env, jobject instance) {
    return (*env)->NewStringUTF(env, "Client-ID");
}