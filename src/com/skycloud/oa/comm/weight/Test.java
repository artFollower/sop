package com.skycloud.oa.comm.weight;

import java.util.Enumeration;

import javax.comm.CommPortIdentifier;

public class Test
{
    public static void main(String[] args)
    {
        //listPortChoices();
        Put put = new Put();
        put.startWeight("COM5");
        String weight = put.writeContinue();
        System.out.println("result:"+weight);
        put.endWeight();
    }
    
    @SuppressWarnings("rawtypes")
	public static void listPortChoices()
    {
        CommPortIdentifier portId;
        Enumeration en = CommPortIdentifier.getPortIdentifiers();
        System.out.println("en:"+en);
        while (en.hasMoreElements())
        {
            portId = (CommPortIdentifier)en.nextElement();
            
            System.out.println(portId.getName());
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL)
            {
                System.out.println("=="+portId.getName());
            }
        }
    }
}
