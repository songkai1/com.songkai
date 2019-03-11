package com.songkai.configs;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SampleZkClient {

	private static final String connectString = "10.3.22.57:2181";
    private static final int sessionTimeout = 2000;

    /** 信号量，阻塞程序执行，用于等待zookeeper连接成功，发送成功信号 */
    /*一旦不加锁，会因为连接zookeeper需要10s，而程序执行需要5s，故程序执行到向zookeeper节点写数据时
    ，zookeeper还没有连接上，因此程序而报错
    */
    static final CountDownLatch connectedSemaphore = new CountDownLatch(1);
    ZooKeeper zkClient = null;
    Stat stat = new Stat();
    
    public SampleZkClient(){
        try {
			zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
			    @Override
			    public void process(WatchedEvent event) {
			        // 获取事件的状态
			        Event.KeeperState keeperState = event.getState();
			        Event.EventType eventType = event.getType();
			        // 如果是建立连接
			        if (Event.KeeperState.SyncConnected == keeperState) {
			            if (Event.EventType.None == eventType) {
			                // 如果建立连接成功，则发送信号量，让后续阻塞程序向下执行
			                System.out.println("zk 建立连接");
			                connectedSemaphore.countDown();
			            }
			        }
			    }
			});
			// 进行阻塞
			connectedSemaphore.await();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 创建节点
     * @throws KeeperException
     * @throws InterruptedException
     * 一、节点类型
     * 1.PERSISTENT                持久化节点
     * 2.PERSISTENT_SEQUENTIAL     顺序自动编号持久化节点，这种节点会根据当前已存在的节点数自动加 1
     * 3.EPHEMERAL                 临时节点， 客户端session超时这类节点就会被自动删除
     * 4.EPHEMERAL_SEQUENTIAL      临时自动编号节点
     * 二、
     * 
     */
    public void testCreate(String nodeName) throws KeeperException, InterruptedException {
        // 参数1：要创建的节点的路径 参数2：节点大数据 参数3：节点的权限 参数4：节点的类型
        String nodeCreated = zkClient.create(nodeName, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        //上传的数据可以是任何类型，但都要转成byte[]
        System.err.println(nodeCreated);
    }
    
    /**
     * 获取所有父节点名称
     * @throws Exception
     */
    public void getChildren(){
        try {
			List<String> children = zkClient.getChildren("/", true);
			for (String child : children) {
			    System.out.println(child);
			}
			Thread.sleep(Long.MAX_VALUE);
			zkClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 获取某个节点数据
     * @throws Exception
     */
    public String getNodeData(String nodeName) {
    	try {
			return new String(zkClient.getData(nodeName, true, null));
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * 给节点添加数据
     * @throws Exception
     */
    public void setNodeData() throws Exception {
    	//zkClient.setData("/test1", "我是修改数据后的第一个子目录/test1".getBytes(),-1);
    }
    
    public void setNodeAcl() throws Exception {
    	zkClient.setACL("/test1", ZooDefs.Ids.OPEN_ACL_UNSAFE, -1);
    }
    
}
