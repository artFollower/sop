package com.skycloud.oa.comm.weight;

import java.util.Enumeration;

import javax.comm.CommPortIdentifier;

import org.apache.log4j.Logger;

public class Put
{
    private static Logger LOG = Logger.getLogger(Put.class);
    
    public String reRead()
    {
        //读取数据  
        SerialBean.ReadPort();
        String result = SerialBean.result.trim(); //我这里temp是形如 wn125.000kg 的数据  
        LOG.debug("当前读数[" + result + "]");
        return result;
    }
    
    //响应开始称重  
    public int startWeight(String num)
    {
        if (SerialBean.openSignal != SerialBean.openSignal_Success)
        {
            //打印当前所有串口
            listPortChoices();
            
            LOG.debug("响应开始称重");
            SerialBean SB = new SerialBean(num);
            SB.Initialize();
            
            //发送指令R，仪器发送一次净重数据  
            SerialBean.WritePort("XON");
        }
        if (SerialBean.openSignal != SerialBean.openSignal_Success)
        {
            LOG.error("comm称重启动失败");
        }
        return SerialBean.openSignal; //返回初始化信息  
    }
    
    //响应停止称重  
    public void endWeight()
    {
        //发送指令R，仪器发送一次净重数据  
        SerialBean.WritePort("XOFF");
        
        //关闭输入、输出流  
        SerialBean.CloseStream();
        
        SerialBean.ClosePort(); //关闭串口  
        
        SerialBean.serialPort = null;
        SerialBean.portId = null;
        
        SerialBean.result = "";
        
    }
    
    /** 
      * 将形如 wn125.000kg 格式的重量转换为 125.000 (kg)(四舍五入，小数点后保留两位) 
     */
    public String change(String source)
    {
        Double result = 0.0;
        String s1 = source.substring(2, 9);
        try
        {
            result = Double.parseDouble(s1);
            result = Arith.round(result, 2);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
        
        return result.toString();
    }
    
    public String writeContinue()
    {
        //发送指令R，仪器发送一次净重数据  
       // SerialBean.WritePort("XON");
        
        //读取数据  
        SerialBean.ReadPort();
        String temp = SerialBean.result.trim(); //我这里temp是形如 wn125.000kg 的数据  
        while (true)
        {
            try
            {
                Thread.sleep(350);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            //读取数据  
            SerialBean.ReadPort();
            temp = SerialBean.result.trim(); //我这里temp是形如 wn125.000kg 的数据  
           // System.out.println("当前读数[" + temp + "]");
            parseData(temp);
        }
    }
    
    
    private long parseData(String temp)
    {
    	long result = 0;
    	
    	if(null != temp && !"".equalsIgnoreCase(temp)){
    		String[] arr = temp.split("[ ][ ]*");
    		long tempValue=0;
    		
    		for(String a : arr){
    			tempValue = toLong(a);
    			if(tempValue > result){
    				result = tempValue;
    			}
    		}
    	}
    	
    	System.out.println("{"+result+"}");
    	return result;
    }
    
    public static Long toLong(Object value) {

		try {
			if (value == null || "".equals(value)) {
				return 0L;
			}
			return Long.valueOf(value.toString());
		} catch (Exception e) {
			return 0L;
		}

	}
    @SuppressWarnings("rawtypes")
    public void listPortChoices()
    {
        CommPortIdentifier portId;
        Enumeration en = CommPortIdentifier.getPortIdentifiers();
        while (en.hasMoreElements())
        {
            portId = (CommPortIdentifier)en.nextElement();
            LOG.debug("comm portName:" + portId.getName());
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL)
            {
                LOG.debug("comm portSerialName:" + portId.getName());
            }
        }
    }
}