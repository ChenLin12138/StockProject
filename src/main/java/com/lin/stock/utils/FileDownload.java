package com.lin.stock.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

	public static void downloadFile(String fileUrl, String fileName){
		
		try(InputStream in = new URL(fileUrl).openStream()){
			Files.copy(in, Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void downloadWithNIO(String fileUrl, String fileName) throws IOException {
		
		URL url = new URL(fileUrl);
		
		try(
			ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
			FileOutputStream fileOutputStream = new FileOutputStream(fileName);
			FileChannel fileChannel = fileOutputStream.getChannel();
			){
			fileOutputStream.getChannel()
			  .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void downloadWithAsyncHttpClient(String fileUrl, String fileName){
  
        try(
        	FileOutputStream stream = new FileOutputStream(fileName);
        	AsyncHttpClient client = Dsl.asyncHttpClient();
        		){
        	 client.prepareGet(fileUrl)
             .execute(new AsyncCompletionHandler<FileOutputStream>() {

                 @Override
                 public State onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {
                     stream.getChannel()
                         .write(bodyPart.getBodyByteBuffer());
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
