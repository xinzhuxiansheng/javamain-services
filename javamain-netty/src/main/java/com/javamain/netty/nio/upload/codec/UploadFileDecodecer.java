package com.javamain.netty.nio.upload.codec;

import com.javamain.netty.nio.upload.FileDto;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class UploadFileDecodecer extends ByteToMessageDecoder {


    //请求上传
    //创建文件
    //将客户端数据写入本地磁盘
    //command 4  fileName 4 8
    //数据长度 + 数据
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes() < 8){
            return;
        }
        //command 4
        int command = in.readInt();

        FileDto fileDto = new FileDto();

        int fileNameLen = in.readInt();

        if(in.readableBytes() < fileNameLen){
            in.resetReaderIndex();
            return;
        }
        
        byte[] data = new byte[fileNameLen];
        in.readBytes(data);
        String fileName = new String(data);
        fileDto.setCommand(command);
        fileDto.setFileName(fileName);

        if(command == 2){
            int dataLen = in.readInt();
            if(in.readableBytes() < dataLen){
                in.resetReaderIndex();
                return;
            }
            byte[] fileData = new byte[dataLen];
            in.readBytes(fileData);
            fileDto.setBytes(fileData);
        }
        in.markReaderIndex();//10004
        out.add(fileDto);
    }
}
