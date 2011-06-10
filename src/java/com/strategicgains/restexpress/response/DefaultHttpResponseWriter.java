/*
 * Copyright 2010, eCollege, Inc.  All rights reserved.
 */
package com.strategicgains.restexpress.response;

import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.nio.charset.Charset;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponse;

import com.strategicgains.restexpress.ContentType;
import com.strategicgains.restexpress.Request;
import com.strategicgains.restexpress.Response;
import com.strategicgains.restexpress.util.HttpSpecification;

/**
 * @author toddf
 * @since Aug 26, 2010
 */
public class DefaultHttpResponseWriter
extends BaseHttpResponseWriter
{
	@Override
	public void write(Channel ch, Request request, Response response)
	{
		HttpResponse httpResponse = new DefaultHttpResponse(HTTP_1_1, response.getResponseStatus());
		addHeaders(response, httpResponse);

		if (response.hasBody()
			&& HttpSpecification.isContentAllowed(response))
		{
			StringBuilder builder = new StringBuilder(response.getBody().toString());
			builder.append("\r\n");
	
			httpResponse.setContent(ChannelBuffers.copiedBuffer(builder.toString(), Charset.forName(ContentType.ENCODING)));
		}
	
		if (request.isKeepAlive())
	  	{
	  		// Add 'Content-Length' header only for a keep-alive connection.
			if (!response.hasHeader(CONTENT_LENGTH)
				&& HttpSpecification.isContentLengthAllowed(response))
	  		{
				httpResponse.setHeader(CONTENT_LENGTH, String.valueOf(httpResponse.getContent().readableBytes()));
	  		}
	
	  		ch.write(httpResponse).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
	  	}
		else
		{
			httpResponse.setHeader(CONNECTION, "close");
	
			// Close the connection as soon as the message is sent.
			ch.write(httpResponse).addListener(ChannelFutureListener.CLOSE);
		}
	}
}
