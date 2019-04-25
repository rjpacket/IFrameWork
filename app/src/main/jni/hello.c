//
// Created by rjp on 2019/4/25.
//

#include "com_rjp_fastframework_Hello.h"

JNIEXPORT jstring JNICALL Java_com_rjp_fastframework_Hello_sayHi
  (JNIEnv * env, jclass jclass1){
  return (*env) -> NewStringUTF(env, "from c");
  }