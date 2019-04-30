package com.ez08.trade.net;


import java.io.File;
import java.io.FileOutputStream;
import java.net.InetSocketAddress;
import java.util.Hashtable;
import java.util.List;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

public class ClientTest {

    private static EventLoopGroup mWorkerGroup;
    private static Bootstrap mBootstrap;
    private static ChannelHandlerContext mCtx;

    private static byte verifySn = 100;
    private static byte verifySn1 = 101;


    //    static int port = 60010;
//    static String host = "127.0.0.1";
    static int port = 35502;
    static String host = "220.181.152.112";

    public static void main(String[] args) {
        connect();
    }

    public static void init() {
        ChannelInitializer<SocketChannel> initializer = new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel arg0) {
                ChannelPipeline pipeline = arg0.pipeline();
                pipeline.addLast("encoder", new NetPackageEncoder());
//                pipeline.addLast("idleStateHandler", new IdleStateHandler(30, 1, 0, TimeUnit.SECONDS));
                pipeline.addLast(new EzMessageDecoder(),
                        new PackageClientHandler());    //	PackageClientHandler处理激活、数据接收、断开连接等事件
            }
        };
        try {
            if (null == mWorkerGroup) {
                mWorkerGroup = new NioEventLoopGroup();
            }
            if (null == mBootstrap) {
                mBootstrap = new Bootstrap();
                mBootstrap.group(mWorkerGroup)
                        .channel(NioSocketChannel.class)
//                        .option(ChannelOption.SO_KEEPALIVE, true)
//                        .option(ChannelOption.ALLOW_HALF_CLOSURE, true)
                        .handler(initializer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean connect() {
        init();

        try {
            ChannelFuture cf = mBootstrap.connect(new InetSocketAddress(host, port));
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture f) throws Exception {
                    if (f.isSuccess()) {
                        System.out.println("开始建立连接....已完成，连接成功");
                    } else {
                        System.out.println("开始建立连接....，连接失败");
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("开始建立连接....，连接失败1");

        }
        return true;
    }


    public static class PackageClientHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            System.out.println("channelActive");
//            byte[] test = {verifySn, 0, 29, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
//                    , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                    0, 0, 0, 0, 20, 0, 0, 0, 10, 0, 0, 0};

//            byte[] test = {1,2,2,3,4,5,5,8,8,6,7,7,8};
            byte[] test = NativeTools.getVerifyCodePackFromJNI(20, 10);
            ctx.writeAndFlush(test);
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("channelInactive");
            super.channelInactive(ctx);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("channelRead");
            super.channelRead(ctx, msg);

            String res = (String)msg;
            System.out.println(res);

//            byte[] pic = (byte[]) msg;
//
//            String path = System.getProperty("user.dir");
//            try {
//                FileOutputStream imageOutput = new FileOutputStream(new File(path + 1 + ".bmp"));
//                imageOutput.write(pic, 0, pic.length);
//                imageOutput.close();
//                System.out.println("Make Picture success,Please find image in " + path);
//            } catch (Exception ex) {
//                System.out.println("Exception: " + ex);
//                ex.printStackTrace();
//            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println("exceptionCaught");
            super.exceptionCaught(ctx, cause);
        }
    }

//
//    struct SVerificationCodeBody_ReqPicA
//    {
//        char        szId[VERIFICATION_CODE_SIZE__ID+1];        //含'\0'
//        DWORD        reserve;
//        DWORD        dwLife;            //寿命毫秒
//        BYTE        yType;            //0:默认(BMP)
//        DWORD        dwPicLen;        //
//        BYTE        bufPic[0];


    public static class EzMessageDecoder extends ByteToMessageDecoder {
        @Override
        protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
//            byteBuf.markReaderIndex();
//
//            byte[] bytes = new byte[2];
//            byteBuf.readBytes(bytes);
//
//            int bodyLength = byteBuf.readInt();
//
//            byte[] szId = new byte[21];
//            byteBuf.readBytes(szId);
//
//            int reserve = byteBuf.readInt();
//            int dwLife = byteBuf.readInt();
//            byte yType = byteBuf.readByte();
//            int dwPicLen = byteBuf.readInt();
//            byte[] right = Int2Bytes_LE(dwPicLen);
//            int finalInt = right[2] * 256 + right[3];
//
//            byte[] full = new byte[finalInt];
//            byteBuf.readBytes(full);
//
//            list.add(full);
//            System.out.println("EzMessageDecoder");

            byteBuf.markReaderIndex();
            if (byteBuf.readableBytes() < 6) {
                return;
            }
            //获取包头对象
            byte[] headdecoded = new byte[6];
            byteBuf.readBytes(headdecoded);
            String jsonhead = NativeTools.parseVerifyCodeHeadFromJNI(headdecoded);

            //获取包体对象
            int bodylength = 888;//需要换成json对应的值
            if (byteBuf.readableBytes() < bodylength) {
                return; // (2)
            }
            byte[] bodydecoded = new byte[bodylength];
            byteBuf.readBytes(bodydecoded);
            String jsonbody = NativeTools.parseVerifyCodeBodyAFromJNI(bodydecoded);
            //获取图片内容,在bodydecoded内容里面,bodydecoded+VerifyCodeBodySizeB偏移
            list.add(jsonbody);

        }
    }

    public static byte[] Int2Bytes_LE(int iValue) {
        byte[] rst = new byte[4];
        // 先写int的最后一个字节
        rst[0] = (byte) (iValue & 0xFF);
        // int 倒数第二个字节
        rst[1] = (byte) ((iValue & 0xFF00) >> 8);
        // int 倒数第三个字节
        rst[2] = (byte) ((iValue & 0xFF0000) >> 16);
        // int 第一个字节
        rst[3] = (byte) ((iValue & 0xFF000000) >> 24);
        return rst;
    }


    public static class NetPackageEncoder extends MessageToByteEncoder {

        @Override
        protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
            System.out.println("NetPackageEncoder");
            byteBuf.writeBytes((byte[]) o);
        }
    }
}

