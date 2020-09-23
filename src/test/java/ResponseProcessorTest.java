import cliente.messageVisitors.ResponseProcessor;
import messages.responses.AvailableContactsPayload;
import messages.responses.RegistrationAttemptPayload;
import messages.responses.Response;
import models.Contact;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ResponseProcessorTest {
    @Test @Ignore
    public void registrationAttemptResponseTest() throws InterruptedException {
        RegistrationAttemptPayload payload = new RegistrationAttemptPayload();
        final String username = "some random guy";
        payload.setUsername(username);
        Response response = new Response();
        response.setOperationType("5");
        response.setPayload(payload);

        BlockingQueue<Response> incomingResponses = new LinkedBlockingQueue<>();
        ResponseProcessor processor = new ResponseProcessor(incomingResponses);
        Thread processorThread = new Thread(processor);
        processorThread.start();

        incomingResponses.add(response);

        processor.stop();
        processorThread.join();

//        Processing response: Response{operationType='5', payload=RegistrationAttemptPayload{username='some random guy'}}
//        In ResponseProcessor
//        response=Response{operationType='5', payload=RegistrationAttemptPayload{username='some random guy'}}
//        payload=RegistrationAttemptPayload{username='some random guy'}
//        Done.
    }

    @Test @Ignore
    public void availableContactsResponseTest() throws InterruptedException {
        AvailableContactsPayload payload1 = new AvailableContactsPayload();
        List<Contact> availableContacts1 = new ArrayList<>();
        availableContacts1.add(new Contact("usernameA"));
        availableContacts1.add(new Contact("usernameB"));
        payload1.setAvailableContacts(availableContacts1);
        Response response1 = new Response();
        response1.setOperationType("1");
        response1.setPayload(payload1);

        AvailableContactsPayload payload2 = new AvailableContactsPayload();
        List<Contact> availableContacts2 = new ArrayList<>();
        availableContacts2.add(new Contact("usernameX"));
        availableContacts2.add(new Contact("usernameY"));
        payload2.setAvailableContacts(availableContacts2);
        Response response2 = new Response();
        response2.setOperationType("1");
        response2.setPayload(payload2);

        BlockingQueue<Response> incomingResponses = new LinkedBlockingQueue<>();
        ResponseProcessor processor = new ResponseProcessor(incomingResponses);
        Thread processorThread = new Thread(processor);
        processorThread.start();

        incomingResponses.add(response1);
        Thread.sleep(Duration.ofSeconds(2).toMillis());
        incomingResponses.add(response2);

        processor.stop();
        processorThread.join();

//        Processing response: Response{operationType='1', payload=AvailableContactsPayload{availableContacts=[Contact{username='usernameA'}, Contact{username='usernameB'}]}}
//        In ResponseProcessor
//        response=Response{operationType='1', payload=AvailableContactsPayload{availableContacts=[Contact{username='usernameA'}, Contact{username='usernameB'}]}}
//        payload=AvailableContactsPayload{availableContacts=[Contact{username='usernameA'}, Contact{username='usernameB'}]}
//        Done.
//                Processing response: Response{operationType='1', payload=AvailableContactsPayload{availableContacts=[Contact{username='usernameX'}, Contact{username='usernameY'}]}}
//        In ResponseProcessor
//        response=Response{operationType='1', payload=AvailableContactsPayload{availableContacts=[Contact{username='usernameX'}, Contact{username='usernameY'}]}}
//        payload=AvailableContactsPayload{availableContacts=[Contact{username='usernameX'}, Contact{username='usernameY'}]}
//        Done.
    }
}
