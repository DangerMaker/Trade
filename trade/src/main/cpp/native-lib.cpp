#include <jni.h>
#include <android/log.h>
#include <string>
#include "TRC4.h"
#include "Packet_VerificationCode.h"

#define LOGI(...) \
  ((void)__android_log_print(ANDROID_LOG_INFO, "hello-libs::", __VA_ARGS__))

extern "C" JNIEXPORT jstring JNICALL
Java_trade_yc_com_yctradelib_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    u_int32_t buflen = 10;
    unsigned char * buffer = new  unsigned char[buflen];
    for(int i=0;i<10;i++)
    {
        buffer[i] = 'a'+i;
    }
    u_int32_t keylen = 16;
    unsigned char * key = new  unsigned char[keylen];
    for(int i=0;i<16;i++)
    {
        key[i] = 'h'+i;
    }

    TRC4(buffer, buflen, key, keylen);
    //
   std::string log = ascii2hex((char*)buffer,buflen);
    //LOGI("abc",log);
    //
    std::string temp = (char*)key;
    std::string hello2 = hello2 + "abcdefghij\r\n";
     hello2 = hello2 + "hijklmnopqrstuvw\r\n加密结果：";
     hello2 = hello2 + log;
    return env->NewStringUTF(hello2.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_trade_yc_com_yctradelib_NativeTool_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    u_int32_t buflen = 10;
    unsigned char * buffer = new  unsigned char[buflen];
    for(int i=0;i<10;i++)
    {
        buffer[i] = 'a'+i;
    }
    u_int32_t keylen = 16;
    unsigned char * key = new  unsigned char[keylen];
    for(int i=0;i<16;i++)
    {
        key[i] = 'h'+i;
    }

    TRC4(buffer, buflen, key, keylen);
    //
   std::string log = ascii2hex((char*)buffer,buflen);
    //LOGI("abc",log);
    //
    std::string temp = (char*)key;
    std::string hello2 = hello2 + "abcdefghij\r\n";
     hello2 = hello2 + "hijklmnopqrstuvw\r\n加密结果：";
     hello2 = hello2 + log;
    return env->NewStringUTF(hello2.c_str());
}

unsigned char* as_unsigned_char_array(JNIEnv *env, jbyteArray array)
{
    int len = env->GetArrayLength (array);
    unsigned char* buf = new unsigned char[len];
    env->GetByteArrayRegion(array, 0, len, reinterpret_cast<jbyte*>(buf));

    return buf;
}


jbyteArray as_byte_array(JNIEnv *env, unsigned char* buf, int len)
{
    jbyteArray array = env->NewByteArray(len);
    env->SetByteArrayRegion(array, 0, len, reinterpret_cast<jbyte*>(buf));

    return array;
}


extern "C" JNIEXPORT jbyteArray JNICALL
Java_com_ez08_trade_net_NativeTool_getVerifyCodePackFromJNI(JNIEnv* env,
jobject /* this */,
int width,
int height
)
{
       DWORD headSize = sizeof(SVerificationCodeHead);
       DWORD bodySize = sizeof(SVerificationCodeBody_ReqPic);
       //DWORD bodySizeA = sizeof(SVerificationCodeBody_ReqPicA);

       SVerificationCodeHead *pHead = (SVerificationCodeHead *)new BYTE[headSize];
       memset(pHead, 0, headSize);
       pHead->wPid = VERIFICATION_CODE_PID__REQ_PIC;
       pHead->dwBodyLen = bodySize;

       SVerificationCodeBody_ReqPic *pBody = (SVerificationCodeBody_ReqPic *)new BYTE[bodySize];
       memset(pBody, 0, bodySize);
       pBody->dwWidth = width;//80;//SCALEDPI(80);
       pBody->dwHeight = height;//SCALEDPI(25);
       //
       int lenall = headSize + bodySize;
       BYTE * buffall = new BYTE[lenall];
       memcpy(buffall,pHead,headSize);
       memcpy(buffall+headSize,pBody,bodySize);
       //
       jbyteArray jbarray = as_byte_array(env,buffall,lenall);
       delete [] buffall; buffall = NULL;
       delete [] pHead; pHead = NULL;
       delete [] pBody; pBody = NULL;
       return jbarray;
}


