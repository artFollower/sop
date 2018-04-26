package com.skycloud.oa.comm.weight;

import org.apache.log4j.Logger;

import com.skycloud.oa.config.Global;

/**
 * <一句话功能简述>串口读取数据任务<br/>
 * <功能详细描述>如果一旦发现任务不在运行或者运行失败，则立即重启任务，再重新读取当前数据
 * 
 * @author  Administrator
 * @version  [版本号, 2015-3-4]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CommJobTimer
{
    private static Logger LOG = Logger.getLogger(CommJobTimer.class);
    
    private static boolean isRunning = false;
    
    public void run()
    {
        LOG.debug("start CommJobTimer job...");
        if (!isRunning)
        {
            isRunning = true;
            try
            {
                exec();
            }
            catch (Exception e)
            {
                LOG.debug("exec CommJobTimer job fail", e);
            }
            finally
            {
                isRunning = false;// 执行完毕
                LOG.debug("end CommJobTimer job");
            }
        }
        else
        {
            LOG.debug("the pre CommJobTimer job is running, exit this job.");
        }
        
    }
    
    private void exec()
    {
        // 如果串口线程不在运行，自动重启读取线程
        Put put = new Put();
        if (SerialBean.openSignal_Success != SerialBean.openSignal)
        {
            LOG.warn("当前comm线程未运行,重启comm读取线程");
     
            if (null == Global.cloudConfig.get("comm.port"))
            {
                LOG.warn("当前comm线程未运行,重启comm读取线程,Global.cloudConfig未初始化");
                return;
            }
            
            String commPort = Global.cloudConfig.get("comm.port").toString();
            int openSignal = put.startWeight(commPort);
            if (SerialBean.openSignal_Success != openSignal)
            {
                LOG.debug("串口读取线程启动失败openSignal=[" + openSignal + "]");
            }
            else
            {
                LOG.debug("串口读取线程启动成功");
            }
        }
        
        LOG.warn("重新读取comm数据");
        if (SerialBean.openSignal_Success != SerialBean.openSignal)
        {
            LOG.warn("comm读取线程未运行，无法重新读取数据");
        }
        else
        {
            put.reRead();
        }
    }
}
