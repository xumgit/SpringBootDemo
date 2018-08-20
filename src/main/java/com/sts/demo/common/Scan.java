package com.sts.demo.common;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.net.util.SubnetUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Scan {
	
	private static final Logger LOG = LogManager.getLogger(Scan.class);
	private final int ThreadPoolSize = 30;
	private ExecutorService executorService;
	
	public boolean detect() {
		boolean result = false;
		
		try {
			executorService = Executors.newFixedThreadPool(ThreadPoolSize);
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			if (networkInterfaces == null || !networkInterfaces.hasMoreElements()){
				return result;
			}
			while (networkInterfaces.hasMoreElements()){
				NetworkInterface networkInterface = networkInterfaces.nextElement();
				if (!networkInterface.isUp()){
					continue;
				} 
				
				List<InterfaceAddress> interfaceAddresses = networkInterface.getInterfaceAddresses();
				if ((interfaceAddresses == null) || interfaceAddresses.isEmpty()){
					continue;
				} 
				LOG.info("ip type is " + networkInterface.getName() + " ip displayname is " + networkInterface.getDisplayName() +
						" ip MTU is " + networkInterface.getMTU());

				for (InterfaceAddress interfaceAddress : interfaceAddresses){
					if (interfaceAddress == null){
						continue;
					} 
					
					InetAddress inetAddress = interfaceAddress.getAddress();
					if (inetAddress == null || inetAddress instanceof Inet6Address){
						continue;
					} 

					String hostAddress = inetAddress.getHostAddress();
                    short networkPrefixLength = interfaceAddress.getNetworkPrefixLength();
                    String subnet = hostAddress + "/" + String.valueOf(networkPrefixLength);
                    SubnetUtils subnetUtils = new SubnetUtils(subnet);
                    SubnetUtils.SubnetInfo subnetInfo = subnetUtils.getInfo();
                    String networkAddress = subnetInfo.getNetworkAddress();
                    //String scannerInput = networkAddress.substring(0, networkAddress.length() - 2);
                    String scannerInput = networkAddress;
                    String lowAddress = subnetInfo.getLowAddress();
                    String highAddress = subnetInfo.getHighAddress();
                    int lowAddr = Integer.parseInt(lowAddress.split("\\.")[3]);
                    int highAddr = Integer.parseInt(highAddress.split("\\.")[3]);
                    LOG.info("subnet="+subnet+" |lowAddr="+lowAddr + " |highAddr=" +highAddr);
                    //result = scanner(scannerInput, networkPrefixLength, lowAddr, highAddr, port, timeout, networkInterface.getDisplayName(), hostAddress);
  
				}
			}
			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	private boolean scanner(String subnet, short networkPrefixLength, int lowAddr, int highAddr, int port, int timeout, String networkInterfaceDisplayName, String hostAddress) {
		boolean result = false;
		
		//List<OnlineDevices> interfaceDevices = new ArrayList(); 
		
		List<String> ipAddressesList = listIpAddresses(subnet, networkPrefixLength, lowAddr, highAddr, port, timeout);
		/*for(String ipAddress: ipAddressesList){
			if(detectDevices(ipAddress, port, networkInterfaceDisplayName, hostAddress) && !result){
				result =  true;
			}
		}*/
		
		return result;

	}
	
	private List<String> listIpAddresses(String subnet, short networkPrefixLength, int lowAddr, int highAddr, int port, int timeout) {
		List ipAddressesList = null;

		try {
			Future ipAddressesListFuture = executorService.submit(new ListIpAddressesCallable(subnet, networkPrefixLength, lowAddr, highAddr, port, timeout));
			ipAddressesList = (List) ipAddressesListFuture.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage(), e);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage(), e);
		} catch (Exception e) {
			// TODO: handle exception
			LOG.error(e.getMessage(), e);
		}
		
		return ipAddressesList;
	}
	

	class ListIpAddressesCallable implements Callable{
		private String subnet;
		private short networkPrefixLength;
		private int lowAddr;
		private int highAddr;
		private int port;
		private int timeout;
		
		public ListIpAddressesCallable(String subnet, short networkPrefixLength, int lowAddr, int highAddr, int port, int timeout){
			this.subnet = subnet;
			this.networkPrefixLength = networkPrefixLength;
			this.lowAddr = lowAddr;
			this.highAddr = highAddr;
			this.port = port;
			this.timeout = timeout;
		}

		@Override
		public Object call() throws Exception {
			List<String> ipAddressesList = new ArrayList<String>();
			
			// if networkPrefixLength less than 24 bits, what wo do?
			//if (networkPrefixLength >= 24) {
				String[] subnetArr = subnet.split("\\.");
				subnet = subnetArr[0] + "." + subnetArr[1] + "." + subnetArr[2];
				for (int hostId = lowAddr; hostId <= highAddr; hostId++) {
		            if(subnet.startsWith("127.0")){
		            	continue;
		            }
					String ip = subnet + "." + hostId;
		            String ipAddr = isListening(ip, port, timeout);
		            if(ipAddr != null){
		            	ipAddressesList.add(ipAddr);
		            }  
		        }
			//} else {
				//LOG.info("networkPrefixLength less than 24 bits, need check again");
			//}
		
			return ipAddressesList;
		}
	}
	
	private String isListening(String ip, int port, int timeout) {
		Socket socket = null;
        try {
            socket = new Socket();
            LOG.info("before socket connect ip="+ip);
            InetSocketAddress inetSocketAddress = new InetSocketAddress(ip, port);
            socket.connect(inetSocketAddress, timeout);
            //no exception, then return ip
            LOG.info("after socket connect ip="+ip);
            return ip;
        }catch (Exception e) {
        	//e.printStackTrace();
        	LOG.info("failed socket connect ip="+ip);
        	return null;
        }finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
	}
		
}
