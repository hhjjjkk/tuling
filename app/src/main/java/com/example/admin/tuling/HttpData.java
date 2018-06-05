package com.example.admin.tuling;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

/**
 * 异步请求
 */
//AsyncTask有四个重要的回调方法，分别是：onPreExecute、doInBackground, onProgressUpdate 和 onPostExecute。
public class HttpData extends AsyncTask<String, Void, String>{

	private HttpClient mHttpClient; //HttpClient请求
	private HttpGet mHttpGet;	      //请求方式
	private HttpResponse mHttpResponse; //发送请求
	private HttpEntity mHttpEntity; //创建httpentity实体
	private InputStream in;
	private HttpGetDataListener listener; //通过构造方法传递
	private String url; //地址
	//通过构造方法来传递url
	public HttpData(String url,HttpGetDataListener listener) {
		this.url = url;
		this.listener = listener;
	}


	/**
	 * 复写回调方法 获取数据
	 * @param params
	 * @return
	 */
	@Override
	protected String doInBackground(String... params) {
		try {
			mHttpClient = new DefaultHttpClient(); //实例化
			mHttpGet = new HttpGet(url);  //通过get请求
			mHttpResponse = mHttpClient.execute(mHttpGet);//发送请求
			mHttpEntity = mHttpResponse.getEntity();//通过HttpResponse获取数据
			in = mHttpEntity.getContent();//获取内容
			BufferedReader br = new BufferedReader(new InputStreamReader(in)); //缓存区获取
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception e) {
		}
		
		return null;
	}
	//通过回调的方式来获取数据
	@Override
	protected void onPostExecute(String result) {
		listener.getDataUrl(result); //通过listener返回数据
		super.onPostExecute(result);
	}
}
