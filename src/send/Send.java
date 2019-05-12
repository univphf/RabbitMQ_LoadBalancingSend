package send;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tondeur-h
 */
public class Send {

    final private String QUEUE_NAME="HELLO";
    String message=null;

    public Send() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");

            Connection conn=factory.newConnection();

            Channel channel=conn.createChannel();

            AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();

            builder.deliveryMode(MessageProperties.PERSISTENT_TEXT_PLAIN.getDeliveryMode());
            builder.priority(MessageProperties.PERSISTENT_TEXT_PLAIN.getPriority());
            builder.contentType("application/json");
            builder.appId("HTSender");

            AMQP.BasicProperties props=builder.build();

            channel.queueDeclare(QUEUE_NAME, false, false, false,null);

            for (int i=0;i<100;i++)
            {
             message="Message N° : "+ i;
            channel.basicPublish("",QUEUE_NAME, props,message.getBytes("UTF-8"));
                System.out.println("Envoie du "+message);
            }

        } catch (IOException | TimeoutException ex) {
            Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, ex);
        }



    }




    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Send();
    }

}
