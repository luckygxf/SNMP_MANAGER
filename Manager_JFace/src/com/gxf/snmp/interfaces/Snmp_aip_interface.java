package com.gxf.snmp.interfaces;

import java.util.List;

public interface Snmp_aip_interface {
	/**
	 * 根据ip,oid返回get消息
	 * @param ip
	 * @param oid
	 * @return
	 */
	public String get(String ip, String oid_str);
	
	/**
	 * 根据ip,oid和新值设置oid节点的值
	 * @param ip
	 * @param oid_str
	 * @param value_new
	 */
	public void set(String ip, String oid_str, String value_new);
	
	/**
	 * 根据ip,oid返回getNext消息
	 * @param ip
	 * @param oid_str
	 * @return
	 */
	public String getNext(String ip, String oid_str);
	
	/**
	 * 根据ip,oid获取bulk消息
	 * @param ip
	 * @param oid_str
	 * @return
	 */
	public List<String> getBulk(String ip, String oid_str);
	
	/**
	 * snmp v3版get api
	 * @param ip
	 * @param oid_str
	 * @return
	 */
	public String getV3(String ip, String oid_str);
	
	/**
	 * 向ip地址发送消息，这里利用发送trap消息来发送任意消息
	 * @param ip
	 * @param message
	 */
	public void sendMessage(String ip, String message);
}
