package com.example.demo.service;

import ch.qos.logback.classic.gaffer.PropertyUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.serial.SerialAddress;
import org.apache.mina.transport.serial.SerialConnector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ConnectService {
    private static Log log = LogFactory.getLog(ConnectService.class);

//    @Value("#{serial.port}")
//    public String PORT;

    public void sendCommand(String command) {
        if (!StringUtils.isEmpty(command)) {
            IoBuffer buffer = IoBuffer.wrap(command.getBytes());
            IoSession session = null;
            try {
                //创建串口连接
                SerialConnector connector = new SerialConnector();
                //绑定处理handler
                connector.setHandler(new IoHandlerAdapter());
                //设置过滤器
                connector.getFilterChain().addLast("logger", new LoggingFilter());
                //配置串口连接
                SerialAddress address = new SerialAddress("COM8", 115200,
                        SerialAddress.DataBits.DATABITS_8,
                        SerialAddress.StopBits.BITS_1 ,
                        SerialAddress.Parity.NONE,
                        SerialAddress.FlowControl.NONE);
                ConnectFuture future = connector.connect(address);
                future.await();
                session = future.getSession();
                session.write(buffer);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null) {
                    session.close(true);
                }
            }
        }
    }
}
