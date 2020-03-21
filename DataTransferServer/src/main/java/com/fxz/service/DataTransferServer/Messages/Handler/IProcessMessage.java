/**
 * @Copyright © 2018 fuxiuzhn Fts Team All rights reserved.
 * @Package: com.fxz.fts.processor 
 * @author: fuxiuzhan@163.com   
 * @date: 2018年8月30日 下午7:15:52 
 * 
 */
package com.fxz.service.DataTransferServer.Messages.Handler;

import java.io.IOException;

import com.fxz.service.DataTransferServer.Messages.BaseMessage;

import io.netty.channel.ChannelHandlerContext;

/**
 * @ClassName: IProcessMessage
 * @Description: TODO
 * @author: fuxiuzhan@163.com
 * @date: 2018年8月30日 下午7:15:52
 */

/** 
 * @ClassName: IProcessMessage 
 * @Description: 消息处理接口
 * @author: fuxiuzhan@163.com
 * @date: 2018年8月30日 下午7:16:54  
 */
public interface IProcessMessage {

	public void process(ChannelHandlerContext ctx, BaseMessage baseMessage) throws IOException;
}
