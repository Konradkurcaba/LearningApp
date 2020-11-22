package pl.kurcaba.learn.helper.remote.backend.messaging;

import pl.kurcaba.learn.helper.common.model.LearnSet;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(activationConfig = {@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "topic/StatusMessageTopic"),
        @ActivationConfigProperty(propertyName = "destinationName", propertyValue = "StatusMessageTopic")
        , @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class MessageTopicReceiver implements MessageListener
{

    @Override
    public void onMessage(Message message)
    {
        try
        {
            LearnSet savedSet = (LearnSet) message.getObjectProperty("savedSet");
            System.out.println("The following set has been saved " + savedSet.getLearnSetName());
        } catch (JMSException e)
        {
            e.printStackTrace();
        }
    }
}
