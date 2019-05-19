package com.lin.stock.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.ExecutionException;

import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.HttpResponseBodyPart;
import org.asynchttpclient.Response;

/**
 * @author Chen Lin
 * @date 2019-05-15
 */

public class FileDownload {
	
	public static int FILE_HEADER_SIZE = 108;

	public static void downloadFile(String fileUrl, String fileName){
		
		try(InputStream in = new URL(fileUrl).openStream()){
			if(in.available() > FILE_HEADER_SIZE) {
				Files.copy(in, Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean downloadWithNIO(String fileUrl, String fileName){
		
		boolean downloadSuccess = false;
		InputStream in = null;
		ReadableByteChannel readableByteChannel = null;
		FileOutputStream fileOutputStream = null;
		
		try {
			URL url = new URL(fileUrl);
			in = url.openStream();
			readableByteChannel = Channels.newChannel(in);
			if(in.available() > FILE_HEADER_SIZE) {
				//创建输出文件
				fileOutputStream = new FileOutputStream(fileName);
				fileOutputStream.getChannel()
				  .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
				downloadSuccess = true;
			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			if(null != in){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null != readableByteChannel){
				try {
					readableByteChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null != fileOutputStream){
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}	
		
		return downloadSuccess;
	}
	
	//这个原理机制暂时不清楚，效率也没上去，暂时我 不使用这个方法
	public static void downloadWithAsyncHttpClient(String fileUrl, String fileName){
  
        try(
        	FileOutputStream stream = new FileOutputStream(fileName);
        	AsyncHttpClient client = Dsl.asyncHttpClient();
        		){
        	 client.prepareGet(fileUrl)
             .execute(new AsyncCompletionHandler<FileOutputStream>() {

                 @Override
                 public State onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {
                	 
                	 if(FILE_HEADER_SIZE != bodyPart.length()) {
                		 stream.getChannel()
                         .write(bodyPart.getBodyByteBuffer()); 
                	 }
                     return State.CONTINUE;
                 }

                 @Override
                 public FileOutputStream onCompleted(Response response) throws Exception {
                     return stream;
                 }
             })
             .get();
        	
        }catch(ExecutionException e) {
        	e.printStackTrace();
        }catch(InterruptedException e) {
        	e.printStackTrace();
        }catch(IOException e) {
        	e.printStackTrace();
        }
	}
}
