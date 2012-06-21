/*
* Created on 2003-12-14 by mshen
*/

package test;
import static org.junit.Assert.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import auction.*;


/** 
  *@author mshen
*/
public class SimpleTest extends BaseTest{
	private HttpClient httpclient;
	private String kindId = "10";
	@Before
    public void setUp() throws Exception {
		//�������ļ��л�ȡkindDaoʵ��
		httpclient = new DefaultHttpClient(new ThreadSafeClientConnManager());
		TestLogin testLogin = new TestLogin();
		testLogin.loginToSystem(httpclient, "tomcat", "tomcat", "a12345");		
    }
	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void TestLogin() 
	{
		TestLogin testLogin = new TestLogin();
		try{
			HttpEntity entityLogin = testLogin.loginToSystem(httpclient, "tomcat", "tomcat", "a12345");
			String content = EntityUtils.toString(entityLogin,encoding);
			Document doc = Jsoup.parse(content);
			assertTrue(doc.getElementsByTag("table").first().text().contains("��ӭ����tomcat")); 
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			httpclient.getConnectionManager().shutdown();
		}
	}
	
	@Test
	public void TestKindList()
	{
		TestMgrKind testMgeKind = new TestMgrKind();
		try{
			HttpEntity entityKindList = testMgeKind.kindList(httpclient);
			String content = EntityUtils.toString(entityKindList,encoding);
			Document doc = Jsoup.parse(content);
			assertEquals(doc.getElementsByTag("table").get(3).getElementsByTag("tr").size(),7);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			httpclient.getConnectionManager().shutdown();
		}
	}
	
	@Test
	public void TestAddKind()
	{
		TestMgrKind testMgeKind = new TestMgrKind();
		try{
			HttpEntity entityAddKind = testMgeKind.addKind(httpclient, "httpclient��һ������", "��һ�����Ե�������Ϣ", "a12345");
			String content = EntityUtils.toString(entityAddKind,encoding);
			Document doc = Jsoup.parse(content);
			assertTrue(doc.getElementsByTag("table").get(3).text().contains("httpclient��һ������"));
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			httpclient.getConnectionManager().shutdown();
		}		
	}
	
	@Test
	public void TestUpdateKind()
	{
		TestMgrKind testMgeKind = new TestMgrKind();
		try{
			HttpEntity entityUpdateKind = testMgeKind.updateKind(httpclient, kindId, "httpclient��һ�����Ը���", "��һ�����Ե�������Ϣ����", "a12345");
			String content = EntityUtils.toString(entityUpdateKind,encoding);
			Document doc = Jsoup.parse(content);
			assertTrue(doc.getElementsByTag("table").get(3).text().contains("httpclient��һ�����Ը���"));
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			httpclient.getConnectionManager().shutdown();
		}
	}
	
	@Test
	public void TestDelKind()
	{
		TestMgrKind testMgeKind = new TestMgrKind();
		try{
			HttpEntity entityDelKind = testMgeKind.delKind(httpclient, kindId);
			String content = EntityUtils.toString(entityDelKind,encoding);
			Document doc = Jsoup.parse(content);
			assertTrue(doc.text().contains("ɾ���ɹ�"));
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			httpclient.getConnectionManager().shutdown();
		}
	}
}