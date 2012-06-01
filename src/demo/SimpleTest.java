/*
* Created on 2003-12-14 by mshen
*/

package demo;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.util.EntityUtils;


/** 
  *@author mshen
*/

public class SimpleTest extends BaseTest{
	public static void main(String[] args) throws IOException
	{
		//����һ��client�ͻ���
		HttpClient httpclient = new DefaultHttpClient(new ThreadSafeClientConnManager());
		TestLogin testLogin = new TestLogin();
		TestMgrKind testMgeKind = new TestMgrKind();
		try{
			//����Login
			HttpEntity entityLogin = testLogin.loginToSystem(httpclient, "tomcat", "tomcat", "a12345");
			if(entityLogin != null){
				if(EntityUtils.toString(entityLogin,encoding).contains("��ӭ����tomcat"))
				{
					System.out.println("yeah, Right!");
				}else{
					System.out.println("oh, no!");
				}
			}
			//�������Kind
			HttpEntity entityAddKind = testMgeKind.addKind(httpclient, "httpclient��һ������", "��һ�����Ե�������Ϣ", "a12345");
			if(entityAddKind != null){
				if(EntityUtils.toString(entityAddKind,encoding).contains("httpclient��һ������"))
				{
					System.out.println("yeah, add success!");
				}else{
					System.out.println("oh, no failed!");
				}
			}
			//����Kind list
			HttpEntity entityKindList = testMgeKind.kindList(httpclient);
			if(entityKindList != null){
				if(EntityUtils.toString(entityKindList,encoding).contains("����"))
				{
					System.out.println("yeah, right!");
				}else{
					System.out.println("oh, not found!");
				}
			}
			//����Update Kind
			HttpEntity entityUpdateKind = testMgeKind.updateKind(httpclient, "16", "httpclient��һ�����Ը���", "��һ�����Ե�������Ϣ����", "a12345");
			if(entityKindList != null){
				if(EntityUtils.toString(entityUpdateKind,encoding).contains("httpclient��һ�����Ը���"))
				{
					System.out.println("yeah, right!");
				}else{
					System.out.println("oh, not found!");
				}
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			//�ر����ӣ��ͷ���Դ
			httpclient.getConnectionManager().shutdown();
		}
	   }
}