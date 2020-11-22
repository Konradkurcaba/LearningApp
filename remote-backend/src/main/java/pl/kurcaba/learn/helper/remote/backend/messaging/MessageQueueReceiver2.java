package pl.kurcaba.learn.helper.remote.backend.messaging;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(activationConfig = {@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/queue/MyQueue"), @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"), @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class MessageQueueReceiver2 implements MessageListener
{

    @Override
    public void onMessage(Message message)
    {
        System.out.println("Message from a queue received");
    }
}

