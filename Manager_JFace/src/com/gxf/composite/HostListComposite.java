package com.gxf.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;

import com.gxf.snmp.impl.Snmp_api;
import com.gxf.snmp.interfaces.Snmp_aip_interface;
import com.gxf.util.AllIP;

public class HostListComposite extends Composite {
	private List listOfHostName ;
	private Label label_detail ;
	private Label label_hostName ;
	private Label label_detail_icon ;
	private Snmp_aip_interface snmpApi = new Snmp_api();
	private AllIP ipTool = new AllIP();

	public HostListComposite(Composite parent, int style) {
		super(parent, style);

		listOfHostName = new List(this, SWT.V_SCROLL | SWT.SINGLE);
		label_detail = new Label(this, SWT.NONE);
		label_hostName = new Label(this, SWT.NONE);
		label_detail_icon = new Label(this, SWT.NONE);
		
		label_hostName.setText("��������������");
		label_detail_icon.setText("������ϸ��Ϣ");
			
		label_hostName.setBounds(5, 10, 100, 30);
		listOfHostName.setBounds(5, 30, 600, 150);
		
		label_detail_icon.setBounds(5, 190, 100, 30);
		label_detail.setBounds(5, 210, 600, 40);
		label_detail.setText("������" + "...");
		
		//�����ݵ��ؼ�����
		init();
		
	}
	
	/**
	 * ���ؿؼ��ϵ�����
	 */
	public void init(){
		//ֻ������ipΪ�յ�ʱ������¼���
		if(AllIP.IPS == null || AllIP.IPS.size() == 0){
			//��ȡ��������������ip
			ipTool.getAllIP();			
		}
		for(int i = 0; i < AllIP.IPS.size(); i++)
			listOfHostName.add(AllIP.IPS.get(i));
		
		
		//���ʱ�������
		listOfHostName.addSelectionListener(new ListClickListener());
	}
	
	class ListClickListener implements SelectionListener{
		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			
			
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			//��ȡѡ��������ip
			String ipSelected[] = listOfHostName.getSelection();
			
			//����������������ʱ��oid
			String oid_osname = "1.3.6.1.2.1.1.5.0";
			String oid_startuptime = "1.3.6.1.2.1.1.3.0";
			
			//ͨ��snmp��ѯ������Ϣ
			String hostName = snmpApi.get(ipSelected[0], oid_osname);
			String startUpTime = snmpApi.get(ipSelected[0], oid_startuptime);
			
			String detail = "��������" + hostName + "\n" 
							+ "����ʱ�䣺"+ startUpTime;
			
			label_detail.setText(detail);
		}
		
	}
}
