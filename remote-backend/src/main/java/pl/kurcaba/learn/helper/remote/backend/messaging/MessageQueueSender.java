package pl.kurcaba.learn.helper.remote.backend.messaging;

import pl.kurcaba.learn.helper.common.model.LearnSet;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.*;

@Stateless
public class MessageQueueSender
{

    @Resource(mappedName = "java:jboss/DefaultJMSConnectionFactory")
    private TopicConnectionFactory connectionFactory;

    @Resource(mappedName = "java:/jms/queue/MyQueue")
    private Queue myQueue;

    public void sendMessage(LearnSet aSavedLearnSet)
    {
        try
        {
            Connection connection = connectionFactory.createTopicConnection();
            connection.start();
            Session statusTopicSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer publisher = statusTopicSession.createProducer(myQueue);
            MapMessage message = statusTopicSession.createMapMessage();
            message.setObject("savedSet", "wwww");
            publisher.send(message);
        } catch (JMSException e)
        {
            e.printStackTrace();
        }
    }

}
