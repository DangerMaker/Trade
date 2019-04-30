#include <jni.h>
#include <android/log.h>
#include <string>
#include "TRC4.h"
#include "Packet_VerificationCode.h"
#include "Packet_TradeBase.h"
#include "GenerateKey.h"
#include "cBase64.h"

#define LOGI(...) \
  ((void)__android_log_print(ANDROID_LOG_INFO, "hello-libs::", __VA_ARGS__))


unsigned char* as_unsigned_char_array(JNIEnv *env, jbyteArray array,int &outlength)
{
    outlength = env->GetArrayLength (array);
    unsigned char* buf = new unsigned char[outlength];
    env->GetByteArrayRegion(array, 0, outlength, reinterpret_cast<jbyte*>(buf));

    return buf;
}


jbyteArray as_byte_array(JNIEnv *env, unsigned char* buf, int len)
{
    jbyteArray array = env->NewByteArray(len);
    env->SetByteArrayRegion(array, 0, len, reinterpret_cast<jbyte*>(buf));

    return array;
}

extern "C" JNIEXPORT jbyteArray JNICALL
 Java_com_ez08_trade_net_NativeTools_getVerifyCodePackFromJNI(JNIEnv* env,
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

//  char        szId[VERIFICATION_CODE_SIZE__ID+1];        //含'\0'
// #define        VERIFICATION_CODE_SIZE__CODE            (size_t(8))
//     char        szCode[VERIFICATION_CODE_SIZE__CODE+1];    //含'\0'
//     DWORD        dwCheckId;
//     DWORD        reserve;

 extern "C" JNIEXPORT jbyteArray JNICALL
 Java_com_ez08_trade_net_NativeTools_getVerifyCodeCheckFromJNI(JNIEnv* env,
 jobject /* this */,
 jbyteArray szId,
 jbyteArray code,
 int checkId,
 int arg0
 )
 {
        int outlength1;
        unsigned char*  szIdBuffer = as_unsigned_char_array(env,szId,outlength1);
        int outlength2;
        unsigned char*  codeBuffer = as_unsigned_char_array(env,szId,outlength2);

        DWORD headSize = sizeof(SVerificationCodeHead);
        DWORD bodySize = sizeof(SVerificationCodeBody_Check);

        SVerificationCodeHead *pHead = (SVerificationCodeHead *)new BYTE[headSize];
        memset(pHead, 0, headSize);
        pHead->wPid = VERIFICATION_CODE_PID__CHECK_CODE;
        pHead->dwBodyLen = bodySize;

        SVerificationCodeBody_Check *pBody = (SVerificationCodeBody_Check *)new BYTE[bodySize];
        memset(pBody, 0, bodySize);
        //todo
        memcpy(pBody->szId,szIdBuffer, sizeof(pBody->szId));
        memcpy(pBody->szCode,codeBuffer, sizeof(pBody->szCode));
        pBody->dwCheckId = checkId;
        pBody->reserve = arg0;
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


extern "C" JNIEXPORT jstring JNICALL
Java_com_ez08_trade_net_NativeTools_parseVerifyCodeHeadFromJNI(JNIEnv* env,
jobject /* this */,
jbyteArray buffer
)
{
    int outlength;
    unsigned char*  bytebuffer = as_unsigned_char_array(env,buffer,outlength);
    SVerificationCodeHead *pHead = (SVerificationCodeHead *)bytebuffer;
    std::string jsonstr = pHead->toJSON();
    return env->NewStringUTF(jsonstr.c_str());
}



extern "C" JNIEXPORT jstring JNICALL
Java_com_ez08_trade_net_NativeTools_parseVerifyCodeBodyAFromJNI(JNIEnv* env,
jobject /* this */,
jbyteArray buffer
)
{
       int outlength;
       unsigned char*  bytebuffer = as_unsigned_char_array(env,buffer,outlength);
       SVerificationCodeBody_ReqPicA *pBody = (SVerificationCodeBody_ReqPicA *)bytebuffer;
       int base64len = (outlength/3+1)*4+1;
       char *basestr = new char[base64len];
       base64_encode((const char *)(bytebuffer+sizeof(SVerificationCodeBody_ReqPicA)),(const long)pBody->dwPicLen,basestr);         //
       std::string jsonstr = pBody->toJSON(basestr);
         //
       delete[] basestr;
       return env->NewStringUTF(jsonstr.c_str());
}

//生成交互秘钥使用的函数
static CGenerateKey _generateKey;
static char *_mx = NULL;

char * genPublicKey() {
    char *gx = NULL;
    if (_mx) _generateKey.CBN_Free(_mx); _mx = NULL;

    if (!_generateKey.CBN_CreateLocalKey(_mx, gx)) {
        return NULL;
    }
    if (_mx == NULL || gx == NULL) {
        if(_mx)  _generateKey.CBN_Free(_mx); _mx = NULL;
        if(gx) _generateKey.CBN_Free(gx); gx = NULL;
        return NULL;
    }
    return gx;
}


extern "C" JNIEXPORT jbyteArray JNICALL
Java_com_ez08_trade_net_NativeTools_genTradePacketKeyExchangePackFromJNI(JNIEnv* env,
jobject /* this */
)
{
       char *gx = genPublicKey();
       DWORD headSize = sizeof(STradeBaseHead);
       DWORD bodySize = sizeof(STradePacketKeyExchange) + (DWORD)strlen(gx) + 1;;

       STradeBaseHead *pHead = (STradeBaseHead *)new BYTE[headSize];
       memset(pHead, 0, headSize);
       pHead->wPid = PID_TRADE_KEY_EXCHANGE;
       pHead->dwBodyLen = bodySize;

       STradePacketKeyExchange *pBody = (STradePacketKeyExchange *)new BYTE[bodySize];
       memset(pBody, 0, bodySize);
       pBody->dwIP = 0x0;//
       pBody->btUseRC4 = 1;//
       //
       int lenall = headSize + bodySize;
       BYTE * buffall = new BYTE[lenall];
       memcpy(buffall,pHead,headSize);
       memcpy(buffall+headSize,pBody,sizeof(STradePacketKeyExchange));
       memcpy(buffall+headSize+sizeof(STradePacketKeyExchange),gx,strlen(gx));
       //
       jbyteArray jbarray = as_byte_array(env,buffall,lenall);
       delete [] buffall; buffall = NULL;
       delete [] pHead; pHead = NULL;
       delete [] pBody; pBody = NULL;
       return jbarray;
}


extern "C" JNIEXPORT jstring JNICALL
Java_com_ez08_trade_net_NativeTools_parseTradeHeadFromJNI(JNIEnv* env,
jobject /* this */,
jbyteArray buffer
)
{
    int outlength;
    unsigned char*  bytebuffer = as_unsigned_char_array(env,buffer,outlength);
    STradeBaseHead *pHead = (STradeBaseHead *)bytebuffer;
    std::string jsonstr = pHead->toJSON();
    return env->NewStringUTF(jsonstr.c_str());
}


extern "C" JNIEXPORT jstring JNICALL
Java_com_ez08_trade_net_NativeTools_parseTradePacketKeyExchangeFromJNI(JNIEnv* env,
jobject /* this */,
jbyteArray buffer
)
{
    int outlength;
    unsigned char*  bytebuffer = as_unsigned_char_array(env,buffer,outlength);
    STradePacketKeyExchange *pBody = (STradePacketKeyExchange *)bytebuffer;
    //
    DWORD nGXLen = outlength - sizeof(STradePacketKeyExchange);
    //
    char *inGX = new char[nGXLen+1];
    memset(inGX, 0, nGXLen+1);
    memcpy(inGX,bytebuffer+sizeof(STradePacketKeyExchange),nGXLen);
    //
    std::string jsonstr = pBody->toJSON(inGX);
    delete[] inGX;inGX = NULL;
    return env->NewStringUTF(jsonstr.c_str());
}


extern "C" JNIEXPORT jbyteArray JNICALL
Java_com_ez08_trade_net_NativeTools_genTradeHeartPackFromJNI(JNIEnv* env,
jobject /* this */
)
{
       DWORD headSize = sizeof(STradeBaseHead);
       STradeBaseHead *pHead = (STradeBaseHead *)new BYTE[headSize];
       memset(pHead, 0, headSize);
       pHead->wPid = 10;
       pHead->dwBodyLen = 0;

       //
       int lenall = headSize;
       BYTE * buffall = new BYTE[lenall];
       memcpy(buffall,pHead,headSize);

       jbyteArray jbarray = as_byte_array(env,buffall,lenall);
       delete [] buffall; buffall = NULL;
       delete [] pHead; pHead = NULL;
       return jbarray;
}

