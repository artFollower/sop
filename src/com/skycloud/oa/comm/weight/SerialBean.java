package com.skycloud.oa.comm.weight;

import java.io.*;

import javax.comm.*;

import org.apache.log4j.Logger;

/** 
* 
* This bean provides some basic functions to implement full duplex 
* information exchange through the serial port. 
* 
*/

public class SerialBean
{
    private static Logger LOG = Logger.getLogger(Put.class);
    
    public static String PortName;
    
    public static CommPortIdentifier portId;
    
    public static SerialPort serialPort;
    
    public static OutputStream out;
    
    public static InputStream in;
    
    //保存最新的读数结果  
    public static String result = "";
    
    public static int openSignal = 0;
    
    public static int openSignal_Success = 1;
    
    /** 
     * 
     * Constructor 
     * 
     * @param PortID the ID of the serial to be used. 1 for COM1, 
     * 2 for COM2, etc. 
     * 
     */
    public SerialBean(String PortID)
    {
        PortName = PortID;
        
    }
    
    /** 
    * 
    * This function initialize the serial port for communication. It starts a 
    * thread which consistently monitors the serial port. Any signal captured 
    * from the serial port is stored into a buffer area. 
    * 
    */
    public int Initialize()
    {
        openSignal = 1;
        
        try
        {
            portId = CommPortIdentifier.getPortIdentifier(PortName);
            
            try
            {
                serialPort = (SerialPort)portId.open("SimpleReadApp", 2000);
            }
            catch (PortInUseException e)
            {
                LOG.error(e);
                if (!SerialBean.portId.getCurrentOwner().equals("SimpleReadApp"))
                {
                    System.out.println("该串口被其它程序占用");
                    openSignal = 2; //该串口被其它程序占用  
                }
                else if (SerialBean.portId.getCurrentOwner().equals("SimpleReadApp"))
                {
                    openSignal = 1;
                    return openSignal;
                }
                
                return openSignal;
            }
            
            //Use InputStream in to read from the serial port, and OutputStream  
            //out to write to the serial port.  
            try
            {
                in = serialPort.getInputStream();
                out = serialPort.getOutputStream();
            }
            catch (IOException e)
            {
                LOG.error(e);
                openSignal = 3; //输入输出流错误  
                return openSignal;
            }
            
            //Initialize the communication parameters to 9600, 8, 1, none.
            try
            {
                serialPort.setSerialPortParams(9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            }
            catch (UnsupportedCommOperationException e)
            {
                LOG.error(e);
                System.out.println("参数不正确  ");
                openSignal = 4; //参数不正确  
                return openSignal;
            }
        }
        catch (NoSuchPortException e)
        {
            LOG.error(e);
            System.out.println("没有该串口  ");
            portId = null;
            openSignal = 5; //
            
            return openSignal;
        }
        
        // when successfully open the serial port, create a new serial buffer,  
        // then create a thread that consistently accepts incoming signals from  
        // the serial port. Incoming signals are stored in the serial buffer.  
        
        // return success information  
        
        return openSignal;
    }
    
    /** 
    * 
    * This function returns a string with a certain length from the incoming 
    * messages. 
    * 
    * @param Length The length of the string to be returned. 
    * 
    */
    
    public static void ReadPort()
    {
        SerialBean.result = "";
        int c;
        try
        {
            if (in != null)
            {
                while (in.available() > 0)
                {
                    c = in.read();
                    Character d = new Character((char)c);
                    SerialBean.result = SerialBean.result.concat(d.toString());
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            LOG.error(e);
        }
    }
    
    /** 
    * 
    * This function sends a message through the serial port. 
    * 
    * @param Msg The string to be sent. 
    * 
    */
    
    public static void WritePort(String Msg)
    {
        
        try
        {
            if (out != null)
            {
                for (int i = 0; i < Msg.length(); i++)
                {
                    out.write(Msg.charAt(i));
                }
            }
            
        }
        catch (IOException e)
        {
            e.printStackTrace();
            LOG.error(e);
        }
    }
    
    /** 
    * 
    * This function closes the serial port in use. 
    * 
    */
    public static void ClosePort()
    {
        if (null != serialPort)
        {
            serialPort.close();
        }
    }
    
    /** 
     * 
     * This function closes the Stream in use. 
     * 
     */
    public static void CloseStream()
    {
        if (null != in)
        {
            try
            {
                in.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
                LOG.error(e);
            }
        }
        if (null != out)
        {
            try
            {
                out.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
                LOG.error(e);
            }
        }
    }
}
